package study.project.pokelytics.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext

abstract class FlowUseCase<out Type, in Params> where Type : Any? {

    abstract suspend fun execute(params: Params): Flow<Type>

    suspend operator fun invoke(
        params: Params,
        onResult: (Type) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        withContext(Dispatchers.IO) {
            execute(params)
                .catch { onError(it) }
                .collect { onResult(it) }
        }
    }
}