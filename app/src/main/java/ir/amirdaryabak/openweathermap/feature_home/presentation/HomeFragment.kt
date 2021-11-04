package ir.amirdaryabak.openweathermap.feature_home.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import ir.amirdaryabak.openweathermap.R
import ir.amirdaryabak.openweathermap.core.BaseFragment
import ir.amirdaryabak.openweathermap.core.utils.exhaustive
import ir.amirdaryabak.openweathermap.databinding.FragmentHomeBinding
import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic_daily.DailyEntity
import ir.amirdaryabak.openweathermap.feature_home.presentation.adapter.DailyWeatherAdapter
import kotlinx.coroutines.flow.collect
import java.util.*
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var dailyWeatherAdapter: DailyWeatherAdapter

    private lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>
    private var fineLocationPermissionGranted = false
    private var accessCoarseLocationPermissionGranted = false

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.MINUTES.toMillis(2)
            fastestInterval = TimeUnit.MINUTES.toMillis(5)
            maxWaitTime = TimeUnit.MINUTES.toMillis(10)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val currentLocation = locationResult.lastLocation
                viewModel.onEvent(
                    HomeViewModel.HandleEvent.GetWeatherByCoordinates(
                        currentLocation.latitude.toString(), currentLocation.longitude.toString()
                    )
                )
                viewModel.onEvent(
                    HomeViewModel.HandleEvent.GetWeatherDaily(
                        currentLocation.latitude.toString(), currentLocation.longitude.toString()
                    )
                )
            }
        }

        permissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                fineLocationPermissionGranted =
                    permissions[Manifest.permission.ACCESS_FINE_LOCATION]
                        ?: fineLocationPermissionGranted
                accessCoarseLocationPermissionGranted =
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION]
                        ?: accessCoarseLocationPermissionGranted
                if (fineLocationPermissionGranted && accessCoarseLocationPermissionGranted) {
                    try {
                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest, locationCallback, Looper.getMainLooper()
                        )
                    } catch (unlikely: SecurityException) {
                        // TODO
//                        Log.e(TAG, "Lost location permissions. Couldn't remove updates. $unlikely")
                    }
                } else {
                    updateOrRequestPermissions()
                }
            }

        updateOrRequestPermissions()

        binding.apply {

        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            binding.apply {
                viewModel.tasksEvent.collect { event ->
                    when (event) {
                        is HomeViewModel.HandleEvent.ShowWeatherByCoordinatesLoading -> {

                        }
                        is HomeViewModel.HandleEvent.GetWeatherByCoordinates -> Unit
                        is HomeViewModel.HandleEvent.OnGetWeatherByCoordinatesSuccess -> {
                            event.geographicEntity?.let { content ->
                                cityName.text = "${content.name}, ${content.sys.country}"
                                temp.text = "${content.main.temp.toInt()}°C"
                                weatherMain.text = content.weatherList[0].main
                            }
                        }

                        is HomeViewModel.HandleEvent.OnGetWeatherByCoordinatesError -> {

                        }
                        is HomeViewModel.HandleEvent.ShowWeatherDailyLoading -> {

                        }
                        is HomeViewModel.HandleEvent.GetWeatherDaily -> {

                        }
                        is HomeViewModel.HandleEvent.OnGetWeatherDailySuccess -> {
                            event.geographicDailyEntity?.let { content ->
                                setupRecyclerView(content.dailyList)
                            }
                        }
                        is HomeViewModel.HandleEvent.OnGetWeatherDailyError -> {

                        }
                    }.exhaustive
                }
            }
        }
    }

    private fun setupAdapter(content: List<DailyEntity>) {
        dailyWeatherAdapter = DailyWeatherAdapter(
            clickListener = { item, position ->

            },
        )
        dailyWeatherAdapter.submitList(content)
    }

    private fun setupRecyclerView(content: List<DailyEntity>) {
        setupAdapter(content)
        with(binding.rvWeekDays) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = dailyWeatherAdapter
        }
    }

    private fun updateOrRequestPermissions() {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        fineLocationPermissionGranted = hasFineLocationPermission
        accessCoarseLocationPermissionGranted = hasAccessCoarseLocationPermission

        val permissionsToRequest = mutableListOf<String>()
        if (!accessCoarseLocationPermissionGranted) {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (!fineLocationPermissionGranted) {
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (permissionsToRequest.isNotEmpty()) {
            permissionsLauncher.launch(permissionsToRequest.toTypedArray())
        }
        if (fineLocationPermissionGranted && accessCoarseLocationPermissionGranted) {
            try {
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest, locationCallback, Looper.getMainLooper()
                )
            } catch (unlikely: SecurityException) {
                // TODO
//                Log.e(TAG, "Lost location permissions. Couldn't remove updates. $unlikely")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

