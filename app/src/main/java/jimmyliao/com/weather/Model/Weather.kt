package jimmyliao.com.weather.Model

import java.io.Serializable

data class Weather(val startTime: String, val endTime: String, val parameterName: String, val parameterUnit: String):Serializable