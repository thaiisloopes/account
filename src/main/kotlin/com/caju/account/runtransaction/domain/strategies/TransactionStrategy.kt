package com.caju.account.runtransaction.domain.strategies

import com.caju.account.commons.infra.repositories.resources.AccountEntity

interface TransactionStrategy {
    fun isAppliedTo(mcc: String): Boolean

    fun execute(account: AccountEntity, amount: Double): Boolean
}