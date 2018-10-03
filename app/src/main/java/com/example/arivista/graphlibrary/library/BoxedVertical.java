package com.example.arivista.graphlibrary.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.arivista.graphlibrary.R;

public class BoxedVertical extends View {

    private static final int MAX = 100;
    private static final int MIN = 0;

    /**
     * The min value of progress value.
     */
    private int mMin = MIN;

    /**
     * The Maximum value that this SeekArc can be set to
     */
    private int mMax = MAX;

    /**
     * The increment/decrement value for each movement of progress.
     */
    private int mStep = 10;

    /**
     * The corner radius of the view.
     */
    private int mCornerRadius = 0;

    /**
     * Text size in SP.
     */
    private float mTextSize = 26;

    /**
     * Text bottom padding in pixel.
     */
    private int mtextBottomPadding = 20;

    private int mPoints;

    private boolean mEnabled = true;
    /**
     * Enable or disable text .
     */
    private boolean mtextEnabled = true;

    /**
     * Enable or disable image .
     */
    private boolean mImageEnabled = false;

    /**
     * mTouchDisabled touches will not move the slider
     * only swipe motion will activate it
     */
    private boolean mTouchDisabled = true;

    private float mProgressSweep = 0;
    private Paint mProgressPaint;
    private Paint mTextPaint;
    private int scrWidth;
    private int scrHeight;
    private OnValuesChangeListener mOnValuesChangeListener;
    private int backgroundColor;
    private int mDefaultValue;
    private Bitmap mDefaultImage;
    private Bitmap mMinImage;
    private Bitmap mMaxImage;
    private Rect dRect = new Rect();
    private boolean firstRun = true;
    private int progressColor;
    private int inverseProgressColor = 0;

    public BoxedVertical(Context context) {
        super(context);
        init(context, null);
    }

    public BoxedVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        System.out.println("INIT");
        float density = getResources().getDisplayMetrics().density;

