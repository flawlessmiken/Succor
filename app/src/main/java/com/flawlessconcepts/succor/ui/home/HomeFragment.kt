package com.flawlessconcepts.succor.ui.home

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.firebaseui_login_sample.LoginViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.flawlessconcepts.succor.R
import com.flawlessconcepts.succor.adapters.*
import com.flawlessconcepts.succor.database.lessonitem.LessonItemDatabase
import com.flawlessconcepts.succor.database.questionsdb.QuestionsDatabase
import com.flawlessconcepts.succor.database.topicsdb.TopicsDatabase
import com.flawlessconcepts.succor.databinding.FragmentHomeBinding
import com.flawlessconcepts.succor.ui.topics.TopicsViewModel
import com.flawlessconcepts.succor.ui.topics.TopicsViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    companion object {
        const val TAG = "MainFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }
    private lateinit var homeViewModel: HomeViewModel

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home, container, false)
        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val questionsDataSource = QuestionsDatabase.getInstance(application).questionsDbDao
        val topicsDataSource = TopicsDatabase.getInstance(application).topicsDbDao
        val lessonsItemDataSource = LessonItemDatabase.getInstance(application).lessonItemDbDao
        val homeViewModelFactory = HomeViewModelFactory(questionsDataSource,topicsDataSource,lessonsItemDataSource,application)

        binding.quiz.setOnClickListener(){
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToQuestionsFragment(1000,false))
        }

        homeViewModel =
            ViewModelProviders.of(
                this, homeViewModelFactory
            ).get(HomeViewModel::class.java)

        //val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
           // textView.text = it
        })

        val adapter =
            SubjectAdapterCircle(SubjectListener { subject ->
                Toast.makeText(context, "${subject.name}", Toast.LENGTH_LONG).show()
                //homeViewModel.onItemClicked(lesson)
            })

        val adapter2 =
            SubjectAdapter(SubjectListener { subject ->
                Toast.makeText(context, "${subject.name}", Toast.LENGTH_LONG).show()
                //homeViewModel.onItemClicked(lesson)
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTopicsFragment())
            })


        val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
        val manager2 = GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
        binding.rcViewCircle.layoutManager = manager
        binding.rcViewRec.layoutManager = manager2

        binding.rcViewCircle.adapter = adapter
        adapter.submitList(dummySubjects())

        binding.rcViewRec.adapter = adapter2
        adapter2.submitList(dummySubjects())

        val myButton: MaterialButton = binding.fab
            myButton.setOnClickListener(){
            launchSignInFlow()
        }


        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE ) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // User successfully signed in.
                Log.i(ContentValues.TAG, "Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!")
            } else {
                // Sign in failed. If response is null, the user canceled the
                // sign-in flow using the back button. Otherwise, check
                // the error code and handle the error.
                Log.i(ContentValues.TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }


    private fun observeAuthenticationState() {
       // val factToDisplay = viewModel.getFactToDisplay(requireContext())

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            // TODO 1. Use the authenticationState variable you just added
            // in LoginViewModel and change the UI accordingly.
            when (authenticationState) {
                // TODO 2.  If the user is logged in,
                // you can customize the welcome message they see by
                // utilizing the getFactWithPersonalization() function provided
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
//                    binding.welcomeText.text = getFactWithPersonalization(factToDisplay)
//                    binding.authButton.text = getString(R.string.logout_button_text)
//                    binding.authButton.setOnClickListener {
//                        // TODO implement logging out user in next step
//                    }
                }
                else -> {
                    // TODO 3. Lastly, if there is no logged-in user,
                    // auth_button should display Login and
                    // launch the sign in screen when clicked.
//                    binding.welcomeText.text = factToDisplay
//
//                    binding.authButton.text = getString(R.string.login_button_text)
//                    binding.authButton.setOnClickListener {
//                        launchSignInFlow()
//                    }
               }
            }
        })
    }


    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email
        // If users choose to register with their email,
        // they will need to create a password as well
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()


        //
        )

        // Create and launch sign-in intent.
        // We listen to the response of this activity with the
        // SIGN_IN_RESULT_CODE code
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                providers
            ).build(), SIGN_IN_RESULT_CODE
        )
    }

}
