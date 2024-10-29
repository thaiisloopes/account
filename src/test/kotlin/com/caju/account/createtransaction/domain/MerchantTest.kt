package com.caju.account.createtransaction.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MerchantTest {
    @Test
    fun `returns mcc from merchantLabel when it was founded`() {
        val merchantLabel = "PAG*JoseDaSilva          RIO DE JANEI BR"

        val result = Merchant.getMccFrom(merchantLabel)

        assertThat(result).isEqualTo("5411")
    }

    @Test
    fun `returns empty when mcc was not found by merchantLabel`() {
        val merchantLabel = "PAG*JoseDaSilva"

        val result = Merchant.getMccFrom(merchantLabel)

        assertThat(result).isEqualTo("")
    }
}
