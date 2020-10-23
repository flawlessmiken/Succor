package com.flawlessconcepts.succor.ui.questions

import android.app.Application
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flawlessconcepts.succor.adapters.createQuestionsFromJson
import com.flawlessconcepts.succor.database.questionsdb.QuestionObj
import com.flawlessconcepts.succor.database.questionsdb.QuestionsDbDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.Exception

class QuestionViewModel(
    dataSourceQuestionItemDbDao: QuestionsDbDao,
    application: Application
) : ViewModel() {

    val questionDatabase = dataSourceQuestionItemDbDao
    val applicationContext = application

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val questions = createQuestionsFromJson(applicationContext)
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)

    private val _currentQuestionCount = MutableLiveData<Int>()
    val currentQuestionCount
        get() = _currentQuestionCount

    private val _question = MutableLiveData<QuestionObj>()
    val question
        get() = _question

    // hold the size of questions given to the viewmodel
    private val _questionSize = MutableLiveData<Int>()
    val questionSize
        get() = _questionSize


    private val _isStudyMood = MutableLiveData<Int>()
    val isStudyMood
        get() = _isStudyMood

    fun setQuestionMood(){
        _isStudyMood.value = _isStudyMood.value!!.plus(1)
    }

//    private val _checkedButton= MutableLiveData<Int>()
//    val checkedButton
//        get() = _checkedButton

    lateinit var answers: MutableList<String>


    private val _navigateToResultFragment = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [SleepTrackerFragment]
     */
    val navigateToResultFragment: LiveData<Boolean?>
        get() = _navigateToResultFragment

    fun onSubmit() {
        _navigateToResultFragment.value = true

    }

    fun doneNavigating() {
        _navigateToResultFragment.value = null
    }


    init {

        _isStudyMood.value = 0
        questions.shuffle()

        questions.forEachIndexed { index, questionr ->
            var ansr = questionr.answers!!.toMutableList()
            ansr.shuffle()
            questions[index].answers = ansr
        }
        _currentQuestionCount.value = 0
        _questionSize.value = questions.size
        reshuffleAnswers()

    }

    fun reshuffleAnswers() {
        setQuestion()
    }

    private fun setQuestion() {
        question.value = questions[currentQuestionCount.value!!]
        // randomize the answers into a copy of the array


        answers = question.value!!.answers!!.toMutableList()
        //answers = answers.shuffled() as MutableList<String>

        // and shuffle them

        ///(applicationContext as AppCompatActivity).supportActionBar?.title ="Michael"
    }

    fun setCheckedButton(option: Int) {
        //questions[currentQuestionCount.value!!].selected = option
    }

    fun onCheckButtonClicked(option: Int) {
        // questions[currentQuestionCount.value!!].selected=option
        // _checkedButton.value=option
        questions[currentQuestionCount.value!!].selected = option
    }

    fun resetQuestionCount(){
        _currentQuestionCount.value =0
    }


    fun getRadioButtonClicked(answer: String) {

    }

    fun scoreCalculator(): List<Int> {
        var correctScores: Int = 0
        var questionSize: Int = 0
        var failedScore: Int = 0
        questions.forEachIndexed { index, questionObject ->

            try {
                if (questionObject.answers!![questionObject.selected!!] == questionObject.correct) {
                    correctScores++
                } else {
                    failedScore++
                }
            }catch (e: Exception){}

            }
            questionSize = questions.size

        val bundle = listOf(
            questionSize,
            correctScores,
            failedScore
        )
        return bundle
    }


    private val _text = MutableLiveData<String>().apply {
        value = "tis " + question.value!!.text
    }

    init {

    }

    fun onNextButtonClicked() {
        _currentQuestionCount.value = _currentQuestionCount.value?.plus(1)

    }

    fun onPreviousButtonClicked() {
        _currentQuestionCount.value = _currentQuestionCount.value?.minus(1)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}