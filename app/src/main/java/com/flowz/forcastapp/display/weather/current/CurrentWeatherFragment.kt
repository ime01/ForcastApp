package com.flowz.forcastapp.display.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.flowz.forcastapp.R
import com.flowz.forcastapp.data.network.ConnectivityInterceptorImpl
import com.flowz.forcastapp.data.network.WeatherNetworkDataSoucreImpl
import com.flowz.forcastapp.data.network.WeatherstackApiService
import com.flowz.forcastapp.display.base.ScopedFragment
import com.flowz.forcastapp.internal.glide.GlideApp
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

        group_loading.visibility = View.GONE

        updateLocation("Toronto")
        updateDateToToday()
        updateTemperature(currentWeather.temperature, currentWeather.feelslike)
        updateCondition(currentWeather.weatherDescriptions[0])
        updatePrecipitation(currentWeather.precip)
        updateWind(currentWeather.windDir, currentWeather.windSpeed, currentWeather.windDegree)
        updateVisibility(currentWeather.visibility)

        GlideApp.with(this@CurrentWeatherFragment)
            .load("${currentWeather.weatherIcons[0]}")
            .into(weather_conditionIcon)

    }

    private fun updateLocation(location: String){

        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday(){

        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperature(temperature: Int, feelsLike: Int){

        textView_temperature.text = "$temperature °C"
        textView_feels_like_temperature.text = "Feels like $feelsLike °C"
    }

    private fun updateCondition(condition: String){

        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double){

        text_precipitation.text = "Precipitation: $precipitationVolume"

//        Toast.makeText(this.context, "Precipitation: $precipitationVolume", Toast.LENGTH_LONG).show()
        Log.e("precipe", "Precipitation: $precipitationVolume")
    }

    private fun updateWind(windDirection: String, windSpeed: Int, windDegree: Int){

        textView_wind.text = "Wind: $windDirection, $windSpeed, $windDegree"
    }

    private fun updateVisibility(visibilityDistance: Int){

        textView_visibility.text = "Visibility: $visibilityDistance"
    }




}
