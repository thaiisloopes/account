package com.caju.account.runtransaction.domain.applications

import com.caju.account.commons.infra.repositories.AccountRepository
import com.caju.account.commons.infra.repositories.resources.AccountEntity
import com.caju.account.runtransaction.domain.strategies.TransactionStrategy
import io.mockk.every
import org.junit.jupiter.api.Test
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.springframework.data.repository.findByIdOrNull

class RunTransactionApplicationTest {
    private val accountRepository = mockk<AccountRepository>()
    private val strategy = mockk<TransactionStrategy>()

    private val application = RunTransactionApplication(accountRepository, listOf(strategy))

    @Test
    fun `returns true when strategy was found and transaction was executed`() {
        val accountId = "123"
        val amount = 100.0
        val mcc = "5411"
        val merchant = "PADARIA DO ZE               SAO PAULO BR"
        val accountEntity = AccountEntity(
            foodBalance = 200.0,
            mealBalance = 300.0,
            cashBalance = 100.0
        )
        every { accountRepository.findByIdOrNull(accountId) } returns accountEntity
        every { strategy.isAppliedTo(mcc) } returns true
        every { strategy.execute(accountEntity, amount) } returns true

        val result = application.perform(accountId, amount, mcc, merchant)

        assertThat(result).isTrue()
    }

    @Test
    fun `returns false when no strategy was found`() {
        val accountId = "123"
        val amount = 100.0
        val mcc = "5411"
        val merchant = "PADARIA DO ZE               SAO PAULO BR"
        val accountEntity = AccountEntity(
            foodBalance = 200.0,
            mealBalance = 300.0,
            cashBalance = 100.0
        )
        every { accountRepository.findByIdOrNull(accountId) } returns accountEntity
        every { strategy.isAppliedTo(mcc) } returns false

        val result = application.perform(accountId, amount, mcc, merchant)

        assertThat(result).isFalse()
    }

    @Test
    fun `returns false when no account was found`() {
        val accountId = "123"
        val amount = 100.0
        val mcc = "5411"
        val merchant = "PADARIA DO ZE               SAO PAULO BR"
        every { accountRepository.findByIdOrNull(accountId) } returns null

        val result = application.perform(accountId, amount, mcc, merchant)

        assertThat(result).isFalse()
    }
}
