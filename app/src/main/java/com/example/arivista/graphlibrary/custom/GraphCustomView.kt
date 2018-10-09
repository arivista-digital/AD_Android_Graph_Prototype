package com.example.arivista.graphlibrary.custom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.*
import com.example.arivista.graphlibrary.R
import com.example.arivista.graphlibrary.models.GraphModel
import java.util.ArrayList

class GraphCustomView : FrameLayout, View.OnTouchListener {
    lateinit var constraintLayout: AbsoluteLayout

    lateinit var frameLayout: FrameLayout

    lateinit var ylegend: TextView

    private var scaleHeight: Double = 0.toDouble()
    private var scaleWidth: Double = 0.toDouble()

    lateinit var reveal: Button
    lateinit var reset: Button


    private var barheight: Int = 0

    lateinit var legendLayout: LinearLayout

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        View.inflate(context, R.layout.cusview, this)
        frameLayout = findViewById(R.id.maincontainer)
        constraintLayout = findViewById(R.id.container)
        ylegend = findViewById(R.id.ylegend)
        legendLayout = findViewById(R.id.legendlayout)
        reset = findViewById(R.id.btnReset)
        reveal = findViewById(R.id.btnReveal)
    }

    fun setMain(graphModel: ArrayList<GraphModel>) {
        val d = resources.displayMetrics.density


        if (graphModel[0].direction == 0) {
            val view = View.inflate(context, R.layout.graphline, null)
            val param = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    (2 * d).toInt())
            view.layoutParams = param

            //bottom view added
            param.setMargins(0, 0, 0, (32 * d).toInt())
            param.gravity = Gravity.BOTTOM

            frameLayout!!.addView(view)
        }
        //up to down bar
        else {
            val view = LayoutInflater.from(context).inflate(R.layout.graphline, null, false)
            val param = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    (2 * d).toInt())
            param.setMargins(0, (32 * d).toInt(), 0, 0)
            param.gravity = Gravity.TOP

            view.layoutParams = param
            frameLayout!!.addView(view)
        }
