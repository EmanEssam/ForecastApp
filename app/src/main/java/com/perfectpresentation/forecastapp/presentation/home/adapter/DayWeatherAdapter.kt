package com.perfectpresentation.forecastapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.perfectpresentation.forecastapp.data.model.Forecastday
import com.perfectpresentation.forecastapp.databinding.ItemWeatherDayBinding

class DayWeatherAdapter(
    private val weatherList: List<Forecastday>,
    private val onItemClicked: (forecastDay: Forecastday) -> Unit
) :
    RecyclerView.Adapter<DayWeatherAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemWeatherDayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: Forecastday) {
            Glide.with(itemView.context).load("https:" + weather.day.condition.icon)
                .into(binding.icCondition)
            binding.tvDay.text = weather.date
            binding.tvCondition.text = weather.day.condition.text
            (weather.hour.first().temp_c.toInt().toString() + "°C").also { binding.tvDegree.text = it }
            itemView.setOnClickListener {
                onItemClicked.invoke(weather)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWeatherDayBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount() = weatherList.size
}