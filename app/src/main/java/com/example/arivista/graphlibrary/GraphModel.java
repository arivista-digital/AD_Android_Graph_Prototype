package com.example.arivista.graphlibrary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Karthee on 11/04/18.
 */
public class GraphModel implements Serializable {
    @SerializedName("direction")
    @Expose
    private Integer direction;
    @SerializedName("yCount")
    @Expose
    private Integer yCount;
    @SerializedName("yDifference")
    @Expose
    private Integer yDifference;
    @SerializedName("yStartValue")
    @Expose
    private Integer yStartValue;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("barCount")
    @Expose
    private Integer barCount;
    @SerializedName("xElements")
    @Expose
    private List<List<XElement>> xElements = null;
    @SerializedName("alignment")
    @Expose
    private String alignment;
    @SerializedName("xCount")
    @Expose
    private Integer xCount;
    @SerializedName("gSize")
    @Expose
    private GSize gSize;
    @SerializedName("yLegend")
    @Expose
    private String yLegend;

    @SerializedName("legends")
    @Expose
    private List<Legend> legends = null;

    private final static long serialVersionUID = 8460401987412335131L;

    public List<Legend> getLegends() {
        return legends;
    }

    public void setLegends(List<Legend> legends) {
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

    public List<List<XElement>> getXElements() {
        return xElements;
    }

    public void setXElements(List<List<XElement>> xElements) {
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
