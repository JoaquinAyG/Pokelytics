package study.project.pokelytics

import android.app.Application
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import study.project.pokelytics.di.*

class PokelyticsApp : Application() {
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@PokelyticsApp)
            modules(
                listOf(
                    viewModelModule,
                    useCaseModule,
                    repositoryModule,
                    appModule,
                    preferenceModule,
                    retrofitModule
                )
            )
        }
    }
}