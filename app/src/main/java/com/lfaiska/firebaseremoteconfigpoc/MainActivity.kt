package com.lfaiska.firebaseremoteconfigpoc

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class MainActivity : AppCompatActivity() {

    lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFirebaseRemoteConfig()
        fetchConfig()
    }

    fun loadFirebaseRemoteConfig() {
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        firebaseRemoteConfig.setDefaults(R.xml.remote_config_default_values)
        firebaseRemoteConfig.setConfigSettings(
                FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build())
    }

    private fun fetchConfig() {
        firebaseRemoteConfig.fetch(0).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                firebaseRemoteConfig.activateFetched()
                applyRemoteConfig()
            } else {
                // Fetch failed
            }
        }
    }

    fun applyRemoteConfig() {
        val textView = findViewById<TextView>(R.id.main_text_view)
        val remoteConfigFontColor = firebaseRemoteConfig.getString("font_color")
        textView.setTextColor(Color.parseColor(remoteConfigFontColor))
    }
}
