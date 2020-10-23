package com.flawlessconcepts.succor.ui.topics

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
import com.flawlessconcepts.succor.R
import com.flawlessconcepts.succor.adapters.TopicsAdapter
import com.flawlessconcepts.succor.adapters.TopicsItemListner
import com.flawlessconcepts.succor.database.lessonitem.LessonItemDatabase
import com.flawlessconcepts.succor.database.questionsdb.QuestionsDatabase
import com.flawlessconcepts.succor.database.topicsdb.TopicsDatabase
import com.flawlessconcepts.succor.databinding.FragmentTopicsBinding

class TopicsFragment : Fragment() {

    private lateinit var topicsViewModel: TopicsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTopicsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_topics, container, false)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val questionsDataSource = QuestionsDatabase.getInstance(application).questionsDbDao
        val topicsDataSource = TopicsDatabase.getInstance(application).topicsDbDao
        val lessonsItemDataSource = LessonItemDatabase.getInstance(application).lessonItemDbDao
        val viewModelFactory = TopicsViewModelFactory(questionsDataSource,topicsDataSource,lessonsItemDataSource,application)

        topicsViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(TopicsViewModel::class.java)

        val textView: TextView = binding.textGallery
        topicsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        binding.topicsViewModel = topicsViewModel

        val adapter =
            TopicsAdapter(TopicsItemListner { topic ->
                Toast.makeText(context, "${topic.topicId}", Toast.LENGTH_LONG).show()
                topicsViewModel.onTopicItemClicked(topic)
            })


        binding.topicsList.adapter = adapter

        topicsViewModel.topics.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        topicsViewModel.navigateToLessonsFragment.observe(this, Observer { topic ->
            topic?.let {
                this.findNavController().navigate(TopicsFragmentDirections.actionTopicsFragmentToLessonsFragment(topic.topicId))
                topicsViewModel.onNextFragmentNavigated()

            }
        })
        binding.setLifecycleOwner(this)


        return binding.root
    }
}