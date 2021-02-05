package com.ride.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils

import com.google.gson.Gson


object PreferenceHelper {
    private var sp: SharedPreferences? = null

    public fun storeValueInPreference(context: Context, key: String, value: String?) {
        sp = context.getSharedPreferences("your_prefs", Activity.MODE_PRIVATE)
        val editor = sp!!.edit()
        editor.putString(key, value)
        editor.commit()
    }

    public fun getValueFromPreference(context: Context, keyValue: String): String {
        sp = context.getSharedPreferences("your_prefs", Activity.MODE_PRIVATE)
        return sp!!.getString(keyValue, "").toString()
    }


    fun clearPreferenceData(context: Context) {
        val preferences = context.getSharedPreferences("your_prefs", Activity.MODE_PRIVATE)
        preferences.edit().remove(keyValue.fullName.name).commit()
        preferences.edit().remove(keyValue.email.name).commit()
        preferences.edit().remove(keyValue.profilePic.name).commit()
        preferences.edit().remove(keyValue.token.name).commit()
        preferences.edit().remove(keyValue.userId.name).commit()
        preferences.edit().remove(keyValue.profile.name).commit()
        preferences.edit().remove(keyValue.pharmacyCartCount.name).commit()
        preferences.edit().remove(keyValue.labCartCount.name).commit()
        preferences.edit().remove(keyValue.fireBaseToken.name).commit()
        preferences.edit().remove(keyValue.isFirstTime.name).commit()
    }

    fun saveData(context: Context, key: String?, value: String?) {
        storeValueInPreference(context, key!!, value)
    }

    fun getData(context: Context, value: String?): String {
        return getValueFromPreference(context, value!!)
    }

    fun saveToken(context: Context, token: String?) {
        storeValueInPreference(context, keyValue.token.name, token)
    }

    fun getToken(context: Context): String {
        return getValueFromPreference(context, keyValue.token.name)
    }

    fun saveLabCartCount(context: Context, labCartCount: String?) {
        storeValueInPreference(context, keyValue.labCartCount.name, labCartCount)
    }

    fun savePharmacyCartCount(context: Context, pharmacyCartCount: String?) {
        storeValueInPreference(context, keyValue.pharmacyCartCount.name, pharmacyCartCount)
    }

    fun saveHealthTipsData(context: Context, healthTipData: String?) {
        storeValueInPreference(context, keyValue.healthTip.name, healthTipData)
    }



    fun clearLabPrefrences(context: Context) {
        saveLabcartData(context, "")
        saveLabData(context, "")
        saveData(context, Constant.CATEGORY_ID, "")
        saveData(context, Constant.CATEGORY_TYPE, "")

    }

    fun saveLabcartData(context: Context, labcart: String?) {
        storeValueInPreference(context, keyValue.labcart.name, labcart)
    }


    fun saveLabData(context: Context, labData: String?) {
        storeValueInPreference(context, keyValue.labData.name, labData)
    }

    fun saveFireBaseToken(context: Context, fcmToken: String?) {
        storeValueInPreference(context, keyValue.fireBaseToken.name, fcmToken)
    }



    fun saveProfileData(context: Context, profileData: String?) {
        storeValueInPreference(context, keyValue.profile.name, profileData)
    }

//    fun getProfileData(context: Context): ProfileData? {
//        val profileStr: String = getValueFromPreference(context, keyValue.profile.name)
//        val gson = Gson()
//        var profileData: ProfileData? = null
//        if (!TextUtils.isEmpty(profileStr)) {
//            profileData = gson.fromJson(profileStr, ProfileData::class.java)
//        }
//        return profileData
//    }

    fun saveUserId(context: Context, token: String?) {
        storeValueInPreference(context, keyValue.userId.name, token)
    }
    fun saveFirstTime(context: Context, istTime: String) {
        storeValueInPreference(context, keyValue.isFirstTime.name, istTime)

    }
    public fun getIsFirstTime(context: Context): String {
        return getValueFromPreference(context, keyValue.isFirstTime.name)

    }

    fun getUserId(context: Context): String {
        return getValueFromPreference(context, keyValue.userId.name)
    }

    fun getPharmacyCartCount(context: Context): String {
        return getValueFromPreference(context, keyValue.pharmacyCartCount.name)
    }

    fun getLabCartCount(context: Context): String {
        return getValueFromPreference(context, keyValue.labCartCount.name)
    }

    fun getFireBaseToken(context: Context): String {
        return getValueFromPreference(context, keyValue.fireBaseToken.name)
    }


    enum class keyValue {
        fullName,
        email,
        profilePic,
        token,
        userId,
        healthTip,
        profile,
        pharmacyCartCount,
        labCartCount,
        labData,
        labcart,
        isFirstTime,
        fireBaseToken
    }
}
