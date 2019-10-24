package com.example.calorietrackerr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity{

    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private Button mRegisterButton;
    private Button mLoginButton;
    private String uname;
    private TextView mloginmessage;
    private String upassword;
    private String hashupassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mRegisterButton = (Button) findViewById(R.id.signupBtn);
        mLoginButton = (Button) findViewById(R.id.loginBtn);
        mloginmessage = (TextView) findViewById(R.id.loginmessage);


        mRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });




        mLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                uname = mUsernameView.getText().toString();
                upassword = mPasswordView.getText().toString();
                hashupassword = MD5.getMD5(upassword);
                UCredentialAsyncTask getUCredential = new UCredentialAsyncTask();
                getUCredential.execute();

            }
        });

    }

    private class UCredentialAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestClient.findByUserCredential(uname, hashupassword); }
        @Override
        protected void onPostExecute (String users){
            if(users.equals("[]")==false){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("uname", uname);
                startActivity(intent);
            }
            else{
                mloginmessage.setText("Username/Password is wrong");
            }
        }

    }

}