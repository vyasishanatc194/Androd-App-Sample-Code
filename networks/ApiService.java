package com.example.userlistexample;

import com.google.gson.JsonElement;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Citrusbug. 
 * User: Ishan Vyas 
 * Date: 10/01/19 
 * Time: 12:00 PM 
 * Title : WebService List
 * Description : This file will have  list of Web Service URL.
 */


public interface ApiService {

        @GET(ApiConstants.USER_LIST_URL)
        Call<JsonElement> doUserList(@Header(ApiConstants.TAGS.Authorization) String bearertoken);

        @GET(ApiConstants.PLAN_LIST_URL)
        Call<JsonElement> doPlanList(@Header(ApiConstants.TAGS.Authorization) String bearertoken);

        @POST(ApiConstants.ADD_USER_URL)
        @Multipart
        Call<JsonElement> doChildAdd(@Header(ApiConstants.TAGS.Authorization) String bearertoken,
                        @Query(ApiConstants.TAGS.username) String username,
                        @Query(ApiConstants.TAGS.first_name) String first_name,
                        @Query(ApiConstants.TAGS.last_name) String last_name, @Query(ApiConstants.TAGS.age) String age,
                        @Query(ApiConstants.TAGS.gender) String gender, @Query(ApiConstants.TAGS.dob) String dob,
                        @Query(ApiConstants.TAGS.phone) String phone,
                        @Query(ApiConstants.TAGS.school_name) String school_name,
                        @Query(ApiConstants.TAGS.school_district_no) String school_district_no,
                        @Query(ApiConstants.TAGS.state) String state,
                        @Query(ApiConstants.TAGS.user_type) String user_type,
                        @Query(ApiConstants.TAGS.plan_id) String plan_id,
                        @Query(ApiConstants.TAGS.amount) String amount,
                        @Query(ApiConstants.TAGS.token_id) String token_id,
                        @Query(ApiConstants.TAGS.fire_base_token) String fire_base_token,
                        @Query(ApiConstants.TAGS.password) String password,
                        @Query(ApiConstants.TAGS.confirm_password) String confirm_password,
                        @Part MultipartBody.Part image);

}
