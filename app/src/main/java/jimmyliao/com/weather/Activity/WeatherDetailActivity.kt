package jimmyliao.com.weather.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jimmyliao.com.weather.Model.Weather
import jimmyliao.com.weather.R
import kotlinx.android.synthetic.main.activity_weather_detail.*

class WeatherDetailActivity : AppCompatActivity() {

    companion object {
        val KEY_WEATHER = "key_weather"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_detail)

        val weather = intent.extras.get(KEY_WEATHER) as Weather?
        tv_detail_startTime.text = weather?.startTime
        tv_detail_endTime.text = weather?.endTime
        val temp = weather?.parameterName + weather?.parameterUnit
        tv_detail_temp.text = temp
    }
}
