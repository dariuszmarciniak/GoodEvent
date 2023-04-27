package daromon.goodevent.retrofit

import daromon.goodevent.model.EventModel
import daromon.goodevent.model.EventData
import retrofit2.Call
import retrofit2.http.*

interface EventAPI {

    @POST("/event/getEvents")
    fun allEvents(@Header("Authorization") token : String,  @Body userId: Long): Call<MutableList<EventData>>


    @POST("/event/save")
    fun  save(@Header("Authorization") token: String ,  @Body eventModel: EventModel): Call <Boolean>

    @POST("/event/getFav")
    fun  getFavEvents(@Header("Authorization") token: String ,  @Body authRequest: AuthRequest): Call<MutableList<EventData>>

    @DELETE("/event/delete/{id}")
    fun deleteEvent(@Header("Authorization") token: String, @Path("id") eventId: Long): Call<Boolean>


}