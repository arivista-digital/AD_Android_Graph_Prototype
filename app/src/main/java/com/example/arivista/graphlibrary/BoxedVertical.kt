package com.example.arivista.graphlibrary


import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.google.gson.Gson
import java.util.ArrayList
import android.view.MotionEvent




class BoxedVertical : View {


    /**
     * The min value of progress value.
     */
    private val mMin = MIN

    /**
     * The Maximum value that this SeekArc can be set to
     */
    private var mMax = MAX

    /**
     * The increment/decrement value for each movement of progress.
     */
    var step = 10

    /**
     * The corner radius of the view.
     */
    private var mCornerRadius = 0

    /**
     * Text size in SP.
     */
    private var mTextSize = 26f

    /**
     * Text bottom padding in pixel.
     */
    private var mtextBottomPadding = 20

    private var mPoints: Int = 0

    private var mEnabled = true
    /**
     * Enable or disable text .
     */
    private var mtextEnabled = true

    /**
     * Enable or disable image .
     */
    var isImageEnabled = false

    /**
     * mTouchDisabled touches will not move the slider
     * only swipe motion will activate it
     */
    private var mTouchDisabled = true

    private var mProgressSweep = 0f
    private var mProgressPaint: Paint? = null
    private var mTextPaint: Paint? = null
    private var scrWidth: Int = 0
    var scrHeight: Int = 0
    private var mOnValuesChangeListener: OnValuesChangeListener? = null
    private var backgroundColor: Int ?= 0
    private var mDefaultValue: Int = 0
    private var mDefaultImage: Bitmap? = null
    private var mMinImage: Bitmap? = null
    private var mMaxImage: Bitmap? = null
    private val dRect = Rect()
    private var firstRun = true
    private var progressColor: Int = 0
    private var inverseProgressColor = 0
//    @BindView(R.id.container)
    lateinit var constraintLayout: AbsoluteLayout

//    @BindView(R.id.maincontainer)
    lateinit var frameLayout: FrameLayout

//    @BindView(R.id.ylegend)
    lateinit var ylegend: TextView

    private val gson: Gson? = null
    private var scaleHeight: Double = 0.toDouble()
    private var scaleWidth: Double = 0.toDouble()
    private val customView: View? = null

//    @BindView(R.id.btnReveal)
    lateinit var reveal: Button
//
//    @BindView(R.id.btnReset)
    lateinit var reset: Button


    private var barheight: Int = 0

//    @BindView(R.id.legendlayout)
    lateinit var legendLayout: LinearLayout

//    @BindView(R.id.close)
//    lateinit var close: ImageView
    //        points = points > mMax ? mMax : points;
    //        points = points < mMin ? mMin : points;
    //        points = mMax - points;
    //double r = ((double)scrHeight / mMax) * points;
    var value: Int
        get() = mPoints
        set(points) {
            Log.e("bar1", "setValue: $points")
            updateProgress(points)

            invalidate()

        }

    var max: Int
        get() = mMax
        set(mMax) {
            if (mMax <= mMin)
                throw IllegalArgumentException("Max should not be less than zero")
            this.mMax = mMax
        }

    var cornerRadius: Int
        get() = mCornerRadius
        set(mRadius) {
            this.mCornerRadius = mRadius
            invalidate()
        }

    var defaultValue: Int
        get() = mDefaultValue
        set(mDefaultValue) {
            if (mDefaultValue > mMax)
                throw IllegalArgumentException("Default value should not be bigger than max value.")
            this.mDefaultValue = mDefaultValue

        }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        println("INIT")


//        ButterKnife.bind(context,view1)
        val density = resources.displayMetrics.density

