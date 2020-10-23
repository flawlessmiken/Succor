package com.flawlessconcepts.succor.ui.leadersboard

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class LeadersBoardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is fragment_new_lesson "
    }
    val text: LiveData<String> = _text






}