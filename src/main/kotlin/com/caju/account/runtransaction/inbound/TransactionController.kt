package com.caju.account.runtransaction.inbound

import com.caju.account.runtransaction.domain.applications.RunTransactionApplication
import com.caju.account.runtransaction.inbound.resources.TransactionRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.RequestBody

@RestController
@RequestMapping("/account")
class TransactionController(
    private val runTransactionApplication: RunTransactionApplication
) {
    @PostMapping("/transactions")
    fun create(@RequestBody request: TransactionRequest): ResponseEntity<Any> {
        val response = runTransactionApplication.perform(
            accountId = request.account,
            amount = request.totalAmount,
            mcc = request.mcc,
            merchant = request.merchant
        )

        return ResponseEntity(response, OK)
    }
}
