package com.flawlessconcepts.succor.database.questionsdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions_table")
data class QuestionObj(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "subject_source")
    var subject: String = "",

    @ColumnInfo(name = "topic_source")
    var topic: String = "",

    @ColumnInfo(name = "question_text")
    var text: String = "",

    @ColumnInfo(name = "selected_ans")
    var selected: Int?,

    @ColumnInfo(name = "question_answers_in_json")
    var answers: MutableList<String> ?,

    @ColumnInfo(name = "correct_answer")
    var correct: String ?

)