package com.flawlessconcepts.succor.ui.result


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.firebaseui_login_sample.LoginViewModel
import com.flawlessconcepts.succor.R
import com.flawlessconcepts.succor.databinding.FragmentHomeBinding
import com.flawlessconcepts.succor.databinding.FragmentResultBinding
import com.flawlessconcepts.succor.ui.home.ResultViewModel
import com.mindorks.RadialProgressBar

class ResultFragment : Fragment() {


    private lateinit var resultViewModel: ResultViewModel

   // private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentResultBinding>(
            inflater, R.layout.fragment_result, container, false)
        resultViewModel =
                ViewModelProviders.of(this).get(ResultViewModel::class.java)

        val radialProgressBar = binding.progressView

        val arguments = ResultFragmentArgs.fromBundle(arguments)
        setUpView(binding,arguments)

        setUpCircularResult(arguments,radialProgressBar)

        binding.btnSimpleDialog.setOnClickListener(){
            //ResultFragmentDialog.newInstance("LogOut","log out proper").show(requireActivity().supportFragmentManager, ResultFragmentDialog.TAG)
                findNavController().navigate(ResultFragmentDirections.actionResultFragmentToQuestionsFragment(0,true))
        }
        return binding.root
    }


    fun setUpView(binding: FragmentResultBinding, bundle: ResultFragmentArgs){
        binding.tvCorrectAns.text = bundle.correct.toString()
        binding.tvWrongAns.text = bundle.failed.toString()
        binding.tvTotalQ.text = bundle.total.toString()
        binding.tvTotalPercent.text = "100%"
        binding.tvCorrectAnsPercent.text =(bundle.correct* 100 / bundle.total ).toString()+"%"
        binding.tvWrongAnsPercent.text =(bundle.failed * 100/ bundle.total ).toString()+"%"


    }


    fun setUpCircularResult(bundle: ResultFragmentArgs,radialProgressBar: RadialProgressBar){
        val outerColor = ArrayList<Int>()
        outerColor.add(Color.parseColor("#FF9800"))
        outerColor.add(Color.parseColor("#FFEB3B"))
        radialProgressBar.setOuterProgressColor(outerColor)
        radialProgressBar.setCircleThickness(0.6f)
        radialProgressBar.setOuterProgress(100)
        radialProgressBar.setInnerProgress(bundle.correct* 100 / bundle.total )
        radialProgressBar.setCenterProgress(bundle.failed * 100/ bundle.total )

    }


}