        // Defaults, may need to link this into theme settings
        backgroundColor = ContextCompat.getColor(context, android.R.color.transparent)
        Log.e("BoxedVertical", "init: $inverseProgressColor")
        if (inverseProgressColor != 0) {
            backgroundColor = inverseProgressColor
            progressColor = ContextCompat.getColor(context, android.R.color.transparent)

            //            backgroundColor = ContextCompat.getColor(context, android.R.color.holo_blue_light);
        } else {
            backgroundColor = ContextCompat.getColor(context, android.R.color.transparent)

        }
        var textColor = ContextCompat.getColor(context, android.R.color.transparent)
        mTextSize = (mTextSize * density).toInt().toFloat()
        mDefaultValue = mMax / 2

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs,
                    R.styleable.BoxedVertical, 0, 0)

            mPoints = a.getInteger(R.styleable.BoxedVertical_points, mPoints)
            mMax = a.getInteger(R.styleable.BoxedVertical_max, mMax)
            mMax = a.getInteger(R.styleable.BoxedVertical_max, mMax)
            step = a.getInteger(R.styleable.BoxedVertical_step, step)
            mDefaultValue = a.getInteger(R.styleable.BoxedVertical_defaultValue, mDefaultValue)
            mCornerRadius = a.getInteger(R.styleable.BoxedVertical_cornerRadius, mCornerRadius)
            mtextBottomPadding = a.getInteger(R.styleable.BoxedVertical_textBottomPadding, mtextBottomPadding)
            //Images
            isImageEnabled = a.getBoolean(R.styleable.BoxedVertical_imageEnabled, isImageEnabled)

            if (isImageEnabled) {
                //                Assert.assertNotNull("When images are enabled, defaultImage can not be null. Please assign a drawable in the layout XML file", a.getDrawable(R.styleable.BoxedVertical_defaultImage));
                //                Assert.assertNotNull("When images are enabled, minImage can not be null. Please assign a drawable in the layout XML file", a.getDrawable(R.styleable.BoxedVertical_minImage));
                //                Assert.assertNotNull("When images are enabled, maxImage can not be null. Please assign a drawable in the layout XML file", a.getDrawable(R.styleable.BoxedVertical_maxImage));

                try {
                    mDefaultImage = (a.getDrawable(R.styleable.BoxedVertical_defaultImage) as BitmapDrawable).bitmap
                    mMinImage = (a.getDrawable(R.styleable.BoxedVertical_minImage) as BitmapDrawable).bitmap
                    mMaxImage = (a.getDrawable(R.styleable.BoxedVertical_maxImage) as BitmapDrawable).bitmap
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            if (inverseProgressColor != 0) {
                progressColor = ContextCompat.getColor(context, android.R.color.transparent)
            } else {
                progressColor = a.getColor(R.styleable.BoxedVertical_progressColor, progressColor)

            }
            progressColor = a.getColor(R.styleable.BoxedVertical_progressColor, progressColor)
            backgroundColor = a.getColor(R.styleable.BoxedVertical_backgroundColor, backgroundColor!!)

            mTextSize = a.getDimension(R.styleable.BoxedVertical_textSize, mTextSize).toInt().toFloat()
            textColor = a.getColor(R.styleable.BoxedVertical_textColor, textColor)

            mEnabled = a.getBoolean(R.styleable.BoxedVertical_enabled, mEnabled)
            mTouchDisabled = a.getBoolean(R.styleable.BoxedVertical_touchDisabled, mTouchDisabled)
            mtextEnabled = a.getBoolean(R.styleable.BoxedVertical_textEnabled, mtextEnabled)

            mPoints = mDefaultValue

            a.recycle()
        }

        // range check
        mPoints = if (mPoints > mMax) mMax else mPoints
        mPoints = if (mPoints < mMin) mMin else mPoints

        mProgressPaint = Paint()
        mProgressPaint!!.color = progressColor
        mProgressPaint!!.isAntiAlias = true
        mProgressPaint!!.style = Paint.Style.STROKE

        mTextPaint = Paint()
        mTextPaint!!.color = textColor
        mTextPaint!!.isAntiAlias = true
        mTextPaint!!.style = Paint.Style.FILL
        mTextPaint!!.textSize = mTextSize

        scrHeight = context.resources.displayMetrics.heightPixels

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        scrWidth = View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        scrHeight = View.getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        mProgressPaint!!.strokeWidth = scrWidth.toFloat()

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        val paint = Paint()

        paint.alpha = 255
        canvas.translate(0f, 0f)
        val mPath = Path()
        mPath.addRoundRect(RectF(0f, 0f, scrWidth.toFloat(), scrHeight.toFloat()), mCornerRadius.toFloat(), mCornerRadius.toFloat(), Path.Direction.CCW)
        canvas.clipPath(mPath, Region.Op.INTERSECT)
        paint.color = this!!.backgroundColor!!
        paint.isAntiAlias = true
        canvas.drawRect(0f, 0f, scrWidth.toFloat(), scrHeight.toFloat(), paint)
        Log.e("box", (canvas.width / 2).toString() + "onDraw: drawline" + canvas.height)

        canvas.drawLine((canvas.width / 2).toFloat(), canvas.height.toFloat(), (canvas.width / 2).toFloat(), mProgressSweep, mProgressPaint!!)

        if (isImageEnabled && mDefaultImage != null && mMinImage != null && mMaxImage != null) {
            //If image is enabled, text will not be shown
            if (mPoints == mMax) {
                drawIcon(mMaxImage!!, canvas)
            } else if (mPoints == mMin) {
                drawIcon(mMinImage!!, canvas)
            } else {
                drawIcon(mDefaultImage!!, canvas)
            }
        } else {
            //If image is disabled and text is enabled show text
            if (mtextEnabled) {
                //                String strPoint = String.valueOf(mPoints);
                //                drawText(canvas, mTextPaint, strPoint);
            }
        }

        if (firstRun) {
            firstRun = false
            //  setValue(mPoints);
        }
    }

    private fun drawText(canvas: Canvas, paint: Paint, text: String) {
        canvas.getClipBounds(dRect)
        val cWidth = dRect.width()
        paint.textAlign = Paint.Align.LEFT
        paint.getTextBounds(text, 0, text.length, dRect)
        val x = cWidth / 2f - dRect.width() / 2f - dRect.left.toFloat()
        canvas.drawText(text, x, (canvas.height - mtextBottomPadding).toFloat(), paint)
    }

    private fun drawIcon(bitmap: Bitmap, canvas: Canvas) {
        var bitmap = bitmap
        bitmap = getResizedBitmap(bitmap, canvas.width / 2, canvas.width / 2)
        canvas.drawBitmap(bitmap, null, RectF((canvas.width / 2 - bitmap.width / 2).toFloat(), (canvas.height - bitmap.height).toFloat(), (canvas.width / 3 + bitmap.width).toFloat(), canvas.height.toFloat()), null)
    }

    private fun getResizedBitmap(bm: Bitmap, newHeight: Int, newWidth: Int): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // create a matrix for the manipulation
        val matrix = Matrix()
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight)
        // recreate the new Bitmap
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mEnabled) {

            this.parent.requestDisallowInterceptTouchEvent(true)

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (mOnValuesChangeListener != null)
                        mOnValuesChangeListener!!.onStartTrackingTouch(this)

                    if (!mTouchDisabled)
                        updateOnTouch(event)
                }
                MotionEvent.ACTION_MOVE -> updateOnTouch(event)
                MotionEvent.ACTION_UP -> {
                    if (mOnValuesChangeListener != null)
                        mOnValuesChangeListener!!.onStopTrackingTouch(this)
                    isPressed = false
                    this.parent.requestDisallowInterceptTouchEvent(false)
                }
                MotionEvent.ACTION_CANCEL -> {
                    if (mOnValuesChangeListener != null)
                        mOnValuesChangeListener!!.onStopTrackingTouch(this)
                    isPressed = false
                    this.parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            return true
        }
        return false
    }

    /**
     * Update the UI components on touch events.
     *
     * @param event MotionEvent
     */
    private fun updateOnTouch(event: MotionEvent) {
        isPressed = true
        val mTouch = convertTouchEventPoint(event.y)
        val progress = Math.round(mTouch).toInt()
        updateProgress(progress)
    }

    private fun convertTouchEventPoint(yPos: Float): Double {
        val wReturn: Float

        if (yPos > scrHeight * 2) {
            wReturn = (scrHeight * 2).toFloat()
            return wReturn.toDouble()
        } else if (yPos < 0) {
            wReturn = 0f
        } else {
            wReturn = yPos
        }

        return wReturn.toDouble()
    }

    private fun updateProgress(progress: Int) {
        mProgressSweep = progress.toFloat()
        mPoints = progress

        mPoints = if (mPoints > scrHeight) scrHeight else mPoints
        mPoints = if (mPoints < 0) 0 else mPoints
        mPoints = (scrHeight - mPoints) * mMax / scrHeight
        mPoints = mPoints - mPoints % step
        if (mOnValuesChangeListener != null) {

            mOnValuesChangeListener!!
                    .onPointsChanged(this, mPoints)
        }

        invalidate()
    }

    fun setinverseProgressColor(inverseProgressColor: Int) {
        this.inverseProgressColor = inverseProgressColor
        init(context, null)
        invalidate()
    }

    interface OnValuesChangeListener {
        /**
         * Notification that the point value has changed.
         *
         * @param boxedPoints The SwagPoints view whose value has changed
         * @param points      The current point value.
         */
        fun onPointsChanged(boxedPoints: BoxedVertical, points: Int)

        fun onStartTrackingTouch(boxedPoints: BoxedVertical)

        fun onStopTrackingTouch(boxedPoints: BoxedVertical)
    }

    fun getProgressColor(): Int {
        return progressColor
    }

    fun setProgressColor(progressColor: Int) {
        this.progressColor = progressColor
        init(context, null)
        invalidate()
    }

    override fun isEnabled(): Boolean {
        return mEnabled
    }

    override fun setEnabled(enabled: Boolean) {
        this.mEnabled = enabled
    }

    fun setOnBoxedPointsChangeListener(onValuesChangeListener: OnValuesChangeListener) {
        mOnValuesChangeListener = onValuesChangeListener
    }

    companion object {

        private val MAX = 100
        private val MIN = 0
    }










    }

