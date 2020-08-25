package com.flowz.forcastapp.display.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.flowz.forcastapp.R
import com.flowz.forcastapp.data.network.ConnectivityInterceptorImpl
import com.flowz.forcastapp.data.network.WeatherNetworkDataSoucreImpl
import com.flowz.forcastapp.data.network.WeatherstackApiService
import com.flowz.forcastapp.display.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

        //To change initializer of created properties use File | Settings | File Templates.

//    companion object {
//        fun newInstance() = CurrentWeatherFragment()
//
////        http://api.weatherstack.com/current?access_key=305f045993f33edb3670756aa132549c&query=New%20York
//    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        // TODO: Use the ViewModel

        bindUI()


//        val apiService = WeatherstackApiService(ConnectivityInterceptorImpl(this.context!!))
//        val weatherNetworkDataSoucre = WeatherNetworkDataSoucreImpl(apiService)
//
//
//        weatherNetworkDataSoucre.downloadedCurrentWeather.observe(viewLifecycleOwner, Observer {
//            textView.setText(it.toString())
//        })
//
//
//        GlobalScope.launch(Dispatchers.Main) {
//
//        weatherNetworkDataSoucre.fetchCurrentWeather("London", "en")
//
//        }
//
    }

    private fun bindUI() = launch{

        val currentWeather = viewModel.weather.await()

        textView.text = currentWeather.toString()
    }

}
