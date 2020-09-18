package com.example.taskweek4.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.taskweek4.R
import kotlinx.android.synthetic.main.splashscreen.*


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        val bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation)

        setContentView(R.layout.splashscreen)
        appLogo.startAnimation(topAnim)
        appName.startAnimation(bottomAnim)
        appSloagan.startAnimation(bottomAnim)
        Handler().postDelayed({

            startActivity(Intent(this,homeActivity::class.java))

            finish()
        }, 4000)



    }
}