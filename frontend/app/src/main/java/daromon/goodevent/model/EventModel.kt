package daromon.goodevent.model


class EventModel {

    private var id = 0
    private var title: String? = null
    private var date: String? = null
    private var place: String? = null
    private var creator : UserModel? = null
    private var image: String? = null
    private var description: String? = null
    private var favouritesStatus: String? = null

    fun getDescription(): String? {
        return this.description
    }

    fun setDescription(description: String) {
        this.description = description
    }


    fun getFavouritesStatus(): String? {
        return favouritesStatus
    }

    fun setFavouritesStatus(favoritesStatus: String?) {
        this.favouritesStatus = favoritesStatus
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getDate(): String? {
        return this.date
    }

    fun setDate(date: String) {
        this.date = date
    }


    fun getPlace(): String? {
        return place
    }

    fun setPlace(place: String?) {
        this.place = place
    }

    fun getCreator(): UserModel? {
        return creator
    }

    fun setCreator(creator: UserModel) {
        this.creator = creator
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }

    override fun toString(): String {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", place='" + place + '\'' +
                ", creatorId=" + creator +
                ", image='" + image + '\'' +
                ", favStatus='" + favouritesStatus + '\'' +
                '}'
    }

}