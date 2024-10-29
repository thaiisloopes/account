package com.caju.account.createtransaction.domain.strategies

import com.caju.account.commons.infra.repositories.resources.AccountEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MealTransactionStrategyTest {
    private val mealStrategy = MealTransactionStrategy()
    private val accountEntity = AccountEntity(
        foodBalance = 200.0,
        mealBalance = 300.0,
        cashBalance = 150.0
    )
    private val amount = 100.0

    @Nested
    inner class IsAppliedTo {
        @Test
        fun `returns true when mcc is 5811 and balance is bigger than amount`() {
            val result = mealStrategy.isAppliedTo("5811", accountEntity, amount)

            assertThat(result).isTrue()
        }

        @Test
        fun `returns true when mcc is 5812 and balance is bigger than amount`() {
            val result = mealStrategy.isAppliedTo("5812", accountEntity, amount)

            assertThat(result).isTrue()
        }

        @Test
        fun `returns false when mcc is not applicable`() {
            val result = mealStrategy.isAppliedTo("5413", accountEntity, amount)

            assertThat(result).isFalse()
        }

        @Test
        fun `returns false when mcc is applicable but amount is bigger than balance`() {
            val result = mealStrategy.isAppliedTo("5812", accountEntity, 350.0)

            assertThat(result).isFalse()
        }

        @Test
        fun `returns false when mcc is empty`() {
            val result = mealStrategy.isAppliedTo("", accountEntity, amount)

            assertThat(result).isFalse()
        }
    }

    @Nested
    inner class Execute {
        @Test
        fun `updates meal balance`() {
            mealStrategy.execute(accountEntity, amount)

            assertThat(accountEntity.mealBalance).isEqualTo(200.0)
        }
    }
}
