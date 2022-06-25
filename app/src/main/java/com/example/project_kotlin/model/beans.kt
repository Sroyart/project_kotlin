package com.example.project_kotlin.model

class ArticlesBeans : ArrayList<ArticlesBeansItem>()

data class ArticlesBeansItem(
    val categories: List<Any>,
    val colors: List<Any>,
    val customerNumber: Int,
    val description: String,
    val grade: Any,
    val id: Int,
    val imagePath: String,
    val materials: List<Any>,
    val name: String,
    val price: Int,
    val size: String,
    val stocks: Int
)

data class ElkBeans(
    val _shards: Shards,
    val hits: Hits,
    val timed_out: Boolean,
    val took: Int
)

data class Shards(
    val failed: Int,
    val skipped: Int,
    val successful: Int,
    val total: Int
)

data class Hits(
    val hits: List<Hit>,
    val max_score: Double,
    val total: Total
)

data class Hit(
    val _id: String,
    val _index: String,
    val _score: Double,
    val _source: Source
)

data class Total(
    val relation: String,
    val value: Int
)

data class Source(
    val category: List<Any>,
    val color: List<Any>,
    val material: List<Any>,
    val name: String,
    val articleId: Int
)

data class RegisterBeans(
    val test: String
)

data class JwtBeans(
    val jwt: String
)