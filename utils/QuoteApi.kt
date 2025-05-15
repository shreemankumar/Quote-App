package com.example.assignment.utils

import com.example.assignment.model.QuoteModel
import retrofit2.Response
import retrofit2.http.GET

interface QuoteApi {
    //https://zenquotes.io/api/today
    @GET("today")
    suspend fun getTodayQuote() : Response<List<QuoteModel>>
}