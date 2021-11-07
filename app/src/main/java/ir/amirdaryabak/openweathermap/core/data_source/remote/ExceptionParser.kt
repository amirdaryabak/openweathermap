package ir.amirdaryabak.openweathermap.core.data_source.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.amirdaryabak.openweathermap.core.model.ErrorModel
import ir.amirdaryabak.openweathermap.core.utils.Constants.SOMETHING_WENT_WRONG
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.lang.reflect.Type

object ExceptionParser {

    const val NO_INTERNET_CONNECTION = "No Internet (Swipe to refresh)"
    private const val CODE_NO_ERROR = 0

    private val gson = Gson()

    fun <T> getErrorMessage(t: Throwable): Resource<T> {
        Timber.d("SafeApi Throwable: %s", t)
        return when (t) {
            is IOException -> {
                Resource.Error(NO_INTERNET_CONNECTION, null, errorCode = getErrorCode(t))
            }
            is HttpException -> {
                val errorJson = extractMessageFromHttpException(t)
                val errorCode = getErrorCode(t)
                Resource.Error(
                    "{$errorJson (Swipe to refresh)}",
                    null,
                    errorCode = errorCode
                )
            }
            else -> {
                Resource.Error(SOMETHING_WENT_WRONG, null, errorCode = getErrorCode(t))
            }
        }
    }

    private fun getErrorCode(t: Throwable): Int {
        return when (t) {
            is IOException -> CODE_NO_ERROR
            is HttpException -> t.code()
            else -> CODE_NO_ERROR
        }
    }

    private fun extractMessageFromHttpException(httpException: HttpException): String {
        val errorBody: ResponseBody? = httpException.response()?.errorBody()
        return try {
            if (errorBody != null) {
                val type: Type = object : TypeToken<ErrorModel>() {}.type
                val errorModel: ErrorModel =
                    gson.fromJson(errorBody.charStream(), type)
                if (errorModel.message?.isNotBlank() == true)
                    errorModel.message
                else
                    SOMETHING_WENT_WRONG
            } else {
                SOMETHING_WENT_WRONG
            }
        } catch (t: Throwable) {
            SOMETHING_WENT_WRONG
        }
    }

}