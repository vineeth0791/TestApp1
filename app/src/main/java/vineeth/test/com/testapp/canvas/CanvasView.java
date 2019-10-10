package vineeth.test.com.testapp.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View implements View.OnTouchListener
{
    private float x;
    private float y;

    Canvas canvas;
    Paint paint;

    public CanvasView(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
    }

    public void onDraw(Canvas canvas)
    {
        ///canvas.drawColor(Color.parseColor("#FF0000"));

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawPaint(paint);

        // draw green circle with anti aliasing turned on
        paint.setAntiAlias(false);
        paint.setColor(Color.GREEN);
        canvas.drawCircle(x, y, 30, paint);

        this.canvas= canvas;
        this.paint=paint;

        Log.d("Canvas","Inside onDraw method");

    }

    public boolean onTouch(View view, MotionEvent me)
    {
        x=me.getX();
        y=me.getY();

        invalidate();
        return true;
    }
}