package daromon.goodevent.retrofit

class AuthRequest {
    private var id: Long? = null
    private var username: String? = null

    fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    fun getId(): Long? {
        return id
    }

    fun setId(id: Long?) {
        this.id = id
    }
}