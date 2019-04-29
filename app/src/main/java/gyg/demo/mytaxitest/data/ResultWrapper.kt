package gyg.demo.mytaxitest.data

sealed class ResultWrapper<T> {
    data class Success<T>(val value: T) : ResultWrapper<T>()
    data class Failure<T>(val error: Throwable) : ResultWrapper<T>()
}
