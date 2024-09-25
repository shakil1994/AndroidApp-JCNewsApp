package com.blackbirds.newsapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class NewsViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    init {
        fetchNewsTopHeadlines()
    }

    fun fetchNewsTopHeadlines() {
        val newsApiClient = NewsApiClient(Constant.apiKey)
        val request = TopHeadlinesRequest.Builder().language("en").build()

        newsApiClient.getTopHeadlines(request, object : NewsApiClient.ArticlesResponseCallback{
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.let{
                    _articles.postValue(it)
                }
            }

            override fun onFailure(throwable: Throwable?) {
                if (throwable != null){
                    Log.e("NewsAPI Response Failed", throwable.localizedMessage)
                }
            }
        })
    }
}