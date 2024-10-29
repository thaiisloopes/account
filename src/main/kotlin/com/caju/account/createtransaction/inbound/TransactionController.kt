package com.caju.account.createtransaction.inbound

import com.caju.account.createtransaction.domain.applications.CreateTransactionApplication
import com.caju.account.createtransaction.inbound.resources.TransactionRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.RequestBody

@RestController
@RequestMapping("/account")
class TransactionController(
    private val createTransactionApplication: CreateTransactionApplication
) {
    @PostMapping("/transactions")
    fun create(@RequestBody request: TransactionRequest): ResponseEntity<Any> {
        val response = createTransactionApplication.perform(
            accountId = request.account,
            amount = request.totalAmount,
            mcc = request.mcc,
            merchant = request.merchant
        )

        return ResponseEntity(response, OK)
    }
}
