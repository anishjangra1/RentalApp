package com.ride.home.booking.ride

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ride.databinding.ItemVehicleSpecificationBinding

class VehicleSpecAdapter : RecyclerView.Adapter<VehicleSpecAdapter.ViewHolder>(){

    class ViewHolder(
        private val binding: ItemVehicleSpecificationBinding,
    ) : RecyclerView.ViewHolder(binding.root){
        init {

        }

        fun bind(name: String){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemVehicleSpecificationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind("Local")
    }

    override fun getItemCount() = 3

}