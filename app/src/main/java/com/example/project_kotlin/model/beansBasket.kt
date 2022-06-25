package com.example.project_kotlin.model

data class BasketFavoriteBeansItems(
    val basketGrpEmb: Any,
    val boxElements: List<BoxElement>,
    val customerId: Int,
    val id: Int
)

data class BoxElement(
    val basketEmb: BasketEmb,
    val boxEmb: BoxEmb,
    val id: Int
)

data class BasketEmb(
    val amount: Int
)

data class BoxEmb(
    val articleId: Int
)