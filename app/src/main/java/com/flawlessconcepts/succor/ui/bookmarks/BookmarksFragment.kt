package com.flawlessconcepts.succor.ui.bookmarks


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.flawlessconcepts.succor.R
import com.flawlessconcepts.succor.databinding.FragmentBookmarksBinding
import com.flawlessconcepts.succor.databinding.FragmentHomeBinding

class BookmarksFragment : Fragment() {


    private lateinit var bookmarksViewModel: BookmarksViewModel

   // private val viewModel by viewModels<LoginViewModel>()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentBookmarksBinding>(
            inflater, R.layout.fragment_bookmarks, container, false)
        bookmarksViewModel =
                ViewModelProviders.of(this).get(BookmarksViewModel::class.java)
//        val textView: TextView = binding.textHome
////        resultViewModel.text.observe(viewLifecycleOwner, Observer {
////            textView.text = it
////        })
        return binding.root
    }

}
