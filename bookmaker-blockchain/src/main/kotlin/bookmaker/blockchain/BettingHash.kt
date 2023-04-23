@file:Suppress("UnstableApiUsage")

package bookmaker.blockchain

import com.google.common.hash.Funnel
import com.google.common.hash.Hashing
import com.google.common.hash.PrimitiveSink
import groovy.transform.ImmutableOptions

interface Hashable {
    /* Block's hash */
    fun hash(): Hash
}

typealias HashGenerator<T> = Funnel<T>

@ImmutableOptions
data class Hash(val value: String) {
    companion object {
        fun <T> of(instance: T, hashGenerator: HashGenerator<T>): Hash = Hash(Hashing.sha256().hashObject(instance, hashGenerator).toString())
    }
}

fun PrimitiveSink.putString(value: String): PrimitiveSink =
    putString(value, Charsets.UTF_8)