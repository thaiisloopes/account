package com.caju.account.createtransaction.domain.applications

import com.caju.account.commons.infra.repositories.AccountRepository
import com.caju.account.createtransaction.domain.strategies.TransactionStrategy
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CreateTransactionApplication(
    private val accountRepository: AccountRepository,
    private val strategies: List<TransactionStrategy>
) {
    fun perform(accountId: String, amount: Double, mcc: String, merchant: String): Boolean {
        val accountEntity = accountRepository.findByIdOrNull(accountId)

        return accountEntity?.let { account ->
            val strategy = strategies.find { it.isAppliedTo(mcc, account, amount) }
            if(strategy != null) {
                strategy.execute(account, amount)
                accountRepository.save(account)
                true
            } else false
        } ?: false
    }
}
