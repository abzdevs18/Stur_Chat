package com.test_app.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test_app.myapplication.api.ApiClient;
import com.test_app.myapplication.api.ApiInterface;
import com.test_app.myapplication.model.SignIn;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText mEmail,mPass;
    Button mLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.lEmail);
        mPass = findViewById(R.id.lPass);

        mLogin = findViewById(R.id.loginBtn);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        login();
                    }
                };
                Thread thread = new Thread(runnable);
                thread.setName("SignIn");
                thread.start();
            }
        });
    }

    private void login() {
        String sEmail = mEmail.getText().toString();
        String sPass = mPass.getText().toString();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<SignIn>> call = apiInterface.signIn(sEmail,sPass);
        call.enqueue(new Callback<List<SignIn>>() {
            @Override
            public void onResponse(Call<List<SignIn>> call, Response<List<SignIn>> response) {
//                Toast.makeText(getApplicationContext(),response.body().get(0).getP_k(),Toast.LENGTH_LONG).show();
                String status = response.body().get(0).getSuccess_login();
                String code = "1";
                if (status.equals("1")){
                    String p_k = response.body().get(0).getP_k();
//                Intent intent = new Intent(getApplicationContext(),Home.class);
//                intent.putExtra("p_k",p_k);
//                startActivity(intent);
//                finish();
                    SharedPreferences sharedPref = Login.this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("p_k",p_k);
                    editor.apply();
                    startActivity(new Intent(Login.this,Messenger.class));
                }else{
                    Toast.makeText(getApplicationContext(),"Seems something went wrong...",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SignIn>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });
    }

    public void register(View view) {
        startActivity(new Intent(Login.this,MainActivity.class));
        finish();
    }
}
