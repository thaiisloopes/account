package com.caju.account.createtransaction.domain.applications

import com.caju.account.commons.infra.repositories.AccountRepository
import com.caju.account.commons.infra.repositories.resources.AccountEntity
import com.caju.account.createtransaction.domain.strategies.TransactionStrategy
import com.caju.account.createtransaction.inbound.resources.APPROVED_TRANSACTION
import com.caju.account.createtransaction.inbound.resources.REJECTED_BY_MISSING_BALANCE
import com.caju.account.createtransaction.inbound.resources.REJECTED_BY_UNKNOWN_ERROR
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.Test
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat

class CreateTransactionApplicationTest {
    private val accountId = "123"
    private val amount = 100.0
    private val mcc = "5411"
    private val merchant = "PADARIA DO ZE               SAO PAULO BR"

    private val accountRepository = mockk<AccountRepository>()
    private val strategy = mockk<TransactionStrategy>()

    private val application = CreateTransactionApplication(accountRepository, listOf(strategy))

    @Test
    fun `returns true when strategy was found and transaction was executed`() {
        val accountEntity = AccountEntity(
            foodBalance = 200.0,
            mealBalance = 300.0,
            cashBalance = 100.0
        )
        every { accountRepository.findByIdWithPessimisticLock(accountId) } returns accountEntity
        every { strategy.isAppliedTo(mcc, accountEntity, amount) } returns true
        every { strategy.execute(accountEntity, amount) } just Runs
        every { accountRepository.save(accountEntity) } returns accountEntity

        val result = application.perform(accountId, amount, mcc, merchant)

        assertThat(result).isEqualTo(APPROVED_TRANSACTION)
    }

    @Test
    fun `returns false when no strategy was found`() {
        val accountEntity = AccountEntity(
            foodBalance = 200.0,
            mealBalance = 300.0,
            cashBalance = 100.0
        )
        every { accountRepository.findByIdWithPessimisticLock(accountId) } returns accountEntity
        every { strategy.isAppliedTo(mcc, accountEntity, amount) } returns false

        val result = application.perform(accountId, amount, mcc, merchant)

        assertThat(result).isEqualTo(REJECTED_BY_MISSING_BALANCE)
    }

    @Test
    fun `returns false when no account was found`() {
        every { accountRepository.findByIdWithPessimisticLock(accountId) } returns null

        val result = application.perform(accountId, amount, mcc, merchant)

        assertThat(result).isEqualTo(REJECTED_BY_UNKNOWN_ERROR)
    }
}
