package com.flawlessconcepts.succor.ui.takeanote


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.flawlessconcepts.succor.R
import com.flawlessconcepts.succor.databinding.FragmentHomeBinding
import com.flawlessconcepts.succor.databinding.FragmentTakeNoteBinding

class TakeANoteFragment : Fragment() {


    private lateinit var takeNoteViewModel: TakeNoteViewModel
   // private val viewModel by viewModels<LoginViewModel>()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTakeNoteBinding>(
            inflater, R.layout.fragment_take_note, container, false)
        takeNoteViewModel =
                ViewModelProviders.of(this).get(TakeNoteViewModel::class.java)
        val textView: TextView = binding.textSlideshow
        takeNoteViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return binding.root
    }

}
