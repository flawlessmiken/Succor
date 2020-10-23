package com.flawlessconcepts.succor.ui.lessons

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.flawlessconcepts.succor.adapters.dummyLesson
import com.flawlessconcepts.succor.database.lessonitem.LessonItem
import com.flawlessconcepts.succor.database.lessonitem.LessonItemDbDao
import kotlinx.coroutines.*

class LessonViewModel(
    dataSourceLessonItemDbDao: LessonItemDbDao,
    application: Application
) : ViewModel() {


    private val _setLessonItemInView = MutableLiveData<LessonItem>()
    val setLesonItemInView
        get() = _setLessonItemInView

    private val _currentLessonCount = MutableLiveData<Int>()
    val currentLessonCount
        get() = _currentLessonCount


    val lessonsDatabase = dataSourceLessonItemDbDao
    val applicationContext = application

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val lessons = lessonsDatabase.getAllLesson()

    init {
        initializelessons()
        _currentLessonCount.value = 0

    }

    private fun initializelessons() {

        uiScope.launch {
            getLessonFromDataBase()
        }
    }

    private suspend fun getLessonFromDataBase(): LessonItem? {
        return withContext(Dispatchers.IO) {
            var lessonItem = lessonsDatabase.getLastLessonInserted()
            if (lessonItem?.id == null) {
                // No data in database yet
                dummyItemsToDataBase(dummyLesson(applicationContext.resources))
            }
            lessonItem
        }
    }

    private suspend fun dummyItemsToDataBase(dummyLesson: List<LessonItem>) {
        for (lesson in dummyLesson) {
            lessonsDatabase.insert(lesson)
        }
    }

    fun onItemClicked(lessonItem: LessonItem) {
        _setLessonItemInView.value = lessonItem
    }

    private val _textMain = MutableLiveData<String>()
    val textMain: LiveData<String> = _textMain

    fun setText(lesson: LessonItem) {
        //var lessonItem = s
        _textMain.value = lesson.heading
    }

    fun onNextItemNavigated() {
        _setLessonItemInView.value = null
    }

    fun onNextButtonClicked() {
        _currentLessonCount.value = _currentLessonCount.value?.plus(1)

    }

    fun onPreviousButtonClicked() {
        _currentLessonCount.value = _currentLessonCount.value?.minus(1)
    }
    fun resetCount() {
        _currentLessonCount.value = 0
    }
    private val _previousBtnVisible = MutableLiveData<Int>()
    val previousBtnVisible
        get() = _previousBtnVisible


    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}