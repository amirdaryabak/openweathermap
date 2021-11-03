package ir.amirdaryabak.openweathermap.core.data_source.remote


sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(
        message: String?,
        data: T?,
        errorCode: Int = 0,
    ) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}