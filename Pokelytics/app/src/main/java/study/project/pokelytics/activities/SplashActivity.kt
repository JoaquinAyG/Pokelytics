package study.project.pokelytics.activities

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import study.project.pokelytics.R
import study.project.pokelytics.databinding.ActivitySplashBinding
import study.project.pokelytics.models.User
import study.project.pokelytics.services.KeyConstants
import study.project.pokelytics.services.PreferenceService
import study.project.pokelytics.viewmodels.SplashViewModel
import study.project.pokelytics.viewmodels.ViewState

@SuppressLint("CustomSplashScreen")
class SplashActivity : ActivityBase<ActivitySplashBinding>() {
    private val splashViewModel: SplashViewModel  by viewModel()
    private val preferenceService: PreferenceService by inject()
    private val firebaseFirestore: FirebaseFirestore by inject()
    override fun getResourceLayout(): Int = R.layout.activity_splash
    override fun initializeView() {
        openDelayedApp()
        binding.apply {
            fuego.startAnimation(
                AnimationUtils.loadAnimation(this@SplashActivity, R.anim.move_left_to_right)
            )
            pokeball.startAnimation(
                AnimationUtils.loadAnimation(this@SplashActivity, R.anim.rotate_pokeball)
            )
        }
    }
    private fun openDelayedApp() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                splashViewModel.openDelayedApp()
            }, 3000
        )
    }
    override fun bindViewModel() {}

    override fun subscribe() {
        splashViewModel.state.observe(this){
            when(it){
                ViewState.SUCCESS -> {
                    val emailUser = preferenceService.getPreference(KeyConstants.EMAIL_KEY)
                    if (emailUser != null) {
                        firebaseFirestore.collection("users").document(emailUser).get().addOnSuccessListener {
                            navigator.goToMain(User(emailUser.toString(), it.get("name") as String, it.get("favouriteList") as String, it.get("team") as String))
                            finish()
                        }
                    }
                }
                ViewState.ERROR ->{
                    navigator.goToLogin()
                    finish()
                }
                else -> {}
            }
        }
    }

}