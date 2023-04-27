package daromon.goodevent.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson

class RetrofitService {
    private var  retrofit = retrofit2.Retrofit.Builder()
        .baseUrl("http://192.168.0.135:8080")
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    open fun getRetrofit(): Retrofit? {
        return retrofit
    }
}