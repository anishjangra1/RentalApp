package com.ride.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ride.data.Plan
import com.ride.databinding.ItemPlanBinding

class RidePlanAdapter(
    private val planList: List<Plan>,
    private var itemListener: ItemListener<Plan>
) : RecyclerView.Adapter<RidePlanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(planList[position])
    }

    override fun getItemCount(): Int = planList.size

    inner class ViewHolder(val binding: ItemPlanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plan: Plan){
            binding.itemClickListener = itemListener
            binding.plan = plan
            binding.executePendingBindings()
        }
    }
}