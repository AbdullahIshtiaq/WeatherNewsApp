package com.example.weathernewsapp.viewModel

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathernewsapp.adapters.WeatherAdapter
import com.example.weathernewsapp.room.WeatherModel
import com.example.weathernewsapp.room.WeatherRepo
import com.example.weathernewsapp.utlis.APIRepository
import com.example.weathernewsapp.utlis.getCurrentDateTime
import kotlinx.coroutines.launch

class WeatherVM(context: Context, val callback: (String) -> Unit) : ViewModel() {

    private val repository = APIRepository()
    private val weatherRepo = WeatherRepo(context)

    private var _weatherList = MutableLiveData<ArrayList<WeatherModel>>()
    val latitute = MutableLiveData<String>()
    val longitute = MutableLiveData<String>()
    var isProgressBar = ObservableField(false)
    var isListVisible = ObservableField(false)


    val weatherAdapter = WeatherAdapter()

    init {
        _weatherList.value = ArrayList()
        fetchWeatherFromDB()
    }


    fun getWeather() {
        isProgressBar.set(true)
        if (latitute.value.isNullOrEmpty() || longitute.value.isNullOrEmpty()) {
            callback("Can't get location. Please try again")
            isProgressBar.set(false)
            return
        }
        viewModelScope.launch {
            val response = repository.getWeather(latitute.value!!, longitute.value!!)
            response.dateTime = getCurrentDateTime()
            val model = WeatherModel(
                name = response.name,
                description = response.weather[0].description,
                dateTime = response.dateTime,
                temp = response.main.temp.toInt(),
                humidity = response.main.humidity,
                wind = response.wind.speed,
            )
            _weatherList.value?.clear()
            saveWeatherToDB(model)
            isProgressBar.set(false)
        }
    }

    private fun setAdapterData(list: ArrayList<WeatherModel>) {
        if (list.isEmpty()) {
            isListVisible.set(false)
        } else {
            isListVisible.set(true)
            weatherAdapter.setData(list)
        }

    }

    private fun saveWeatherToDB(weatherModel: WeatherModel) {
        viewModelScope.launch {
            weatherRepo.insert(weatherModel)
        }
    }

    private fun fetchWeatherFromDB() {
        viewModelScope.launch {
            weatherRepo.getAllWeather().collect { model ->
                if (model.isNotEmpty()) {
                    _weatherList.value?.addAll(model)
                    setAdapterData(_weatherList.value!!)
                    callback("Showing last saved data")
                }
            }
        }
    }

    fun deleteAllWeather() {
        viewModelScope.launch {
            weatherRepo.deleteAllWeather()
            callback("All weather deleted")
        }
        _weatherList.value?.clear()
        setAdapterData(_weatherList.value!!)
    }
}

