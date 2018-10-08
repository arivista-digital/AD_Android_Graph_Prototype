package com.example.arivista.graphlibrary.models

import java.util.ArrayList

/**
 * Created by Karthee on 11/04/18.
 */
class GraphModel(var direction: Int?, var yCount: Int?, var yDifference: Int?, var yStartValue: Int?, var barCount: Int?, xElements: ArrayList<XElement>, var alignment: String?, var xCount: Int?,  var yLegend: String?) {
    var xElements: ArrayList<XElement>? = null


    init {
        this.xElements = xElements
    }
}
