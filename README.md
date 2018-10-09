### Bar Graph ###

* Language - Android Kotlin
* IDE - Android Studio 3.1

### What is this repository for? ###

* This is Customized bar graph
* Bar set by touch and drag event

### Use Customized Component ###

* Bar Graph
* Layout

### Solutions ###

* Touch and Drag set by bar value
* Bar count is customized
* Reveal bar value auto fill color.
* Bar color is customized
* Clear bar values
* Reveal bar values

### How to use ###

 * activity_main Layout initialize
 
```
 <com.example.arivista.graphlibrary.custom.GraphCustomView
        android:layout_width="match_parent"
        android:id="@+id/find"
        android:layout_height="match_parent">
    </com.example.arivista.graphlibrary.custom.GraphCustomView>
```
* MainActivity Funtionalites
* Add the questions
### Graph bar Setting ###

* Color
* X Text
* Fill value

```
        var xElementList: ArrayList<XElement> 
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
```
### Graph Setting ###

```
       var graphModel: ArrayList<GraphModel>
       graphModel.add(GraphModel(0, 10, 2, 0, 1, xElementList, "right", xElementList.size, "y", 140, 60))

```

* Custom view object creation
```
      val customView1 = findViewById<GraphCustomView>(R.id.arivista_custom_view)
      customView1.setMain(graphModel)
```


### Screenshots ###

 ![Alt text](/app/screenshots/mcq.gif)

### Links ###
* [Arivista Digital Pvt Ltd](https://www.arivistadigital.org/ "Arivista")

### License ###

* Copyright 2018 The Android Open Source Project, Inc.
* Arivista Digital Pvt Ltd
