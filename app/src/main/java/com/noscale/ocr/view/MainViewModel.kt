package com.noscale.ocr.view

import androidx.lifecycle.*
import com.noscale.ocr.data.Expression
import com.noscale.ocr.data.source.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventFlow = eventChannel.receiveAsFlow()

    val switchStorage = MutableLiveData(repository.usingDb)

    val expressions: LiveData<List<Expression>> = switchStorage.switchMap { isSwitch ->
        repository.usingDb = isSwitch
        repository.read().asLiveData()
    }

    fun start() {
        viewModelScope.launch {
            eventChannel.send(Event.Start)
        }
    }

    fun insert(text: String?, result: Double) {
        viewModelScope.launch {
            val data = Expression(
                name = text,
                result = result.toInt()
            )
            repository.write(data)
        }
    }
}

sealed class Event {
    object Start: Event()
}