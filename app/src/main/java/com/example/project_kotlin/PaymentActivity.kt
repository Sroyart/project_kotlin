package com.example.project_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stripe.android.Stripe

class PaymentActivity : AppCompatActivity() {
    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        //Payment pas implémenté en back et front

//        stripe = Stripe(
//            this,
//            PaymentConfiguration.getInstance(applicationContext).publishableKey
//        )

    }

//    private fun startCheckout() {
//        // Create a PaymentIntent by calling the sample server's /create-payment-intent endpoint.
//        ApiClient().createPaymentIntent(
//            "card",
//            completion = { paymentIntentClientSecret, error ->
//                run {
//                    paymentIntentClientSecret?.let {
//                        this.paymentIntentClientSecret = it
//                    }
//                    error?.let {
//                        displayAlert(
//                            "Failed to load page",
//                            "Error: $error"
//                        )
//                    }
//                }
//            })
//
//        // Confirm the PaymentIntent with the card widget
//        payButton.setOnClickListener {
//            cardInputWidget.paymentMethodCreateParams?.let { params ->
//                TaskTracker.onStart()
//                val confirmParams = ConfirmPaymentIntentParams
//                    .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret)
//                stripe.confirmPayment(this, confirmParams)
//            }
//        }
//    }
//
//    private fun onPaymentResult(paymentResult: PaymentResult) {
//        val message = when (paymentResult) {
//            is PaymentResult.Completed -> {
//                "Completed!"
//            }
//            is PaymentResult.Canceled -> {
//                "Canceled!"
//            }
//            is PaymentResult.Failed -> {
//                "Failed: " + paymentResult.throwable.message
//            }
//        }
//        displayAlert(
//            "Payment Result:",
//            message,
//            restartDemo = true
//        )
//    }
//
//    private fun displayAlert(
//        title: String,
//        message: String,
//        restartDemo: Boolean = false
//    ) {
//        runOnUiThread {
//            val builder = AlertDialog.Builder(this)
//                .setTitle(title)
//                .setMessage(message)
//            if (restartDemo) {
//                builder.setPositiveButton("Restart demo") { _, _ ->
//                    cardInputWidget.clear()
//                    startCheckout()
//                }
//            } else {
//                builder.setPositiveButton("Ok", null)
//            }
//            builder
//                .create()
//                .show()
//        }
//    }
}