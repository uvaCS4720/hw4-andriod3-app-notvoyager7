package edu.virginia.cs.androidapp3

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MainUIState(
    val counterValue: Int
)

class MainViewModel(

) : ViewModel() {

}