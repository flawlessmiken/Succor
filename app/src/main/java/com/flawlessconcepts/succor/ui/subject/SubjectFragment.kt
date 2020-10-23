package com.flawlessconcepts.succor.ui.subject

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
import androidx.recyclerview.widget.GridLayoutManager
import com.flawlessconcepts.succor.R
import com.flawlessconcepts.succor.adapters.*
import com.flawlessconcepts.succor.database.lessonitem.LessonItemDatabase
import com.flawlessconcepts.succor.database.questionsdb.QuestionsDatabase
import com.flawlessconcepts.succor.database.topicsdb.TopicsDatabase
import com.flawlessconcepts.succor.databinding.FragmentSubjectBinding
import com.flawlessconcepts.succor.databinding.FragmentTopicsBinding

class SubjectFragment : Fragment() {

    private lateinit var subjectViewModel: SubjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSubjectBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_subject, container, false)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val questionsDataSource = QuestionsDatabase.getInstance(application).questionsDbDao
        val topicsDataSource = TopicsDatabase.getInstance(application).topicsDbDao
        val lessonsItemDataSource = LessonItemDatabase.getInstance(application).lessonItemDbDao
        val viewModelFactory = SubjectViewModelFactory(questionsDataSource,topicsDataSource,lessonsItemDataSource,application)

        subjectViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(SubjectViewModel::class.java)

        val textView: TextView = binding.textGallery
        subjectViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        binding.subjectViewModel = subjectViewModel

        val manager2 = GridLayoutManager(activity, 4, GridLayoutManager.HORIZONTAL, false)
        binding.subjectList.layoutManager = manager2

        val adapter =
            SubjectAdapter(SubjectListener { subject ->
                Toast.makeText(context, "${subject.name}", Toast.LENGTH_LONG).show()
                subjectViewModel.onTopicItemClicked(subject)
            })


        binding.subjectList.adapter = adapter
        adapter.submitList(dummySubjects())

//        subjectViewModel.subjects.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.submitList(it)
//            }
//        })
        subjectViewModel.navigateToTopicsFragment.observe(viewLifecycleOwner, Observer { subject ->
            subject?.let {
                this.findNavController().navigate(SubjectFragmentDirections.actionSubjectFragmentToTopicsFragment())
                subjectViewModel.onNextFragmentNavigated()
            }
        })
        binding.setLifecycleOwner(this)


        return binding.root
    }
}