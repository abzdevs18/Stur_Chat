package com.test_app.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test_app.myapplication.ChatActivity;
import com.test_app.myapplication.R;
import com.test_app.myapplication.model.Users;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
private List<Users> users;
private Context context;

    public UserAdapter(List<Users> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_items,viewGroup,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Users u_model =users.get(i);
        holder.u_model = u_model;
        holder.mName.setText(users.get(i).getUsername());
        holder.mEmail.setText(users.get(i).getEmail());
        Uri uri = Uri.parse("https://primelinetools.com/pub/media/catalog/product/placeholder/default/ajax-loader02_4.gif");
        Glide.with(context)
                .load(users.get(i).getImage())
//                .thumbnail(Glide.with(context).load(uri))
                .centerCrop()
                .crossFade()
                .into(holder.prof_icon);
        holder.mCount.setText(users.get(i).getUnreadMessage());
        holder.mTime.setText(users.get(i).getTime());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mName,mEmail,mTime,mCount;
        Users u_model;
        CircleImageView prof_icon;

        LinearLayout mWrapper;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mEmail = itemView.findViewById(R.id.email);
            prof_icon = itemView.findViewById(R.id.usr_id_list);
            mWrapper =  itemView.findViewById(R.id.wrapId);
            mTime = itemView.findViewById(R.id.latestMsg);
            mCount =  itemView.findViewById(R.id.countMsg);
//            if (mCount.getText().toString() != "0"){
//                mWrapper.setVisibility(View.VISIBLE);
//            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent chat_intent = new Intent(itemView.getContext(), ChatActivity.class);
                    chat_intent.putExtra("p_k",u_model.getP_k());
                    chat_intent.putExtra("username",u_model.getUsername());
                    chat_intent.putExtra("lastname",u_model.getLastname());
                    chat_intent.putExtra("email",u_model.getEmail());
                    chat_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    itemView.getContext().startActivity(chat_intent);

                }
            });

        }
    }

}
