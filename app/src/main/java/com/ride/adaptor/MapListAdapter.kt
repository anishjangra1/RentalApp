package com.ride.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ride.data.Vehicle
import com.ride.databinding.ItemLocationBinding

class MapListAdapter (private val listener: VehicleItemListener) : RecyclerView.Adapter<MapListAdapter.ViewHolder>() {

    var data: List<Vehicle>? = null

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
        return ViewHolder(ItemLocationBinding.inflate(LayoutInflater.from(parent.context)), listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return if(data == null) 0 else data!!.size
    }

    fun submitData(list: List<Vehicle>){
        data = list
        notifyDataSetChanged()
    }

}

class VehicleItemListener( var clickListener: (vehicle: Vehicle) -> Unit){
    fun onClick(vehicle: Vehicle, view: View)  =  run {
        clickListener (vehicle)
    }
}