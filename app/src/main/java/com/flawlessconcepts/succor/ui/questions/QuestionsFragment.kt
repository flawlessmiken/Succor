package com.flawlessconcepts.succor.ui.questions

import android.os.Bundle
import android.view.*
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.flawlessconcepts.succor.R
import com.flawlessconcepts.succor.database.questionsdb.QuestionObj
import com.flawlessconcepts.succor.database.questionsdb.QuestionsDatabase
import com.flawlessconcepts.succor.databinding.FragmentQuestionsBinding


class QuestionsFragment : Fragment() {

    private lateinit var questionViewModels: QuestionViewModel

    private lateinit var optionA:RadioButton
    private lateinit var optionB:RadioButton
    private lateinit var optionC:RadioButton
    private lateinit var optionD:RadioButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentQuestionsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_questions, container, false
        )

        // context
        val application = requireNotNull(this.activity).application
        //data source
        val questionItemDataSource = QuestionsDatabase.getInstance(application).questionsDbDao

        //viewmodel factory
        val viewModelFactory = QuestionViewModelFactory(questionItemDataSource, application)
        questionViewModels =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(QuestionViewModel::class.java)

            //set xml data viewmodel to binding
        binding.questionViewModel = questionViewModels

        //determine what mood to display by number of count in isMood in viewmodel
        questionViewModels.setQuestionMood()

        // set the value of the radiobutton
        optionA = binding.optionA
        optionB = binding.optionB
        optionC = binding.optionC
        optionD = binding.optionD

        //create a list of radio button
        val list = listOf<RadioButton>(optionA,optionB,optionC,optionD)


        val textView: TextView = binding.questionEntry

        questionViewModels.questionSize.observe(viewLifecycleOwner, Observer { size->

            questionViewModels.currentQuestionCount.observe(viewLifecycleOwner, Observer {count->

                if (count < size  && count >= 0){
                questionViewModels.reshuffleAnswers()
                binding.invalidateAll()}
            })
        })


        questionViewModels.navigateToResultFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                alertSummit()
            }
        })
        questionViewModels.question.observe(viewLifecycleOwner, Observer {
            binding.options.clearCheck()
            //disableOptions(binding)

            questionViewModels.isStudyMood.observe(viewLifecycleOwner, Observer { mood ->
                if (mood <= 1) {
                    enableOptions(binding)
                } else {
                    disableOptions(binding,list,it)
                }
            })

            if (it.selected != null || it.selected == 90) {
                Toast.makeText(context, "${it.selected}", Toast.LENGTH_LONG).show()
                when (it.selected) {
                    0 -> binding.optionA.isChecked = true
                    1 -> binding.optionB.isChecked = true
                    2 -> binding.optionC.isChecked = true
                    3 -> binding.optionD.isChecked = true
                }
                binding.invalidateAll()
            }


        })


        (activity as AppCompatActivity).supportActionBar?.title = "Questions"
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.question_fragment_menu, menu)
    }

    fun disableOptions(
       binding: FragmentQuestionsBinding,
       list: List<RadioButton>, it: QuestionObj
    ) {
//        binding.optionA.isEnabled = false
//        binding.optionB.isEnabled = false
//        binding.optionC.isEnabled = false
//        binding.optionD.isEnabled = false
        list.forEachIndexed(){index,button->
            button.isEnabled=false
        }
        binding.fabSubmit.visibility = View.GONE


        list.forEach(){button->
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0)
            button.setBackgroundResource(R.drawable.roundable)
        }
        if (it.selected != null || it.selected == 90) {
            if (it.answers!![it.selected!!] == it.correct) {
                //list[it.selected!!].isChecked=true
                markCorrect(list[it.selected!!])
            }else{
                markWrong(list[it.selected!!])
            }
            binding.invalidateAll()
        }
        it.answers!!.forEachIndexed(){index,answer->
            if (answer==it.correct){
                markCorrectansGood(list[index])
            }
        }






    }

    fun markCorrectansGood(radioButton: RadioButton){
        radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_correct, 0)
        radioButton.setBackgroundResource(R.drawable.radiobuttonselector_correct)
    }

    fun markCorrect(radioButton: RadioButton){
        radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_correct, 0)
        radioButton.setBackgroundResource(R.drawable.radiobuttonselector_correct)
    }
    fun markWrong(radioButton: RadioButton){
        radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wrong, 0)
        radioButton.setBackgroundResource(R.drawable.radiobuttonselector_wrong)
    }

    fun enableOptions(binding: FragmentQuestionsBinding) {
        binding.optionA.isEnabled = true
        binding.optionB.isEnabled = true
        binding.optionC.isEnabled = true
        binding.optionD.isEnabled = true
        binding.fabSubmit.visibility = View.VISIBLE

    }

    fun alertSummit() {
        val builder = AlertDialog.Builder(requireContext())
        //set title for alert dialog
        builder.setTitle("Submit")
        //set message for alert dialog
        builder.setMessage("You are about to submit your questions")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action


        builder.setPositiveButton("Yes") { dialogInterface, which ->
            this.findNavController().navigate(

                QuestionsFragmentDirections.actionQuestionsFragmentToResultFragment(
                    questionViewModels.scoreCalculator()[0],
                    questionViewModels.scoreCalculator()[1],
                    questionViewModels.scoreCalculator()[2]
                )
            )
            questionViewModels.setQuestionMood()
            questionViewModels.doneNavigating()
            questionViewModels.resetQuestionCount()
            Toast.makeText(requireContext(), "clicked yes", Toast.LENGTH_LONG).show()
        }
        //performing cancel action
//        builder.setNeutralButton("Cancel") { dialogInterface, which ->
//            Toast.makeText(requireContext(), "clicked cancel\n operation cancel", Toast.LENGTH_LONG)
//                .show()
//        }
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            Toast.makeText(requireContext(), "clicked No", Toast.LENGTH_LONG).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

    }
}