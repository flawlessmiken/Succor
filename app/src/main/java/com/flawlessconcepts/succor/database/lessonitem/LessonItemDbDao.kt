/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flawlessconcepts.succor.database.lessonitem
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.flawlessconcepts.succor.database.questionsdb.QuestionObj


/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
interface LessonItemDbDao {

    @Insert
    fun insert(lessonItem: LessonItem)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param night new value to write
     */
    @Update
    fun update(lessonItem: LessonItem)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from lesson_item_table WHERE id = :key")
    fun get(key: Long): LessonItem

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM lesson_item_table")
    fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM lesson_item_table ORDER BY id DESC")
    fun getAllLesson(): LiveData<List<LessonItem>>

    @Query("SELECT * FROM lesson_item_table WHERE topic_source = :key ORDER BY id DESC")
    fun getAllMySubjectsQuestion(key: String): LiveData<List<LessonItem>>

    @Query("SELECT * FROM lesson_item_table WHERE topic_source = :key ORDER BY id DESC")
    fun getLastLessonObject(key: String): LiveData<List<LessonItem>>
    /**
     * Selects and returns the latest questionObj.
     */
    @Query("SELECT * FROM lesson_item_table ORDER BY id DESC LIMIT 1")
    fun getLastLessonInserted(): LessonItem?
}
