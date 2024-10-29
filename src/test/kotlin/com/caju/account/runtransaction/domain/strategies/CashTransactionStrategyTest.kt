package com.caju.account.runtransaction.domain.strategies

import com.caju.account.commons.infra.repositories.resources.AccountEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CashTransactionStrategyTest {
    private val cashStrategy = CashTransactionStrategy()
    private val accountEntity = AccountEntity(
        foodBalance = 200.0,
        mealBalance = 300.0,
        cashBalance = 150.0
    )
    private val amount = 100.0

    @Nested
    inner class IsAppliedTo {
        @Test
        fun `returns true when mcc is not 5411 5412 5811 or 5812 and balance is bigger than amount`() {
            val result = cashStrategy.isAppliedTo("5410", accountEntity, amount)

            assertThat(result).isTrue()
        }

        @Test
        fun `returns true when mcc is empty and balance is bigger than amount`() {
            val result = cashStrategy.isAppliedTo("", accountEntity, amount)

            assertThat(result).isTrue()
        }

        @Test
        fun `returns false when mcc is not applicable`() {
            val result = cashStrategy.isAppliedTo("5411", accountEntity, amount)

            assertThat(result).isFalse()
        }

        @Test
        fun `returns false when mcc is applicable but amount is bigger than balance`() {
            val result = cashStrategy.isAppliedTo("5411", accountEntity, 350.0)

            assertThat(result).isFalse()
        }
    }

    @Nested
    inner class Execute {
        @Test
        fun `updates cash balance`() {
            cashStrategy.execute(accountEntity, amount)

            assertThat(accountEntity.cashBalance).isEqualTo(50.0)
        }
    }
}
