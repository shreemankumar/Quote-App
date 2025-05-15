package com.example.assignment.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.RetrofitInstance
import kotlinx.coroutines.launch

class QuoteViewModel : ViewModel() {
    var quote by mutableStateOf("Loading...")
        private set
    var author by mutableStateOf("")

    init {
        getQuote()
    }

    private fun getQuote() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.quoteApi.getTodayQuote()
                if (response.isSuccessful) {
                    val quoteObj = response.body()?.first()
                    quote = quoteObj?.q ?: "No quote found"
                    author = quoteObj?.a ?: ""
                } else {
                    quote = "Failed to load"
                }
            } catch (e: Exception) {
                quote = "Error occurred"
            }
        }
    }
}
