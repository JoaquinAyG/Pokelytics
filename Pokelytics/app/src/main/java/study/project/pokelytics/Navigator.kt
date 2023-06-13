package study.project.pokelytics

import android.content.Context
import study.project.pokelytics.activities.InfoActivity
import study.project.pokelytics.activities.LoginActivity
import study.project.pokelytics.activities.MainActivity
import study.project.pokelytics.activities.PolicyActivity
import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.models.User

class Navigator(
    private val context: Context
) {

    fun goToLogin() {
        context.apply { startActivity(LoginActivity.getIntent(context)) }
    }

    fun goToMain(user: User) {
        context.apply {
            startActivity(MainActivity.getIntent(context, user))
        }
    }
    fun goToPolicy() {
        context.apply { startActivity(PolicyActivity.getIntent(context)) }
    }

    fun goToInfoPage(pokemon: Pokemon) {
        context.apply { startActivity(InfoActivity.getIntent(context, pokemon.id)) }
    }
}
