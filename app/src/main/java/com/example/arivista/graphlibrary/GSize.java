package com.example.arivista.graphlibrary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GSize implements Serializable
{

@SerializedName("height")
@Expose
private Integer height;
@SerializedName("width")
@Expose
private Double width;
private final static long serialVersionUID = 398895610178998812L;

public Integer getHeight() {
return height;
}

public void setHeight(Integer height) {
this.height = height;
}

public Double getWidth() {
return width;
}

public void setWidth(Double width) {
this.width = width;
}

}