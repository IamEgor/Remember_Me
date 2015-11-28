package yegor_gruk.example.com.rememberme.Views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Egor on 26.11.2015.
 */
public class MyImageView extends ImageView {

    BorderDrawable borderDrawable;

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        borderDrawable.draw(canvas);
        super.dispatchDraw(canvas);
    }

    class BorderDrawable extends Drawable {

        private Rect mBounds;
        private Paint mBorderPaint;

        private int thickness = 3;

        public BorderDrawable(Rect bounds, int thickness, int color) {
            mBounds = bounds;
            mBorderPaint = new Paint();
            mBorderPaint.setStrokeWidth(thickness);
            mBorderPaint.setColor(color);
        }

        @Override
        public void draw(Canvas canvas) {
            //left border
            canvas.drawLine(mBounds.left - thickness / 2, mBounds.top, mBounds.left - thickness / 2, mBounds.bottom, mBorderPaint);
            //top border
            canvas.drawLine(mBounds.left, mBounds.top - thickness / 2, mBounds.right, mBounds.top - thickness / 2, mBorderPaint);
            //right border
            canvas.drawLine(mBounds.right + thickness / 2, mBounds.top, mBounds.right + thickness / 2, mBounds.bottom, mBorderPaint);
            //bottom border
            canvas.drawLine(mBounds.left, mBounds.bottom + thickness / 2, mBounds.right, mBounds.bottom + thickness / 2, mBorderPaint);
        }

        @Override
        public void setAlpha(int alpha) {

        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return 0;
        }

    }
}
