package com.test_app.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test_app.myapplication.api.ApiClient;
import com.test_app.myapplication.api.ApiInterface;
import com.test_app.myapplication.model.Registration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText mUsername,mLastname,mEmail,mPassword;
    Button mSubmit;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd = new ProgressDialog(this);
        mUsername = findViewById(R.id.fname);
        mLastname = findViewById(R.id.lname);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);

        mSubmit = findViewById(R.id.submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        registration();
                    }
                };

                Thread thread = new Thread(runnable);
                thread.setName("Register");
                thread.start();
            }
        });
    }

    private void registration() {
        String sUsername = mUsername.getText().toString();
        String sLastname = mLastname.getText().toString();
        String sEmail = mEmail.getText().toString();
        String sPassword = mPassword.getText().toString();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Registration>> call =apiInterface.signup(sUsername,sLastname,sEmail,sPassword);
        call.enqueue(new Callback<List<Registration>>() {
            @Override
            public void onResponse(Call<List<Registration>> call, Response<List<Registration>> response) {
                pd.hide();
                mUsername.setText("");
                mLastname.setText("");
                mEmail.setText("");
                mPassword.setText("");
                Toast.makeText(getApplicationContext(),"Success Singup",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Registration>> call, Throwable t) {
                pd.hide();
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });

    }

    public void login(View view) {
        startActivity(new Intent(MainActivity.this,Login.class));
        finish();
    }
}
