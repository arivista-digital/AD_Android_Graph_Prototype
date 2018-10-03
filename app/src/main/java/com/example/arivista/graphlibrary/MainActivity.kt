package com.example.arivista.graphlibrary

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.AbsoluteLayout
import android.widget.FrameLayout
import com.example.arivista.graphlibrary.library.BoxedVertical

class MainActivity : AppCompatActivity(), View.OnTouchListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var constraintLayout = findViewById(R.id.container) as AbsoluteLayout
        var frameLayout = findViewById(R.id.maincontainer) as FrameLayout
//        //Directly add
        val view = LayoutInflater.from(this).inflate(R.layout.bar_layout, null, false)as View
        var customView= view.findViewById(R.id.boxed_vertical) as BoxedVertical

        customView.minimumWidth=40
        customView.minimumHeight=10
        customView.max= 50
        customView.scrHeight=50

        customView.step=1
//                    customView.setValue(barheight);
        try {

            customView.setinverseProgressColor(Color.BLACK)
        } catch (e: Exception) {
            e.printStackTrace()
        }



        frameLayout.addView(view)


    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        val x = event!!.getX().toInt()
        val y = event!!.getY().toInt()
        val width = v!!.getLayoutParams().width
        val height = v!!.getLayoutParams().height

        when (event.getAction()) {
            MotionEvent.ACTION_DOWN -> {
                Log.e(">>", "width:$width height:$height x:$x y:$y")
                v.getLayoutParams().height = y
                v.requestLayout()
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
                Log.e(">>", "width:$width height:$height x:$x y:$y")
                v.getLayoutParams().height = y
                v.requestLayout()
            }
        }
        return false
    }
}
