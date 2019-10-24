package com.example.calorietrackerr;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class StepsFragment extends Fragment {
    View vSteps;
    private TextView tvTotalsteps;
    private EditText etSetsteps;
    private Button bSetsteps;
    private Button bEditsteps;
    private ListView lvSteps;

    StepsDatabase db = null;
    private ArrayList<String> stepList;
    private ArrayAdapter<String> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vSteps = inflater.inflate(R.layout.fragment_steps, container, false);

        tvTotalsteps = (TextView) vSteps.findViewById(R.id.totalsteps);
        etSetsteps = (EditText) vSteps.findViewById(R.id.setsteps);
        bSetsteps = (Button) vSteps.findViewById(R.id.setstepsBtn);
        bEditsteps = (Button) vSteps.findViewById(R.id.editstepsBtn);
        lvSteps = (ListView) vSteps.findViewById(R.id.list_view);

        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                StepsDatabase.class, "StepsDatabse")
                .fallbackToDestructiveMigration()
                .build();

        stepList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_list_item_1, stepList);
        lvSteps.setAdapter(adapter);


        lvSteps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String option = stepList.get(position);
                String[] parts = option.split(" ");
                etSetsteps.setText(parts[0]+" "+parts[3]);
            }
        });

        bSetsteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertDatabase addDatabase = new InsertDatabase();
                addDatabase.execute();
            }
        });

        bEditsteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDatabase updateDatabase = new UpdateDatabase();
                updateDatabase.execute();
            }
        });

        return vSteps;
    }


    private class InsertDatabase extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params) {
            if (!(etSetsteps.getText().toString().isEmpty())){
                String details = etSetsteps.getText().toString();
                Steps steps = new Steps(details);
                long id = db.stepsDao().insert(steps);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                return (id + " " + simpleDateFormat.format(date) +" "+details);
            }
            else
                return "";
        }
        @Override
        protected void onPostExecute(String details) {
            stepList.add(details);
            adapter.notifyDataSetChanged();
            ReadTotalsteps readTotalsteps = new ReadTotalsteps();
            readTotalsteps.execute();
        }
    }

    private class UpdateDatabase extends AsyncTask<Void, Void, String>
    {
        Integer stepsid = 0;
        String newTaken;
        @Override protected String doInBackground(Void... params) {
            Steps steps=null;
            String[] details= etSetsteps.getText().toString().split(" ");
            if (details.length==2) {
                stepsid = Integer.parseInt(details[0]);
                newTaken = details[1];
                steps = db.stepsDao().findByID(stepsid);
                steps.setStep(details[1]);
            }
            if (steps!=null) {
                db.stepsDao().updateInfo(steps);
                return (details[0] + " " + details[1]);
            }
            return "";
        }
        @Override
        protected void onPostExecute(String details) {
            int position = lvSteps.getCheckedItemPosition();
            String option = stepList.get(position);
            String[] parts = option.split(" ");
            //int oldstep = Integer.parseInt(parts[3]);
            adapter.remove(stepList.get(position));
            String newRecord = parts[0] +" "+ parts[1] + " "+parts[2] +" " + String.valueOf(newTaken);
            adapter.insert(newRecord,position);
            adapter.notifyDataSetChanged();
            ReadTotalsteps readTotalsteps = new ReadTotalsteps();
            readTotalsteps.execute();
        }
    }


    private class ReadTotalsteps extends AsyncTask<Void, Void, String> {
        Integer totalstep=0;
        @Override
        protected String doInBackground(Void... params) {
            List<Steps> steps = db.stepsDao().getAll();
            if (!(steps.isEmpty() || steps == null)) {
                String totalstepStr = "";
                for (Steps temp : steps) {
                    Integer eachstep = Integer.parseInt(temp.getStep());
                    totalstep = totalstep + eachstep;
                    totalstepStr = String.valueOf(totalstep);
                }
                return totalstepStr;
            } else
                return "";
        }

        @Override
        protected void onPostExecute(String details) {
            tvTotalsteps.setText(details);
        }
    }

}
