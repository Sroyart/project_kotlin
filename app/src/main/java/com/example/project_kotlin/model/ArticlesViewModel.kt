package com.example.project_kotlin.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

class ArticlesViewModel : ViewModel() {

    //MutableLiveData modifier par postValue executerons le code contenue dans .observe(viewLifecycleOwner){} que j'appelerais dans ma classe

    val data = MutableLiveData<ArticlesBeans?>()
    val dataCategories = MutableLiveData<CategoriesBeans?>()
    val dataElk = MutableLiveData<ElkBeans?>()
    val dataConn = MutableLiveData<JwtBeans?>()
    val dataOne = MutableLiveData<ArticlesBeansItem?>()
    val dataRegister = MutableLiveData<String?>()
    val errorMessage = MutableLiveData<String?>()
    val threadRunning = MutableLiveData(false)
    val dataFavoriteBasket = MutableLiveData<BasketFavoriteBeansItems?>()
    val threadFavoriteRunning = MutableLiveData(false)
    val threadOneFavoriteRunning = MutableLiveData(false)
    val threadOneCategoriesRunning = MutableLiveData(false)


    fun loadData(id: String) {
        threadRunning.postValue(true)
        data.postValue(null)
        errorMessage.postValue(null)
        thread {

            try {
                data.postValue(
                    RequestUtils.loadArticles(
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

    fun loadCategories(url: String) {
        threadOneCategoriesRunning.postValue(true)
        dataCategories.postValue(null)
        errorMessage.postValue(null)
        thread {

            try {
                dataCategories.postValue(
                    RequestUtils.loadCategories(
                        url
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            threadOneCategoriesRunning.postValue(false)
        }
    }

    fun loadArticlesByIds(query: String) {
        threadOneFavoriteRunning.postValue(true)
        dataOne.postValue(null)
        errorMessage.postValue(null)
        thread {

            try {
                dataOne.postValue(
                    RequestUtils.loadArticlesByIds(
                        "http://localhost:8082/articles/grp", query
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            threadOneFavoriteRunning.postValue(false)
        }
    }

    fun loadFavoritesBasketsData(url: String) {
        threadFavoriteRunning.postValue(true)
        dataFavoriteBasket.postValue(null)
        errorMessage.postValue(null)

        thread {
            try {
                dataFavoriteBasket.postValue(
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

    fun postRegister(myUrl: String, query: String) {
        threadRunning.postValue(true)
        dataRegister.postValue(null)
        errorMessage.postValue(null)
        thread {

            try {
                RequestUtils.registerPost(myUrl, query)

                dataRegister.postValue(
                    "ok"
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