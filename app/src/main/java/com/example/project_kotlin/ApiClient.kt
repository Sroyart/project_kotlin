package com.example.project_kotlin

import okhttp3.OkHttpClient

class ApiClient {
    private val httpClient = OkHttpClient()

//    fun createPaymentIntent(
//        paymentMethodType: String,
//        completion: (paymentIntentClientSecret: String?, error: String?) -> Unit
//    ) {
//
//        val mediaType = "application/json; charset=utf-8".toMediaType()
//        val requestJson = """
//            {
//                "currency":"usd",
//                "paymentMethodType":"$paymentMethodType"
//            }
//            """
//        val body = requestJson.toRequestBody(mediaType)
//        val request = Request.Builder()
//            .url("http://localhost:8083/pay" + "create-payment-intent")
//            .post(body)
//            .build()
//        httpClient.newCall(request)
//            .enqueue(object : Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                    completion(null, "$e")
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    if (!response.isSuccessful) {
//                        completion(null, "$response")
//                    } else {
//                        val responseData = response.body?.string()
//                        val responseJson =
//                            responseData?.let { JSONObject(it) } ?: JSONObject()
//
//                        // The response from the server contains the PaymentIntent's client_secret
//                        var paymentIntentClientSecret: String =
//                            responseJson.getString("clientSecret")
//                        completion(paymentIntentClientSecret, null)
//                    }
//                }
//            })
//    }
}