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
        xElementList.add(XElement("#00A19A", "x1", 12f))
        xElementList.add(XElement("#29235C", "x2", 1f))
        xElementList.add(XElement("#662483", "x3", 3f))
        xElementList.add(XElement("#A3195B", "x4", 6f))
        xElementList.add(XElement("#A3195B", "x5", 11f))
        xElementList.add(XElement("#00A19A", "x6", 8f))
        xElementList.add(XElement("#29235C", "x7", 4f))
        xElementList.add(XElement("#662483", "x8", 2f))
        xElementList.add(XElement("#A3195B", "x9", 5f))
        xElementList.add(XElement("#A3195B", "x10", 10f))
//        legends.add(Legend("#000", "X1"))
        graphModel.add(GraphModel(0, 10, 2, 0, 1, xElementList, "right", xElementList.size, "y", 140, 60))
        val customView1 = findViewById<GraphCustomView>(R.id.arivista_custom_view)
        customView1.setMain(graphModel)
    }

}
