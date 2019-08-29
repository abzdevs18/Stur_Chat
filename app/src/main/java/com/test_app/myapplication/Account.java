package com.test_app.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test_app.myapplication.api.ApiClient;
import com.test_app.myapplication.api.ApiInterface;
import com.test_app.myapplication.model.AccountData;
import com.test_app.myapplication.model.Profile_Update;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.test_app.myapplication.Imported.RealPathUtil.getRealPath;

public class Account extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView mProfile, mSelectBtn;
    private Button mSave,mLogOut;
    private static final int img_req = 777;
    private Bitmap bitmap;
    private String part_uri;
    private String part_image;
    private ProgressDialog pd;
    /*
    * User data initializing*/
    TextInputEditText mUsrName;
    TextInputEditText mUsrLName;
    TextInputEditText mUsrEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        pd = new ProgressDialog(this);
        mProfile = findViewById(R.id.circleImageView);
        mSave = findViewById(R.id.update_save_btn);
        mSave.setOnClickListener(this);
        mSelectBtn = findViewById(R.id.prof_pic_update_btn);
        mSelectBtn.setOnClickListener(this);
        mLogOut = findViewById(R.id.ac_logout);
        mLogOut.setOnClickListener(this);

        /*Fiding the fields*/
        mUsrName = (TextInputEditText) findViewById(R.id.fname);
        mUsrLName = (TextInputEditText) findViewById(R.id.lname);
        mUsrEmail = (TextInputEditText) findViewById(R.id.email);
        /*We get this from/during we login*/
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String user_pk = sharedPreferences.getString("p_k", "");
        prof(user_pk);


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
                        .into(mProfile);
                mUsrName.setText(response.body().get(0).getUsername());
                mUsrLName.setText(response.body().get(0).getLastname());
                mUsrEmail.setText(response.body().get(0).getEmail());
//                Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<AccountData>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_save_btn:
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        updateProfile();
//                    }
//                };
//                Thread thread = new Thread(runnable);
//                thread.setName("update");
//                thread.start();
                pd.setCancelable(false);
                pd.setMessage("Uploading....");
                pd.show();
                updateProfile();
                break;
            case R.id.prof_pic_update_btn:
                selectImage();
                break;
            case R.id.ac_logout:
                logOut();
                break;
        }

    }

    private void logOut() {
        /*We get this from/during we login*/
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    startActivity(new Intent(Account.this,Login.class));
    }

    private void updateProfile() {
        /*We get this from/during we login*/
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String user_pk = sharedPreferences.getString("p_k", "");

        RequestBody id = RequestBody.create(MultipartBody.FORM, user_pk);

        File file = new File(part_image);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo",file.getName(),requestBody);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Profile_Update>> updateCall = apiInterface.profUpdate(id,body);
        updateCall.enqueue(new Callback<List<Profile_Update>>() {
            @Override
            public void onResponse(Call<List<Profile_Update>> call, Response<List<Profile_Update>> response) {
                Log.d("Helo",response.message());
                pd.hide();

            }

            @Override
            public void onFailure(Call<List<Profile_Update>> call, Throwable t) {
                Log.d("Helo",t.toString());
                pd.hide();

            }
        });
        Log.d("P_K",user_pk);
        Log.d("IMG",requestBody.toString());

    }

    private void selectImage() {
        Intent intent;// = new Intent();
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
            Uri path = data.getData();
//
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                mProfile.setImageBitmap(bitmap);
                /*A Life saver class from github: RealPathUtil with getRealPath() method*/
                part_image = getRealPath(this,path);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*The result of the above method is bitmap
     * so we convert it with the below's method into string
     * to store it in our DB
     * */
    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private String realPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_icx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_icx);
        Log.d("LOGCAT", result);
        cursor.close();
        return result;
    }

    /*This only works if the device is kitkat API 19*/
//    private String getRealPath(Context context,Uri uri){
//        Cursor cursor = null;
//        try{
//            String[] projection = {MediaStore.Images.Media.DATA};
//            cursor = context.getContentResolver().query(uri,projection,null,null,null);
//            int clumn_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(clumn_index);
//
//        }catch (Exception e){
//            Log.e("Error","getRealPath Exception : "+e.toString());
//            return "";
//        }finally {
//            if (cursor != null ){
//                cursor.close();
//            }
//        }
//    }
}