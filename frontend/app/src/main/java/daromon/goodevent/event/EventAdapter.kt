package daromon.goodevent.event

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView

import daromon.goodevent.R
import daromon.goodevent.model.EventData


class EventAdapter(val eventData: MutableList<EventData>): RecyclerView.Adapter<EventViewHolder>() {

    private lateinit var mListener : onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener)
    {
        mListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_event_item,parent,false)
        return EventViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        return holder.bindView(eventData[position])
    }

    override fun getItemCount(): Int {
        return eventData.size
    }

}

class EventViewHolder(itemView: View, listener: EventAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.eventListItemTitle)
    private val place: TextView = itemView.findViewById(R.id.eventListItemPlace)
    private val date: TextView = itemView.findViewById(R.id.eventListItemDate)
    private val creator: TextView = itemView.findViewById(R.id.eventListItemCreator)
    private val image: ImageView = itemView.findViewById(R.id.eventListItemImage)
    private val description: TextView = itemView.findViewById(R.id.descriptionListItemPlace)
    private val favouritesButton: Button = itemView.findViewById(R.id.favouritesButtonEvent)
    fun bindView(eventData: EventData) {

        title.text = eventData.title
        place.text= eventData.place
        date.text= eventData.date
        creator.text= eventData.creator!!.username.toString()
        description.text=eventData.description
        val imageBytes = Base64.decode(eventData.image, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        image.setImageBitmap(decodedImage)

        if(eventData.favouritesStatus == "0")
        {
            favouritesButton.setBackgroundResource(R.drawable.fav_black)

        }else favouritesButton.setBackgroundResource(R.drawable.fav_red)


    }

    init {
        itemView.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
    }


}