package com.ride.data

data class GetPlansResponse (val plans: List<Plan>){

    private var selectedPlan: Plan? = null

    fun selectPlan(plan: Plan){
        if (selectedPlan != null) {
            selectedPlan!!.isPlanSelected = false
        }
        selectedPlan = plan.also {
            it.isPlanSelected = true
        }
    }
}