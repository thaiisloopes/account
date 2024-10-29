package com.caju.account.createtransaction.domain

enum class Merchant(val label: String, val mcc: String? = null) {
    UBER_TRIP("UBER TRIP                   SAO PAULO BR"),
    UBER_EATS("UBER EATS                   SAO PAULO BR", "")
}
