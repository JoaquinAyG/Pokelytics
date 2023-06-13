package study.project.pokelytics.di

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import study.project.pokelytics.services.PreferenceService

val preferenceModule = module {
    single {
        PreferenceService(EncryptedSharedPreferences.create(androidContext(),
            "${androidContext().packageName}.preferences",
            MasterKey.Builder(androidContext()).setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
        )
    }
}