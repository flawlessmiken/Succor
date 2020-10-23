package com.flawlessconcepts.succor.ui.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.flawlessconcepts.succor.R
import com.flawlessconcepts.succor.databinding.FragmentHomeBinding
import com.flawlessconcepts.succor.databinding.FragmentProfileBinding
import com.flawlessconcepts.succor.ui.home.VideoViewModel

class ProfileFragment : Fragment() {


    private lateinit var profileViewModel: ProfileViewModel

   // private val viewModel by viewModels<LoginViewModel>()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater, R.layout.fragment_profile, container, false)
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel::class.java)
//        val textView: TextView = binding.textHome
////        resultViewModel.text.observe(viewLifecycleOwner, Observer {
////            textView.text = it
////        })
        return binding.root
    }

}
