package ir.amirdaryabak.openweathermap.feature_home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ir.amirdaryabak.openweathermap.R
import ir.amirdaryabak.openweathermap.core.BaseFragment
import ir.amirdaryabak.openweathermap.core.utils.exhaustive
import ir.amirdaryabak.openweathermap.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()

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


        binding.apply {

        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            binding.apply {
                viewModel.tasksEvent.collect { event ->
                    when (event) {
                        is HomeViewModel.HandleEvent.ShowWeatherByCoordinatesLoading -> TODO()
                        is HomeViewModel.HandleEvent.GetWeatherByCoordinates -> TODO()
                        is HomeViewModel.HandleEvent.OnGetWeatherByCoordinatesError -> TODO()
                        is HomeViewModel.HandleEvent.OnGetWeatherByCoordinatesSuccess -> TODO()
                    }.exhaustive
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

