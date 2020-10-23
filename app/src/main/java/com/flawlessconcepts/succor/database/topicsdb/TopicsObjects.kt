package com.flawlessconcepts.succor.database.topicsdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "topics_table")
data class TopicsObjects(
    @PrimaryKey(autoGenerate = true)
    var topicId: Long = 0L,

    @ColumnInfo(name = "topic_name")
    var name: String = "",

    @ColumnInfo(name = "topic_desc")
    var desc: String = "",

    @ColumnInfo(name = "subject_source")
    var subject: String = "",

    @ColumnInfo(name = "thumbnail")
    var thumbnail: String = "",

    @ColumnInfo(name = "video_source")
    var video: String = "",

    @ColumnInfo(name = "lessons_list_in_json")
    var lessonslist: String = "",

    @ColumnInfo(name = "related_questions_ids")
    var questions: String = "",

    @ColumnInfo(name = "progress")
    var progress: Int = 10,

    @ColumnInfo(name = "favourite")
    var favourite: Boolean = false,

    @ColumnInfo(name = "islocked")
    var isLocked: Boolean?


)