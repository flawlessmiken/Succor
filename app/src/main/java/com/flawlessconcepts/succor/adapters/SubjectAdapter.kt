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
import com.flawlessconcepts.succor.database.Subject
import com.flawlessconcepts.succor.databinding.ItemSubjectBinding

class SubjectAdapter(val clickListener: SubjectListener) : ListAdapter<Subject,SubjectAdapter.ViewHolder>(SubjectDiffUtils()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemSubjectBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Subject, clickListener: SubjectListener) {
            binding.clickListener = clickListener
            binding.subject = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSubjectBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
class SubjectDiffUtils : DiffUtil.ItemCallback<Subject>() {

    override fun areItemsTheSame(oldsubject: Subject, newsubject: Subject): Boolean {
        return oldsubject.name == newsubject.name
    }


    override fun areContentsTheSame(oldsubject: Subject, newsubject: Subject): Boolean {
        return oldsubject == newsubject
    }


}
class SubjectListener(val clickListener: (lesson: Subject) -> Unit) {
    fun onClick(subjectItem: Subject) = clickListener(subjectItem)
}

