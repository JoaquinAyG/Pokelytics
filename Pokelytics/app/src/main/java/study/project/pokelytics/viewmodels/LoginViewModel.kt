package study.project.pokelytics.viewmodels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import study.project.pokelytics.firebase.FirebaseHelper
import study.project.pokelytics.models.LoginCredentials
import study.project.pokelytics.models.User
import study.project.pokelytics.usecases.SaveUserPreferencesUseCase

class LoginViewModel(
    private val firebaseHelper: FirebaseHelper,
    val saveUserPreferencesUseCase: SaveUserPreferencesUseCase
) : ViewModalBase() {

    fun login(loginCredentials: LoginCredentials) {
        mutableState.postValue(ViewState.LOADING)
        viewModelScope.launch {
            firebaseHelper.subscribeToLoginListener(loginCredentials, {
                saveUserPreferences(User(loginCredentials.email, "", "", ""))
                mutableState.postValue(ViewState.SUCCESS)
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