package com.ride.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ride.R
import com.ride.data.PlanResponse

import java.util.*
import kotlin.collections.ArrayList

class RidePlanAdapter(
    private var context: Context,
    private var itemListener: ItemListener,
    private val getRidePlanResponse: ArrayList<PlanResponse>
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

        val planResponse: PlanResponse = getRidePlanResponse[position]
        holder.tvPlanName.text = planResponse.plan

    }

    override fun getItemCount(): Int = getRidePlanResponse.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPlanName: TextView = itemView.findViewById(R.id.tvPlanName)
        val tvPlanPrice: TextView = itemView.findViewById(R.id.tvPlanPrice)
        val tvPlanDuration: ImageView = itemView.findViewById(R.id.tvPlanDuration)
    }

    interface ItemListener {
        fun onTap(id: Int?)
    }
}