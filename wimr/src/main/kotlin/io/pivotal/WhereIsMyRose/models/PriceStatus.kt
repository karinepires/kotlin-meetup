package io.pivotal.WhereIsMyRose.models

sealed class PriceStatus(val message: String)

object ErrorInvalidRange : PriceStatus("maxPrice must be bigger than minPrice")
object OK : PriceStatus("Price is OK")


//object ErrorTooExpensive : PriceStatus("Price is too expensive")




