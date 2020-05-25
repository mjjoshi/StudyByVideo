package com.android.studybyvideo.ApiClient;


import com.android.studybyvideo.ResponseOtp;
import com.android.studybyvideo.model.LoginResponse;
import com.android.studybyvideo.model.ReponseAboutUs;
import com.android.studybyvideo.model.ReponseQuestionBank;
import com.android.studybyvideo.model.ReponseReview;
import com.android.studybyvideo.model.ReponseReviewList;
import com.android.studybyvideo.model.ResponseChapter;
import com.android.studybyvideo.model.ResponseCoursesListModel;
import com.android.studybyvideo.model.ResponseLogin;
import com.android.studybyvideo.model.ResponseReports;
import com.android.studybyvideo.model.ResponseSumbit;
import com.android.studybyvideo.model.ResponseTestName;
import com.android.studybyvideo.model.ResponseTestType;
import com.android.studybyvideo.model.ResponseVideo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ApiInterface {

@FormUrlEncoded
    @POST("studybyvideo/")
    Call<ResponseLogin> sendOtp(@Header("Content-Type") String contenttype,
                                @Field("api_name") String name,
                                @Field("mobile") String param);
@FormUrlEncoded
    @POST("studybyvideo/")
    Call<ResponseOtp> verifyOtp(@Header("Content-Type") String contenttype,
                                @Field("api_name") String name,
                                @Field("mobile") String param,
                                @Field("otp") String otp,
                                @Field("deviceId") String deviceId,
                                @Field("deviceType") String deviceType);

@FormUrlEncoded
    @POST("studybyvideo/")
    Call<ResponseCoursesListModel> getAllCourses(@Header("Content-Type") String contenttype,
                                                 @Field("api_name") String name,
                                                 @Field("mobile") String param,
                                                 @Field("teacher_id") String teacher_id);

@FormUrlEncoded
    @POST("studybyvideo/")
    Call<ResponseChapter> getAllChapters(@Header("Content-Type") String contenttype,
                                         @Field("api_name") String name,
                                         @Field("bookName") String bookName);
@FormUrlEncoded
    @POST("studybyvideo/")
    Call<ResponseVideo> getVideoOfChapter(@Header("Content-Type") String contenttype,
                                       @Field("api_name") String name,
                                       @Field("chapterId") String bookName);
@FormUrlEncoded
    @POST("studybyvideo/")
    Call<ReponseQuestionBank> getQuestionBank(@Header("Content-Type") String contenttype,
                                              @Field("api_name") String name,
                                              @Field("chapterId") String bookName,@Field("bookId") String bookId);

@FormUrlEncoded
    @POST("studybyvideo/")
    Call<ReponseQuestionBank> getTestQuestionBank(@Header("Content-Type") String contenttype,
                                              @Field("api_name") String name,
                                              @Field("ttname_id") String bookName);
@FormUrlEncoded
    @POST("studybyvideo/")
    Call<ReponseReview> getReviewedQuestionsList(@Header("Content-Type") String contenttype,
                                                 @Field("api_name") String name,
                                                 @Field("ttname_id") String bookName);

@FormUrlEncoded
    @POST("studybyvideo/")
    Call<ReponseAboutUs> getSupportDetails(@Header("Content-Type") String contenttype,
                                           @Field("api_name") String name);
@FormUrlEncoded
    @POST("studybyvideo/")
    Call<ResponseTestType> getTestTypes(@Header("Content-Type") String contenttype,
                                        @Field("api_name") String name,
                                        @Field("teacher_id") String teacher_id);

@FormUrlEncoded
    @POST("studybyvideo/")
    Call<ResponseTestName> getTestTypeNames(@Header("Content-Type") String contenttype,
                                            @Field("api_name") String name, @Field("testType") String testType,
                                            @Field("mobile") String param,
                                            @Field("teacher_id") String teacher_id);

    @FormUrlEncoded
    @POST("studybyvideo/")
    Call<ReponseQuestionBank> addAnswerOfQuestion(@Header("Content-Type") String contenttype,
                                              @Field("api_name") String name,
                                              @Field("book_id") String book_id,@Field("chapter_id") String chapter_id,
                                              @Field("user_mobile") String user_mobile,@Field("question_number") String question_number,
                                              @Field("answer_marked") String answer_marked,@Field("correct_answer") String correct_answer,
                                              @Field("check_status") boolean check_status,@Field("time_taken_to_solve") String time_taken_to_solve
            ,@Field("flag") String flag  ,@Field("ttname_id") String ttname_id ,@Field("is_submit") String is_submit,@Field("client_id") String client_id
                                              );
    @FormUrlEncoded
    @POST("studybyvideo/")
    Call<ResponseSumbit> submit(@Header("Content-Type") String contenttype,
                                @Field("api_name") String name,
                                @Field("book_id") String book_id, @Field("chapter_id") String chapter_id,
                                @Field("user_mobile") String user_mobile, @Field("question_number") String question_number,
                                @Field("answer_marked") String answer_marked, @Field("correct_answer") String correct_answer,
                                @Field("check_status") boolean check_status, @Field("time_taken_to_solve") String time_taken_to_solve
            , @Field("flag") String flag  , @Field("ttname_id") String ttname_id , @Field("is_submit") String is_submit, @Field("client_id") String client_id
                                              );

    @FormUrlEncoded
    @POST("studybyvideo/")
    Call<ResponseReports> getTestResultList(@Header("Content-Type") String contenttype,
                                            @Field("api_name") String name,
                                            @Field("client_id") String client_id
                                );

    @FormUrlEncoded
    @POST("studybyvideo/")
    Call<ResponseBody> addQuestionByStudent(@Header("Content-Type") String contenttype,
                                            @Field("api_name") String name,
                                            @Field("client_id") String client_id,
                                            @Field("question") String question,
                                            @Field("chapter_id") String chapter_id
                                );


}
