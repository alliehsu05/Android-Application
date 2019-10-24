package com.example.calorietrackerr;

import android.content.Context;
import android.content.Intent;
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
import java.util.concurrent.TimeoutException;


public class HomeFragment extends Fragment {
    View vHome;
    private TextView tvWelcome;
    private TextView tvCalGoal;
    private EditText etSetGoal;
    private Button bSetGoal;
    private Button bGetUserinfo;
    private TextView tvGetUserinfo;
    private TextView tvDatetime;
    private String userfname = "";
    private String address = "";
    private String userinfo ="";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vHome = inflater.inflate(R.layout.fragment_home, container, false);
        tvWelcome = (TextView) vHome.findViewById(R.id.welcome);
        tvCalGoal = (TextView) vHome.findViewById(R.id.calgoal);
        etSetGoal = (EditText) vHome.findViewById(R.id.setgoal);
        bSetGoal = (Button) vHome.findViewById(R.id.seteditBtn);
        tvGetUserinfo = (TextView) vHome.findViewById(R.id.userinfo);
        bGetUserinfo = (Button) vHome.findViewById(R.id.getbyusername);
        tvDatetime = (TextView) vHome.findViewById(R.id.dateTime);

        MainActivity activity = (MainActivity) getActivity();
        userfname = activity.getUname();

        tvWelcome.setText("Welcome "+userfname);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        tvDatetime.setText(simpleDateFormat.format(date));

        UsernameAsyncTask getAlluserinfo = new UsernameAsyncTask();
        getAlluserinfo.execute();


        bSetGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCalGoal();
                String submit = tvCalGoal.getText().toString();
                SharedPreferences spGoal = getActivity().getSharedPreferences("goal", Context.MODE_PRIVATE);
                SharedPreferences.Editor eGoal = spGoal.edit();
                eGoal.putString("totalgoal",submit);
                eGoal.apply();

            }
        });


        bGetUserinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UsernameAsyncTask getAlluserinfo = new UsernameAsyncTask();
                //getAlluserinfo.execute();
                Intent intent = new Intent(getActivity().getBaseContext(),MapActivity.class);
                intent.putExtra("address",address);
                getActivity().startActivity(intent);
            }
        });

        return vHome;
    }


    public void setCalGoal(){
        String newGoal = etSetGoal.getText().toString();
        tvCalGoal.setText(newGoal);
    }


    private class UsernameAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestClient.findByUsername(userfname);
        }
        @Override
        protected void onPostExecute (String users){
            userinfo = users;
            userinfo = userinfo.substring(1, userinfo.length()-1);
            String[] parts = users.split("\\:");
            String[] a = parts[1].split("\\,");
            address = a[0].substring(1, a[0].length() - 1);

            tvGetUserinfo.setText("Your address is : " +address);

            SharedPreferences spUserinfo = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor eUserinfo = spUserinfo.edit();
            eUserinfo.putString("uuserinfo",userinfo);
            eUserinfo.apply();

        }

    }

}

