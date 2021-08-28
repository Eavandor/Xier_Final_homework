package RetrofitInterfaces


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Login2Service {
    @FormUrlEncoded
    @POST("login")
    fun sendLogin2Msg(
        @Field("email") email:String,
        @Field("verification") tel:String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("login")
    fun sendCreateAccountMsg(
        @Field("email") email:String,
        @Field("password") pwd:String,
        @Field("verification") tel:String): Call<ResponseBody>

    @GET("parking/queryParkAll")
    fun getVerificationCode(@Query("email") email: String): Call<ResponseBody>
}