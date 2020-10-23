package com.flawlessconcepts.succor.adapters

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.room.TypeConverter
import com.flawlessconcepts.succor.R
import com.flawlessconcepts.succor.database.Subject
import com.flawlessconcepts.succor.database.lessonitem.LessonItem
import com.flawlessconcepts.succor.database.questionsdb.QuestionObj
import com.flawlessconcepts.succor.database.topicsdb.TopicsObjects
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun resetList(): MutableList<TopicsObjects> {
    val mtopics = mutableListOf(
        TopicsObjects(topicId = 1,name = "Introduction to Physics",
            desc = "this will introduce you to the basic of physics",
            isLocked = false,progress = 75),

        TopicsObjects(topicId = 2,name = "Introduction to Physics",
            desc = "this will introduce you to the basic of physics",
            isLocked = false,progress = 25),

        TopicsObjects(topicId = 3,name = "Ohms law",
            desc = "ohm law talks about this",
            isLocked = false,progress = 20
        ),

        TopicsObjects(topicId = 4,name = "Viscosity",
            desc = "this will introduce you to the basic of physics",
            isLocked = true,progress = 2)
    )
    return mtopics


}



fun dummyLesson(resources: Resources): MutableList<LessonItem> {
    val mlesson = mutableListOf(
        LessonItem(topic = "Introduction to Physics",heading = resources.getString(R.string.intro_phy_01).toString()
        ),
        LessonItem(topic = "Introduction to Physics",heading = "What is physics?" +resources.getString( R.string.intro_phy_02)
        ),
        LessonItem(topic = "Introduction to Physics",heading = "Mechanics" + resources.getString(R.string.intro_phy_03)
        )
    )
    return mlesson
}

fun dummySubjects():MutableList<Subject>{
    val subjects = mutableListOf(
        Subject(name = "Physics",image = 1),
        Subject(name = "Mathematics",image = 2),
        Subject(name = "English",image = 3),
        Subject(name = "Biology",image = 4),
        Subject(name = "Economics",image = 5),
        Subject(name = "Chemistry",image = 6),
        Subject(name = "Agriculture",image = 7),
        Subject(name = "Government",image = 8),
        Subject(name = "Literature",image = 9),
        Subject(name = "Commerce",image = 10),
        Subject(name = "Geography",image = 11)

    )
    return subjects


}
val dummyAns : String = "{\n" +
        "  \"answers\": [\n" +
        "    \"hydrogen\",\n" +
        "    \"hylium\",\n" +
        "    \"berylium\",\n" +
        "    \"boron\"\n" +
        "  ]\n" +
        "}"


fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}



fun createQuestionsFromJson(context:Context):MutableList<QuestionObj>{
    val jsonFileString = getJsonDataFromAsset(context, "physicquestion.json")
    if (jsonFileString != null) {
        Log.i("data", jsonFileString)
    }

    val gson = Gson()
        val listQuestionsType = object : TypeToken<List<QuestionObj>>() {}.type

    var questions: List<QuestionObj> = gson.fromJson(jsonFileString, listQuestionsType)
    return questions.toMutableList()
}


class Converters {

    @TypeConverter
    fun listToJson(value: MutableList<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}


fun formartText(source:String): Spanned {
    val sb = source
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(sb.toString())
    }
}