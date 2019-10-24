package com.example.calorietrackerr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity{

    private DatePicker datePicker;
    private EditText mFnameView;
    private EditText mSnameView;
    private EditText mEmailView;
    private String mDobView;
    private EditText mHeightView;
    private EditText mWeightView;
    private EditText mAddressView;
    private EditText mSteps;
    private EditText mUname;
    private EditText mPword;
    private RadioGroup mRadioSexGroupView;
    private RadioButton mRadioSexButton;
    private TextView mTest;
    Integer userid;
    String useridStr = "";
    private String upassword;
    private String hashupassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFnameView = (EditText)findViewById(R.id.fnameText);
        mSnameView = (EditText)findViewById(R.id.snameText);
        mEmailView = (EditText)findViewById(R.id.emailText);
        mHeightView = (EditText)findViewById(R.id.heightText);
        mWeightView = (EditText)findViewById(R.id.weightText);
        mAddressView = (EditText)findViewById(R.id.addressText);
        mSteps = (EditText)findViewById(R.id.stepsText);
        mUname = (EditText) findViewById(R.id.unameText);
        mPword = (EditText) findViewById(R.id.pwordText);
        mRadioSexGroupView = (RadioGroup) findViewById(R.id.radioGroup);
        mTest = (TextView)findViewById(R.id.test);


        final Spinner spinner = findViewById(R.id.LoAspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.level, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        final Spinner spinner2 = findViewById(R.id.Postcodespinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.postcode, android.R.layout.simple_spinner_item);
        spinner2.setAdapter(adapter2);


        datePicker = (DatePicker) findViewById(R.id.dobPicker);

        CountUsersAsyncTask countUsersAsyncTask = new CountUsersAsyncTask();
        countUsersAsyncTask.execute();


        final Button mRegisterButton = (Button) findViewById(R.id.registerBtn);
        mRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
              //  Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
               // startActivity(intent);
                final String mLoAView = spinner.getSelectedItem().toString();
                final String mPostcode = spinner2.getSelectedItem().toString();

                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                mDobView = String.format("%04d-%02d-%02d",year,month,day);
                mDobView= mDobView+"T00:00:00+11:00";

                int selectedId = mRadioSexGroupView.getCheckedRadioButtonId();
                mRadioSexButton = (RadioButton) findViewById(selectedId);
                String gender = mRadioSexButton.getText().toString();
                if(gender.equals("Male")){
                    gender = "M";
                }else{
                    gender = "F";
                }

                upassword = mPword.getText().toString();
                hashupassword = MD5.getMD5(upassword);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                String signupdate = simpleDateFormat.format(date)+"T00:00:00+11:00";

                CreateUsersAsyncTask createUsersAsyncTask = new CreateUsersAsyncTask();
                createUsersAsyncTask.execute(useridStr,mFnameView.getText().toString(),mSnameView.getText().toString(), mEmailView.getText().toString(), mDobView, mHeightView.getText().toString(), mWeightView.getText().toString(),
                                               gender, mAddressView.getText().toString(), mPostcode, mLoAView, mSteps.getText().toString());
                CreateCredentialAsyncTask createCredentialAsyncTask = new CreateCredentialAsyncTask();
                createCredentialAsyncTask.execute(useridStr, mUname.getText().toString(),hashupassword,signupdate);

            }
        });

    }

    private class CreateUsersAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            Users users=new Users(Integer.parseInt(params[0]),params[1],params[2],params[3], params[4],Double.parseDouble(params[5]),Double.parseDouble(params[6]),params[7],params[8],Integer.parseInt(params[9]),Integer.parseInt(params[10]),Integer.parseInt(params[11]));
            RestClient.createUsers(users);
            return "User was added";
        }
        @Override
        protected void onPostExecute(String response) {
        }
    }

    private class CreateCredentialAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            Credential credential=new Credential(Integer.parseInt(params[0]),params[1],params[2],params[3]);
            RestClient.createCredential(credential);
            return "Credential was added";
        }
        @Override
        protected void onPostExecute(String response) {
            mTest.setText(response);
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private class CountUsersAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestClient.countUsers();
        }
        @Override
        protected void onPostExecute (String num){
            userid = Integer.parseInt(num);
            userid = userid+1;
            useridStr = userid.toString();
        }
    }
}
