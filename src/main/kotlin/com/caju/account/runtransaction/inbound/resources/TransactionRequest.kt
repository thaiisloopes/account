package com.caju.account.runtransaction.inbound.resources

data class TransactionRequest(
    val account: String,
    val totalAmount: Double,
    val mcc: String,
    val merchant: String
)
