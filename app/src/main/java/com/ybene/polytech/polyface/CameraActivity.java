package com.ybene.polytech.polyface;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;
import com.ybene.polytech.polyface.graphics.DotOverlay;
import com.ybene.polytech.polyface.graphics.GraphicOverlay;
import com.ybene.polytech.polyface.graphics.RectOverlay;

import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    private CameraView cameraView;
    private GraphicOverlay graphicOverlay;
    private ImageButton detectImageButton, swapImageButton;
    private ArrayList<FirebaseVisionPoint> leftEyebrowExtremePoints;
    private ArrayList<FirebaseVisionPoint> rightEyebrowExtremePoints;
    private ArrayList<FirebaseVisionPoint> mouthWidthExtremePoints;
    private ArrayList<FirebaseVisionPoint> mouthHeightExtremePoints;

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
        graphicOverlay.clear();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
        graphicOverlay.clear();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Init
        cameraView = findViewById(R.id.activity_camera_camera_view);
        graphicOverlay = findViewById(R.id.activity_camera_graphic_overlay);
        detectImageButton = findViewById(R.id.activity_camera_detect_button);
        swapImageButton = findViewById(R.id.activity_camera_camera_swap);

        leftEyebrowExtremePoints = new ArrayList<>();
        rightEyebrowExtremePoints = new ArrayList<>();
        mouthHeightExtremePoints = new ArrayList<>();
        mouthWidthExtremePoints = new ArrayList<>();


        // Setting front camera
        cameraView.setFacing(CameraKit.Constants.FACING_FRONT);

        // Event
        detectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.start();
                cameraView.captureImage();
                graphicOverlay.clear();
            }
        });

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, cameraView.getWidth(), cameraView.getHeight(), false);
                cameraView.stop();

                Toast.makeText(getApplication().getBaseContext(), "Analyzing...", Toast.LENGTH_LONG).show();
                runFaceDetector(bitmap);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        swapImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cameraView.getFacing() == CameraKit.Constants.FACING_FRONT) {
                    cameraView.setFacing(CameraKit.Constants.FACING_BACK);
                } else {
                    cameraView.setFacing(CameraKit.Constants.FACING_FRONT);
                }
            }
        });
    }

    private void runFaceDetector(Bitmap bitmap) {

        // Create an image from the taken picture
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        // FaceDetector setup
        FirebaseVisionFaceDetectorOptions options = new FirebaseVisionFaceDetectorOptions.Builder()
                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.NO_CLASSIFICATIONS)
                .build();

        // FaceDetector creation
        FirebaseVisionFaceDetector faceDetector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options);

        // Run face detection
        faceDetector.detectInImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                        processFaceResult(firebaseVisionFaces);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    private void processFaceResult(List<FirebaseVisionFace> firebaseVisionFaces) {

        for (FirebaseVisionFace face : firebaseVisionFaces) {

            Rect bounds = face.getBoundingBox();
            RectOverlay rect = new RectOverlay(graphicOverlay, bounds);
            graphicOverlay.add(rect);

            // Get all contours
            FirebaseVisionFaceContour faceContour = face.getContour(FirebaseVisionFaceContour.FACE);

            FirebaseVisionFaceContour rightEyeContour = face.getContour(FirebaseVisionFaceContour.RIGHT_EYE);
            FirebaseVisionFaceContour leftEyeContour = face.getContour(FirebaseVisionFaceContour.LEFT_EYE);

            FirebaseVisionFaceContour rightEyebrowBottomContour = face.getContour(FirebaseVisionFaceContour.RIGHT_EYEBROW_BOTTOM);
            FirebaseVisionFaceContour rightEyebrowTopContour = face.getContour(FirebaseVisionFaceContour.RIGHT_EYEBROW_TOP);
            FirebaseVisionFaceContour leftEyebrowBottomContour = face.getContour(FirebaseVisionFaceContour.LEFT_EYEBROW_BOTTOM);
            FirebaseVisionFaceContour leftEyebrowTopContour = face.getContour(FirebaseVisionFaceContour.LEFT_EYEBROW_TOP);

            FirebaseVisionFaceContour lowerLipBottomContour = face.getContour(FirebaseVisionFaceContour.LOWER_LIP_BOTTOM);
            FirebaseVisionFaceContour lowerLipTopContour = face.getContour(FirebaseVisionFaceContour.LOWER_LIP_TOP);
            FirebaseVisionFaceContour upperLipBottomContour = face.getContour(FirebaseVisionFaceContour.UPPER_LIP_BOTTOM);
            FirebaseVisionFaceContour upperLipTopContour = face.getContour(FirebaseVisionFaceContour.UPPER_LIP_TOP);

            FirebaseVisionFaceContour noseBottomContour = face.getContour(FirebaseVisionFaceContour.NOSE_BOTTOM);
            FirebaseVisionFaceContour noseBridgeContour = face.getContour(FirebaseVisionFaceContour.NOSE_BRIDGE);

            /*// Drawing points for the face
            for (FirebaseVisionPoint point : faceContour.getPoints()) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.BLUE, 2.0f);
                graphicOverlay.add(dot);
            }

            // Drawing points for eyes
            for (FirebaseVisionPoint point : rightEyeContour.getPoints()) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.RED, 2.0f);
                graphicOverlay.add(dot);
            }

            for (FirebaseVisionPoint point : leftEyeContour.getPoints()) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.RED, 2.0f);
                graphicOverlay.add(dot);
            }

            // Draw points for eyebrows
            for (FirebaseVisionPoint point : rightEyebrowBottomContour.getPoints()) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.GREEN, 2.0f);
                graphicOverlay.add(dot);
            }

            for (FirebaseVisionPoint point : rightEyebrowTopContour.getPoints()) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.GREEN, 2.0f);
                graphicOverlay.add(dot);
            }

            for (FirebaseVisionPoint point : leftEyebrowBottomContour.getPoints()) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.GREEN, 2.0f);
                graphicOverlay.add(dot);
            }

            for (FirebaseVisionPoint point : leftEyebrowTopContour.getPoints()) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.GREEN, 2.0f);
                graphicOverlay.add(dot);
            }

            // Draw points for lips
            for (FirebaseVisionPoint point : lowerLipBottomContour.getPoints()) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.MAGENTA, 2.0f);
                graphicOverlay.add(dot);
            }

            for (FirebaseVisionPoint point : lowerLipTopContour.getPoints()) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.MAGENTA, 2.0f);
                graphicOverlay.add(dot);
            }

            for (FirebaseVisionPoint point : upperLipBottomContour.getPoints()) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.MAGENTA, 2.0f);
                graphicOverlay.add(dot);
            }

            for (FirebaseVisionPoint point : upperLipTopContour.getPoints()) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.MAGENTA, 2.0f);
                graphicOverlay.add(dot);
            }

            // Draw points for nose
            for (FirebaseVisionPoint point : noseBottomContour.getPoints()) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.LTGRAY, 2.0f);
                graphicOverlay.add(dot);
            }

            for (FirebaseVisionPoint point : noseBridgeContour.getPoints()) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.LTGRAY, 2.0f);
                graphicOverlay.add(dot);
            }*/

            // Reference point
            DotOverlay referencePoint = new DotOverlay(graphicOverlay,
                    noseBridgeContour.getPoints().get(0).getX(),
                    (noseBridgeContour.getPoints().get(0).getY() + noseBridgeContour.getPoints().get(1).getY())/2,
                    Color.RED,
                    4.0f);
            graphicOverlay.add(referencePoint);

            leftEyebrowExtremePoints.add(leftEyebrowTopContour.getPoints().get(0));
            leftEyebrowExtremePoints.add(leftEyebrowTopContour.getPoints().get(4));
            rightEyebrowExtremePoints.add(rightEyebrowTopContour.getPoints().get(0));
            rightEyebrowExtremePoints.add(rightEyebrowTopContour.getPoints().get(4));
            mouthWidthExtremePoints.add(upperLipTopContour.getPoints().get(0));
            mouthWidthExtremePoints.add(upperLipTopContour.getPoints().get(10));
            mouthHeightExtremePoints.add(upperLipTopContour.getPoints().get(5));
            mouthHeightExtremePoints.add(lowerLipBottomContour.getPoints().get(4));

            for (FirebaseVisionPoint point : leftEyebrowExtremePoints) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.RED, 4.0f);
                graphicOverlay.add(dot);
            }

            for (FirebaseVisionPoint point : rightEyebrowExtremePoints) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.RED, 4.0f);
                graphicOverlay.add(dot);
            }

            for (FirebaseVisionPoint point : mouthWidthExtremePoints) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.RED, 4.0f);
                graphicOverlay.add(dot);
            }

            for (FirebaseVisionPoint point : mouthHeightExtremePoints) {
                DotOverlay dot = new DotOverlay(graphicOverlay, point.getX(), point.getY(), Color.RED, 4.0f);
                graphicOverlay.add(dot);
            }



            Toast.makeText(getBaseContext(), "Done !", Toast.LENGTH_SHORT).show();
        }
    }

    private void processEmotionRecognition () {

    }
}
