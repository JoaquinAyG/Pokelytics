package study.project.pokelytics.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module
import study.project.pokelytics.Navigator

@FlowPreview
@ExperimentalCoroutinesApi
val appModule = module {
    factory { (context: Context) -> Navigator(context) }
    single { Firebase.firestore }
    single { FirebaseAuth.getInstance() }
}