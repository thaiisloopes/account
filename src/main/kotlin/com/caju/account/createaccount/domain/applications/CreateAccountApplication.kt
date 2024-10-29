package com.caju.account.createaccount.domain.applications

import com.caju.account.commons.infra.repositories.AccountRepository
import com.caju.account.commons.infra.repositories.resources.AccountEntity
import org.springframework.stereotype.Service

@Service
class CreateAccountApplication(
    private val accountRepository: AccountRepository
) {
    fun process(foodBalance: Double, mealBalance: Double, cashBalance: Double) {
        accountRepository.save(
            AccountEntity(
                foodBalance = foodBalance,
                mealBalance = mealBalance,
                cashBalance = cashBalance
            )
        )
    }
}
