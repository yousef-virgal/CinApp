package com.example.taskweek4.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.taskweek4.R
import kotlinx.android.synthetic.main.mainactivity.*
class homeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.mainactivity)

        val navController = Navigation
            .findNavController(this, R.id.mainFragment)

        NavigationUI.setupWithNavController(navigationBar, navController)




    }
}








