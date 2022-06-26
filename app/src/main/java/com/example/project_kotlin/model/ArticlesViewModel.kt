package com.example.project_kotlin.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project_kotlin.BasketData
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
    val dataBasketArray = MutableLiveData<BasketData>()
    val threadBasketRunning = MutableLiveData<Boolean>(false)

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

    fun loadBasketData(data: BasketFavoriteBeansItems) {
        threadBasketRunning.postValue(true)
        dataOne.postValue(null)
        errorMessage.postValue(null)

        thread {
            try {
                for (i in data.boxElements) {
                    println(i.boxEmb.articleId)


                    dataOne.postValue(
                        RequestUtils.loadOneArticles(
                            "http://10.0.2.2:80/api/articles/${i.boxEmb.articleId}"
                        )
                    )

//                    images += arrayOf(dataBasket.imagePath)
//                    details += arrayOf(it.description)
//                   prices += arrayOf(it.price)
//                   titles += arrayOf(it.name)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            threadBasketRunning.postValue(false)
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

    fun postFavorite(myUrl: String, query: String) {
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