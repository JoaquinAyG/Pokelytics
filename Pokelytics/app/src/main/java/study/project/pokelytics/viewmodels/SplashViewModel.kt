package study.project.pokelytics.viewmodels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import study.project.pokelytics.models.User
import study.project.pokelytics.services.KeyConstants
import study.project.pokelytics.services.PreferenceService

class SplashViewModel (
    val preferenceService: PreferenceService
    ): ViewModalBase() {

     fun openDelayedApp() {

        viewModelScope.launch {
            mutableState.postValue(ViewState.LOADING)
            viewModelScope.launch {
                if(preferenceService.getPreference(KeyConstants.EMAIL_KEY)?.isNotEmpty() == true){
                    //navigator.goToLogin()
                    mutableState.postValue(ViewState.SUCCESS)
                }else{
                    mutableState.postValue(ViewState.ERROR)
                    //navigator.goToMain()
                }
            }
        }
    }


}
