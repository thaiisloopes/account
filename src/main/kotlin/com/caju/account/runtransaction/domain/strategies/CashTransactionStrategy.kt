package com.caju.account.runtransaction.domain.strategies

import com.caju.account.commons.infra.repositories.resources.AccountEntity
import org.springframework.stereotype.Component

@Component
class CashTransactionStrategy: TransactionStrategy {
    override fun isAppliedTo(mcc: String) =
        mcc !in listOf("5411", "5412", "5811", "5812")

    override fun execute(account: AccountEntity, amount: Double): Boolean {
        return if(amount > 0.0 && account.cashBalance >= amount) {
            account.cashBalance -= amount
            true
        } else false
    }
}
