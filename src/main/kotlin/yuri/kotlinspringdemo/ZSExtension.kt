package yuri.kotlinspringdemo

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlin.reflect.KClass

fun Any.toJsonString(): String {
    return jacksonObjectMapper().writeValueAsString(this)
}

fun Any.toJson(): Map<*, *> {
    return jacksonObjectMapper().readValue(this.toJsonString(), Map::class.java)
}

inline fun <reified T : Any> String.toBean(): T {
    return jacksonObjectMapper().readValue(this, T::class.java)
}

fun <T : Any> Map<String, *>.toBean(kClass: KClass<T>): T {
    return jacksonObjectMapper().convertValue(this, kClass.java)
}
