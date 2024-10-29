package com.caju.account.runtransaction.domain.strategies

import com.caju.account.commons.infra.repositories.resources.AccountEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class FoodTransactionStrategyTest {
    private val foodStrategy = FoodTransactionStrategy()
    private val accountEntity = AccountEntity(
        foodBalance = 200.0,
        mealBalance = 300.0,
        cashBalance = 150.0
    )
    private val amount = 100.0

    @Nested
    inner class IsAppliedTo {
        @Test
        fun `returns true when mcc is 5411 and balance is bigger than amount`() {
            val result = foodStrategy.isAppliedTo("5411", accountEntity, amount)

            assertThat(result).isTrue()
        }

        @Test
        fun `returns true when mcc is 5412 and balance is bigger than amount`() {
            val result = foodStrategy.isAppliedTo("5412", accountEntity, amount)

            assertThat(result).isTrue()
        }

        @Test
        fun `returns false when mcc is not applicable`() {
            val result = foodStrategy.isAppliedTo("5413", accountEntity, amount)

            assertThat(result).isFalse()
        }

        @Test
        fun `returns false when mcc is applicable but amount is bigger than balance`() {
            val result = foodStrategy.isAppliedTo("5411", accountEntity, 350.0)

            assertThat(result).isFalse()
        }

        @Test
        fun `returns false when mcc is empty`() {
            val result = foodStrategy.isAppliedTo("", accountEntity, amount)

            assertThat(result).isFalse()
        }
    }

    @Nested
    inner class Execute {
        @Test
        fun `updates food balance`() {
            foodStrategy.execute(accountEntity, amount)

            assertThat(accountEntity.foodBalance).isEqualTo(100.0)
        }
    }
}
