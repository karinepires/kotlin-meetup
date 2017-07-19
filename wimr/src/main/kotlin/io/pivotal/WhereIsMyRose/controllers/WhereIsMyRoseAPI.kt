package io.pivotal.WhereIsMyRose.controllers

import io.pivotal.WhereIsMyRose.models.ErrorInvalidRange
import io.pivotal.WhereIsMyRose.models.OK
import io.pivotal.WhereIsMyRose.models.Place
import io.pivotal.WhereIsMyRose.models.PriceStatus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class WhereIsMyRoseAPI {

    fun getListPlaces(minPrice: Double?, maxPrice: Double?) = listOf(
            Place(name = "Kotlin Meetup", price = 2.0),
            Place(name = "Rose Bar", price = 3.3)
    ).filter {
        it.price >= (minPrice ?: Double.NEGATIVE_INFINITY) && it.price <= (maxPrice ?: Double.POSITIVE_INFINITY)
    }

    fun validatePrice(minPrice: Double?, maxPrice: Double?): PriceStatus =
            if (minPrice != null && maxPrice != null && minPrice > maxPrice) ErrorInvalidRange
            else OK

//            if (minPrice != null && minPrice >= 42.0) ErrorTooExpensive else OK


//    @RequestMapping("/places")
//    fun listPlaces(
//            @RequestParam(required = false) minPrice: Double?,
//            @RequestParam(required = false) maxPrice: Double?
//    ) = listOf(
//            Place(name = "Kotlin Meetup", price = 2.0),
//            Place(name = "Rose Bar", price = 3.3)
//    ).filter {
//        it.price >= (minPrice ?: Double.NEGATIVE_INFINITY) &&
//                it.price <= (maxPrice ?: Double.POSITIVE_INFINITY)
//    }

    @RequestMapping("/places")
    fun listPlaces(
            @RequestParam(required = false) minPrice: Double?,
            @RequestParam(required = false) maxPrice: Double?
    ): ResponseEntity<Any> = when (validatePrice(minPrice, maxPrice)) {
        is ErrorInvalidRange -> ResponseEntity(ErrorInvalidRange, HttpStatus.BAD_REQUEST)
        is OK -> ResponseEntity(getListPlaces(minPrice, maxPrice), HttpStatus.OK)
    }
}

