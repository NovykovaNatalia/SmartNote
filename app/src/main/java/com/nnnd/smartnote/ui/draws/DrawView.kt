package com.nnnd.smartnote.ui.draws

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import java.util.*

class DrawView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {
    private var mX = 0f
    private var mY = 0f
    lateinit var mPath: Path
    lateinit private var mPaint: Paint
    private val paths = ArrayList<FingerPathItem>()
    private var currentColor = 0
    private var color = DEFAULT_BG_COLOR
    private var strokeWidth = 0
    lateinit var mBitmap: Bitmap
    private var mCanvas: Canvas? = null
    private val mBitmapPaint = Paint(Paint.DITHER_FLAG)

    init {
    }

    fun prepearePaint() {
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.color = DEFAULT_COLOR
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.xfermode = null
        mPaint.alpha = 0xff
    }

    fun setColor(color: Int) {
        currentColor = color
    }

    fun getBGColor(white: Int): Int {
        return DEFAULT_BG_COLOR
    }

    fun setBrushSize(size: Int) {
        strokeWidth = size
    }
    fun init(metrics: DisplayMetrics, paint: Paint) {
        if(paint != null) {
            mPaint = paint
        }
        prepearePaint();
        val height = metrics.heightPixels
        val width = metrics.widthPixels
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
        currentColor = DEFAULT_COLOR
        strokeWidth = BRUSH_SIZE
    }

    fun normal() {
        resetDefaultParams()
    }

    fun clear() {
        color = DEFAULT_BG_COLOR
        paths.clear()
        normal()
        resetDefaultParams()
        invalidate()
    }

    fun resetDefaultParams() {
        strokeWidth = 20
        currentColor = Color.BLACK
    }
    override fun onDraw(canvas: Canvas) {
        canvas.save()
        mCanvas!!.drawColor(color)
        for (fp in paths) {
            mPaint.color = fp.color
            mPaint.strokeWidth = fp.strokeWidth.toFloat()
            mPaint.maskFilter = null
            mCanvas!!.drawPath(fp.path, mPaint)
        }
        canvas.drawBitmap(mBitmap, 0f, 0f, mBitmapPaint)
        canvas.restore()
    }

    private fun touchStart(x: Float, y: Float) {
        mPath = Path()
        val fp = FingerPathItem(currentColor, strokeWidth, mPath)
        paths.add(fp)
        mPath.reset()
        mPath.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touchUp() {
        mPath.lineTo(mX, mY)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }
        return true
    }

    fun save() {
        //TODO: create our item, copy all paths to this item, and save this item in array dataStoreHandler
//        var paintItem = PaintItem()
//        paintItem.paint = mPaint
//        DataStoreHandler.draws.add(paintItem)
        //TODO: or it will save mPaints.
    }

    companion object {
        var BRUSH_SIZE = 20
        const val DEFAULT_COLOR = Color.BLACK
        const val DEFAULT_BG_COLOR = Color.WHITE
        private const val TOUCH_TOLERANCE = 4f
    }
}