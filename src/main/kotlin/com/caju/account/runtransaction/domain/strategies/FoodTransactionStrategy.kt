package com.caju.account.runtransaction.domain.strategies

import com.caju.account.commons.infra.repositories.resources.AccountEntity
import org.springframework.stereotype.Component

@Component
class FoodTransactionStrategy: TransactionStrategy {
    override fun isAppliedTo(mcc: String, account: AccountEntity, amount: Double) =
        (mcc == "5411" || mcc == "5412") && account.foodBalance >= amount

    override fun execute(account: AccountEntity, amount: Double) {
        account.foodBalance -= amount
    }
}
