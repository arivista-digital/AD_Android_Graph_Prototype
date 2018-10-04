package com.example.arivista.graphlibrary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class XElement {

    private String color;
    private String xValue;
    private Float actualValueInPercent;


    public XElement(String color, String xValue, Float actualValueInPercent) {
        this.color = color;
        this.xValue = xValue;
        this.actualValueInPercent = actualValueInPercent;
    }

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