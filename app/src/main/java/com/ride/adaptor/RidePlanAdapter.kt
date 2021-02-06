package com.ride.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ride.R
import com.ride.data.PlanResponse

import java.util.*

class RidePlanAdapter(
    private var context: Context,
    private var itemListener: ItemListener,
    private val planResponseList: List<PlanResponse>
) : RecyclerView.Adapter<RidePlanAdapter.ViewHolder>() {
    lateinit var mContext: Context
    private val mRandom = Random()
    lateinit var mItemListener: ItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        mContext = context
        mItemListener = itemListener
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val categoryData: PlanResponse = planResponseList[position]
        holder.name.text = "Plan :"+categoryData.plan
        holder.duration.text = "Duration :"+categoryData.duration.toString()
        holder.price.text = "Price :Rs."+categoryData.rate.toString()

    }

    override fun getItemCount(): Int = planResponseList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val duration: TextView = itemView.findViewById(R.id.duration)
    }

    interface ItemListener {
        fun onTap(id: Int?)
    }
}