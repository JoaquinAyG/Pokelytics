package study.project.pokelytics.fragments.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.ext.android.viewModel
import study.project.pokelytics.R
import study.project.pokelytics.activities.ActivityBase
import study.project.pokelytics.databinding.FragmentSignUpBinding
import study.project.pokelytics.fragments.FragmentBase
import study.project.pokelytics.models.LoginCredentials
import study.project.pokelytics.models.User
import study.project.pokelytics.viewmodels.SignUpViewModel
import study.project.pokelytics.viewmodels.ViewState


class SignUpFragment : FragmentBase<FragmentSignUpBinding>() {

    override fun getResourceLayout(): Int = R.layout.fragment_sign_up
    private val signUpViewModel: SignUpViewModel by viewModel()
    private val GOOGLE_SIGN_IN = 1

    override fun initializeView() {
        binding.apply {
            btnSignUp.setOnClickListener {
                val expRegular = Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{6,15}\$")

                if (email.text.isEmpty() && password.text.isEmpty() && repassword.text.isEmpty()) {
                    showError(etemail, resources.getString(R.string.emailRequired))
                    showError(etpassword, resources.getString(R.string.passRequired))
                    showError(etrepassword, resources.getString(R.string.repassRequired))
                } else if (!email.text.contains("@")) {
                    showError(etemail, resources.getString(R.string.emailValid))  //This field can´t be empty
                } else if (!expRegular.matches(password.text.toString())) {
                    showError(etpassword, resources.getString(R.string.passNeeds))
                } else if (password.text.toString() != repassword.text.toString()) {
                    showError(etpassword, resources.getString(R.string.passSame))
                } else {
                    val credentials = LoginCredentials(email.text.toString(), password.text.toString(), name.text.toString())
                    signUpViewModel.signUp(credentials)
                }
            }
            btnCancel.setOnClickListener {
                findNavController().navigate(R.id.signUpFragmentToLoginSelectionFragment)
            }
            btnGoogle.setOnClickListener {
                signUpGoogle()
            }
            tvsTermsPolicy.setOnClickListener{
                (activity as ActivityBase<*>).navigator.goToPolicy()
            }
        }
    }
    override fun bindViewModel() {
        binding.apply {
            lifecycleOwner = this@SignUpFragment
        }
    }

    override fun subscribe() {
        signUpViewModel.state.observe(viewLifecycleOwner){
            when(it){
                ViewState.SUCCESS -> {
                    Toast.makeText(requireContext(), resources.getString(R.string.verifyEmail), Toast.LENGTH_LONG).show()
                    (activity as ActivityBase<*>).navigator.goToMain(User.getDefaultUser())//COGER USUARIO BD
                    activity?.finish()
                }
                ViewState.ERROR ->{
                    showErrorSignUp()
                }
                else -> {}
            }
        }
    }

    private fun showError(textInputLayout: TextInputLayout, error: String) {
        textInputLayout.error = error
    }

    private fun showErrorSignUp(){
        Toast.makeText(requireContext(), resources.getString(R.string.emailExists), Toast.LENGTH_SHORT).show()
    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigate(R.id.signUpFragmentToLoginSelectionFragment)
        }
    }

    fun signUpGoogle(){
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(requireContext(), googleConf)
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful){
                            FirebaseAuth.getInstance().currentUser?.sendEmailVerification()?.addOnSuccessListener {
                                Toast.makeText(requireContext(), resources.getString(R.string.verifyEmail), Toast.LENGTH_LONG).show()

                                FirebaseAuth.getInstance().currentUser?.email?.let { it1 ->
                                    val name = FirebaseAuth.getInstance().currentUser?.displayName.toString()
                                    signUpViewModel.saveUserPreferences(User(it1, name, "", ""))
                                    (activity as ActivityBase<*>).navigator.goToMain(User(it1, name, "", ""))
                                    activity?.finish()
                                }
                            }
                            FirebaseAuth.getInstance().currentUser?.email?.let { it1 ->
                                FirebaseFirestore.getInstance().collection("users").document(it1).set(
                                    hashMapOf(
                                        "favouriteList" to "",
                                        "team" to "",
                                        "name" to FirebaseAuth.getInstance().currentUser?.displayName
                                    )
                                )
                            }

                        }else{
                            Toast.makeText(requireContext(), resources.getString(R.string.emailExists), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }catch (e: ApiException){
                Log.w("ERROR", " " + e)
            }

        }
    }

}