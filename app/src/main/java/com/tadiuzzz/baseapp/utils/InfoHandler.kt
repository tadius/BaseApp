package com.tadiuzzz.baseapp.utils

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import com.tadiuzzz.baseapp.di.scope.AppScope
import timber.log.Timber
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

@AppScope
class InfoHandler @Inject constructor() {

    val errorHandler = CoroutineExceptionHandler(handler = { _, error ->

        showLoading(false)

        val defaultErrorMsg = "Что-то пошло не так:"
        val errorMsg = when (error) {
            is ConnectException -> "Проблемы с соединением, проверьте подключение:"
            is HttpException -> "Ошибка сервера:"
            is UnknownHostException -> "Неизвестный адрес сервера:"
            else -> defaultErrorMsg
        }

        Timber.e("%s %s", errorMsg, error.localizedMessage)
        emitErrorMessage(errorMsg + error.localizedMessage)

    })

    private val _infoMessageEvent = SingleLiveEvent<String>()
    val infoMessageEvent: LiveData<String>
        get() = _infoMessageEvent

    private val _errorMessageEvent = SingleLiveEvent<String>()
    val errorMessageEvent: LiveData<String>
        get() = _errorMessageEvent

    val isLoading = ObservableBoolean(false)

    fun emitInfoMessage(message: String) {
        _infoMessageEvent.value = message
    }

    fun emitErrorMessage(message: String) {
        _errorMessageEvent.value = message
    }

    fun showLoading(flag: Boolean) {
        isLoading.set(flag)
    }


}