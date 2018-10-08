package com.example.arivista.graphlibrary


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.arivista.graphlibrary.custom.GraphCustomView
import com.example.arivista.graphlibrary.models.GraphModel
import com.example.arivista.graphlibrary.models.XElement
import java.util.*


class GraphActivity : AppCompatActivity() {
    internal lateinit var graphModel: ArrayList<GraphModel>
    internal lateinit var xElementList: ArrayList<XElement>
//    internal lateinit var legends: ArrayList<Legend>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        graphModel = ArrayList()
        xElementList = ArrayList()
        xElementList = ArrayList()
        xElementList.add(XElement("#00A19A", "x1", -25f))
        xElementList.add(XElement("#29235C", "x2", -15f))
        xElementList.add(XElement("#662483", "x3", -13f))
        xElementList.add(XElement("#A3195B", "x4", -24f))
        xElementList.add(XElement("#A3195B", "x5", -24f))
        xElementList.add(XElement("#A3195B", "x6", -24f))
        xElementList.add(XElement("#A3195B", "x7", -24f))
        xElementList.add(XElement("#A3195B", "x8", -24f))
        xElementList.add(XElement("#A3195B", "x9", -24f))
        xElementList.add(XElement("#A3195B", "x10",-24f))
        xElementList.add(XElement("#A3195B", "x11",-24f))
//        legends.add(Legend("#000", "X1"))
        graphModel.add(GraphModel(0, 11, 2, 0, 1, xElementList, "right", xElementList.size, "y"))
        val customView1 = findViewById<GraphCustomView>(R.id.find)
        customView1.setMain(graphModel)
   }

}
