package com.flawlessconcepts.succor.ui.lessons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.flawlessconcepts.succor.R
import com.flawlessconcepts.succor.adapters.BaseNumberAdapter
import com.flawlessconcepts.succor.adapters.BaseNumberListener
import com.flawlessconcepts.succor.database.lessonitem.LessonItemDatabase
import com.flawlessconcepts.succor.databinding.FragmentLessonsBinding
import com.flawlessconcepts.succor.ui.topics.TopicsFragmentDirections

class LessonsFragment : Fragment() {

    private lateinit var lessonViewModel: LessonViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLessonsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_lessons, container, false
        )


        val application = requireNotNull(this.activity).application

        val stringtopicId = LessonsFragmentArgs.fromBundle(arguments).topicId

        val lessonsItemDataSource = LessonItemDatabase.getInstance(application).lessonItemDbDao
        val viewModelFactory = LessonViewModelFactory(lessonsItemDataSource, application)
        lessonViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(LessonViewModel::class.java)


        binding.lessonsViewModel = lessonViewModel

        val textView: TextView = binding.textSlideshow
        lessonViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val adapter =
            BaseNumberAdapter(BaseNumberListener { lesson ->
                Toast.makeText(context, "${lesson.id}", Toast.LENGTH_LONG).show()
                lessonViewModel.onItemClicked(lesson)
            })
       // val manager = LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false)
//        binding.rcView.layoutManager = manager
//
//        binding.rcView.adapter = adapter
//
//        lessonViewModel.lessons.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.submitList(it)
//            }
//        })

        lessonViewModel.lessons.observe(viewLifecycleOwner, Observer { lesson ->
            lesson?.let {

                lessonViewModel.currentLessonCount.observe(viewLifecycleOwner, Observer { count ->
                    if (count <lesson.size && count >= 0) {
                        lessonViewModel.setText(lesson[count])


                    }else{
                        this.findNavController().navigate(LessonsFragmentDirections.actionLessonsFragmentToQuestionsFragment(stringtopicId,false))
                        lessonViewModel.resetCount()
                    }

                    if (count <= 0){
                        lessonViewModel.previousBtnVisible.value = View.GONE
                    }else lessonViewModel.previousBtnVisible.value = View.VISIBLE


                })

            }
        })
        binding.setLifecycleOwner(this)





        return binding.root
    }
}