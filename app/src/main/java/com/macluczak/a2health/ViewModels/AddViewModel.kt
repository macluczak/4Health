package com.macluczak.a2health.ViewModels

import androidx.lifecycle.ViewModel

class AddViewModel: ViewModel() {
    var title = ""

    fun setTitleName(name: String){
        title =  name
    }
}