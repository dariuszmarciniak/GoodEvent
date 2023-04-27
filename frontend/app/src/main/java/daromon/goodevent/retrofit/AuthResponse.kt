package daromon.goodevent.retrofit

import daromon.goodevent.model.EventData

class AuthResponse {
    private var username: String? = null
    private var accessToken: String? = null
    private var idUser: Long? = null


    fun AuthResponse(username: String?, accessToken: String?, userId: Long ) {
        this.username = username
        this.accessToken = accessToken
        this.idUser=userId

    }

    fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    fun getAccessToken(): String? {
        return accessToken
    }

    fun setAccessToken(accessToken: String?) {
        this.accessToken = accessToken
    }
    fun getUserId() : Long?
    {
        return this.idUser
    }



}