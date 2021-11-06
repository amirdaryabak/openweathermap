package ir.amirdaryabak.openweathermap.core.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.amirdaryabak.openweathermap.R
import ir.amirdaryabak.openweathermap.core.utils.networkCapabilities.ConnectionLiveData
import ir.amirdaryabak.openweathermap.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.findNavController()


        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this) {
            setUpIsNetworkAvailableView(it)
        }

    }

    private fun setUpIsNetworkAvailableView(it: Boolean) {
        if (it) {
            binding.apply {
                isNetworkAvailable.visibility = View.GONE
                txtIsNetworkAvailable.visibility = View.GONE
            }
        } else {
            binding.apply {
                isNetworkAvailable.setBackgroundColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.purple_700
                    )
                )
                txtIsNetworkAvailable.text = "No Internet (Swipe to refresh)"
                isNetworkAvailable.visibility = View.VISIBLE
                txtIsNetworkAvailable.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}