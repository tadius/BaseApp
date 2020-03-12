package com.tadiuzzz.baseapp.feature.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import com.tadiuzzz.baseapp.data.entity.EntityExample
import com.tadiuzzz.baseapp.feature.navigation.Navigator
import com.tadiuzzz.baseapp.utils.InfoHandler
import timber.log.Timber
import javax.inject.Inject

class ExampleViewModel @Inject constructor(
    private val getData: GetDataUseCase,
    private val getDataFromDb: GetDataFromDbUseCase,
    private val infoHandler: InfoHandler,
    private val navigator: Navigator
) : ViewModel() {

    private var viewModelJob = SupervisorJob()
    private val viewModelScope =
        CoroutineScope(Dispatchers.Main + viewModelJob + infoHandler.errorHandler)

    private val _exampleData = MutableLiveData<EntityExample>()
    val exampleData: LiveData<EntityExample> = _exampleData

    init {
        loadRemoteData()
        Timber.d("ExampleViewModel init")

    }

    fun loadRemoteData() {

        if (infoHandler.isLoading.get()) return

        viewModelScope.launch {
            infoHandler.showLoading(true)
            //запрос данных и ожидание их получения
            val exampleResponse =
                withContext(IO) {
                    delay(3000)
                    getData("example_id", "example_amount")
                }
            infoHandler.showLoading(false)

            if (exampleResponse.isSuccessful && exampleResponse.body() != null) {
                _exampleData.value = exampleResponse.body()
                infoHandler.emitInfoMessage("Данные получены успешно")
            } else {
                infoHandler.emitInfoMessage("Сервер вернул пустой объект")
            }

        }

    }

    fun loadLocalData() {

        if (infoHandler.isLoading.get()) return

        viewModelScope.launch {
            infoHandler.showLoading(true)
            //запрос данных и ожидание их получения
            val data : List<EntityExample>? =
                withContext(IO) {
                    delay(3000)
                    getDataFromDb(3)
                }
            infoHandler.showLoading(false)

            if(data != null && data.isNotEmpty()) {
                _exampleData.value = data.get(0)
            } else {
                infoHandler.emitInfoMessage("В базе нет таких объектов")
            }

        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}