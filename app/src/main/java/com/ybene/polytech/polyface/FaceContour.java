package com.ybene.polytech.polyface;

import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour;

public class FaceContour {

    private FirebaseVisionFace face;
    private FirebaseVisionFaceContour faceContour;
    private FirebaseVisionFaceContour rightEyeContour, leftEyeContour;
    private FirebaseVisionFaceContour rightEyebrowBottomContour, rightEyebrowTopContour;
    private FirebaseVisionFaceContour leftEyebrowBottomContour, leftEyebrowTopContour;
    private  FirebaseVisionFaceContour lowerLipBottomContour, lowerLipTopContour;
    private FirebaseVisionFaceContour upperLipBottomContour, upperLipTopContour;
    private FirebaseVisionFaceContour noseBottomContour, noseBridgeContour;

    public FaceContour (FirebaseVisionFace face) {
        this.face = face;

        // Face contour
        faceContour = face.getContour(FirebaseVisionFaceContour.FACE);

        // Eyes contour
        rightEyeContour = face.getContour(FirebaseVisionFaceContour.RIGHT_EYE);
        leftEyeContour = face.getContour(FirebaseVisionFaceContour.LEFT_EYE);

        // Eyebrows contour
        rightEyebrowTopContour = face.getContour(FirebaseVisionFaceContour.RIGHT_EYEBROW_TOP);
        rightEyebrowBottomContour = face.getContour(FirebaseVisionFaceContour.RIGHT_EYEBROW_BOTTOM);
        leftEyebrowTopContour = face.getContour(FirebaseVisionFaceContour.LEFT_EYEBROW_TOP);
        leftEyebrowBottomContour = face.getContour(FirebaseVisionFaceContour.LEFT_EYEBROW_BOTTOM);

        // Lips
        lowerLipTopContour = face.getContour(FirebaseVisionFaceContour.LOWER_LIP_TOP);
        lowerLipBottomContour = face.getContour(FirebaseVisionFaceContour.LOWER_LIP_BOTTOM);
        upperLipTopContour = face.getContour(FirebaseVisionFaceContour.UPPER_LIP_TOP);
        upperLipBottomContour = face.getContour(FirebaseVisionFaceContour.UPPER_LIP_BOTTOM);

        // Nose
        noseBridgeContour = face.getContour(FirebaseVisionFaceContour.NOSE_BRIDGE);
        noseBottomContour = face.getContour(FirebaseVisionFaceContour.NOSE_BOTTOM);
    }

    public FirebaseVisionFace getFace() {
        return face;
    }

    public void setFace(FirebaseVisionFace face) {
        this.face = face;
    }

    public FirebaseVisionFaceContour getFaceContour() {
        return faceContour;
    }

    public void setFaceContour(FirebaseVisionFaceContour faceContour) {
        this.faceContour = faceContour;
    }

    public FirebaseVisionFaceContour getRightEyeContour() {
        return rightEyeContour;
    }

    public void setRightEyeContour(FirebaseVisionFaceContour rightEyeContour) {
        this.rightEyeContour = rightEyeContour;
    }

    public FirebaseVisionFaceContour getLeftEyeContour() {
        return leftEyeContour;
    }

    public void setLeftEyeContour(FirebaseVisionFaceContour leftEyeContour) {
        this.leftEyeContour = leftEyeContour;
    }

    public FirebaseVisionFaceContour getRightEyebrowBottomContour() {
        return rightEyebrowBottomContour;
    }

    public void setRightEyebrowBottomContour(FirebaseVisionFaceContour rightEyebrowBottomContour) {
        this.rightEyebrowBottomContour = rightEyebrowBottomContour;
    }

    public FirebaseVisionFaceContour getRightEyebrowTopContour() {
        return rightEyebrowTopContour;
    }

    public void setRightEyebrowTopContour(FirebaseVisionFaceContour rightEyebrowTopContour) {
        this.rightEyebrowTopContour = rightEyebrowTopContour;
    }

    public FirebaseVisionFaceContour getLeftEyebrowBottomContour() {
        return leftEyebrowBottomContour;
    }

    public void setLeftEyebrowBottomContour(FirebaseVisionFaceContour leftEyebrowBottomContour) {
        this.leftEyebrowBottomContour = leftEyebrowBottomContour;
    }

    public FirebaseVisionFaceContour getLeftEyebrowTopContour() {
        return leftEyebrowTopContour;
    }

    public void setLeftEyebrowTopContour(FirebaseVisionFaceContour leftEyebrowTopContour) {
        this.leftEyebrowTopContour = leftEyebrowTopContour;
    }

    public FirebaseVisionFaceContour getLowerLipBottomContour() {
        return lowerLipBottomContour;
    }

    public void setLowerLipBottomContour(FirebaseVisionFaceContour lowerLipBottomContour) {
        this.lowerLipBottomContour = lowerLipBottomContour;
    }

    public FirebaseVisionFaceContour getLowerLipTopContour() {
        return lowerLipTopContour;
    }

    public void setLowerLipTopContour(FirebaseVisionFaceContour lowerLipTopContour) {
        this.lowerLipTopContour = lowerLipTopContour;
    }

    public FirebaseVisionFaceContour getUpperLipBottomContour() {
        return upperLipBottomContour;
    }

    public void setUpperLipBottomContour(FirebaseVisionFaceContour upperLipBottomContour) {
        this.upperLipBottomContour = upperLipBottomContour;
    }

    public FirebaseVisionFaceContour getUpperLipTopContour() {
        return upperLipTopContour;
    }

    public void setUpperLipTopContour(FirebaseVisionFaceContour upperLipTopContour) {
        this.upperLipTopContour = upperLipTopContour;
    }

    public FirebaseVisionFaceContour getNoseBottomContour() {
        return noseBottomContour;
    }

    public void setNoseBottomContour(FirebaseVisionFaceContour noseBottomContour) {
        this.noseBottomContour = noseBottomContour;
    }

    public FirebaseVisionFaceContour getNoseBridgeContour() {
        return noseBridgeContour;
    }

    public void setNoseBridgeContour(FirebaseVisionFaceContour noseBridgeContour) {
        this.noseBridgeContour = noseBridgeContour;
    }
}
