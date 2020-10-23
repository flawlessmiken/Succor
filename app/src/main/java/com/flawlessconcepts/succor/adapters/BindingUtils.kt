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

import android.graphics.Color
import android.opengl.Visibility
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.flawlessconcepts.succor.R
import com.flawlessconcepts.succor.database.Subject
import com.flawlessconcepts.succor.database.lessonitem.LessonItem
import com.flawlessconcepts.succor.database.questionsdb.QuestionObj
import com.flawlessconcepts.succor.database.topicsdb.TopicsObjects
import de.hdodenhof.circleimageview.CircleImageView


@BindingAdapter("setTopicName")
fun TextView.setTopicName(item: TopicsObjects) {
    text = item.name
}

@BindingAdapter("setTopicDescription")
fun TextView.setTopicDescription(item: TopicsObjects) {
    text = item.desc
}

@BindingAdapter("setProgress")
fun ProgressBar.setProgress(item: TopicsObjects) {
    progress = (item.progress)
}

@BindingAdapter("setLocked")
fun ImageView.setLocked(item: TopicsObjects) {
    if (item.isLocked!!) {
        //visibility = VISIBLE
        //
        setImageResource(R.drawable.ic_baseline_lock_24)


    } else setImageResource(R.drawable.ic_baseline_forward_24)

}

@BindingAdapter("image")
fun CircleImageView.setImageResourc(item: TopicsObjects) {
    setImageResource(R.drawable.ic_atom)
}

@BindingAdapter("setNumber")
fun TextView.setNumber(item: LessonItem) {
    text = item.heading
}

@BindingAdapter("setSubjectName")
fun TextView.setSubjectName(item: Subject) {
    text = item.name
}


@BindingAdapter("setSubjectCount")
fun TextView.setSubjectCount(item: Subject) {
    text = item.image.toString()
}

@BindingAdapter("formatText")
fun TextView.formatText(item: QuestionObj) {
    text = formartText(item.text.toString())
}



@BindingAdapter("setImageUrl")
fun setImageUrl(imageView: ImageView, item: Subject) {


    imageView.setImageResource(when (item.image) {

        1 -> R.drawable.ic_atom
        2 -> R.drawable.ic_maths

        3 -> R.drawable.ic_english

        4 -> R.drawable.ic_biology
        5 -> R.drawable.ic_money
        6 -> R.drawable.ic_chemistry
        7 -> R.drawable.ic_agriculture
        8 -> R.drawable.ic_government
        9 -> R.drawable.ic_literature
        10 -> R.drawable.ic_commerce
        11 -> R.drawable.ic_geography
        else -> R.drawable.ic_atom
    })

//    var imgUrl: String ="https://www.cleanpng.com/png-symbol-science-atom-chemistry-clip-art-png-free-at-693553/"
//    imgUrl.let {
//        val imgUri = it.toUri().buildUpon().scheme("https").build()
//        Glide.with(imageView)
//            .load("https://www.cleanpng.com/png-symbol-science-atom-chemistry-clip-art-png-free-at-693553/")
//            .apply(
//                RequestOptions()
//                    .placeholder(R.drawable.ic_atom)
//                    .error(R.drawable.ic_baseline_home)
//            )
//            .into(imageView)
//    }

}


//
//
//@BindingAdapter("sleepQualityString")
//fun TextView.setSleepQualityString(item: TopicsObjects) {
//    text = convertNumericQualityToString(item.sleepQuality, context.resources)
//}
//
//
//
//@BindingAdapter("sleepImage")
//fun ImageView.setSleepImage(item: TopicsObjects) {
//    setImageResource(when () {
//        0 -> R.drawable.ic_sleep_0
//        1 -> R.drawable.ic_sleep_1
//        2 -> R.drawable.ic_sleep_2
//
//        3 -> R.drawable.ic_sleep_3
//
//        4 -> R.drawable.ic_sleep_4
//        5 -> R.drawable.ic_sleep_5
//        else -> R.drawable.ic_sleep_active
//    })
//}
