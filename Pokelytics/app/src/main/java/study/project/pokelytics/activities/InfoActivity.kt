package study.project.pokelytics.activities

import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import study.project.pokelytics.R
import study.project.pokelytics.databinding.ActivityInfoBinding

class InfoActivity: ActivityBase<ActivityInfoBinding>() {

    private lateinit var navHost: NavHostFragment
    private lateinit var navController : NavController

    override fun getResourceLayout(): Int = R.layout.activity_info

    override fun initializeView() {
        initializeNavGraph()
    }

    private fun initializeNavGraph() {
        navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val graph = navHost.navController.navInflater.inflate(R.navigation.nav_graph_info)
        navHost.navController.graph = graph
        navController = navHost.navController
    }

    override fun bindViewModel() {
        binding.apply {
            lifecycleOwner = this@InfoActivity
        }
    }

    override fun subscribe() {}

    companion object {
        private const val POKEMON = "user"
        fun getIntent(context: Context, pokemon: Int) = Intent(context, InfoActivity::class.java)
            .apply {
                putExtra(POKEMON, pokemon)
            }
    }

    fun loadData(): Int {
        return intent.getIntExtra(POKEMON, 0)
    }
}