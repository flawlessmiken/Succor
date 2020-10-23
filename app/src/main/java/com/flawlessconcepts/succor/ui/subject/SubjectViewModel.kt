package com.flawlessconcepts.succor.ui.subject

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flawlessconcepts.succor.adapters.dummySubjects
import com.flawlessconcepts.succor.adapters.resetList
import com.flawlessconcepts.succor.database.Subject
import com.flawlessconcepts.succor.database.lessonitem.LessonItem
import com.flawlessconcepts.succor.database.lessonitem.LessonItemDbDao
import com.flawlessconcepts.succor.database.questionsdb.QuestionsDbDao
import com.flawlessconcepts.succor.database.topicsdb.TopicsDbDao
import com.flawlessconcepts.succor.database.topicsdb.TopicsObjects
import kotlinx.coroutines.*

class SubjectViewModel(
    dataSourceQuestionsDbDao: QuestionsDbDao,
    dataSourceTopicsDbDao: TopicsDbDao,
    dataSourceLessonItemDbDao: LessonItemDbDao,
    application: Application
) : ViewModel() {


    val topicsDatabase = dataSourceTopicsDbDao

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val subjects = dummySubjects()

    private var currentLesson = MutableLiveData<Subject?>()


    private val _navigateToTopicsFragment = MutableLiveData<Subject>()
    val navigateToTopicsFragment
        get() = _navigateToTopicsFragment



    init {
        initializetopics()
    }

    private fun initializetopics() {

        uiScope.launch {
           // getTopicsFromDataBase()
        }
    }

//    private suspend fun getTopicsFromDataBase(): TopicsObjects? {
//        return withContext(Dispatchers.IO) {
//            var topicObj = topicsDatabase.getLastTopicInserted()
//            if (topicObj?.topicId== null) {
//                // No data in database yet
//                dummyItemsToDataBase(resetList())
//            }
//            topicObj
//        }
//    }

//    private suspend fun dummyItemsToDataBase(dummyTopics: List<TopicsObjects>){
//        for (topicsObj in dummyTopics) {
//           insert(topicsObj)
//        }
//    }




//    private suspend fun insert(subject: Subject) {
//        withContext(Dispatchers.IO) {
//            topicsDatabase.insert(topicsObjects)
//        }
//    }

    fun onTopicItemClicked(topicclicked: Subject) {
        _navigateToTopicsFragment.value = topicclicked
    }

    fun onNextFragmentNavigated() {
        _navigateToTopicsFragment.value = null
    }




    private val _text = MutableLiveData<String>().apply {
        value = "This are the list of topics"
    }
    val text: LiveData<String> = _text

    private var topic = MutableLiveData<Subject?>()

    //val nights = database.getAllNights()
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score
   // private lateinit val wordList: MutableList<String>






//
//    private fun resetList() {
//        mtopics = List(
//            TopicsObjects(name = "Introduction to Physics",
//            desc = "this will introduce you to the basic of physics",
//            isLocked = false,progress = 25),
//
//            TopicsObjects(name = "Introduction to Physics",
//                desc = "this will introduce you to the basic of physics",
//                isLocked = false,progress = 25),
//
//            TopicsObjects(name = "Ohms law",
//                desc = "ohm law talks about this",
//                isLocked = false,progress = 20
//            ),
//
//            TopicsObjects(name = "Viscosity",
//                desc = "this will introduce you to the basic of physics",
//                isLocked = true,progress = 10)
//        )
//        mtopics.shuffle()
//    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}