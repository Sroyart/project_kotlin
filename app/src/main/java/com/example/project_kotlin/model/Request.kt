package com.example.project_kotlin.model

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()

private val gson = Gson()
val client = OkHttpClient()

class RequestUtils {
    companion object {
        fun loadArticles(myUrl: String): ArticlesBeans {
            val json: String = sendGet(myUrl.format())
            val article = gson.fromJson(json, ArticlesBeans::class.java)

            return article
        }

        fun loadCategories(myUrl: String): CategoriesBeans {
            val json: String = sendGet(myUrl.format())
            val categories = gson.fromJson(json, CategoriesBeans::class.java)

            return categories
        }


        fun loadArticlesByIds(myUrl: String, query: String): ArticlesBeansItem {
            val json: String = sendPost(
                myUrl.format(""), query
            )
            val article = gson.fromJson(json, ArticlesBeansItem::class.java)

            return article
        }

        fun loadFavoritesArticles(myArticle: String, myUrl: String): BasketFavoriteBeansItems {
            val json: String = sendGet(myUrl.format(myArticle))
            val favorites = gson.fromJson(json, BasketFavoriteBeansItems::class.java)
            return favorites
        }

        fun loadPost(myUrl: String, query: String): ElkBeans {
            val json: String = sendPost(
                myUrl.format(""), query
            )
            val article = gson.fromJson(json, ElkBeans::class.java)

            return article
        }

        fun registerPost(myUrl: String, query: String) {
            sendPost(
                myUrl.format(""), query
            )
        }

        fun connPost(myUrl: String, query: String): JwtBeans {
            var json: String = sendPost(
                myUrl.format(""), query
            )
            json = "{\"jwt\":\"${json}\"}"


            val article = gson.fromJson(json, JwtBeans::class.java)

            println(article)

            return article
        }

        fun sendGet(url: String): String {
            val request = Request.Builder().url(url).build()
            return client.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    throw Exception("R??ponse du serveur incorrect :${it.code}")
                }
                it.body?.string() ?: ""
            }
        }

        fun sendPost(url: String, paramJson: String): String {
            println(paramJson)

            //Corps de la requ??te
            val body = paramJson.toRequestBody(MEDIA_TYPE_JSON)

            //Cr??ation de la requete
            val request = Request.Builder().url(url).post(body).build()

            //Execution de la requ??te
            return client.newCall(request).execute().use {
                //Analyse du code retour
                if (!it.isSuccessful) {
                    throw Exception("R??ponse du serveur incorrect :${it.code}")
                }
                //R??sultat de la requete
                it.body?.string() ?: ""
            }
        }

    }

}