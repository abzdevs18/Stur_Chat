package com.test_app.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.test_app.myapplication.adapter.UserAdapter;
import com.test_app.myapplication.api.ApiClient;
import com.test_app.myapplication.api.ApiInterface;
import com.test_app.myapplication.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    private RecyclerView mRecycler;
    private List<Users> users;
    private UserAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRecycler = findViewById(R.id.userList);
        layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setHasFixedSize(true);
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        final String user_pk = sharedPreferences.getString("p_k","");
//        Intent data_intent = getIntent();
//        String user_pk = data_intent.getStringExtra("p_k");
        users(user_pk,"");
        Toast.makeText(getApplicationContext(),user_pk,Toast.LENGTH_LONG).show();

    }

    private void users(String p_k,String query) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String sId = p_k;

        final Call<List<Users>> usersList = apiInterface.userList(sId,query);
        usersList.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                users = response.body();
                adapter = new UserAdapter(users,getApplicationContext());
                mRecycler.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });

    }
}
