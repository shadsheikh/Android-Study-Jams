package farees.hussain.bunkmanager.db.preferences

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.preferencesKey

// to be added at the end of the application as a update

class SettingsManager(context: Context) {
    enum class UImode{
        LIGHT, DARK
    }
    var firstTime = true
    lateinit var userName : String
    var targetPercentage = 75

    private val dataStore = context.createDataStore(name = "settings_pref")

    companion object{
        val isLightMode = preferencesKey<Boolean>("light_mode")
    }
}