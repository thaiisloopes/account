package com.caju.account.createaccount.inbound

import com.caju.account.createaccount.domain.applications.CreateAccountApplication
import com.caju.account.createaccount.inbound.resources.AccountRequest
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus

@ExtendWith(MockKExtension::class)
class AccountControllerTest {
    @MockK
    private lateinit var accountApplication: CreateAccountApplication

    @InjectMockKs
    private lateinit var accountController: AccountController

    @Test
    fun `returns created status code when account was created successfully`() {
        val request = AccountRequest(
            foodBalance = 100.0,
            mealBalance = 200.0,
            cashBalance = 300.0
        )
        every {
            accountApplication.process(request.foodBalance, request.mealBalance, request.cashBalance)
        } just Runs

        val result = accountController.create(request)

        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
    }
}
