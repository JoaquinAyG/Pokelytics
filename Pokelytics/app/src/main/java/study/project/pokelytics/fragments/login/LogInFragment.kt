package study.project.pokelytics.fragments.login

import android.content.ContentValues
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
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import study.project.pokelytics.R
import study.project.pokelytics.activities.ActivityBase
import study.project.pokelytics.databinding.FragmentLogInBinding
import study.project.pokelytics.fragments.FragmentBase
import study.project.pokelytics.models.LoginCredentials
import study.project.pokelytics.models.User
import study.project.pokelytics.services.KeyConstants
import study.project.pokelytics.services.PreferenceService
import study.project.pokelytics.viewmodels.LoginViewModel
import study.project.pokelytics.viewmodels.ViewState

class LogInFragment : FragmentBase<FragmentLogInBinding>() {

    override fun getResourceLayout(): Int = R.layout.fragment_log_in
    private val loginViewModel: LoginViewModel by viewModel()
    private val preferenceService: PreferenceService by inject()
    private val GOOGLE_SIGN_IN = 1

    override fun initializeView() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (email.text.isEmpty() && password.text.isEmpty()){
                    showError(etemail, resources.getString(R.string.emailRequired))
                    showError(etpassword, resources.getString(R.string.passRequired))
                }else if(!email.text.contains("@")) {
                    showError(etemail, resources.getString(R.string.emailValid))
                }else{
                    val credentials = LoginCredentials(email.text.toString(), password.text.toString(), "")
                    loginViewModel.login(credentials)
                }
            }
            btnCancel.setOnClickListener{
                findNavController().navigate(R.id.logInFragmentToLoginSelectionFragment)
            }
            resetPass.setOnClickListener{
                findNavController().navigate(R.id.logInFragmentToResetPasswordFragment)
            }
            btnGoogle.setOnClickListener{
                loginGoogle()
            }

        }
    }

    override fun bindViewModel() {
        binding.apply {
            lifecycleOwner = this@LogInFragment
        }
    }

    override fun subscribe() {
        loginViewModel.state.observe(viewLifecycleOwner){
            when(it){
                ViewState.SUCCESS -> {
                    val emailUser = preferenceService.getPreference(KeyConstants.EMAIL_KEY)
                    if (emailUser != null) {
                        FirebaseFirestore.getInstance().collection("users").document(emailUser).get().addOnSuccessListener {
                            loginViewModel.saveUserPreferences(User(emailUser.toString(), it.get("name").toString(), it.get("favouriteList").toString(), it.get("team").toString()))
                            (activity as ActivityBase<*>).navigator.goToMain(User(emailUser.toString(), it.get("name").toString(), it.get("favouriteList").toString(), it.get("team").toString()))
                            activity?.finish()
                        }
                    }
                }
                ViewState.ERROR ->{
                    showErrorLogin()
                }
                else -> {}
            }
        }
    }

    private fun showError(textInputLayout: TextInputLayout, error: String){
        textInputLayout.error = error
    }
    private fun showErrorLogin(){
        Toast.makeText(requireContext(), resources.getString(R.string.emailOrPassWrong), Toast.LENGTH_SHORT).show()
    }

    val callback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            findNavController().navigate(R.id.logInFragmentToLoginSelectionFragment)
        }
    }

    private fun loginGoogle(){
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
        if (requestCode == GOOGLE_SIGN_IN && data != null) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val email = account.email
                if (email != null) {
                    FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val signInMethods = task.result?.signInMethods ?: emptyList<String>()
                                if (signInMethods.isEmpty()) {
                                    Toast.makeText(requireContext(), resources.getString(R.string.accountNotExists), Toast.LENGTH_LONG).show()
                                } else {
                                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                                    FirebaseAuth.getInstance().signInWithCredential(credential)
                                        .addOnCompleteListener { authTask ->
                                            if (authTask.isSuccessful) {
                                                FirebaseFirestore.getInstance().collection("users").document(email).get().addOnSuccessListener {
                                                    loginViewModel.saveUserPreferences(User(email, it.get("name").toString(), it.get("favouriteList").toString(), it.get("team").toString()))
                                                    (activity as ActivityBase<*>).navigator.goToMain(User(email, it.get("name").toString(), it.get("favouriteList").toString(), it.get("team").toString()))
                                                    activity?.finish()
                                                }

                                            }
                                        }
                                }
                            } else {
                                Log.w(ContentValues.TAG, "Error fetching sign-in methods for email", task.exception)
                            }
                        }
                }

            } catch (e: ApiException) {
                Log.w(ContentValues.TAG, "Google sign in failed", e)
            }
        }
    }


}