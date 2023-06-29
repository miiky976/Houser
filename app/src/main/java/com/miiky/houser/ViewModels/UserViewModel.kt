package com.miiky.houser.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    val User = mutableStateOf("")
    val Name = mutableStateOf("")
    val Last = mutableStateOf("")
    val Email = mutableStateOf("")
    init {
        viewModelScope.launch {  }
    }
}