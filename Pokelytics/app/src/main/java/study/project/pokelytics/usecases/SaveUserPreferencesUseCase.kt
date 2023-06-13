package study.project.pokelytics.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import study.project.pokelytics.models.User
import study.project.pokelytics.services.KeyConstants
import study.project.pokelytics.services.PreferenceService

class SaveUserPreferencesUseCase (

    private val preferenceService: PreferenceService

) : FlowUseCase<Unit, User>() { //Unit, User(mail, pass)

    override suspend fun execute(params: User): Flow<Unit> {
        preferenceService.savePreference(KeyConstants.EMAIL_KEY, params.email)
        preferenceService.savePreference(KeyConstants.NAME_KEY, params.name)
        return flowOf()
    }
}


