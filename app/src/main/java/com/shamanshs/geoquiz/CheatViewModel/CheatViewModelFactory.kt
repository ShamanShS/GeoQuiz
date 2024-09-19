package com.shamanshs.geoquiz.CheatViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CheatViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor().newInstance()
    }
}