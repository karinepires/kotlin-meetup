package io.pivotal.WhereIsMyRose.controllers

import org.hamcrest.Matchers.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest
class WhereIsMyRoseAPITest {
    @Autowired lateinit var mockMvc: MockMvc

    @Test fun `list all places`() {
        mockMvc.perform(get("/api/places"))
                .andExpect(status().isOk)
                .andExpect(jsonPath(
                        "$[*].name",
                        equalTo(listOf("Kotlin Meetup", "Rose Bar")))
                )
                .andExpect(jsonPath(
                        "$[*].price",
                        equalTo(listOf(2.0, 3.3)))
                )
//                .andExpect(jsonPath(
//                        "$[*].location",
//                        equalTo(listOf(
//                                "33 Rue la Fayette, 75009 Paris",
//                                "24 Rue de Ponthieu, 75008 Paris"
//                        )))
//                )
    }

    @Test fun `list places within a price range`() {
        mockMvc.perform(get("/api/places?minPrice=2.0&maxPrice=3.0"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$[*].name", equalTo(listOf("Kotlin Meetup"))))
                .andExpect(jsonPath("$[*].price", equalTo(listOf(2.0))))
        mockMvc.perform(get("/api/places?minPrice=2.0&maxPrice=3.3"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$[*].name", equalTo(listOf("Kotlin Meetup", "Rose Bar"))))
                .andExpect(jsonPath("$[*].price", equalTo(listOf(2.0, 3.3))))
        mockMvc.perform(get("/api/places?minPrice=3.0&maxPrice=4.0"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$[*].name", equalTo(listOf("Rose Bar"))))
                .andExpect(jsonPath("$[*].price", equalTo(listOf(3.3))))
    }

    @Test fun `get error if minPrice is bigger than maxPrice`() {
        mockMvc.perform(get("/api/places?minPrice=42.0&maxPrice=1.0"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message", equalTo("maxPrice must be bigger than minPrice")))
    }

//    @Test fun `get error if minPrice is 42 or more`() {
//        mockMvc.perform(get("/api/places?minPrice=42.0&maxPrice=3.0"))
//                .andExpect(status().isOk)
//                .andExpect(jsonPath("$[*].name", equalTo(listOf("Kotlin Meetup"))))
//    }
}


