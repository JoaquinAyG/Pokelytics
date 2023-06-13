package study.project.pokelytics.viewmodels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import study.project.pokelytics.firebase.FirebaseHelper
import study.project.pokelytics.models.LoginCredentials
import study.project.pokelytics.models.User
import study.project.pokelytics.usecases.SaveUserPreferencesUseCase

class SignUpViewModel (
    private val firebaseHelper: FirebaseHelper,
    val saveUserPreferencesUseCase: SaveUserPreferencesUseCase
): ViewModalBase(){
    fun signUp(loginCredentials: LoginCredentials) {
        mutableState.postValue(ViewState.LOADING)
        viewModelScope.launch {
            firebaseHelper.subscribeToRegisterListener(
                loginCredentials,
                {
                    mutableState.postValue(ViewState.SUCCESS)
                    saveUserPreferences(it)
                }, {
                    mutableState.postValue(ViewState.ERROR)
                }
            )

        }
    }
    fun saveUserPreferences(user: User){
        viewModelScope.launch {
            saveUserPreferencesUseCase(user)
        }
    }
}