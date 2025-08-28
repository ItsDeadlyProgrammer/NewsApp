package com.example.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    var articles by mutableStateOf<List<Article>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var selectedTopic by mutableStateOf("general")
        private set


    val topics = listOf("general", "business", "entertainment", "health", "science", "sports", "technology")

    private val api = RetrofitInstance.api
    private val apiKey = "INSERT YOUR NEWS API HERE"

    fun selectTopic(topic: String) {
        selectedTopic = topic
        fetchNewsByTopic(topic)
    }

    fun fetchNews() = fetchNewsByTopic(selectedTopic)

    fun fetchNewsByTopic(topic: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = api.getTopHeadlinesByCategory("us", topic, apiKey)
                if (response.status == "ok") {
                    articles = response.articles
                    error = null
                } else {
                    error = "Error loading news"
                }
            } catch (e: Exception) {
                error = e.localizedMessage
            }
            isLoading = false
        }
    }
}
