import exceptions.CallWithErrorException
import retrofit2.Call
import retrofit2.Response
import java.util.*

fun <T> Call<T>.retryCall(): T? = retryCall {
    config { retries = 3 }
}.throwExceptionIfError()


private fun <T> Response<T>.throwExceptionIfError(): T? = this.errorBody()
    .takeIf(Objects::nonNull)
    ?.let { throw CallWithErrorException() }
    ?: this.body()