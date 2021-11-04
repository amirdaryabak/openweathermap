package ir.amirdaryabak.openweathermap.feature_home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.amirdaryabak.openweathermap.databinding.WeekDayItemBinding
import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic_daily.DailyEntity

class DailyWeatherAdapter(
    private val weekDaysName: List<String>,
    private val clickListener: (DailyEntity, Int) -> Unit,
) : ListAdapter<DailyEntity, DailyWeatherAdapter.MyViewHolder>(DiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = WeekDayItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dailyEntity: DailyEntity = getItem(position)
        holder.bindData(dailyEntity)
    }

    override fun getItemId(position: Int): Long = getItem(position).hashCode().toLong()

    inner class MyViewHolder(private val binding: WeekDayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(dailyEntity: DailyEntity) {
            binding.apply {
                dailyEntity.let {
                    dayName.text = "${weekDaysName[adapterPosition]}"
                    rainPercentage.text = "${it.rain.toInt()}%"
                    tempMin.text = "${it.temp.min.toInt()}°C"
                    tempMax.text = "${it.temp.max.toInt()}°C/"
                }
                root.setOnClickListener {
                    clickListener.invoke(dailyEntity, adapterPosition)
                }
            }

        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<DailyEntity>() {

        override fun areItemsTheSame(oldItem: DailyEntity, newItem: DailyEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DailyEntity, newItem: DailyEntity): Boolean {
            return oldItem == newItem
        }

    }

}
