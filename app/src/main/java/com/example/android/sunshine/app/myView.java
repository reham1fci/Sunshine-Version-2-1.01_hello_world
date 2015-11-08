package com.example.android.sunshine.app;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by thy on 21/09/2015.
 */
public class myView extends View {

    public myView(Context context) {
        super(context);
    }

    public myView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public myView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
       // event.getText().add(windSpeedDir);
        return true;
    }
}
