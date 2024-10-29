package com.caju.account.createaccount.inbound.resources

data class AccountRequest(
    val foodBalance: Double,
    val mealBalance: Double,
    val cashBalance: Double
)
