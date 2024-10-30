package com.caju.account.createaccount.inbound

import com.caju.account.createaccount.domain.applications.CreateAccountApplication
import com.caju.account.createaccount.inbound.resources.AccountRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus.CREATED

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val createAccountApplication: CreateAccountApplication
) {
    @PostMapping
    fun create(@RequestBody request: AccountRequest): ResponseEntity<Any> {
        createAccountApplication.process(request.foodBalance, request.mealBalance, request.cashBalance)

        return ResponseEntity(CREATED)
    }
}
