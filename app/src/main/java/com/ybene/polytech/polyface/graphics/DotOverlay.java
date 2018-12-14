package com.ybene.polytech.polyface.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DotOverlay extends GraphicOverlay.Graphic {

    private int DOT_COLOR;
    private float DOT_SIZE, x, y;
    private Paint dotPaint;

    private GraphicOverlay graphicOverlay;

    public DotOverlay (GraphicOverlay graphicOverlay, float x, float y, int color, float size) {

        super(graphicOverlay);
        this.graphicOverlay = graphicOverlay;
        this.x = x;
        this.y = y;
        this.DOT_COLOR = color;
        this.DOT_SIZE = size;

        dotPaint = new Paint();
        dotPaint.setColor(DOT_COLOR);
        dotPaint.setStrokeWidth(DOT_SIZE);

        postInvalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, 4, dotPaint);
    }
}