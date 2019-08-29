package com.test_app.myapplication;


import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.test_app.myapplication.adapter.UserAdapter;
import com.test_app.myapplication.api.ApiClient;
import com.test_app.myapplication.api.ApiInterface;
import com.test_app.myapplication.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity {
    private RecyclerView mRecycler;
    private List<Users> users;
    private UserAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ApiInterface apiInterface;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mRecycler = findViewById(R.id.searchable_user);
        layoutManager = new LinearLayoutManager(Search.this);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setHasFixedSize(true);

        SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        final String user_pk = sharedPreferences.getString("p_k","");


        searchView = findViewById(R.id.s);
        searchView.setIconifiedByDefault(true);
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                users(user_pk,s);
//                Toast.makeText(Search.this,"s",Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                users(user_pk,s);
//                Toast.makeText(Search.this,s,Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        users(user_pk,"");
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search,menu);
////        MenuItem searchItem = menu.findItem(R.id.search_bar);
//        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName())
//        );
//        searchView.setIconifiedByDefault(true);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
//        return true;
//    }

    private void users(String p_k,String query) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String sId = p_k;

        final Call<List<Users>> usersList = apiInterface.userList(sId,query);
        usersList.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                users = response.body();
                adapter = new UserAdapter(users,Search.this);
                mRecycler.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });

    }
}
