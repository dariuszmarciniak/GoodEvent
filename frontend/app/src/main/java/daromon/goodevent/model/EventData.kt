package daromon.goodevent.model

import android.os.Parcel
import android.os.Parcelable

data class EventData(
    val id: String?,
    val title: String?,
    val place: String?,
    val date: String?,
    val creator: UserModel?,
    val image: String?,
    val description: String?,
    var favouritesStatus: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(UserModel::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(place)
        parcel.writeString(date)
        parcel.writeParcelable(creator, flags)
        parcel.writeString(image)
        parcel.writeString(description)
        parcel.writeString(favouritesStatus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventData> {
        override fun createFromParcel(parcel: Parcel): EventData {
            return EventData(parcel)
        }

        override fun newArray(size: Int): Array<EventData?> {
            return arrayOfNulls(size)
        }
    }
}
