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
        fun loadArticles(myArticle: String, myUrl: String): ArticlesBeans {
            println(myUrl.format(myArticle))
            val json: String = sendGet(myUrl.format(myArticle))
            val article = gson.fromJson(json, ArticlesBeans::class.java)
            println("json : $article")

            return article
        }

        fun loadPost(myUrl: String, query: String): ElkBeans {
            println(myUrl.format(""))
            val json: String = sendPost(
                myUrl.format(""), query
            )
            val article = gson.fromJson(json, ElkBeans::class.java)
            println("json : $article")

            return article
        }

        fun registerPost(myUrl: String, query: String): String {
            println(myUrl.format(""))
            val json: String = sendPost(
                myUrl.format(""), query
            )
            val article = gson.fromJson(json, RegisterBeans::class.java)
            println("json : $article")

            return "article"
        }


        fun sendGet(url: String): String {
            println("url : $url")
            val request = Request.Builder().url(url).build()
            println(request)
            return client.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    throw Exception("Réponse du serveur incorrect :${it.code}")
                }
                it.body?.string() ?: ""
            }
        }

        fun sendPost(url: String, paramJson: String): String {
            println("url : $url")
            println(paramJson)

            //Corps de la requête
            val body = paramJson.toRequestBody(MEDIA_TYPE_JSON)

            //Création de la requete
            val request = Request.Builder().url(url).post(body).build()

            //Execution de la requête
            return client.newCall(request).execute().use {
                //Analyse du code retour
                if (!it.isSuccessful) {
                    throw Exception("Réponse du serveur incorrect :${it.code}")
                }
                //Résultat de la requete
                it.body?.string() ?: ""
            }
        }

    }

}