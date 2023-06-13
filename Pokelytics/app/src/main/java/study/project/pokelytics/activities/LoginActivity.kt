package study.project.pokelytics.activities

import android.content.Context
import android.content.Intent
import androidx.navigation.fragment.NavHostFragment
import study.project.pokelytics.R
import study.project.pokelytics.databinding.ActivityLoginBinding

class LoginActivity : ActivityBase<ActivityLoginBinding>() {


    override fun getResourceLayout(): Int = R.layout.activity_login

    override fun initializeView() {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val graph = navHost.navController.navInflater.inflate(R.navigation.nav_graph_login)
        navHost.navController.graph = graph
    }
    override fun bindViewModel() {
        binding.apply {
            lifecycleOwner = this@LoginActivity
        }
    }
    override fun subscribe() {

    }

    companion object {
        fun getIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}