package study.project.pokelytics.viewmodels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import study.project.pokelytics.models.LoginCredentials
import study.project.pokelytics.usecases.ResetPasswordUseCase

class ResetPasswordViewModel (
    val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModalBase() {

    fun resetPassword(loginCredentials: LoginCredentials) {
        mutableState.postValue(ViewState.LOADING)
        viewModelScope.launch {
            resetPasswordUseCase(
                loginCredentials,
                {
                    mutableState.postValue(ViewState.SUCCESS)
                }, {
                    mutableState.postValue(ViewState.ERROR)
                }
            )
        }
    }

}