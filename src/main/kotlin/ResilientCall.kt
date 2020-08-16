import retrofit2.Call
import retrofit2.Response

class ResilientCall<T>(private val call: Call<T>, var config: ResilientConfig = ResilientConfig()) {
    fun call(): Response<T> {
        repeat(config.retries) {
            runCatching {
                return tryOnce()
            }
        }

        return tryOnce()
    }

    private fun tryOnce() = call.clone().execute()

}

fun <T> Call<T>.retryCall(block: ResilientCall<T>.() -> Unit): Response<T> = ResilientCall(this)
    .also {
        block(it)
    }.call()

fun <T> ResilientCall<T>.config(configBlock: ResilientConfig.() -> Unit) {
    config = ResilientConfig().apply(configBlock)
}