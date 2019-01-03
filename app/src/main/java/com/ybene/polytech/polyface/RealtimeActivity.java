package com.ybene.polytech.polyface;

import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.ybene.polytech.polyface.graphics.DotDotOverlay;
import com.ybene.polytech.polyface.graphics.DotDotOverlayRef;
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
import com.ybene.polytech.polyface.graphics.LineOverlay;
import com.ybene.polytech.polyface.graphics.RectOverlay;

import java.util.List;
import java.util.Map;

public class RealtimeActivity extends AppCompatActivity {

    private CameraView cameraView;
    private GraphicOverlay graphicOverlay;
    private ImageButton swapImageButton;
    private static Boolean isFacingFront;


    private FirebaseVisionFaceDetectorOptions options;
    private FirebaseVisionFaceDetector detector;

    private Computation computation;
    private ImageButton referenceButton;

    private int frameCounter;

    private FirebaseVisionFaceContour contourRef;
    private DotDotOverlayRef dotRef;
    private TextView emotionTextView;
    private TextView counterView;



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

        //EmotionTextView
        emotionTextView = (TextView) findViewById(R.id.activity_realtime_emotionTextView);
        counterView = (TextView) findViewById(R.id.activity_realtime_counter);
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


        //Computation
        computation = new Computation(RealtimeActivity.this);
        frameCounter = 0;
        referenceButton = (ImageButton) findViewById(R.id.activity_realtime_reference);
        counterView.setText(String.valueOf(frameCounter));



        // Frame processing to draw points
        cameraView.addFrameProcessor(new FrameProcessor() {
            @Override
            @WorkerThread
            public void process(final Frame frame) {
                graphicOverlay.clear();
                frameCounter++;
                counterView.setText(String.valueOf(frameCounter));
                Log.i("FRAMENB", String.valueOf(frameCounter));

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

                //Log.i("SIZE", "Height " + firebaseVisionImage.getBitmapForDebugging().getHeight() + " Width " + firebaseVisionImage.getBitmapForDebugging().getWidth());
                int height = firebaseVisionImage.getBitmapForDebugging().getHeight() ;
                int width = firebaseVisionImage.getBitmapForDebugging().getWidth();


                Bitmap bitmapdebug = firebaseVisionImage.getBitmapForDebugging();
                detector.detectInImage(firebaseVisionImage)
                        .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>()
                        {
                            @Override
                            public void onSuccess(final List<FirebaseVisionFace> firebaseVisionFaces)
                            {
                                if(firebaseVisionFaces.size()!=0)
                                {
                                    //Listener Reference Button
                                    referenceButton.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            //TODO set reference points
                                            if(firebaseVisionFaces.size()!=0)
                                            {
                                                computation.setReference(new FacialPoints(firebaseVisionFaces.get(0)));
                                                Toast.makeText(RealtimeActivity.this, "FaceContour referenced", Toast.LENGTH_SHORT).show();
                                                contourRef = firebaseVisionFaces.get(0).getContour(FirebaseVisionFaceContour.ALL_POINTS);
                                                dotRef = new DotDotOverlayRef(graphicOverlay, contourRef.getPoints());
                                            }
                                        }
                                    });

                                    //If counter==5 -> compute

                                    if(frameCounter>15 && computation.getReference()!=null && firebaseVisionFaces.size()!=0)
                                    {
                                        emotionTextView.clearComposingText();
                                        frameCounter = 0;
                                        computation.setActual(new FacialPoints(firebaseVisionFaces.get(0)));
                                        List<Map.Entry<String, Double>> detectedEmotions = computation.compute();
                                        //TODO affichage des emotions
                                        String emotions = "";
                                        for(int i=0; i<detectedEmotions.size(); i++)
                                        {
                                            emotions = emotions + detectedEmotions.get(i).getKey() + ": " + detectedEmotions.get(i).getValue();
                                        }
                                        emotionTextView.setText(emotions);
                                    }

                                    FirebaseVisionFaceContour contour = firebaseVisionFaces.get(0).getContour(FirebaseVisionFaceContour.ALL_POINTS);
                                    DotDotOverlay dot = new DotDotOverlay(graphicOverlay, contour.getPoints());
                                    if(dotRef!=null)
                                    {
                                        graphicOverlay.add(dotRef);
                                    }
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