        // Defaults, may need to link this into theme settings
        backgroundColor = ContextCompat.getColor(context, R.color.cardview_light_background);
        Log.e("BoxedVertical", "init: " + inverseProgressColor);
        if (inverseProgressColor != 0) {
            backgroundColor = inverseProgressColor;
            progressColor = ContextCompat.getColor(context, R.color.cardview_light_background);

//            backgroundColor = ContextCompat.getColor(context, android.R.color.holo_blue_light);
        } else {
            backgroundColor = ContextCompat.getColor(context, R.color.cardview_light_background);

        }
        int textColor = ContextCompat.getColor(context, R.color.cardview_light_background);
        mTextSize = (int) (mTextSize * density);
        mDefaultValue = mMax / 2;

        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.BoxedVertical, 0, 0);

            mPoints = a.getInteger(R.styleable.BoxedVertical_points, mPoints);
            mMax = a.getInteger(R.styleable.BoxedVertical_max, mMax);
            mMax = a.getInteger(R.styleable.BoxedVertical_max, mMax);
            mStep = a.getInteger(R.styleable.BoxedVertical_step, mStep);
            mDefaultValue = a.getInteger(R.styleable.BoxedVertical_defaultValue, mDefaultValue);
            mCornerRadius = a.getInteger(R.styleable.BoxedVertical_cornerRadius, mCornerRadius);
            mtextBottomPadding = a.getInteger(R.styleable.BoxedVertical_textBottomPadding, mtextBottomPadding);
            //Images
            mImageEnabled = a.getBoolean(R.styleable.BoxedVertical_imageEnabled, mImageEnabled);

            if (mImageEnabled) {
//                Assert.assertNotNull("When images are enabled, defaultImage can not be null. Please assign a drawable in the layout XML file", a.getDrawable(R.styleable.BoxedVertical_defaultImage));
//                Assert.assertNotNull("When images are enabled, minImage can not be null. Please assign a drawable in the layout XML file", a.getDrawable(R.styleable.BoxedVertical_minImage));
//                Assert.assertNotNull("When images are enabled, maxImage can not be null. Please assign a drawable in the layout XML file", a.getDrawable(R.styleable.BoxedVertical_maxImage));

                try {
                    mDefaultImage = ((BitmapDrawable) a.getDrawable(R.styleable.BoxedVertical_defaultImage)).getBitmap();
                    mMinImage = ((BitmapDrawable) a.getDrawable(R.styleable.BoxedVertical_minImage)).getBitmap();
                    mMaxImage = ((BitmapDrawable) a.getDrawable(R.styleable.BoxedVertical_maxImage)).getBitmap();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inverseProgressColor != 0) {
                progressColor = ContextCompat.getColor(context, R.color.cardview_light_background);
            } else {
                progressColor = a.getColor(R.styleable.BoxedVertical_progressColor, progressColor);

            }
            progressColor = a.getColor(R.styleable.BoxedVertical_progressColor, progressColor);
            backgroundColor = a.getColor(R.styleable.BoxedVertical_backgroundColor, backgroundColor);

            mTextSize = (int) a.getDimension(R.styleable.BoxedVertical_textSize, mTextSize);
            textColor = a.getColor(R.styleable.BoxedVertical_textColor, textColor);

            mEnabled = a.getBoolean(R.styleable.BoxedVertical_enabled, mEnabled);
            mTouchDisabled = a.getBoolean(R.styleable.BoxedVertical_touchDisabled, mTouchDisabled);
            mtextEnabled = a.getBoolean(R.styleable.BoxedVertical_textEnabled, mtextEnabled);

            mPoints = mDefaultValue;

            a.recycle();
        }

        // range check
        mPoints = (mPoints > mMax) ? mMax : mPoints;
        mPoints = (mPoints < mMin) ? mMin : mPoints;

        mProgressPaint = new Paint();
        mProgressPaint.setColor(progressColor);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setColor(textColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mTextSize);

        scrHeight = context.getResources().getDisplayMetrics().heightPixels;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        scrWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        scrHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        mProgressPaint.setStrokeWidth(scrWidth);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();

        paint.setAlpha(255);
        canvas.translate(0, 0);
        Path mPath = new Path();
        mPath.addRoundRect(new RectF(0, 0, scrWidth, scrHeight), mCornerRadius, mCornerRadius, Path.Direction.CCW);
        canvas.clipPath(mPath, Region.Op.INTERSECT);
        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);
        canvas.drawRect(0, 0, scrWidth, scrHeight, paint);
        Log.e("box", canvas.getWidth() / 2 + "onDraw: drawline" + canvas.getHeight());

        canvas.drawLine(canvas.getWidth() / 2, canvas.getHeight(), canvas.getWidth() / 2, mProgressSweep, mProgressPaint);

        if (mImageEnabled && mDefaultImage != null && mMinImage != null && mMaxImage != null) {
            //If image is enabled, text will not be shown
            if (mPoints == mMax) {
                drawIcon(mMaxImage, canvas);
            } else if (mPoints == mMin) {
                drawIcon(mMinImage, canvas);
            } else {
                drawIcon(mDefaultImage, canvas);
            }
        } else {
            //If image is disabled and text is enabled show text
            if (mtextEnabled) {
//                String strPoint = String.valueOf(mPoints);
//                drawText(canvas, mTextPaint, strPoint);
            }
        }

        if (firstRun) {
            firstRun = false;
            //  setValue(mPoints);
        }
    }

    private void drawText(Canvas canvas, Paint paint, String text) {
        canvas.getClipBounds(dRect);
        int cWidth = dRect.width();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), dRect);
        float x = cWidth / 2f - dRect.width() / 2f - dRect.left;
        canvas.drawText(text, x, canvas.getHeight() - mtextBottomPadding, paint);
    }

    private void drawIcon(Bitmap bitmap, Canvas canvas) {
        bitmap = getResizedBitmap(bitmap, canvas.getWidth() / 2, canvas.getWidth() / 2);
        canvas.drawBitmap(bitmap, null, new RectF((canvas.getWidth() / 2) - (bitmap.getWidth() / 2), canvas.getHeight() - bitmap.getHeight(), (canvas.getWidth() / 3) + bitmap.getWidth(), canvas.getHeight()), null);
    }

    private Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mEnabled) {

            this.getParent().requestDisallowInterceptTouchEvent(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mOnValuesChangeListener != null)
                        mOnValuesChangeListener.onStartTrackingTouch(this);

                    if (!mTouchDisabled)
                        updateOnTouch(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    updateOnTouch(event);
                    break;
                case MotionEvent.ACTION_UP:
                    if (mOnValuesChangeListener != null)
                        mOnValuesChangeListener.onStopTrackingTouch(this);
                    setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    if (mOnValuesChangeListener != null)
                        mOnValuesChangeListener.onStopTrackingTouch(this);
                    setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return true;
        }
        return false;
    }

    /**
     * Update the UI components on touch events.
     *
     * @param event MotionEvent
     */
    private void updateOnTouch(MotionEvent event) {
        setPressed(true);
        double mTouch = convertTouchEventPoint(event.getY());
        int progress = (int) Math.round(mTouch);
        updateProgress(progress);
    }

    private double convertTouchEventPoint(float yPos) {
        float wReturn;

        if (yPos > (scrHeight * 2)) {
            wReturn = scrHeight * 2;
            return wReturn;
        } else if (yPos < 0) {
            wReturn = 0;
        } else {
            wReturn = yPos;
        }

        return wReturn;
    }

    private void updateProgress(int progress) {
        mProgressSweep = progress;
        mPoints = progress;

        mPoints = (mPoints > scrHeight) ? scrHeight : mPoints;
        mPoints = (mPoints < 0) ? 0 : mPoints;
        mPoints = ((scrHeight - mPoints) * mMax) / scrHeight;
        mPoints = mPoints - (mPoints % mStep);
        if (mOnValuesChangeListener != null) {

            mOnValuesChangeListener
                    .onPointsChanged(this, mPoints);
        }

        invalidate();
    }

    public void setinverseProgressColor(int inverseProgressColor) {
        this.inverseProgressColor = inverseProgressColor;
        init(getContext(), null);
        invalidate();
    }

    public interface OnValuesChangeListener {
        /**
         * Notification that the point value has changed.
         *
         * @param boxedPoints The SwagPoints view whose value has changed
         * @param points      The current point value.
         */
        void onPointsChanged(BoxedVertical boxedPoints, int points);

        void onStartTrackingTouch(BoxedVertical boxedPoints);

        void onStopTrackingTouch(BoxedVertical boxedPoints);
    }

    public void setValue(int points) {


//        points = points > mMax ? mMax : points;
//        points = points < mMin ? mMin : points;
//        points = mMax - points;
        //double r = ((double)scrHeight / mMax) * points;
        Log.e("bar1", "setValue: " + points);
        updateProgress(points);

        invalidate();

    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        init(getContext(), null);
        invalidate();
    }

    public int getValue() {
        return mPoints;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    public int getMax() {
        return mMax;
    }

    public void setMax(int mMax) {
        if (mMax <= mMin)
            throw new IllegalArgumentException("Max should not be less than zero");
        this.mMax = mMax;
    }

    public void setCornerRadius(int mRadius) {
        this.mCornerRadius = mRadius;
        invalidate();
    }

    public int getCornerRadius() {
        return mCornerRadius;
    }

    public int getDefaultValue() {
        return mDefaultValue;
    }

    public void setDefaultValue(int mDefaultValue) {
        if (mDefaultValue > mMax)
            throw new IllegalArgumentException("Default value should not be bigger than max value.");
        this.mDefaultValue = mDefaultValue;

    }


    public int getScrHeight() {
        return scrHeight;
    }

    public void setScrHeight(int scrHeight) {
        this.scrHeight = scrHeight;
    }

    public int getStep() {
        return mStep;
    }

    public void setStep(int step) {
        mStep = step;
    }

    public boolean isImageEnabled() {
        return mImageEnabled;
    }

    public void setImageEnabled(boolean mImageEnabled) {
        this.mImageEnabled = mImageEnabled;
    }

    public void setOnBoxedPointsChangeListener(OnValuesChangeListener onValuesChangeListener) {
        mOnValuesChangeListener = onValuesChangeListener;
    }
}
