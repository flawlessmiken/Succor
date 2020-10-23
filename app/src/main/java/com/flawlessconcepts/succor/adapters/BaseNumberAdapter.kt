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
package com.flawlessconcepts.succor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flawlessconcepts.succor.database.lessonitem.LessonItem
import com.flawlessconcepts.succor.database.topicsdb.TopicsObjects
import com.flawlessconcepts.succor.databinding.ItemNumberBinding
import com.flawlessconcepts.succor.databinding.ListItemTopicsBinding

class BaseNumberAdapter(val clickListener: BaseNumberListener) : ListAdapter<LessonItem, BaseNumberAdapter.ViewHolder>(BaseNumberNavDiffUtils()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemNumberBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: LessonItem, clickListener: BaseNumberListener) {
            binding.clickListener = clickListener
            binding.lesson = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNumberBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class BaseNumberNavDiffUtils : DiffUtil.ItemCallback<LessonItem>() {

    override fun areItemsTheSame(oldlesson: LessonItem, newlesson: LessonItem): Boolean {
        return oldlesson.id === newlesson.id
    }


    override fun areContentsTheSame(oldlesson: LessonItem, newlesson: LessonItem): Boolean {
        return oldlesson == newlesson
    }


}
class BaseNumberListener(val clickListener: (lesson: LessonItem) -> Unit) {
    fun onClick(lessonItem: LessonItem) = clickListener(lessonItem)
}