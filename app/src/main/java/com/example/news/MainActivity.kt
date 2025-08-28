package com.example.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.news.ui.theme.NewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsTheme {
                val authViewModel: AuthViewModel = viewModel()
                val user = authViewModel.user

                if (user == null) {
                    AuthScreen(authViewModel)
                } else {
                    val newsViewModel: NewsViewModel = viewModel()
                    NewsScreen(newsViewModel,authViewModel)
                }
            }
        }
    }
}
