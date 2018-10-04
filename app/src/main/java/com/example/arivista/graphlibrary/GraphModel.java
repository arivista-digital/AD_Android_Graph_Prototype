package com.example.arivista.graphlibrary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karthee on 11/04/18.
 */
public class GraphModel {

    private Integer direction;

    private Integer yCount;

    private Integer yDifference;
    private Integer yStartValue;
    private String image;
    private Integer barCount;
    private ArrayList<XElement> xElements = null;
    private String alignment;
    private Integer xCount;
    private GSize gSize;
    private String yLegend;
    private ArrayList<Legend> legends = null;


    public GraphModel(Integer direction, Integer yCount, Integer yDifference, Integer yStartValue, String image, Integer barCount, ArrayList<XElement> xElements, String alignment, Integer xCount, GSize gSize, String yLegend, ArrayList<Legend> legends) {
        this.direction = direction;
        this.yCount = yCount;
        this.yDifference = yDifference;
        this.yStartValue = yStartValue;
        this.image = image;
        this.barCount = barCount;
        this.xElements = xElements;
        this.alignment = alignment;
        this.xCount = xCount;
        this.gSize = gSize;
        this.yLegend = yLegend;
        this.legends = legends;
    }

    public ArrayList<Legend> getLegends() {
        return legends;
    }

    public void setLegends(ArrayList<Legend> legends) {
        this.legends = legends;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getYCount() {
        return yCount;
    }

    public void setYCount(Integer yCount) {
        this.yCount = yCount;
    }

    public Integer getYDifference() {
        return yDifference;
    }

    public void setYDifference(Integer yDifference) {
        this.yDifference = yDifference;
    }

    public Integer getYStartValue() {
        return yStartValue;
    }

    public void setYStartValue(Integer yStartValue) {
        this.yStartValue = yStartValue;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getBarCount() {
        return barCount;
    }

    public void setBarCount(Integer barCount) {
        this.barCount = barCount;
    }

    public ArrayList<XElement> getXElements() {
        return xElements;
    }

    public void setXElements(ArrayList<XElement> xElements) {
        this.xElements = xElements;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public Integer getXCount() {
        return xCount;
    }

    public void setXCount(Integer xCount) {
        this.xCount = xCount;
    }

    public GSize getGSize() {
        return gSize;
    }

    public void setGSize(GSize gSize) {
        this.gSize = gSize;
    }

    public String getYLegend() {
        return yLegend;
    }

    public void setYLegend(String yLegend) {
        this.yLegend = yLegend;
    }
}
