package com.example.news


import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsScreen(newsViewModel: NewsViewModel,authViewModel: AuthViewModel) {
    var selectedUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        newsViewModel.selectTopic(newsViewModel.selectedTopic)
    }

    val articles = newsViewModel.articles
    val isLoading = newsViewModel.isLoading
    val error = newsViewModel.error
    val logoPainter = painterResource(R.drawable.news)

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { newsViewModel.fetchNews() }
    )

    if (selectedUrl != null) {
        ArticleWebViewScreen(url = selectedUrl!!) {
            selectedUrl = null
        }
    } else {
        Column(Modifier.fillMaxSize()) {
            NewsTopAppBar(
                logo = logoPainter,
                title = "Latest News",
                showLogout = true,
                onLogoutClick = { authViewModel.signOut() }
            )
            TopicBar(newsViewModel)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .pullRefresh(pullRefreshState)
            ) {
                when {
                    isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                    error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = error, color = MaterialTheme.colorScheme.error)
                    }
                    else -> NewsImageList(
                        articles = articles,
                        topic = newsViewModel.selectedTopic
                    ) { article ->
                        selectedUrl = article.url
                    }
                }
                PullRefreshIndicator(
                    refreshing = isLoading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}



@Composable
fun NewsImageList(
    articles: List<Article>,
    topic: String,
    onClick: (Article) -> Unit
) {
    val topicEmojis = mapOf(
        "general" to "📰",
        "business" to "\uD83D\uDCB0",
        "entertainment" to "🎬",
        "health" to "🩺",
        "science" to "🔬",
        "sports" to "⚽",
        "technology" to "\uD83D\uDCF1"
    )
    val emoji = topicEmojis[topic] ?: "📰"

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(articles) { article ->
            var imageLoadFailed by remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(article) },
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer, contentColor = MaterialTheme.colorScheme.onSurface)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(12.dp)
                ) {
                    if (article.urlToImage != null && !imageLoadFailed) {
                        val painter = rememberAsyncImagePainter(
                            article.urlToImage,
                            onError = { imageLoadFailed = true }
                        )
                        Image(
                            painter = painter,
                            contentDescription = article.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            contentScale = ContentScale.FillWidth
                        )
                    } else {
                        Text(
                            text = emoji,
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier.padding(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 3,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}



@Composable
fun TopicBar(viewModel: NewsViewModel) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp)
    ) {
        items(viewModel.topics) { topic ->
            val isSelected = topic == viewModel.selectedTopic
            FilterChip(
                onClick = { viewModel.selectTopic(topic) },
                label = {
                    Text(text = topic.replaceFirstChar { it.uppercase() })
                },
                selected = isSelected,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ArticleWebView(url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleWebViewScreen(url: String, onBack: () -> Unit) {
    BackHandler {
        onBack()
    }
    Column {
        TopAppBar(
            title = { Text("Article") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        ArticleWebView(url)
    }
}