//        ylegend!!.text = "2"

        val observer = constraintLayout!!.viewTreeObserver
        observer.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // TODO Auto-generated method stub
                val headerLayoutWidth = constraintLayout!!.width
                val headerLayoutHeight = constraintLayout!!.height / graphModel[0].yCount!!
                val LayoutHeight = constraintLayout!!.height
                barheight = constraintLayout!!.height




                constraintLayout!!.viewTreeObserver.removeGlobalOnLayoutListener(this)



                var y: Int

                if (graphModel[0].direction == 0) {
                    y = 0

                } else {
                    y = (-graphModel[0].yCount!!)!! * graphModel[0].yDifference!!

                }

                for (i in graphModel[0].yCount!! downTo 0) {
                    val view = LayoutInflater.from(context).inflate(R.layout.graphlevel, null, false)
                    val yvalue = view.findViewById<TextView>(R.id.xvalue)

                    yvalue.text = y.toString()

                    if (i == graphModel[0].yCount)
                        y = y
                    else {
                        y = y + graphModel[0].yDifference!!
                    }
                    //y text set


                    //y bar value
                    val param = AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            20, headerLayoutHeight * i )
                    view.layoutParams = param as ViewGroup.LayoutParams?
                    if (y != 0)
                        constraintLayout!!.addView(view)
                }
                val LayoutWidth = constraintLayout!!.width / graphModel[0].xCount!!
                for (i in 0 until graphModel[0].xCount!!) {
                    if (graphModel[0].direction == 0) {
                        val bar = LayoutInflater.from(context).inflate(R.layout.bar_layout, null, false)
                        bar.tag = "bar$i"
                        val customView = bar.findViewById<BoxedVertical>(R.id.boxed_vertical)
                        customView.max = graphModel[0].yCount!! * graphModel[0].yDifference!!
                        customView.step = 1
                        try {
                            customView.setProgressColor(Color.parseColor(graphModel[0].xElements!![i].color))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        //bar value
                        val params = AbsoluteLayout.LayoutParams(graphModel[0].barWidth!!,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                graphModel[0].xDifference!! * i +140, 0)
                        bar.layoutParams = params
                        constraintLayout!!.addView(bar)
                        if (graphModel[0].barCount == 2) {
                            val bar2 = LayoutInflater.from(context).inflate(R.layout.bar_layout, null, false)
                            bar2.tag = "bar2$i"
                            val customView2 = bar2.findViewById<BoxedVertical>(R.id.boxed_vertical)

                            customView2.max = graphModel[0].yCount!! * graphModel[0].yDifference!!

                            customView2.step = 1
                            try {

                                customView2.setProgressColor(Color.parseColor(graphModel[0].xElements!![i].color))
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            val params2 = AbsoluteLayout.LayoutParams(120,
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    LayoutWidth * i + 20, 0)
                            bar2.layoutParams = params2
                            constraintLayout!!.addView(bar2)
                        }
                    } else {
                        val bar = LayoutInflater.from(context).inflate(R.layout.bar_layout, null, false)
                        bar.tag = "bar$i"
                        val customView = bar.findViewById<BoxedVertical>(R.id.boxed_vertical)

                        customView.max = graphModel[0].yCount!! * graphModel[0].yDifference!!

                        customView.step = 1
                        try {


                            customView.setinverseProgressColor(Color.parseColor(graphModel[0].xElements!![i].color))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        val params = AbsoluteLayout.LayoutParams(120,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                LayoutWidth * i + 200, 0)
                        bar.layoutParams = params

                        constraintLayout!!.addView(bar)
                    }
                }

                var x = 0

                for (i in 0 until graphModel[0].xCount!!) {
                    val view: View

                    if (graphModel[0].direction == 0) {
                        view = LayoutInflater.from(context).inflate(R.layout.xlevel, null, false)
                    } else {
                        view = LayoutInflater.from(context).inflate(R.layout.ixlevel, null, false)

                    }

                    val xvalue = view.findViewById<TextView>(R.id.xvalue)

                    if (i == 0)
                        x = 1
                    else
                        x = x + 1

                    //set x name
                    xvalue.text = graphModel[0].xElements!![i].xValue

                    if (graphModel[0].direction == 0) {
                        //x bar value
                        val param = AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                graphModel[0].xDifference!! * i +150, LayoutHeight - 40)
                        view.layoutParams = param
                        constraintLayout!!.addView(view)
                    } else {
                        val param = AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                LayoutWidth * i + 230, 0)
                        view.layoutParams = param
                        constraintLayout!!.addView(view)
                    }
                }
                setGraphtozero(graphModel)

            }
        })
        reset!!.setOnClickListener { setGraphtozero(graphModel) }
        reveal!!.setOnClickListener {
            revealAnswer(graphModel)
        }

    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val x = event!!.x.toInt()
        val y = event.y.toInt()
        val width = v!!.layoutParams.width
        val height = v.layoutParams.height


        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.layoutParams.height = y
                v.requestLayout()
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
                v.layoutParams.height = y
                v.requestLayout()
            }
        }
        return false
    }

    //Reveal answers
    private fun revealAnswer(graphModel:ArrayList<GraphModel>){
        for (i in 0 until constraintLayout!!.childCount) {
            for (j in 0 until graphModel[0].xCount!!) {
                val child = constraintLayout!!.getChildAt(i)
                try {
                    if (child.tag == "bar$j") {

                        val customView = child.findViewById<BoxedVertical>(R.id.boxed_vertical)
                        val points = graphModel[0].yCount!! * graphModel[0].yDifference!!
                        val actualValueInPercent = graphModel[0].xElements!![j].actualValueInPercent!!

                        val v1 = actualValueInPercent / points.toFloat()
                        val percent = v1 * 100 * (barheight / 100)

                        if (graphModel[0].direction == 0)
                            customView.value = percent.toInt()
                        else
                            customView.value = 100 - percent.toInt()

                        val value = customView.value
                    }
                    if (child.tag == "bar2$j") {

                        val customView = child.findViewById<BoxedVertical>(R.id.boxed_vertical)
                        val points = graphModel[0].yCount!! * graphModel[0].yDifference!!
                        val actualValueInPercent = graphModel[0].xElements!![j].actualValueInPercent!!

                        val v1 = actualValueInPercent / points.toFloat()
                        val percent = v1 * 100 * (barheight / 100)

                        if (graphModel[0].direction == 0)
                            customView.value = percent.toInt()
                        else
                            customView.value = 100 - percent.toInt()

                        val value = customView.value
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }


    private fun setGraphtozero(graphModel: ArrayList<GraphModel>) {
        for (i in 0 until constraintLayout!!.childCount) {
            for (j in 0 until graphModel[0].xCount!!) {
                val child = constraintLayout!!.getChildAt(i)
                try {
                    if (child.tag == "bar$j") {

                        val customView = child.findViewById<BoxedVertical>(R.id.boxed_vertical)
                        if (graphModel[0].direction == 0)
                            customView.value = barheight
                        else
                            customView.value = 0


                    }
                    if (child.tag == "bar2$j") {

                        val customView = child.findViewById<BoxedVertical>(R.id.boxed_vertical)


                        if (graphModel[0].direction == 0)
                            customView.value = barheight
                        else
                            customView.value = 0

                        val value = customView.value
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }
}