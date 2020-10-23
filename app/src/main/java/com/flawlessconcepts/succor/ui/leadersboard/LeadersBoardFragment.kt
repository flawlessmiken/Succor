package com.flawlessconcepts.succor.ui.leadersboard


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
import com.flawlessconcepts.succor.databinding.FragmentLeadersBoardBinding

class LeadersBoardFragment : Fragment() {


    private lateinit var leadersBoardViewmodel: LeadersBoardViewModel

   // private val viewModel by viewModels<LoginViewModel>()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLeadersBoardBinding>(
            inflater, R.layout.fragment_leaders_board, container, false)
        leadersBoardViewmodel =
                ViewModelProviders.of(this).get(LeadersBoardViewModel::class.java)
        val textView: TextView = binding.textSlideshow
        leadersBoardViewmodel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return binding.root
    }

}
