package com.caju.account.commons.infra.repositories.resources

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Column
import jakarta.persistence.GenerationType.IDENTITY
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "account")
data class AccountEntity(
    var foodBalance: Double,
    var mealBalance: Double,
    var cashBalance: Double
) {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false, updatable = false, unique = true)
  var id: Long = 0

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  lateinit var createdAt: LocalDateTime

  @UpdateTimestamp
  @Column(nullable = false, updatable = true)
  lateinit var updatedAt: LocalDateTime
}
