package com.example.arivista.graphlibrary.models

import java.util.ArrayList

/**
 * Created by Karthee on 11/04/18.
 */
class GraphModel(var direction: Int?, var yCount: Int?, var yDifference: Int?, var yStartValue: Int?, var image: String?, var barCount: Int?, xElements: ArrayList<XElement>, var alignment: String?, var xCount: Int?, var gSize: GSize?, var yLegend: String?, legends: ArrayList<Legend>) {
    var xElements: ArrayList<XElement>? = null
    var legends: ArrayList<Legend>? = null


    init {
        this.xElements = xElements
        this.legends = legends
    }
}
