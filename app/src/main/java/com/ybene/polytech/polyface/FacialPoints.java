package com.ybene.polytech.polyface;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.vision.face.Contour;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour;

import java.util.List;

public class FacialPoints
{
    private Point eyebrow1;
    private Point eyebrow2;
    private Point eyebrow3;
    private Point eyebrow4;
    private Point mouth1;
    private Point mouth2;
    private Point mouth3;
    private Point mouth4;
    private Point midPoint;

    private Point extra;

    public FacialPoints(FirebaseVisionFace face)
    {
        eyebrow1 = new Point(face.getContour(Contour.LEFT_EYEBROW_TOP).getPoints().get(0).getX(), face.getContour(Contour.LEFT_EYEBROW_TOP).getPoints().get(0).getY());
        eyebrow2 = new Point(face.getContour(Contour.RIGHT_EYEBROW_TOP).getPoints().get(0).getX() ,face.getContour(Contour.RIGHT_EYEBROW_TOP).getPoints().get(0).getY());
        eyebrow3 = new Point(face.getContour(Contour.LEFT_EYEBROW_BOTTOM).getPoints().get(4).getX() , face.getContour(Contour.LEFT_EYEBROW_BOTTOM).getPoints().get(4).getY());
        eyebrow4 = new Point(face.getContour(Contour.RIGHT_EYEBROW_BOTTOM).getPoints().get(4).getX() , face.getContour(Contour.RIGHT_EYEBROW_BOTTOM).getPoints().get(4).getY());
        mouth1 = new Point(face.getContour(Contour.UPPER_LIP_TOP).getPoints().get(0).getX() , face.getContour(Contour.UPPER_LIP_TOP).getPoints().get(0).getY() );
        mouth2 = new Point(face.getContour(Contour.UPPER_LIP_TOP).getPoints().get(10).getX(), face.getContour(Contour.UPPER_LIP_TOP).getPoints().get(10).getY() );
        mouth3 = new Point(face.getContour(Contour.UPPER_LIP_TOP).getPoints().get(5).getX(), face.getContour(Contour.UPPER_LIP_TOP).getPoints().get(5).getY() );
        mouth4 = new Point(face.getContour(Contour.LOWER_LIP_BOTTOM).getPoints().get(4).getX(),face.getContour(Contour.LOWER_LIP_BOTTOM).getPoints().get(4).getY() );
        midPoint = new Point(face.getContour(Contour.NOSE_BRIDGE).getPoints().get(1).getX() ,face.getContour(Contour.NOSE_BRIDGE).getPoints().get(1).getY());
        extra = new Point(face.getContour(Contour.LOWER_LIP_TOP).getPoints().get(4).getX() ,face.getContour(Contour.LOWER_LIP_TOP).getPoints().get(4).getY());
    }

    public Point getExtra() {
        return extra;
    }

    public void setExtra(Point extra) {
        this.extra = extra;
    }

    public Point getMidPoint() {
        return midPoint;
    }

    public void setMidPoint(Point midPoint) {
        this.midPoint = midPoint;
    }

    public Point getEyebrow1() {
        return eyebrow1;
    }

    public void setEyebrow1(Point eyebrow1) {
        this.eyebrow1 = eyebrow1;
    }

    public Point getEyebrow2() {
        return eyebrow2;
    }

    public void setEyebrow2(Point eyebrow2) {
        this.eyebrow2 = eyebrow2;
    }

    public Point getEyebrow3() {
        return eyebrow3;
    }

    public void setEyebrow3(Point eyebrow3) {
        this.eyebrow3 = eyebrow3;
    }

    public Point getEyebrow4() {
        return eyebrow4;
    }

    public void setEyebrow4(Point eyebrow4) {
        this.eyebrow4 = eyebrow4;
    }

    public Point getMouth1() {
        return mouth1;
    }

    public void setMouth1(Point mouth1) {
        this.mouth1 = mouth1;
    }

    public Point getMouth2() {
        return mouth2;
    }

    public void setMouth2(Point mouth2) {
        this.mouth2 = mouth2;
    }

    public Point getMouth3() {
        return mouth3;
    }

    public void setMouth3(Point mouth3) {
        this.mouth3 = mouth3;
    }

    public Point getMouth4() {
        return mouth4;
    }

    public void setMouth4(Point mouth4) {
        this.mouth4 = mouth4;
    }
}
