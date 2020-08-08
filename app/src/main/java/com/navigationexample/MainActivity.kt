package com.navigationexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var Back_Key_Before_Time: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment,R.id.searchFragment,R.id.myPageFragment,R.id.menuFragment)
        )
        navController = nav_host_fragment.findNavController()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.homeFragment, R.id.searchFragment, R.id.myPageFragment, R.id.menuFragment -> showBotNav()
                else -> hideBotNav()
            }
        }
        bottom_nav_bar.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_container).navigateUp(appBarConfiguration)
    }
    override fun onBackPressed() {
        if(appBarConfiguration.topLevelDestinations.contains(navController.currentDestination?.id)){
            if(System.currentTimeMillis() - Back_Key_Before_Time < 2000){
                finish()
            }else{
                Toast.makeText(application, "Exit?", Toast.LENGTH_SHORT).show()
                Back_Key_Before_Time = System.currentTimeMillis()
            }
            return;
        }
        super.onBackPressed()
    }
    private fun hideBotNav() {
        bottom_nav_bar.visibility = View.GONE
    }

    private fun showBotNav() {
        bottom_nav_bar.visibility = View.VISIBLE
    }

}