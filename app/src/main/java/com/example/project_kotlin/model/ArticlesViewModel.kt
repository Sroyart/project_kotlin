package com.example.project_kotlin.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

class ArticlesViewModel : ViewModel() {
    val data = MutableLiveData<ArticlesBeans?>()
    val dataElk = MutableLiveData<ElkBeans?>()
    val dataConn = MutableLiveData<JwtBeans?>()
    val dataOne = MutableLiveData<ArticlesBeansItem?>()
    val dataRegister = MutableLiveData<String?>()
    val errorMessage = MutableLiveData<String?>()
    val dataBasket = MutableLiveData<BasketFavoriteBeansItems?>()
    val threadRunning = MutableLiveData<Boolean>(false)
    val dataFavorite = MutableLiveData<BasketFavoriteBeansItems?>()
    val threadFavoriteRunning = MutableLiveData<Boolean>(false)
    val threadOneFavoriteRunning = MutableLiveData<Boolean>(false)

    fun loadData(id: String) {
        threadRunning.postValue(true)
        data.postValue(null)
        dataElk.postValue(null)
        errorMessage.postValue(null)
        thread {

            try {
                data.postValue(
                    RequestUtils.loadArticles(
                        "",
                        "http://10.0.2.2:80/api/articles/$id"
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            threadRunning.postValue(false)
        }
    }

    fun loadOneData(id: String) {
        threadOneFavoriteRunning.postValue(true)
        dataOne.postValue(null)
        errorMessage.postValue(null)
        thread {

            try {
                dataOne.postValue(
                    RequestUtils.loadOneArticles(
                        "http://10.0.2.2:80/api/articles/$id"
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            threadOneFavoriteRunning.postValue(false)
        }
    }

    fun loadFavoritesData(url: String) {
        threadFavoriteRunning.postValue(true)
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
            threadFavoriteRunning.postValue(false)
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

    fun loadBasketData(url: String) {
        threadFavoriteRunning.postValue(true)
        dataBasket.postValue(null)
        errorMessage.postValue(null)

        thread {
            try {
                dataBasket.postValue(
                    RequestUtils.loadFavoritesArticles(
                        "",
                        url
                    )
                )

            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            threadFavoriteRunning.postValue(false)
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