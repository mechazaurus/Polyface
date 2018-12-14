package com.ybene.polytech.polyface;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.ybene.polytech.polyface.graphics.DotDotOverlay;
import com.ybene.polytech.polyface.graphics.DotOverlay;
import com.ybene.polytech.polyface.graphics.GraphicOverlay;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Facing;
import com.otaliastudios.cameraview.Frame;
import com.otaliastudios.cameraview.FrameProcessor;
import com.otaliastudios.cameraview.Size;

import java.util.List;

public class RealtimeActivity extends AppCompatActivity {

    private CameraView cameraView;
    private GraphicOverlay graphicOverlay;
    private ImageButton swapImageButton;
    private static Boolean isFacingFront;


    private FirebaseVisionFaceDetectorOptions options;
    private FirebaseVisionFaceDetector detector;

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);

        // Init
        cameraView = findViewById(R.id.activity_realtime_camera_view);
        graphicOverlay = findViewById(R.id.activity_realtime_graphic_overlay);
        swapImageButton = findViewById(R.id.activity_realtime_camera_swap);

        // Setting front camera
        cameraView.setFacing(Facing.FRONT);
        isFacingFront = true;

        // Swap button setup
        swapImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFacingFront == true) {
                    cameraView.setFacing(Facing.BACK);
                    isFacingFront = false;
                } else {
                    cameraView.setFacing(Facing.FRONT);
                    isFacingFront = true;
                }
            }
        });

        // Detection options
        options = new FirebaseVisionFaceDetectorOptions.Builder()
                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.NO_CLASSIFICATIONS)
                .build();

        // Creating face detector
        detector = FirebaseVision.getInstance().getVisionFaceDetector(options);

        // Frame processing to draw points
        cameraView.addFrameProcessor(new FrameProcessor() {
            @Override
            @WorkerThread
            public void process(Frame frame) {
                graphicOverlay.clear();

                byte[] data = frame.getData();
                int rotation = frame.getRotation();
                Size size = frame.getSize();

                int frameRotation = rotation / 90;

                FirebaseVisionImageMetadata metadata = new FirebaseVisionImageMetadata.Builder()
                        .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                        .setWidth(size.getWidth())
                        .setRotation(frameRotation)
                        .setHeight(size.getHeight())
                        .build();

                FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromByteArray(data, metadata);

                Log.i("SIZE", "Height " + firebaseVisionImage.getBitmapForDebugging().getHeight() + " Width " + firebaseVisionImage.getBitmapForDebugging().getWidth());

                Bitmap bitmapdebug = firebaseVisionImage.getBitmapForDebugging();
                detector.detectInImage(firebaseVisionImage)
                        .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>()
                        {
                            @Override
                            public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces)
                            {
                                if(firebaseVisionFaces.size()!=0)
                                {
                                    //Toast.makeText(MainActivity.this, "Face detected", Toast.LENGTH_SHORT).show();

                                    //RectOverlay rect = new RectOverlay(graphicOverlay,firebaseVisionFaces.get(0).getBoundingBox() );
                                    FirebaseVisionFaceContour contour = firebaseVisionFaces.get(0).getContour(FirebaseVisionFaceContour.ALL_POINTS);
                                    DotDotOverlay dot = new DotDotOverlay(graphicOverlay, contour.getPoints());
                                    //graphicOverlay.add(rect);
                                    graphicOverlay.add(dot);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(RealtimeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
