package com.caju.account.runtransaction.domain.strategies

import com.caju.account.commons.infra.repositories.resources.AccountEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MealTransactionStrategyTest {
    private val mealStrategy = MealTransactionStrategy()

    @Nested
    inner class IsAppliedTo {
        @Test
        fun `returns true when mcc is 5811`() {
            val result = mealStrategy.isAppliedTo("5811")

            assertThat(result).isTrue()
        }

        @Test
        fun `returns true when mcc is 5812`() {
            val result = mealStrategy.isAppliedTo("5812")

            assertThat(result).isTrue()
        }

        @Test
        fun `returns false when mcc is not applicable`() {
            val result = mealStrategy.isAppliedTo("5413")

            assertThat(result).isFalse()
        }

        @Test
        fun `returns false when mcc is empty`() {
            val result = mealStrategy.isAppliedTo("")

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
                cashBalance = 100.0
            )
            val amount = 100.0

            val result = mealStrategy.execute(accountEntity, amount)

            assertThat(result).isTrue()
        }

        @Test
        fun `returns true when balance is exactly the same amount to execute debit`() {
            val accountEntity = AccountEntity(
                foodBalance = 200.0,
                mealBalance = 300.0,
                cashBalance = 100.0
            )
            val amount = 200.0

            val result = mealStrategy.execute(accountEntity, amount)

            assertThat(result).isTrue()
        }

        @Test
        fun `returns false when balance is not enough to execute debit`() {
            val accountEntity = AccountEntity(
                foodBalance = 200.0,
                mealBalance = 50.0,
                cashBalance = 100.0
            )
            val amount = 100.0

            val result = mealStrategy.execute(accountEntity, amount)

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

            val result = mealStrategy.execute(accountEntity, amount)

            assertThat(result).isFalse()
        }
    }
}
