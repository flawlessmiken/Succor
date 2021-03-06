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

package com.flawlessconcepts.succor.database.topicsdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.flawlessconcepts.succor.database.topicsdb.TopicsObjects


/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
interface TopicsDbDao {

    @Insert
    fun insert(topicObj: TopicsObjects)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param night new value to write
     */
    @Update
    fun update(topicObj: TopicsObjects)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from topics_table WHERE topicId = :key")
    fun get(key: Long): TopicsObjects

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM topics_table")
    fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM topics_table ORDER BY topicId DESC")
    fun getAllTopics(): LiveData<List<TopicsObjects>>

    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM topics_table ORDER BY topicId DESC LIMIT 1")
    fun getLastTopicInserted(): TopicsObjects?

    /**
     * Selects and returns the night with given nightId.
     */
    @Query("SELECT * from topics_table WHERE topicId = :key")
    fun getNightWithId(key: Long): LiveData<TopicsObjects>
}
