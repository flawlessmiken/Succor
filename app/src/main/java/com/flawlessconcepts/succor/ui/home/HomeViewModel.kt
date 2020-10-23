package com.flawlessconcepts.succor.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flawlessconcepts.succor.database.lessonitem.LessonItemDbDao
import com.flawlessconcepts.succor.database.questionsdb.QuestionsDbDao
import com.flawlessconcepts.succor.database.topicsdb.TopicsDbDao

class HomeViewModel(
    dataSourceQuestionsDbDao: QuestionsDbDao,
    dataSourceTopicsDbDao: TopicsDbDao,
    dataSourceLessonItemDbDao: LessonItemDbDao,
    application: Application
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text






}