package jimmyliao.com.weather.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jimmyliao.com.weather.Model.Weather
import jimmyliao.com.weather.R
import kotlinx.android.synthetic.main.item_weather.view.*

class MainAdapter(val context: Context, val data: MutableList<Weather?>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val ITEM_WEATHER = 0
    private val ITEM_IMAGE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == ITEM_WEATHER) ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_weather, parent,false ))
        else ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemViewType(position: Int) = if (data[position] is Weather) ITEM_WEATHER else ITEM_IMAGE

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weather: Weather?){
            if(weather != null){
                itemView.tv_startTime.text = weather.startTime
                itemView.tv_endTime.text = weather.endTime
                val tempStr = weather.parameterName + weather.parameterUnit
                itemView.tv_temp.text = tempStr
            }
        }
    }
}