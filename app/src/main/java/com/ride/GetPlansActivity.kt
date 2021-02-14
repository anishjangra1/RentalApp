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
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ride.adaptor.RidePlanAdapter
import com.ride.data.PlanResponse
import com.ride.databinding.ActivityGetStartedBinding
import com.ride.viewmodels.GetPlanViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GetPlansActivity : AppCompatActivity(){
    private var recyclerViewRidePlans: RecyclerView? = null
    var tvContinue: TextView? = null

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
        registerObserver()
    }

    private fun initUi() {
        recyclerViewRidePlans = findViewById(R.id.recyclerViewRidePlans)
//        binding.btnCountinueWithPhone.setOnClickListener(clickListener)

        getPlans()
    }

    override fun startSearch(
        initialQuery: String?,
        selectInitialQuery: Boolean,
        appSearchData: Bundle?,
        globalSearch: Boolean
    ) {
        super.startSearch(initialQuery, selectInitialQuery, appSearchData, globalSearch)
    }

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btnCountinueWithPhone -> {
//                val intent = Intent(this, GenerateOtpActivity::class.java)
//                startActivity(intent)
            }
        }
    }
    private fun getPlans(){
//                progressBar.visibility = View.VISIBLE
                viewModel.getPlans()
    }


    private fun setDataInView(planResponseList: List<PlanResponse>) {
        recyclerViewRidePlans?.visibility = View.VISIBLE
        recyclerViewRidePlans?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = RidePlanAdapter(this, object : RidePlanAdapter.ItemListener {
            override fun onTap(position: Int?) {

            }
        }, planResponseList)
        recyclerViewRidePlans?.adapter = adapter
    }


    private fun registerObserver(){
        viewModel.planResponseList.observe(this) { planResponseList ->
            setDataInView(planResponseList)

        }
//        viewModel.planResponseList().observe(this, { fruitlist ->
//            // update UI
//            val adapter: ArrayAdapter<String> = ArrayAdapter<Any?>(
//                this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, fruitlist
//            )
//            // Assign adapter to ListView
//            listView.setAdapter(adapter)
//            progressBar.visibility = View.GONE
//        })

    }
}


