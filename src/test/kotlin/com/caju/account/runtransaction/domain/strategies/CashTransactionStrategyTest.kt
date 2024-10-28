package com.caju.account.runtransaction.domain.strategies

import com.caju.account.commons.infra.repositories.resources.AccountEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CashTransactionStrategyTest {
    private val cashStrategy = CashTransactionStrategy()

    @Nested
    inner class IsAppliedTo {
        @Test
        fun `returns true when mcc is not 5411 5412 5811 or 5812`() {
            val result = cashStrategy.isAppliedTo("5410")

            assertThat(result).isTrue()
        }

        @Test
        fun `returns true when mcc is empty`() {
            val result = cashStrategy.isAppliedTo("")

            assertThat(result).isTrue()
        }

        @Test
        fun `returns false when mcc is not applicable`() {
            val result = cashStrategy.isAppliedTo("5411")

            assertThat(result).isFalse()
        }
    }

    @Nested
    inner class Execute {
        @Test
        fun `returns true when balance is enough to execute debit`() {
            val accountEntity = AccountEntity(
                foodBalance = 200.0,
                mealBalance = 300.0,
                cashBalance = 150.0
            )
            val amount = 100.0

            val result = cashStrategy.execute(accountEntity, amount)

            assertThat(result).isTrue()
        }

        @Test
        fun `returns true when balance is exactly the same amount to execute debit`() {
            val accountEntity = AccountEntity(
                foodBalance = 200.0,
                mealBalance = 300.0,
                cashBalance = 100.0
            )
            val amount = 100.0

            val result = cashStrategy.execute(accountEntity, amount)

            assertThat(result).isTrue()
        }

        @Test
        fun `returns false when balance is not enough to execute debit`() {
            val accountEntity = AccountEntity(
                foodBalance = 200.0,
                mealBalance = 300.0,
                cashBalance = 50.0
            )
            val amount = 100.0

            val result = cashStrategy.execute(accountEntity, amount)

            assertThat(result).isFalse()
        }

        @Test
        fun `returns false when amount to debit is 0`() {
            val accountEntity = AccountEntity(
                foodBalance = 200.0,
                mealBalance = 300.0,
                cashBalance = 100.0
            )
            val amount = 0.0

            val result = cashStrategy.execute(accountEntity, amount)

            assertThat(result).isFalse()
        }
    }
}