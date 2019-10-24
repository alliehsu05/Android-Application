package com.example.calorietrackerr;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class CalorietrackerFragment extends Fragment {

    View vCalorietracker;
    private TextView tvGoal;
    private TextView tvSteps;
    private TextView tvConsumed;
    private TextView tvBurned;
    Integer userid=2;
    String date="2019-04-03";
    Integer totalstep =0;
    String calgoal;
    StepsDatabase db = null;
    EditText editText = null;
    TextView textView_read = null;
    TextView textView_delete = null;
    TextView textView_update = null;
    Integer reportid;
    String reportidStr = "";
    String userinfo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vCalorietracker = inflater.inflate(R.layout.fragment_calorietracker, container, false);

        tvGoal = (TextView) vCalorietracker.findViewById(R.id.calgoal);
        tvSteps = (TextView) vCalorietracker.findViewById(R.id.steps);
        tvConsumed = (TextView) vCalorietracker.findViewById(R.id.consumed);
        tvBurned = (TextView) vCalorietracker.findViewById(R.id.burned);

        SharedPreferences spGoal = getActivity().getSharedPreferences("goal", Context.MODE_PRIVATE);
        calgoal = spGoal.getString("totalgoal",null);
        tvGoal.setText(calgoal);

        SharedPreferences spUserinfo = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        userinfo = spUserinfo.getString("uuserinfo",null);

        ConsumedAsyncTask consumedAsyncTask = new ConsumedAsyncTask();
        consumedAsyncTask.execute();
        BurnedAsyncTask burnedAsyncTask = new BurnedAsyncTask();
        burnedAsyncTask.execute();
        CountReportAsyncTask countReportAsyncTask = new CountReportAsyncTask();
        countReportAsyncTask.execute();


        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                StepsDatabase.class, "StepsDatabse")
                .fallbackToDestructiveMigration()
                .build();

        textView_read = (TextView) vCalorietracker.findViewById(R.id.textView_read);
        Button deleteButton = (Button) vCalorietracker.findViewById(R.id.deleteButton);
        textView_delete = (TextView) vCalorietracker.findViewById(R.id.textView_delete);


        ReadTotalsteps readTotalsteps = new ReadTotalsteps();
        readTotalsteps.execute();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DeleteDatabase deleteDatabase = new DeleteDatabase();
                deleteDatabase.execute();
            }
        });

        return vCalorietracker;
    }

    private class ConsumedAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestClient.getTotalconsumed(userid, date); }
        @Override
        protected void onPostExecute (String result){
            tvConsumed.setText(result);
        }

    }

    private class BurnedAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestClient.getTotalburned(userid); }
        @Override
        protected void onPostExecute (String result){
            tvBurned.setText(result);
        }

    }

    private class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String reportdate = simpleDateFormat.format(date)+"T00:00:00+11:00";
            CreateReportAsyncTask createReportAsyncTask = new CreateReportAsyncTask();
            createReportAsyncTask.execute(reportidStr,userinfo,reportdate, tvConsumed.getText().toString(), tvBurned.getText().toString(), totalstep.toString(), calgoal);
            db.stepsDao().deleteAll();
        return null;
        }
        protected void onPostExecute(Void param) {
            textView_delete.setText("Report was created, Good Night!");
            ReadTotalsteps readTotalsteps = new ReadTotalsteps();
            readTotalsteps.execute();
        }
    }

    private class ReadTotalsteps extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params) {
            List<Steps> steps = db.stepsDao().getAll();
            if (!(steps.isEmpty() || steps == null) ){
                String totalstepStr = "";
                for (Steps temp : steps) {
                    Integer eachstep = Integer.parseInt(temp.getStep());
                    totalstep = totalstep + eachstep;
                    totalstepStr = String.valueOf(totalstep);
                }
                return totalstepStr;
            }
            else
                return "";
        }
        @Override
        protected void onPostExecute(String details) {
            tvSteps.setText(details);
        }
    }

    private class CreateReportAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            Report report=new Report(Integer.parseInt(params[0]),params[1],params[2],Double.parseDouble(params[3]), Double.parseDouble(params[4]),Integer.parseInt(params[5]),Double.parseDouble(params[6]));
            RestClient.createReport(report);
            return "Report was created";
        }
        @Override
        protected void onPostExecute(String response) {

        }
    }

    private class CountReportAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestClient.countReport();
        }
        @Override
        protected void onPostExecute (String num){
            reportid = Integer.parseInt(num);
            reportid = reportid+1;
            reportidStr = reportid.toString();
        }

    }



}
