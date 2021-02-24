package com.ride.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ride.data.Vehicle
import com.ride.databinding.ItemLocationBinding

class MapListAdapter (
    private val listener: VehicleItemListener,
    var data: List<Vehicle>
    ) : RecyclerView.Adapter<MapListAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: ItemLocationBinding,
        private val listener: VehicleItemListener
    ) : RecyclerView.ViewHolder(binding.root){
        init {

        }

        fun bind(vehicle: Vehicle){
            binding.vehicle = vehicle
            binding.clickListener = listener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    public fun changeData(list: List<Vehicle>){
        data = list
        notifyDataSetChanged()
    }

}

class VehicleItemListener( var clickListener: (vehicle: Vehicle) -> Unit){
    fun onClick(vehicle: Vehicle, view: View)  =  run {
        clickListener (vehicle)
    }
}