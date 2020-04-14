package com.earaujo.animationnew

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
    }

    private fun setup() {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.mainFrameLayout, MainFragment())
            .commit()
    }

    companion object {
        var position = 0
    }

}
