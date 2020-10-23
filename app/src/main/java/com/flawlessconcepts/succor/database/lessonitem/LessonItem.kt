package com.flawlessconcepts.succor.database.lessonitem

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson_item_table")
data class LessonItem(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "topic_source")
    var topic: String = "",

    @ColumnInfo(name = "lesson_heading")
    var heading: String = "",

    @ColumnInfo(name = "thumbnail")
    var thumbnail: String = ""

)