package com.caju.account.runtransaction.domain.strategies

import com.caju.account.commons.infra.repositories.resources.AccountEntity
import org.springframework.stereotype.Component

@Component
class MealTransactionStrategy: TransactionStrategy {
    override fun isAppliedTo(mcc: String, account: AccountEntity, amount: Double) =
        (mcc == "5811" || mcc == "5812") && account.mealBalance >= amount

    override fun execute(account: AccountEntity, amount: Double) {
        account.mealBalance -= amount
    }
}
