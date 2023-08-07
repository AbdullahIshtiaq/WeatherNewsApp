package com.example.weathernewsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathernewsapp.R
import com.example.weathernewsapp.databinding.WeatherItemLayoutBinding
import com.example.weathernewsapp.room.WeatherModel

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private var weatherList: List<WeatherModel> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item_layout, parent, false)
        return WeatherViewHolder(view)
    }

    fun setData(data: List<WeatherModel>) {
        weatherList = data.reversed()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bindLayout(weatherList[position])
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    inner class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = WeatherItemLayoutBinding.bind(view)

        fun bindLayout(item: WeatherModel) {
            binding.apply {
                tvLocationName.text = item.name
                tvDescription.text = item.description
                tvHumidity.text = "${item.humidity} %"
                tvTemperature.text = "${item.temp.toInt() - 273} °C"
                tvPressure.text = "${item.wind} m/s"
                tvDateTime.text = item.dateTime
            }
        }

    }
}