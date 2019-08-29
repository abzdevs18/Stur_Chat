package com.test_app.myapplication.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgabrielfreitas.core.BlurImageView;
import com.test_app.myapplication.R;
import com.test_app.myapplication.adapter.FriendsAdapter;
import com.test_app.myapplication.adapter.UserAdapter;
import com.test_app.myapplication.api.ApiClient;
import com.test_app.myapplication.api.ApiInterface;
import com.test_app.myapplication.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendFragment extends Fragment {
    private RecyclerView mRecycler;
    private List<Users> users;
    private FriendsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ApiInterface apiInterface;
    BlurImageView mBlur;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.friend_fragment,container,false);
        mBlur = v.findViewById(R.id.imgFriendBlur);
        mRecycler = v.findViewById(R.id.fRecyCler);
        layoutManager = new LinearLayoutManager(v.getContext());
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setHasFixedSize(true);
        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        final String user_pk = sharedPreferences.getString("p_k","");
        users(user_pk,"");
        return  v;
    }
    private void users(String p_k,String query) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String sId = p_k;

        final Call<List<Users>> usersList = apiInterface.userList(sId,"");
        usersList.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                users = response.body();
                adapter = new FriendsAdapter(users,getActivity());
                mRecycler.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });

    }
}
