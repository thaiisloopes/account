package com.caju.account.createtransaction.inbound.resources

const val APPROVED_TRANSACTION = "00"
const val REJECTED_BY_MISSING_BALANCE = "51"
const val REJECTED_BY_UNKNOWN_ERROR = "07"

data class TransactionResponse(
    val code: String
)
