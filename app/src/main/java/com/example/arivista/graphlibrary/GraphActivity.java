package com.example.arivista.graphlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GraphActivity extends AppCompatActivity implements View.OnTouchListener {

    @BindView(R.id.container)
    AbsoluteLayout constraintLayout;

    @BindView(R.id.maincontainer)
    FrameLayout frameLayout;

    @BindView(R.id.ylegend)
    TextView ylegend;

    private Gson gson;
    private double scaleHeight;
    private double scaleWidth;
    private View customView;

    @BindView(R.id.btnReveal)
    Button reveal;

    @BindView(R.id.btnReset)
    Button reset;

    @BindView(R.id.btnSubmit)
    Button submit;
    private int barheight;

    @BindView(R.id.legendlayout)
    LinearLayout legendLayout;

    @BindView(R.id.close)
    ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        ButterKnife.bind(this);
        final String coursejsonlist = loadCoursesJSONFromAsset();


        gson = new Gson();
        final GraphModel graphModel = gson.fromJson(coursejsonlist, GraphModel.class);

        Log.e("realm", "getCourses: " + graphModel.getImage());
        float d = getResources().getDisplayMetrics().density;

        try {
            for (Legend legend : graphModel.getLegends()) {
                View view = LayoutInflater.from(GraphActivity.this).inflate(R.layout.legendlayout, null, false);

                View legendColor = view.findViewById(R.id.legendbar);
                TextView legendName = view.findViewById(R.id.legnedname);
                legendColor.setBackgroundColor(Color.parseColor(legend.getColor()));
                legendName.setText(legend.getName());

                LinearLayout.LayoutParams param =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(param);

                legendLayout.addView(view);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (graphModel.getDirection().equals(0)) {
            View view = LayoutInflater.from(GraphActivity.this).inflate(R.layout.graphline, null, false);
            FrameLayout.LayoutParams param =
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            (int) (2 * d));
            view.setLayoutParams(param);
            param.setMargins(0, 0, 0, (int) (32 * d));
            param.gravity = Gravity.BOTTOM;

            frameLayout.addView(view);
        } else {
            View view = LayoutInflater.from(GraphActivity.this).inflate(R.layout.graphline, null, false);
            FrameLayout.LayoutParams param =
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            (int) (2 * d));
            param.setMargins(0, (int) (32 * d), 0, 0);
            param.gravity = Gravity.TOP;

            view.setLayoutParams(param);
            frameLayout.addView(view);
        }

        ylegend.setText(graphModel.getYLegend());

        final Uri uri = Uri.parse("file:///android_asset/graph/" + graphModel.getImage());
        Log.e("image", String.valueOf(uri));

        ViewTreeObserver observer = constraintLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                int headerLayoutWidth = constraintLayout.getWidth();
                int headerLayoutHeight = constraintLayout.getHeight() / graphModel.getYCount();
                int LayoutHeight = constraintLayout.getHeight();
                barheight = constraintLayout.getHeight() - 64;

                double r, a = graphModel.getGSize().getHeight(), b = graphModel.getGSize().getWidth(), p;

                r = (a / b);

                p = a * b;
                System.out.println("Aspect Ratio = " + r + "");
                System.out.println("\nPixel = " + p + "");

//                int height = (int) ((a + (headerLayoutHeight - a)) * r);
                int height = (int) (headerLayoutWidth * r);
//                int width = (int) (b + (headerLayoutWidth - b) * r);
                int width = (int) headerLayoutWidth;


                constraintLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                scaleHeight = height / a;
                scaleWidth = width / b;

                int y;

                if (graphModel.getDirection().equals(0)) {
                    y = 0;

                } else {
                    y = -graphModel.getYCount() * graphModel.getYDifference();

                }

                for (int i = graphModel.getYCount(); i >= 0; i--) {
                    View view = LayoutInflater.from(GraphActivity.this).inflate(R.layout.graphlevel, null, false);

//                    customView.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
//                    customView.setOnTouchListener(GraphActivity.this);

                    TextView yvalue = view.findViewById(R.id.xvalue);
                    if (i == graphModel.getYCount())
                        y = y;
                    else
                        y = y + graphModel.getYDifference();

                    yvalue.setText(String.valueOf(y));

                    AbsoluteLayout.LayoutParams param =
                            new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    10, (headerLayoutHeight * i) - 10);

                    view.setLayoutParams(param);

                    if (y != 0)
                        constraintLayout.addView(view);
                }


                int LayoutWidth = constraintLayout.getWidth() / graphModel.getXCount();

                for (int i = 0; i < graphModel.getXCount(); i++) {

                    if (graphModel.getDirection().equals(0)) {

                        View bar = LayoutInflater.from(GraphActivity.this).inflate(R.layout.bar_layout, null, false);
                        bar.setTag("bar" + i);
                        BoxedVertical customView = bar.findViewById(R.id.boxed_vertical);

                        customView.setMax(graphModel.getYCount() * graphModel.getYDifference());

                        customView.setStep(1);
//                    customView.setValue(barheight);
                        try {
                            Log.e(TAG, "onGlobalLayout: " + graphModel.getXElements()
                                                                    .get(i).get(0).getColor());
                            customView.setProgressColor(Color.parseColor(graphModel.getXElements()
                                                                                 .get(i).get(0).getColor()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        AbsoluteLayout.LayoutParams params =
                                new AbsoluteLayout.LayoutParams(120,
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        (LayoutWidth * i) + 200, 0);
                        bar.setLayoutParams(params);
                        constraintLayout.addView(bar);
                        if (graphModel.getBarCount() == 2) {
                            View bar2 = LayoutInflater.from(GraphActivity.this).inflate(R.layout.bar_layout, null, false);
                            bar2.setTag("bar2" + i);
                            BoxedVertical customView2 = bar2.findViewById(R.id.boxed_vertical);

                            customView2.setMax(graphModel.getYCount() * graphModel.getYDifference());

                            customView2.setStep(1);
//                    customView2.setValue(barheight);
                            try {
                                Log.e(TAG, "onGlobalLayout: " + graphModel.getXElements()
                                                                        .get(i).get(1).getColor());
                                customView2.setProgressColor(Color.parseColor(graphModel.getXElements()
                                                                                      .get(i).get(1).getColor()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            AbsoluteLayout.LayoutParams params2 =
                                    new AbsoluteLayout.LayoutParams(120,
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            (LayoutWidth * i) + 340, 0);
                            bar2.setLayoutParams(params2);
                            constraintLayout.addView(bar2);

                        }

                    } else {
                        View bar = LayoutInflater.from(GraphActivity.this).inflate(R.layout.bar_layout, null, false);
                        bar.setTag("bar" + i);
                        BoxedVertical customView = bar.findViewById(R.id.boxed_vertical);

                        customView.setMax(graphModel.getYCount() * graphModel.getYDifference());

                        customView.setStep(1);
//                    customView.setValue(barheight);
                        try {
                            Log.e(TAG, "onGlobalLayout: " + graphModel.getXElements()
                                                                    .get(i).get(0).getColor());

                            customView.setinverseProgressColor(Color.parseColor(graphModel.getXElements()
                                                                                        .get(i).get(0).getColor()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        AbsoluteLayout.LayoutParams params =
                                new AbsoluteLayout.LayoutParams(120,
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        (LayoutWidth * i) + 200, 0);
                        bar.setLayoutParams(params);

                        constraintLayout.addView(bar);
                    }
                }

                int x = 0;

                for (int i = 0; i < graphModel.getXCount(); i++) {
                    View view;

                    if (graphModel.getDirection().equals(0)) {
                        view = LayoutInflater.from(GraphActivity.this).inflate(R.layout.xlevel, null, false);
                    } else {
                        view = LayoutInflater.from(GraphActivity.this).inflate(R.layout.ixlevel, null, false);

                    }
//                    customView.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
//                    customView.setOnTouchListener(GraphActivity.this);

                    TextView xvalue = view.findViewById(R.id.xvalue);

                    if (i == 0)
                        x = 1;
                    else
                        x = x + 1;

                    xvalue.setText(graphModel.getXElements().get(i).get(0).getXValue());

                    if (graphModel.getDirection().equals(0)) {
                        AbsoluteLayout.LayoutParams param =
                                new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        (LayoutWidth * i) + 230, LayoutHeight - 60);
                        view.setLayoutParams(param);
                        constraintLayout.addView(view);
                    } else {
                        AbsoluteLayout.LayoutParams param =
                                new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        (LayoutWidth * i) + 230, 0);
                        view.setLayoutParams(param);
                        constraintLayout.addView(view);
                    }
                }
                setGraphtozero(graphModel);

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < constraintLayout.getChildCount(); i++) {
                    for (int j = 0; j < graphModel.getXCount(); j++) {
                        View child = constraintLayout.getChildAt(i);
                        try {
                            if (child.getTag().equals("bar" + j)) {

                                BoxedVertical customView = child.findViewById(R.id.boxed_vertical);
                                int points = graphModel.getYCount() * graphModel.getYDifference();
                                float actualValueInPercent = graphModel.getXElements().get(j)
                                                                   .get(0).getActualValueInPercent();

                                float v1 = ((float) actualValueInPercent / (float) points);
                                float percent = ((v1 * 100) * (barheight / 100));

                                if (graphModel.getDirection().equals(0))
                                    customView.setValue((int) percent);
                                else
                                    customView.setValue(100 - (int) percent);

                                int value = customView.getValue();
                            }
                            if (child.getTag().equals("bar2" + j)) {

                                BoxedVertical customView = child.findViewById(R.id.boxed_vertical);
                                int points = graphModel.getYCount() * graphModel.getYDifference();
                                float actualValueInPercent = graphModel.getXElements().get(j)
                                                                   .get(1).getActualValueInPercent();

                                float v1 = ((float) actualValueInPercent / (float) points);
                                float percent = ((v1 * 100) * (barheight / 100));

                                if (graphModel.getDirection().equals(0))
                                    customView.setValue((int) percent);
                                else
                                    customView.setValue(100 - (int) percent);

                                int value = customView.getValue();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });

        submit.setVisibility(View.GONE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < constraintLayout.getChildCount(); i++) {
                    for (int j = 0; j < graphModel.getXCount(); j++) {
                        View child = constraintLayout.getChildAt(i);
                        try {
                            if (child.getTag().equals("bar" + j)) {

                                BoxedVertical customView = child.findViewById(R.id.boxed_vertical);
                                int value = customView.getValue();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGraphtozero(graphModel);
            }
        });

    }

    private void setGraphtozero(GraphModel graphModel) {
        for (int i = 0; i < constraintLayout.getChildCount(); i++) {
            for (int j = 0; j < graphModel.getXCount(); j++) {
                View child = constraintLayout.getChildAt(i);
                try {
                    if (child.getTag().equals("bar" + j)) {

                        BoxedVertical customView = child.findViewById(R.id.boxed_vertical);
                        if (graphModel.getDirection().equals(0))
                            customView.setValue(barheight);
                        else
                            customView.setValue(0);


                        //   Log.e(TAG, "toZero: " + barheight);

                    }
                    if (child.getTag().equals("bar2" + j)) {

                        BoxedVertical customView = child.findViewById(R.id.boxed_vertical);


                        if (graphModel.getDirection().equals(0))
                            customView.setValue(barheight);
                        else
                            customView.setValue(0);

                        int value = customView.getValue();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int width = v.getLayoutParams().width;
        int height = v.getLayoutParams().height;

//        Log.e(">>","width:"+width+" height:"+height+" x:"+x+" y:"+y);

//        if((x - width <= 20 && x - width > 0) ||(width - x <= 20 && width - x > 0)){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(">>", "width:" + width + " height:" + height + " x:" + x + " y:" + y);
                v.getLayoutParams().height = y;
                v.requestLayout();
                break;
            case MotionEvent.ACTION_MOVE:
//                    Log.e(">>","width:"+width+" height:"+height+" x:"+x+" y:"+y);
//                    v.getLayoutParams().height = y;
//                    v.requestLayout();
                break;
            case MotionEvent.ACTION_UP:
                Log.e(">>", "width:" + width + " height:" + height + " x:" + x + " y:" + y);
                v.getLayoutParams().height = y;
                v.requestLayout();
                break;
        }
//        }
        return false;
    }



    public String JSONFromAsset(String jsonfileName) {
        String json = null;
        try {
            InputStream is = this.getAssets().open(jsonfileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private String TAG = GraphActivity.class.getSimpleName();
    float initialX, initialY;


    //returns courses details list
    public String loadCoursesJSONFromAsset() {
        return JSONFromAsset("c1-m7-p10.json");
    }


}
