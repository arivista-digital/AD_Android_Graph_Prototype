package com.example.arivista.graphlibrary


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import com.example.arivista.graphlibrary.custom.GraphCustomView
import com.example.arivista.graphlibrary.models.GSize
import com.example.arivista.graphlibrary.models.GraphModel
import com.example.arivista.graphlibrary.models.Legend
import com.example.arivista.graphlibrary.models.XElement
import java.util.*


class GraphActivity : AppCompatActivity() {



    internal lateinit var graphModel: ArrayList<GraphModel>
    internal lateinit var xElementList: ArrayList<XElement>
    internal lateinit var legends: ArrayList<Legend>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        ButterKnife.bind(this)

        graphModel = ArrayList()
        xElementList = ArrayList()
        xElementList.add(XElement("#00A19A", "x1", 35f))
        xElementList.add(XElement("#29235C", "x2", 15f))
        xElementList.add(XElement("#662483", "x3", -13f))

        legends = ArrayList()
        legends.add(Legend("#000", "X1"))
        graphModel.add(GraphModel(0, 3, 10, 0, "", 1, xElementList, "right", xElementList.size, GSize(388, 704.17422867513608), "y", legends))
        val customView1 = findViewById<GraphCustomView>(R.id.find)

        customView1.setMain(graphModel)

   }

}
