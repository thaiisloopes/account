package com.caju.account.commons.infra.repositories

import com.caju.account.commons.infra.repositories.resources.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import jakarta.persistence.LockModeType.PESSIMISTIC_WRITE

@Repository
interface AccountRepository : JpaRepository<AccountEntity, String> {
    @Lock(PESSIMISTIC_WRITE)
    @Query("SELECT a FROM AccountEntity a WHERE a.id = :id")
    fun findByIdWithPessimisticLock(id: String): AccountEntity?
}
