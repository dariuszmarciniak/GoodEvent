package daromon.goodevent.retrofit

import daromon.goodevent.event.Event
import daromon.goodevent.model.EventData
import daromon.goodevent.model.UserModel
import retrofit2.Call
import retrofit2.http.*


interface UserAPI {

    @GET("/user/getUsers")
    fun allUsers(): Call<List<UserModel>>

    @POST("/user/register")
    fun save(@Body userModel: UserModel): Call <Boolean>

    @POST("/user/login")
    fun login(@Body userModel: UserModel): Call <AuthResponse>

    @POST("/user/updatePassword")
    fun updatePassword(@Header("Authorization") token: String, @Body userModel: UserModel): Call<Boolean>

    @POST("/{userId}/favoriteEvents/{eventId}")
    fun saveToFavourites(@Header("Authorization") token: String, @Path("userId") userId: Long, @Path("eventId") eventId: Long): Call<Boolean>

    @DELETE("/{userId}/delete/favoriteEvents/{eventId}")
    fun deleteFromFavourites(@Header("Authorization") token: String, @Path("userId") userId: Long, @Path("eventId") eventId: Long): Call<Boolean>

    @POST("/{userId}/favorite-events")
    fun getFavourites(@Header("Authorization") token: String, @Path("userId") userId: Long): Call<MutableList<EventData>>


}