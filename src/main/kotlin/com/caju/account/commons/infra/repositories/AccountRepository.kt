package com.caju.account.commons.infra.repositories

import com.caju.account.commons.infra.repositories.resources.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<AccountEntity, String>
