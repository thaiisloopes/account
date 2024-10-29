package com.caju.account

import com.caju.account.commons.infra.repositories.AccountRepository
import com.caju.account.createaccount.inbound.resources.AccountRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun beforeEach() {
        accountRepository.deleteAll()
    }

    @Test
    fun `creates account successfully`() {
        val request = AccountRequest(
            foodBalance = 100.0,
            mealBalance = 200.0,
            cashBalance = 300.0
        )
        val data = ObjectMapper().writeValueAsString(request)

        mockMvc.perform(
            post("/accounts")
                .contentType(APPLICATION_JSON)
                .content(data)
        )
            .andExpect(status().isCreated)
            .andExpect {
                assertThat(accountRepository.findAll()).hasSize(1)
            }
    }
}
