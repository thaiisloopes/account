package com.caju.account.createtransaction.domain.applications

import com.caju.account.commons.infra.repositories.AccountRepository
import com.caju.account.commons.infra.repositories.TransactionRepository
import com.caju.account.commons.infra.repositories.resources.AccountEntity
import com.caju.account.commons.infra.repositories.resources.TransactionEntity
import com.caju.account.createtransaction.domain.Merchant
import com.caju.account.createtransaction.domain.strategies.TransactionStrategy
import com.caju.account.createtransaction.inbound.resources.APPROVED_TRANSACTION
import com.caju.account.createtransaction.inbound.resources.REJECTED_BY_MISSING_BALANCE
import com.caju.account.createtransaction.inbound.resources.REJECTED_BY_UNKNOWN_ERROR
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CreateTransactionApplication(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val strategies: List<TransactionStrategy>
) {
    @Transactional
    fun perform(accountId: String, amount: Double, mcc: String, merchant: String): String {
        return runCatching {
            val accountEntity = accountRepository.findByIdWithPessimisticLock(accountId)
                ?: return REJECTED_BY_UNKNOWN_ERROR

            accountEntity.let {
                strategies.find { it.isAppliedTo(getMcc(mcc, merchant), accountEntity, amount) }
            }?.let { transactionStrategy ->
                val transactionEntity = TransactionEntity(accountId, amount, merchant, getMcc(mcc, merchant))
                handleApprovedTransaction(transactionStrategy, accountEntity, transactionEntity, amount)
            } ?: REJECTED_BY_MISSING_BALANCE
        }.getOrElse { REJECTED_BY_UNKNOWN_ERROR }
    }

    private fun handleApprovedTransaction(
        strategy: TransactionStrategy,
        account: AccountEntity,
        transaction: TransactionEntity,
        amount: Double
    ): String {
        strategy.execute(account, amount)
        accountRepository.save(account)
        transactionRepository.save(transaction)
        return APPROVED_TRANSACTION
    }

    private fun getMcc(mcc: String, merchant: String): String {
        return Merchant.getMccFrom(merchant).ifEmpty { mcc }
    }
}
