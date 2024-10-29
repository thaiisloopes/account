package com.caju.account.createtransaction.domain.applications

import com.caju.account.commons.infra.repositories.AccountRepository
import com.caju.account.commons.infra.repositories.resources.AccountEntity
import com.caju.account.createtransaction.domain.strategies.TransactionStrategy
import com.caju.account.createtransaction.inbound.resources.APPROVED_TRANSACTION
import com.caju.account.createtransaction.inbound.resources.REJECTED_BY_MISSING_BALANCE
import com.caju.account.createtransaction.inbound.resources.REJECTED_BY_UNKNOWN_ERROR
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CreateTransactionApplication(
    private val accountRepository: AccountRepository,
    private val strategies: List<TransactionStrategy>
) {
    @Transactional
    fun perform(accountId: String, amount: Double, mcc: String, merchant: String): String {
        val accountEntity = accountRepository.findByIdWithPessimisticLock(accountId)
            ?: return REJECTED_BY_UNKNOWN_ERROR

        return accountEntity.let {
            val strategy = strategies.find { it.isAppliedTo(mcc, accountEntity, amount) }

            if(strategy != null) {
                handleApprovedTransaction(strategy, accountEntity, amount)
            } else REJECTED_BY_MISSING_BALANCE
        }
    }

    private fun handleApprovedTransaction(
        strategy: TransactionStrategy,
        account: AccountEntity,
        amount: Double
    ): String {
        strategy.execute(account, amount)
        accountRepository.save(account)
        return APPROVED_TRANSACTION
    }
}
