package RetrofitInterfaces

import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface VerificationService {
    @GET("register/verify")
    fun sendVerificationMsg(@Query("email") email: String): Call<ResponseBody>


    @POST("register")
    fun sendRegisterMsg(@Body jso:RequestBody): Call<ResponseBody>

    @POST("login")
    fun sendLoginMsg(@Body jso:RequestBody): Call<ResponseBody>

    @POST("customUnit/create")
    fun sendCardMsg(@Header("Authorization") token:String, @Body jso:RequestBody): Call<ResponseBody>

    @GET("customUnit/mine")
    fun getCardMsg(@Header("Authorization") token:String,@Query("state") state: String): Call<ResponseBody>

    @FormUrlEncoded
    @PUT("customUnit/update")
    fun updateTextCardMsg(
        @Header("Authorization") token:String,
        @Field("id") id:String,
        @Field("record") record:String): Call<ResponseBody>


    @FormUrlEncoded
    @POST("wordGroup/create")
    fun createWordBookPlanMsg(
        @Header("Authorization") token:String,
        @Field("bookId") email:String,
        @Field("total") pwd:String): Call<ResponseBody>


    @FormUrlEncoded
    @PUT("wordGroup/newUnit")
    fun getDigitMsg(
        @Header("Authorization") token:String,
        @Field("groupId") groupId:String,
        @Field("listSize") listSize:String): Call<ResponseBody>



    @POST("wordList")
    fun getWByDMsg(@Header("Authorization") token:String, @Body jso:RequestBody): Call<ResponseBody>


    @GET("customUnit/mine")
    fun sendTryWordMsg(@Header("Authorization") token:String,@Query("state") state: String): Call<ResponseBody>



    @PUT("user/update")
    fun updateUserIformation(
        @Header("Authorization") token:String,
        @Body jso:RequestBody): Call<ResponseBody>

    @GET("board/mine")
    fun sendMissCountMsg(@Header("Authorization") token:String): Call<ResponseBody>


}