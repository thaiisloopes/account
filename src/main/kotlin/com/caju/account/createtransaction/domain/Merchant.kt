package com.caju.account.createtransaction.domain

enum class Merchant(val label: String, val mcc: String? = null) {
    UBER_TRIP("PAG*JoseDaSilva          RIO DE JANEI BR", "5411"),
    UBER_EATS("UBER EATS                   SAO PAULO BR", "5811");

    companion object {
        fun getMccFrom(merchantLabel: String): String {
            return entries.find { it.label == merchantLabel }?.mcc ?: ""
        }
    }
}
