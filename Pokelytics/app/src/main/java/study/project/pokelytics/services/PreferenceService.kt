package study.project.pokelytics.services

import android.content.SharedPreferences

class PreferenceService (
        private val sharedPreferences: SharedPreferences
    ){
        fun savePreference(key: String, value: String?){
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
        }
        fun removePreference(key: String){
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.remove(key)
            editor.apply()
        }
        fun getPreference(key: String): String?{
            return sharedPreferences.getString(key, null)
        }
}