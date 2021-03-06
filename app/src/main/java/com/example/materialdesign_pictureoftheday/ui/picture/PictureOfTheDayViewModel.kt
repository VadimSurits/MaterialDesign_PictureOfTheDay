package com.example.materialdesign_pictureoftheday.ui.picture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.materialdesign_pictureoftheday.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    fun getData(date: String?): LiveData<PictureOfTheDayData> {
        sendServerRequest(date)
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(date: String?) {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            if(date.isNullOrEmpty()){
                retrofitImpl
                    .getRetrofitImpl()
                    .getPictureOfTheDay(apiKey)
                    .enqueue(object : Callback<PODServerResponseData> {

                        override fun onResponse(
                            call: Call<PODServerResponseData>,
                            response: Response<PODServerResponseData>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                liveDataForViewToObserve.value = PictureOfTheDayData.Success(
                                    response.body()!!
                                )
                            } else {
                                val message = response.message()
                                if (message.isNullOrEmpty()) {
                                    liveDataForViewToObserve.value = PictureOfTheDayData.Error(
                                        Throwable("Unidentified error")
                                    )
                                } else {
                                    liveDataForViewToObserve.value = PictureOfTheDayData.Error(
                                        Throwable(message)
                                    )
                                }
                            }
                        }

                        override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                            liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                        }
                    })
            } else {
                retrofitImpl
                    .getRetrofitImpl()
                    .getPictureOfTheDay(date, apiKey)
                    .enqueue(object : Callback<PODServerResponseData> {

                        override fun onResponse(
                            call: Call<PODServerResponseData>,
                            response: Response<PODServerResponseData>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                liveDataForViewToObserve.value = PictureOfTheDayData.Success(
                                    response.body()!!
                                )
                            } else {
                                val message = response.message()
                                if (message.isNullOrEmpty()) {
                                    liveDataForViewToObserve.value = PictureOfTheDayData.Error(
                                        Throwable("Unidentified error")
                                    )
                                } else {
                                    liveDataForViewToObserve.value = PictureOfTheDayData.Error(
                                        Throwable(message)
                                    )
                                }
                            }
                        }

                        override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                            liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                        }
                    })
            }
        }
    }
}