package com.caju.account.runtransaction.domain.strategies

import com.caju.account.commons.infra.repositories.resources.AccountEntity
import org.springframework.stereotype.Component

@Component
class FoodTransactionStrategy: TransactionStrategy {
    override fun isAppliedTo(mcc: String) =
        mcc == "5411" || mcc == "5412"

    override fun execute(account: AccountEntity, amount: Double): Boolean {
        return if(amount > 0.0 && account.foodBalance >= amount) {
            account.foodBalance -= amount
            true
        } else false
    }
}
