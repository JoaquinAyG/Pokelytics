package study.project.pokelytics.viewmodels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import study.project.pokelytics.api.model.Location
import study.project.pokelytics.api.model.NamedApiResource
import study.project.pokelytics.event.LiveEvent
import study.project.pokelytics.event.MutableLiveEvent
import study.project.pokelytics.event.postEvent
import study.project.pokelytics.usecases.GetLocationUseCase

class MoreInfoLocationViewModel(
    val getLocationUseCase: GetLocationUseCase
): ViewModalBase() {

    private val mutableLocation = MutableLiveEvent<Location>()
    val location: LiveEvent<Location> = mutableLocation

    fun getLocationExtraInfo(item: NamedApiResource) {
        mutableState.postValue(ViewState.LOADING)
        viewModelScope.launch {
            getLocationUseCase(
                item,
                {
                    mutableState.postValue(ViewState.SUCCESS)
                    mutableLocation.postEvent(it)
                }, {
                    mutableState.postValue(ViewState.ERROR)
                }
            )
        }
    }
}
