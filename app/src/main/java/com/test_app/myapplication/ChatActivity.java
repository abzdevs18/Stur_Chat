package com.test_app.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test_app.myapplication.adapter.MessageAdapter;
import com.test_app.myapplication.api.ApiClient;
import com.test_app.myapplication.api.ApiInterface;
import com.test_app.myapplication.model.AccountData;
import com.test_app.myapplication.model.Is_Typing;
import com.test_app.myapplication.model.Messages;
import com.test_app.myapplication.model.SendMessage;
import com.test_app.myapplication.model.Typing;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.test_app.myapplication.Imported.RealPathUtil.getRealPath;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mReciever,mSendIcon;
    EditText mMsg;
    Bitmap bitmap;
    private TextWatcher input=null;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Messages> messages;
    private MessageAdapter messageAdapter;
    private ImageView mTopBack,mTypingGif, mThumbnail,mThumbnailClose,mSelect;
    private String p_k;
    private RelativeLayout mTypingIcon;
    private static final int img_req = 777;
    ConstraintLayout mThumbnailViewGroup;

    CircleImageView mProf,mTypingHead;
    TextView mRecevierChat,mRemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mThumbnailViewGroup = findViewById(R.id.thumnail);

        mThumbnail = findViewById(R.id.image_send);
        mThumbnailClose = findViewById(R.id.image_send_close);
        mSelect = findViewById(R.id.img_select_send);
        mSelect.setOnClickListener(this);

        mTypingIcon = findViewById(R.id.typing_n);
        mTopBack = findViewById(R.id.top_back);

        mTypingHead = findViewById(R.id.circleImageView4);
        mProf = findViewById(R.id.prof_icon);
        mTypingGif = findViewById(R.id.typingGif);
        mRecevierChat = findViewById(R.id.reciever);
        mRemail = findViewById(R.id.rEmail);


        Intent data = getIntent();
        String username = data.getStringExtra("username");
        final String user_id = data.getStringExtra("p_k");
        prof(user_id);
        Toolbar toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setIcon(R.drawable.ic_chevron);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        final String sender = sharedPreferences.getString("p_k","");
        /*Fetching Messages Start here*/
        recyclerView = findViewById(R.id.msgs_recycler);
        layoutManager = new LinearLayoutManager(this);
        // TODO: 18/04/2019 The code below the setReverseLayout() is really cool..
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        // TODO: 18/04/2019 What the below Does is retrieving messages from DB every 2 seconds
        final Thread fetching_msg = new Thread(){
            @Override
            public void run() {
                while (!interrupted()){
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                message_recieve(sender,user_id);
                                is_typing(user_id,sender);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        fetching_msg.start();

        /*Fetching Messages Ends here*/

        mReciever = findViewById(R.id.reciever);
        mMsg = findViewById(R.id.msg);
        mSendIcon = findViewById(R.id.send_icon);
        
        mReciever.setText(username);

        mSendIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String m = mMsg.getText().toString();
                sendMessage(user_id,sender,m);

            }
        });

        input = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    typing(user_id,sender);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stop_typing();
                    }
                },3000);
            }
        };
        mMsg.addTextChangedListener(input);
    }
    private void prof(String user_pk) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<AccountData>> userAccount = apiInterface.accountData(user_pk);
        userAccount.enqueue(new Callback<List<AccountData>>() {
            @Override
            public void onResponse(Call<List<AccountData>> call, Response<List<AccountData>> response) {
                Glide.with(getApplicationContext())
                        .load(response.body().get(0).getImage())
                        .centerCrop()
                        .crossFade()
                        .into(mProf);
                Glide.with(getApplicationContext())
                        .load(response.body().get(0).getImage())
                        .centerCrop()
                        .crossFade()
                        .into(mTypingHead);
                Uri uri = Uri.parse("http://192.168.0.82:8080/stur_app/uploaded_prof_pic/default/typing.gif");
                Glide.with(getApplicationContext())
                        .load(uri)
                        .centerCrop()
                        .crossFade()
                        .into(mTypingGif);
                mRecevierChat.setText(response.body().get(0).getUsername());
                mRemail.setText(response.body().get(0).getEmail());
//                Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<AccountData>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
//                startActivity(new Intent(ChatActivity.this,Messenger.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    private void stop_typing() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Is_Typing>> stop_typing = apiInterface.updateTyping(p_k);
        stop_typing.enqueue(new Callback<List<Is_Typing>>() {
            @Override
            public void onResponse(Call<List<Is_Typing>> call, Response<List<Is_Typing>> response) {
                // TODO: 18/04/2019 This where the respnse goes when we successfully deleted the temp_msgs from DB
                Log.d("Typing","Rd"+response.message());

            }

            @Override
            public void onFailure(Call<List<Is_Typing>> call, Throwable t) {
                Is_Typing is = new Is_Typing();
                String n = is.getTyping();
                String mesg = t.getMessage();
                Log.d("Typing",n+"+"+mesg);
            }
        });
    }

    private void is_typing(final String user_id, final String sender) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        final Call<List<Is_Typing>> is_typing = apiInterface.is_typing(sender,user_id);
        is_typing.enqueue(new Callback<List<Is_Typing>>() {
            @Override
            public void onResponse(Call<List<Is_Typing>> call, Response<List<Is_Typing>> response) {
                String typing = "1";
                String stop = "0";
                String is_typing = response.body().get(0).getTyping();
                String reciever = response.body().get(0).getReciever();
                String m_sender = response.body().get(0).getSender();
                if (is_typing.equals(typing)){
                    if (reciever.equals(sender) && m_sender.equals(user_id)){
                        // TODO: 18/04/2019 Here put the typing notification For now we use Toast 
//                        Toast.makeText(getApplicationContext(),"someone",Toast.LENGTH_LONG).show();
                        mTypingIcon.setVisibility(View.VISIBLE);
//                        Log.d("Typing",p_k);
                        Log.d("User_ID",user_id);
                        Log.d("Sender",sender);

                        Log.d("Typing","someone is typing now...");

                    }
                }else if(is_typing.equals(stop)){
                    mTypingIcon.setVisibility(View.GONE);
                    Log.d("Typing","stop now.");

                }
            }

            @Override
            public void onFailure(Call<List<Is_Typing>> call, Throwable t) {
                Log.d("Typing",t.toString());

            }
        });
    }

    private void typing(final String reciever, final String sender) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Typing>> typing = apiInterface.typing(reciever,sender);
        typing.enqueue(new Callback<List<Typing>>() {
            @Override
            public void onResponse(Call<List<Typing>> call, Response<List<Typing>> response) {
                // TODO: 18/04/2019 Here we declare the value of the message Primary key
                String id = response.body().get(0).getP_k().toString();
//                p_k = response.body().get(0).getP_k();
                Log.d("User_ID",reciever+""+sender);
                p_k = id;
//                Toast.makeText(getApplicationContext(),p_k,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<List<Typing>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });
    }

    private void message_recieve(String reciever,String sender) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Messages>> r_chats = apiInterface.getMessages(reciever,sender);
        r_chats.enqueue(new Callback<List<Messages>>() {
            @Override
            public void onResponse(Call<List<Messages>> call, Response<List<Messages>> response) {
                messages = response.body();
                messageAdapter = new MessageAdapter(messages,getApplicationContext());
                recyclerView.setAdapter(messageAdapter);
//                Toast.makeText(getApplicationContext(),"Locaded",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<List<Messages>> call, Throwable t) {

            }
        });
    }

    private void sendMessage(final String receiver, final String sender, String message) {
        final String sRe=receiver.toString();
        final String sSend = sender.toString();
        final String sMsg = message.toString();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<SendMessage>> mChat = apiInterface.sendMessage(sRe,sSend,sMsg);
        mChat.enqueue(new Callback<List<SendMessage>>() {
            @Override
            public void onResponse(Call<List<SendMessage>> call, Response<List<SendMessage>> response) {
//                Toast.makeText(getApplicationContext(),"S",Toast.LENGTH_LONG).show();
                mMsg.setText("");
//                message_recieve(sender,receiver);
            }

            @Override
            public void onFailure(Call<List<SendMessage>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),sRe + sSend + sMsg+t.toString(),Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_select_send:
                selectImageToSend();
                break;
        }

    }

    private void selectImageToSend() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }else {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }

        /*Selecting a file which has a type of Image*/
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, img_req);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == img_req && resultCode == RESULT_OK && data != null) {
            mThumbnailViewGroup.setVisibility(View.VISIBLE);
            Uri path = data.getData();
//
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                mThumbnail.setImageBitmap(bitmap);
                /*A Life saver class from github: RealPathUtil with getRealPath() method*/
//                part_image = getRealPath(this,path);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
