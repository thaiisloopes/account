package com.caju.account.runtransaction.domain.strategies

import com.caju.account.commons.infra.repositories.resources.AccountEntity
import org.springframework.stereotype.Component

@Component
class MealTransactionStrategy: TransactionStrategy {
    override fun isAppliedTo(mcc: String) =
        mcc == "5811" || mcc == "5812"

    override fun execute(account: AccountEntity, amount: Double): Boolean {
        return if(amount > 0.0 && account.mealBalance >= amount) {
            account.mealBalance -= amount
            true
        } else false
    }
}
