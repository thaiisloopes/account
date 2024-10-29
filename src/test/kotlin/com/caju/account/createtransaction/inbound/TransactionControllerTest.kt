package com.caju.account.createtransaction.inbound

import com.caju.account.createtransaction.domain.applications.CreateTransactionApplication
import com.caju.account.createtransaction.inbound.resources.*
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity

@ExtendWith(MockKExtension::class)
class TransactionControllerTest {
    @MockK
    private lateinit var createTransactionApplication: CreateTransactionApplication

    @InjectMockKs
    private lateinit var transactionController: TransactionController

    private val request = TransactionRequest(
        account = "123",
        totalAmount = 50.00,
        mcc = "5811",
        merchant = "PADARIA DO ZE               SAO PAULO BR"
    )

    @Test
    fun `returns ok status code with code 00 when transaction was created successfully`(){
        every {
            createTransactionApplication.perform(request.account, request.totalAmount, request.mcc, request.merchant)
        } returns APPROVED_TRANSACTION

        val result = transactionController.create(request)

        assertThat(result).isEqualTo(ResponseEntity(TransactionResponse("00"), OK))
    }

    @Test
    fun `returns ok status code with code 51 when transaction was reject by missing balance`(){
        every {
            createTransactionApplication.perform(request.account, request.totalAmount, request.mcc, request.merchant)
        } returns REJECTED_BY_MISSING_BALANCE

        val result = transactionController.create(request)

        assertThat(result).isEqualTo(ResponseEntity(TransactionResponse("51"), OK))
    }

    @Test
    fun `returns ok status code with code 07 when transaction was reject by unknown error`(){
        every {
            createTransactionApplication.perform(request.account, request.totalAmount, request.mcc, request.merchant)
        } returns REJECTED_BY_UNKNOWN_ERROR

        val result = transactionController.create(request)

        assertThat(result).isEqualTo(ResponseEntity(TransactionResponse("07"), OK))
    }
}