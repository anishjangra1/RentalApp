package com.ride

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ride.adaptor.RidePlanAdapter
import com.ride.data.PlanResponse
import com.ride.databinding.ActivityGetStartedBinding
import com.ride.viewmodels.GetPlanViewModel

class GetPlansActivity : AppCompatActivity(){
    var recyclerViewRidePlans: RecyclerView? = null
    var btnCountinueWithPhone: TextView? = null
    lateinit var binding: ActivityGetStartedBinding
    private lateinit var progressBar: ProgressBar
    private val  viewModel: GetPlanViewModel  by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        setContentView(R.layout.activity_ride_plans)

        initUi()
    }

    private fun initUi() {
        recyclerViewRidePlans = findViewById(R.id.recyclerViewRidePlans)
//        setDataInView()

//        getPlans()
    }

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btnCountinueWithPhone -> {
                val intent = Intent(this, GenerateOtpActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun getPlans(){
//                progressBar.visibility = View.VISIBLE
                viewModel.getPlans()
            }



private fun setDataInView(planArrayList: ArrayList<PlanResponse>) {
    recyclerViewRidePlans?.visibility = View.VISIBLE
    recyclerViewRidePlans?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    val adapter = RidePlanAdapter(this, object : RidePlanAdapter.ItemListener {
        override fun onTap(position: Int?) {

        }
    }, planArrayList)
    recyclerViewRidePlans?.adapter = adapter
}
}

