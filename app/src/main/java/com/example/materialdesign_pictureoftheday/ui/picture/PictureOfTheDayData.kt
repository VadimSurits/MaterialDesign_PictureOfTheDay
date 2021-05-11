package com.example.materialdesign_pictureoftheday.ui.picture

sealed class PictureOfTheDayData {
    data class Success(val serverResponseData: PODServerResponseData) : PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
    object Loading: PictureOfTheDayData()
}