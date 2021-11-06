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
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import ir.amirdaryabak.openweathermap.R
import ir.amirdaryabak.openweathermap.core.BaseFragment
import ir.amirdaryabak.openweathermap.core.utils.exhaustive
import ir.amirdaryabak.openweathermap.core.utils.hideKeyboard
import ir.amirdaryabak.openweathermap.core.utils.navigateSafely
import ir.amirdaryabak.openweathermap.databinding.FragmentHomeBinding
import ir.amirdaryabak.openweathermap.feature_home.domain.entity.geographic_daily.DailyEntity
import ir.amirdaryabak.openweathermap.feature_home.presentation.adapter.DailyWeatherAdapter
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
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
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
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
                    requestLocation()
                } else {
                    updateOrRequestPermissions()
                }
            }

        updateOrRequestPermissions()

        binding.apply {
            tieName.setOnEditorActionListener { _, _, _ ->
                viewModel.onEvent(
                    HomeViewModel.HandleEvent.GetWeatherByCityName(
                        tieName.text.toString()
                    )
                )
                tieName.setText("")
                requireActivity().hideKeyboard(tieName)
                true
            }

            swipeRefreshLayout.setOnRefreshListener {
                swipeRefreshLayout.isRefreshing = false
                requestLocation()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            binding.apply {
                viewModel.tasksEvent.collect { event ->
                    when (event) {
                        is HomeViewModel.HandleEvent.ShowLoading -> {
                            toggleLoading(true)
                        }
                        is HomeViewModel.HandleEvent.GetWeatherByCoordinates -> Unit
                        is HomeViewModel.HandleEvent.OnGetWeatherByCoordinatesSuccess -> {
                            toggleLoading(false)
                            event.geographicEntity?.let { content ->
                                cityName.text = "${content.name}, ${content.sys.country}"
                                temp.text = "${content.main.temp.toInt()}°C"
                                weatherMain.text = content.weatherList[0].main
                            }
                        }

                        is HomeViewModel.HandleEvent.OnGetWeatherByCoordinatesError -> {
                            showErrorView(event.errorMessage)
                        }
                        is HomeViewModel.HandleEvent.GetWeatherDaily -> Unit
                        is HomeViewModel.HandleEvent.OnGetWeatherDailySuccess -> {
                            toggleLoading(false)
                            event.geographicDailyEntity?.let { content ->
                                setupRecyclerView(content.dailyList)
                            }
                        }
                        is HomeViewModel.HandleEvent.OnGetWeatherDailyError -> {
                            showErrorView(event.errorMessage)
                        }

                        is HomeViewModel.HandleEvent.GetWeatherByCityName -> Unit
                        is HomeViewModel.HandleEvent.OnGetWeatherByCityNameSuccess -> {
                            toggleLoading(false)
                            event.geographicEntity?.let { content ->
                                cityName.text = "${content.name}, ${content.sys.country}"
                                temp.text = "${content.main.temp.toInt()}°C"
                                weatherMain.text = content.weatherList[0].main

                                viewModel.onEvent(
                                    HomeViewModel.HandleEvent.GetWeatherDaily(
                                        content.coordinates.lat.toString(), content.coordinates.lat.toString()
                                    )
                                )
                            }
                        }
                        is HomeViewModel.HandleEvent.OnGetWeatherByCityNameError -> {
                            showErrorView(event.errorMessage)
                        }
                    }.exhaustive
                }
            }
        }
    }

    private fun showErrorView(errorMessage: String?) {
        binding.apply {
            loadingView.isVisible = true
            errorTxt.isVisible = true
            loadingProgress.isVisible = false
            errorTxt.text = errorMessage
        }
    }

    private fun toggleLoading(bool: Boolean) {
        binding.apply {
            loadingView.isVisible = bool
            loadingProgress.isVisible = bool
            errorTxt.isVisible = false
        }
    }

    private fun getNameOfWeekDay(): MutableList<String> {
        val list = mutableListOf<String>()
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        val calendar = Calendar.getInstance()
        list.add("Today")

        for (i in 0 until 7) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            val day = calendar.time
            val dayName = sdf.format(day)
            list.add(dayName)
        }

        return list
    }

    private fun setupAdapter(content: List<DailyEntity>) {
        dailyWeatherAdapter = DailyWeatherAdapter(
            weekDaysName = getNameOfWeekDay(),
            clickListener = { item, position ->
                findNavController().navigateSafely(
                    resId = R.id.action_homeFragment_to_weatherDetailFragment,
                    args = Bundle().apply {
                        putSerializable("day", item)
                    }
                )
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
            requestLocation()
        }
    }

    private fun requestLocation() {
        try {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
            )
        } catch (unlikely: SecurityException) {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

