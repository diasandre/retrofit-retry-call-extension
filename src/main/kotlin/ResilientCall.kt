import retrofit2.Call
import retrofit2.Response

class ResilientCall<T>(private val call: Call<T>, private val retries: Int = 3) {

    fun call(): Response<T> {
        repeat(retries) {
            runCatching {
                return execute()
            }
        }

        return execute()
    }

    private fun execute(): Response<T> = call.clone()
        .execute()

}