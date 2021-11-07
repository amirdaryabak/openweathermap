package ir.amirdaryabak.openweathermap.feature_weather_more_detail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import ir.amirdaryabak.openweathermap.R
import ir.amirdaryabak.openweathermap.core.BaseFragment
import ir.amirdaryabak.openweathermap.core.utils.exhaustive
import ir.amirdaryabak.openweathermap.databinding.FragmentWeatherDetailsBinding
import ir.amirdaryabak.openweathermap.feature_home.presentation.adapter.DailyWeatherAdapter
import kotlinx.coroutines.flow.collect
import java.util.*


@AndroidEntryPoint
class WeatherDetailFragment : BaseFragment(R.layout.fragment_weather_details) {

    private var _binding: FragmentWeatherDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<WeatherDetailsViewModel>()
    private val args by navArgs<WeatherDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            args.day.let {
                cityName.text = args.dayName
                temp.text = "${it.weatherList[0].main}"
                rainPercentage.text = "${it.rain.toInt()}%"
                tempMin.text = "${it.temp.min.toInt()}°C"
                tempMax.text = "${it.temp.max.toInt()}°C/"
                dummyTxtHumidity.text = "${it.humidity}"
                dummyTxtWindSpeed.text = "${it.wind_speed}"
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

