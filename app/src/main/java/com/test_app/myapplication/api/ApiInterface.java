package com.test_app.myapplication.api;

import com.test_app.myapplication.model.AccountData;
import com.test_app.myapplication.model.Is_Typing;
import com.test_app.myapplication.model.Messages;
import com.test_app.myapplication.model.Profile_Update;
import com.test_app.myapplication.model.Registration;
import com.test_app.myapplication.model.SendMessage;
import com.test_app.myapplication.model.SignIn;
import com.test_app.myapplication.model.Typing;
import com.test_app.myapplication.model.Users;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("db_functions/registration.php")
    Call<List<Registration>> signup(@Field("username") String username,
                                    @Field("lastname") String lastname,
                                    @Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("db_functions/login.php")
    Call<List<SignIn>> signIn(@Field("email") String email,
                             @Field("password") String password);
    @FormUrlEncoded
    @POST("db_functions/Account_Details.php")
    Call<List<AccountData>> accountData(@Field("p_k") String email);

    /*We have fields below
    * because even though we display the user during login instantly
    * We use same Interface during user searching
    * */

    @FormUrlEncoded
    @POST("db_functions/users.php")
    Call<List<Users>> userList(@Field("p_k") String p_k,
                               @Field("query") String query);

    @FormUrlEncoded
    @POST("db_functions/send_msg.php")
    Call<List<SendMessage>> sendMessage(@Field("receiver") String receiver,
                                        @Field("sender") String sender,
                                        @Field("message") String message);
    @FormUrlEncoded
    @POST("db_functions/msgs.php")
    Call<List<Messages>> getMessages(@Field("reciever") String reciever,
                                     @Field("sender") String sender);

    @FormUrlEncoded
    @POST("db_functions/typing.php")
    Call<List<Typing>> typing(@Field("reciever") String reciever,
                              @Field("sender") String sender);

    @FormUrlEncoded
    @POST("db_functions/is_typing.php")
    Call<List<Is_Typing>> is_typing(@Field("reciever") String reciever,
                                    @Field("sender") String sender);
    @FormUrlEncoded
    @POST("db_functions/update_typing.php")
    Call<List<Is_Typing>> updateTyping(@Field("p_k") String p_k);

    @Multipart
    @POST("db_functions/prof_update.php")
    Call<List<Profile_Update>> profUpdate(@Part("p_k") RequestBody p_k,
                                  @Part MultipartBody.Part photo);
}
