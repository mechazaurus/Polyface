package com.ybene.polytech.polyface;

public class Point
{
    private double x;
    private double y;

    public Point(double X, double Y)
    {
        this.x = X;
        this.y = Y;
    }

    public double compareUpSideY(Point ref)
    {
        return (ref.getY()-y);
    }
    public double compareDownSideY(Point ref)
    {
        return (y - ref.getY());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString()
    {
        String toReturn="";
        toReturn = "X:"+x+" Y:"+y;
        return toReturn;
    }
}
