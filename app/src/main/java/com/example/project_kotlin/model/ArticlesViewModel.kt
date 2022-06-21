package com.example.project_kotlin.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

class ArticlesViewModel : ViewModel() {
    val data = MutableLiveData<ArticlesBeans?>()
    val dataElk = MutableLiveData<ElkBeans?>()
    val dataConn = MutableLiveData<JwtBeans?>()
    val dataRegister = MutableLiveData<String?>()
    val errorMessage = MutableLiveData<String?>()
    val threadRunning = MutableLiveData<Boolean>(false)
    val dataFavorite = MutableLiveData<FavorieBeansItems?>()

    fun loadData() {
        threadRunning.postValue(true)
        data.postValue(null)
        dataElk.postValue(null)
        errorMessage.postValue(null)
        thread {

            try {
                data.postValue(
                    RequestUtils.loadArticles(
                        "",
                        "http://10.0.2.2:80/api/articles"
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            threadRunning.postValue(false)
        }
    }

    fun loadFavoritesData(url: String) {
        threadRunning.postValue(true)
        dataFavorite.postValue(null)
        errorMessage.postValue(null)
        thread {

            try {
                dataFavorite.postValue(
                    RequestUtils.loadFavoritesArticles(
                        "",
                        url
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            threadRunning.postValue(false)
        }
    }

    fun loadPostData(myUrl: String, query: String) {
        threadRunning.postValue(true)
        dataElk.postValue(null)
        errorMessage.postValue(null)
        thread {

            try {
                dataElk.postValue(
                    RequestUtils.loadPost(myUrl, query)
                )
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            threadRunning.postValue(false)
        }
    }

    fun postRegister(myUrl: String, query: String) {
        threadRunning.postValue(true)
        dataRegister.postValue(null)
        errorMessage.postValue(null)
        thread {

            try {
                dataRegister.postValue(
                    RequestUtils.registerPost(myUrl, query)
                )
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            threadRunning.postValue(false)
        }
    }

    fun postConn(myUrl: String, query: String) {
        threadRunning.postValue(true)
        dataConn.postValue(null)
        errorMessage.postValue(null)
        thread {

            try {
                dataConn.postValue(
                    RequestUtils.connPost(myUrl, query)
                )

            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            threadRunning.postValue(false)
        }

    }
}