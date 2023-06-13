package study.project.pokelytics.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import study.project.pokelytics.api.ApiConstants.COLLECTIONS_NAV_ITEMS
import study.project.pokelytics.api.ApiConstants.NAV_ITEMS_ID
import study.project.pokelytics.firebase.FirebaseHelper
import study.project.pokelytics.models.NavItem
import study.project.pokelytics.models.User

class NavigationViewModel(
    private val firebaseHelper: FirebaseHelper
): ViewModalBase() {

    private val mutableNavItems = MutableLiveData<List<NavItem>>()
    val navItems: LiveData<List<NavItem>> = mutableNavItems

    private val mutableUser = MutableLiveData<User>()
    val user: LiveData<User> = mutableUser

    fun getNavItems() {
        mutableState.postValue(ViewState.LOADING)
        viewModelScope.launch {
            firebaseHelper.subscribeToKeyResponse<String>(
                COLLECTIONS_NAV_ITEMS,
                NAV_ITEMS_ID,
                onResult = {
                    try {
                        val res = Gson().fromJson(it, Array<NavItem>::class.java)
                        mutableState.postValue(ViewState.SUCCESS)
                        mutableNavItems.postValue(res.toList())
                    } catch (e: Exception) {
                        mutableState.postValue(ViewState.ERROR)
                    }
                },
                onError = {
                    mutableState.postValue(ViewState.ERROR)
                }
            )
        }
    }

    fun setUser(user: User) {
        mutableUser.postValue(user)
    }
}
