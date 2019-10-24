package com.example.calorietrackerr;

import android.app.slice.Slice;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment {

    View vReport;
    private DatePicker datePicker;
    private Button bPie;
    private EditText etStartDate;
    private EditText etEndDate;
    private Button bBar;
    private TextView tvTtest;

    private String piedate;
    Integer userid=2;
    String pietotalconsumed = "";
    String pietotalburned = "";
    String startdate = "";
    String enddate = "";
    String pieremaining = "1500";
    String bartotalconsumed = "";
    String bartotalburned = "";

    private PieChart pieChart;
    private BarChart barChart;


    int a = 18;
    int b =222;
    int c = 909;

    String consumed;
    String burned;
    String remaining;
    String consumedperiod;
    String burnedperiod;

    int[] colorClassArray = new int[]{Color.DKGRAY,Color.RED,Color.BLUE};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vReport = inflater.inflate(R.layout.fragment_report, container, false);

        datePicker = (DatePicker) vReport.findViewById(R.id.datePicker);
        bPie = (Button) vReport.findViewById(R.id.pieBtn);
        etStartDate = (EditText) vReport.findViewById(R.id.startdate);
        etEndDate = (EditText) vReport.findViewById(R.id.enddate);
        bBar = (Button) vReport.findViewById(R.id.barBtn);
        tvTtest = (TextView) vReport.findViewById(R.id.ttest);
        pieChart = (PieChart) vReport.findViewById(R.id.pieChart);
        barChart = (BarChart) vReport.findViewById(R.id.barChart);









        bPie.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                piedate = String.format("%04d-%02d-%02d",year,month,day);
                ConsumedAsyncTask consumedAsyncTask = new ConsumedAsyncTask();
                consumedAsyncTask.execute();
                BurnedAsyncTask burnedAsyncTask = new BurnedAsyncTask();
                burnedAsyncTask.execute();




            }
        });

        bBar.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                startdate = etStartDate.getText().toString();
                enddate = etEndDate.getText().toString();
                PeriodresultAsyncTask periodresultAsyncTask = new PeriodresultAsyncTask();
                periodresultAsyncTask.execute();




            }
        });

        return vReport;
    }


    private class ConsumedAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestClient.getTotalconsumed(userid, piedate); }
        @Override
        protected void onPostExecute (String result){
            pietotalconsumed = result;
        }

    }

    private class BurnedAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestClient.getTotalburned(userid); }
        @Override
        protected void onPostExecute (String result){
            pietotalburned = result;
            tvTtest.setText("Your total consumed of the date: "+pietotalconsumed+" total burned: "+pietotalburned+" remaining calorie: "+pieremaining);

            String[] consumedparts =  pietotalconsumed.split("\\.");
            String[] burnedparts =  pietotalburned.split("\\.");
            String[] remainingparts =  pieremaining.split("\\.");
            consumed = consumedparts[0];
            burned = burnedparts[0];
            remaining = remainingparts[0];

            PieDataSet pieDataSet = new PieDataSet(dataValues1(),"");
            pieDataSet.setColors(colorClassArray);
            PieData pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieChart.invalidate();
        }

    }

    private class PeriodresultAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestClient.getPeriodresult(userid,startdate,enddate); }
        @Override
        protected void onPostExecute (String result){
            String[] parts = result.split(" ");
            bartotalconsumed = parts[5];
            bartotalburned = parts[11];
            tvTtest.setText("Total consumed: "+bartotalconsumed+" Total burned: "+bartotalburned);

            String[] consumedparts =  bartotalconsumed.split("\\.");
            String[] burnedparts =  bartotalburned.split("\\.");
            consumedperiod = consumedparts[0];
            burnedperiod = burnedparts[0];

            BarDataSet barDataSet1 = new BarDataSet(barEntries1(),"Consumed");
            barDataSet1.setColors(Color.RED);
            BarDataSet barDataSet2 = new BarDataSet(barEntries2(),"Burned");
            barDataSet2.setColor(Color.CYAN);

            BarData data = new BarData(barDataSet1,barDataSet2);
            barChart.setData(data);

            String[] total = new String[]{"Total Calorie of period"};
            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(total));
            xAxis.setCenterAxisLabels(true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1);
            xAxis.setGranularityEnabled(true);

            barChart.setDragEnabled(true);
            barChart.setVisibleXRangeMaximum(3);

            float barSpace = 0.08f;
            float groupSpace = 0.44f;
            data.setBarWidth(0.10f);

            barChart.getXAxis().setAxisMinimum(0);
            barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*1);
            barChart.getAxisLeft().setAxisMinimum(0);

            barChart.groupBars(0,groupSpace,barSpace);
            barChart.invalidate();



        }

    }


    private ArrayList<PieEntry> dataValues1(){
        ArrayList<PieEntry> dataVals = new ArrayList<PieEntry>();
        dataVals.add(new PieEntry(Integer.valueOf(consumed),"Consumed"));
        dataVals.add(new PieEntry(Integer.valueOf(burned),"Burned"));
        dataVals.add(new PieEntry(Integer.valueOf(remaining),"Remaining"));
        return dataVals;
    }

    private ArrayList<BarEntry> barEntries1(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1,Integer.valueOf(consumedperiod)));
        return barEntries;
    }

    private ArrayList<BarEntry> barEntries2(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1,Integer.valueOf(burnedperiod)));
        return barEntries;
    }


}
