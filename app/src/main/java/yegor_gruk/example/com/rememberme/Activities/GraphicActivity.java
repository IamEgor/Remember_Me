package yegor_gruk.example.com.rememberme.Activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.SQLException;
import java.util.ArrayList;

import yegor_gruk.example.com.rememberme.DataBase.HelperFactory;
import yegor_gruk.example.com.rememberme.DataBase.ModelDAO;
import yegor_gruk.example.com.rememberme.R;

/**
 * Created by Egor on 30.11.2015.
 */
public class GraphicActivity extends Activity {

    public static final int FILLING_ALPHA = 127;

    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_graphics);

        //mChart.setOnChartValueSelectedListener(this);
        mChart = (LineChart) findViewById(R.id.chart);
        mChart.setDrawGridBackground(false);
        mChart.setDescription("");
        mChart.setDrawBorders(false);

        mChart.setBackgroundColor(Color.WHITE);

        //mChart.getAxisRight().setDrawAxisLine(true);
        //mChart.getAxisRight().setDrawGridLines(false);
        mChart.getAxisRight().setEnabled(false);

        mChart.getAxisLeft().setDrawAxisLine(true);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawAxisLine(false);
        mChart.getXAxis().setDrawGridLines(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        setData(20, 30);

        //Legend l = mChart.getLegend();
        //l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);

    }

    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        //for (int i = 0; i < count; i++) { xVals.add((i) + ""); }

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        try {
            ModelDAO dao = HelperFactory.getHelper().getModelDAO();
            int counter = 0;

            String prev = "";
            Float currentVal;
            int currentIndex;

            for (String[] resultArray : dao.getStatistics()) {

                currentVal = Float.parseFloat(resultArray[1]);

                if (!prev.equals(resultArray[0])) {

                    xVals.add(resultArray[0]);

                    yVals1.add(new Entry(currentVal, counter));

                    if (resultArray[2].equals("0"))
                        yVals2.add(new Entry(currentVal, counter));
                    else
                        yVals2.add(new Entry(0f, counter));

                    counter++;
                } else {
                    currentIndex = yVals1.size() - 1;
                    yVals1.set(currentIndex, new Entry(yVals1.get(currentIndex).getVal() + currentVal, counter - 1));
                }

                prev = resultArray[0];
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals1, "Выполнено");
        LineDataSet set2 = new LineDataSet(yVals2, "Пропущено");

        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        set1.setColor(ColorTemplate.getHoloBlue());
        set2.setColor(Color.RED);

        set1.setCircleColor(Color.LTGRAY);
        set2.setCircleColor(Color.LTGRAY);

        set1.setLineWidth(2f);
        set2.setLineWidth(2f);

        set1.setCircleSize(3f);
        set2.setCircleSize(3f);

        set1.setFillAlpha(FILLING_ALPHA);
        set2.setFillAlpha(180);

        set1.setFillColor(ColorTemplate.getHoloBlue());
        set2.setFillColor(Color.RED);

        set1.setDrawCircleHole(false);
        set2.setDrawCircleHole(false);

        set1.setDrawCubic(true);
        set2.setDrawCubic(true);

        set1.setDrawFilled(true);
        set2.setDrawFilled(true);

        set1.setDrawHorizontalHighlightIndicator(false);
        set2.setDrawHorizontalHighlightIndicator(false);

        set1.setHighLightColor(Color.BLUE);
        set2.setHighLightColor(Color.CYAN);


        //set1.setHighLightColor(getResources().getColor(R.color.primary_dark));
        //set1.setFillFormatter(new MyFillFormatter(0f));

        //set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();

        dataSets.add(set1); // add the datasets
        dataSets.add(set2);

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);
    }

    public void setCubic(View view) {

        ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mChart.getData()
                .getDataSets();

        for (LineDataSet set : sets) {
            if (set.isDrawCubicEnabled())
                set.setDrawCubic(false);
            else
                set.setDrawCubic(true);
        }

        mChart.invalidate();

    }


    public void setFilled(View view) {

        ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mChart.getData()
                .getDataSets();

        for (LineDataSet set : sets) {
            if (set.isDrawFilledEnabled())
                set.setDrawFilled(false);
            else
                set.setDrawFilled(true);
        }

        mChart.invalidate();
    }

}
