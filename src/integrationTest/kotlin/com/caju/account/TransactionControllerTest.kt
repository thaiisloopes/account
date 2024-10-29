package com.caju.account

import com.caju.account.commons.infra.repositories.AccountRepository
import com.caju.account.commons.infra.repositories.resources.AccountEntity
import com.caju.account.createtransaction.inbound.resources.TransactionRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {
    @Autowired
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun beforeEach() {
        accountRepository.deleteAll()
    }

    @Test
    fun `creates transaction successfully`() {
        val accountEntity = AccountEntity(
            foodBalance = 100.0,
            mealBalance = 200.0,
            cashBalance = 300.0
        )
        accountRepository.save(accountEntity)

        val request = TransactionRequest(
            account = accountEntity.id.toString(),
            totalAmount = 50.00,
            mcc = "5811",
            merchant = "PADARIA DO ZE               SAO PAULO BR"
        )
        val data = ObjectMapper().writeValueAsString(request)

        mockMvc.perform(
            post("/transactions")
                .contentType(APPLICATION_JSON)
                .content(data)
        )
            .andExpect(status().isOk)
            .andExpect(
                content().json(
                    """ {
                            "code": "00"
                        }
                        """
                )
            )
    }
}
