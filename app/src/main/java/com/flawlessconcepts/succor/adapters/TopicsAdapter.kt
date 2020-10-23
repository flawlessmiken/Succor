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
import com.flawlessconcepts.succor.database.topicsdb.TopicsObjects
import com.flawlessconcepts.succor.databinding.ListItemTopicsBinding

class TopicsAdapter(val clickListener: TopicsItemListner) : ListAdapter<TopicsObjects, TopicsAdapter.ViewHolder>(
    TopicsDiffCallback()
) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    class ViewHolder private constructor(val binding: ListItemTopicsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: TopicsObjects, clickListener: TopicsItemListner) {
            binding.clickListener = clickListener
            binding.topic = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTopicsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }
}


class TopicsDiffCallback : DiffUtil.ItemCallback<TopicsObjects>() {

    override fun areItemsTheSame(oldtopic: TopicsObjects, newtopic: TopicsObjects): Boolean {
        return oldtopic.topicId == oldtopic.topicId
    }


    override fun areContentsTheSame(oldtopic: TopicsObjects, newtopic: TopicsObjects): Boolean {
        return oldtopic == newtopic
    }


}
class TopicsItemListner(val clickListener: (topic: TopicsObjects) -> Unit) {
    fun onClick(topicItem: TopicsObjects) = clickListener(topicItem)
}