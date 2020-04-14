package jimmyliao.com.weather.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.okhttp.*
import jimmyliao.com.weather.Adapter.MainAdapter
import jimmyliao.com.weather.Constant.Constant
import jimmyliao.com.weather.Model.Weather
import jimmyliao.com.weather.R
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val TAG = "TAG_MainActivity"
    private lateinit var adapter: MainAdapter
    private val weatherArr = mutableListOf<Weather?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        receiveData()
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(this, weatherArr)
        recycler.adapter = adapter
    }

    private fun receiveData() {
        isLoading()
        val urlBuilder = HttpUrl.parse(Constant.baseUrl + Constant.optionID).newBuilder()
        urlBuilder.addQueryParameter(Constant.authorizationParam, Constant.authorizationValue)
        urlBuilder.addQueryParameter(Constant.locationParam, Constant.locationValue)
        urlBuilder.addQueryParameter(Constant.elementnameParam, Constant.elementnameValue)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(urlBuilder.toString())
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(request: Request?, e: IOException?) {
                runOnUiThread { Log.e(TAG, e?.message) }
            }

            override fun onResponse(response: Response?) {
                val resStr = response?.body()?.string()
                runOnUiThread {
                    if (resStr != null) {
                        val resObj = Gson().fromJson(resStr, JsonObject::class.java)
                        val records =
                            resObj.get("records").asJsonObject.get("location").asJsonArray[0].asJsonObject.get("weatherElement").asJsonArray[0].asJsonObject.get(
                                "time"
                            ).asJsonArray
                        records.forEachIndexed { index, it ->
                            val recordObj = it.asJsonObject
                            val parameter = recordObj.get("parameter").asJsonObject
                            weatherArr.add(
                                Weather(
                                    recordObj.get("startTime").asString,
                                    recordObj.get("endTime").asString,
                                    parameter.get("parameterName").asString,
                                    parameter.get("parameterUnit").asString
                                )
                            )
                            if (index != records.size() - 1) {
                                weatherArr.add(null)
                            }
                        }
                        adapter.notifyDataSetChanged()
                        Log.d(TAG, weatherArr.toString())
                    }
                    finishLoading()
                }
            }

        })
    }

    private fun isLoading() {
        bar_loading.visibility = View.VISIBLE
        tv_loading.visibility = View.VISIBLE
    }

    private fun finishLoading() {
        bar_loading.visibility = View.INVISIBLE
        tv_loading.visibility = View.INVISIBLE
    }
}
