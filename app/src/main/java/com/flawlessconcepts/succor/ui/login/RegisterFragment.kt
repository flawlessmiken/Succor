package com.flawlessconcepts.succor.ui.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.firebaseui_login_sample.LoginViewModel
import com.flawlessconcepts.succor.R
import com.flawlessconcepts.succor.databinding.FragmentHomeBinding
import com.flawlessconcepts.succor.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {



    private val viewModel by viewModels<LoginViewModel>()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(
            inflater, R.layout.fragment_register, container, false)

//        val textView: TextView = binding.textHome
////        resultViewModel.text.observe(viewLifecycleOwner, Observer {
////            textView.text = it
////        })
        return binding.root
    }

}
