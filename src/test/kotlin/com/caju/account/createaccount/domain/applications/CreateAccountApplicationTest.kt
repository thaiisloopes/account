package com.caju.account.createaccount.domain.applications

import com.caju.account.commons.infra.repositories.AccountRepository
import com.caju.account.commons.infra.repositories.resources.AccountEntity
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CreateAccountApplicationTest {
    @MockK
    private lateinit var accountRepository: AccountRepository

    @InjectMockKs
    private lateinit var createAccountApplication: CreateAccountApplication

    @Test
    fun `creates account successfully with given balances`() {
        val foodBalance = 100.0
        val mealBalance = 200.0
        val cashBalance = 300.0
        val entity = AccountEntity(foodBalance, mealBalance, cashBalance)
        every { accountRepository.save(entity) } returns entity

        createAccountApplication.process(foodBalance, mealBalance, cashBalance)

        verify(exactly = 1) { accountRepository.save(entity) }
    }
}
