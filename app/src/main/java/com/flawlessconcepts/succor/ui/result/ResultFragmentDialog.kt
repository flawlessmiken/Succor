package com.flawlessconcepts.succor.ui.result


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.firebaseui_login_sample.LoginViewModel
import com.flawlessconcepts.succor.R
import com.flawlessconcepts.succor.databinding.FragmentHomeBinding
import com.flawlessconcepts.succor.databinding.FragmentResultBinding
import com.flawlessconcepts.succor.ui.home.ResultViewModel
import kotlinx.android.synthetic.main.item_result_dialog.view.*

class ResultFragmentDialog : DialogFragment() {


    private lateinit var resultViewModel: ResultViewModel

   // private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_result_dialog, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    private fun setupClickListeners(view: View) {
        view.btnPositive.setOnClickListener {
            // TODO: Do some task here
            dismiss()
        }
        view.btnNegative.setOnClickListener {
            // TODO: Do some task here
            dismiss()
        }
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView(view: View) {
        view.tvTitle.text = arguments?.getString(KEY_TITLE)
        view.tvSubTitle.text = arguments?.getString(KEY_SUBTITLE)
    }



    companion object {

        const val TAG = "ResultDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"

        fun newInstance(title: String, subTitle: String): ResultFragmentDialog {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_SUBTITLE, subTitle)
            val fragment = ResultFragmentDialog()
            fragment.arguments = args
            return fragment
        }

    }
}
