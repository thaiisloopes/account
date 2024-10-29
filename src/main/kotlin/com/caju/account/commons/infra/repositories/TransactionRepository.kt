package com.caju.account.commons.infra.repositories

import com.caju.account.commons.infra.repositories.resources.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<TransactionEntity, String>
