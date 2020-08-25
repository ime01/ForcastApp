package com.flowz.forcastapp

import android.app.Application
import com.flowz.forcastapp.data.db.ForecastDatabase
import com.flowz.forcastapp.data.network.*
import com.flowz.forcastapp.data.repository.ForcastRepository
import com.flowz.forcastapp.data.repository.ForcastRepositoryImpl
import com.flowz.forcastapp.display.weather.current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>()with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherstackApiService(instance()) }
        bind<WeatherNetworkDataSoucre>()with singleton { WeatherNetworkDataSoucreImpl(instance()) }
        bind<ForcastRepository>()with singleton { ForcastRepositoryImpl(instance(), instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}