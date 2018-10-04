package com.example.arivista.graphlibrary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class XElement implements Serializable {

    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("xValue")
    @Expose
    private String xValue;
    @SerializedName("actualValueInPercent")
    @Expose
    private Float actualValueInPercent;

    private final static long serialVersionUID = -3264190176493283974L;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getXValue() {
        return xValue;
    }

    public void setXValue(String xValue) {
        this.xValue = xValue;
    }

    public Float getActualValueInPercent() {
        return actualValueInPercent;
    }

    public void setActualValueInPercent(Float actualValueInPercent) {
        this.actualValueInPercent = actualValueInPercent;
    }

}