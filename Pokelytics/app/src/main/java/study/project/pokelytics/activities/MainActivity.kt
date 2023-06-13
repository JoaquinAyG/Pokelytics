package study.project.pokelytics.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import study.project.pokelytics.R
import study.project.pokelytics.adapters.NavAdapter
import study.project.pokelytics.databinding.ActivityMainBinding
import study.project.pokelytics.databinding.NavigationDrawerLayoutBinding
import study.project.pokelytics.models.NavItem
import study.project.pokelytics.models.User
import study.project.pokelytics.serializable
import study.project.pokelytics.services.KeyConstants
import study.project.pokelytics.services.PreferenceService
import study.project.pokelytics.setImageToUrl
import study.project.pokelytics.viewmodels.NavigationViewModel
import java.util.Locale


class MainActivity : ActivityBase<ActivityMainBinding>() {

    override fun getResourceLayout(): Int = R.layout.activity_main
    private val viewModel: NavigationViewModel by viewModel()
    private lateinit var navLayoutManager: LinearLayoutManager
    private lateinit var settingsLayoutManager: LinearLayoutManager
    private lateinit var navAdapter: NavAdapter
    private lateinit var navHost: NavHostFragment
    private lateinit var navController : NavController
    private var backPressedTime: Long = 0
    private lateinit var navDrawerBinding: NavigationDrawerLayoutBinding
    private val preferenceService: PreferenceService by inject()
    private val fAuth: FirebaseAuth by inject()


    private lateinit var user: User
    val photoUser = fAuth.currentUser?.photoUrl

    override fun initializeView() {
        initializeCallbacks()
        loadData()
        navLayoutManager = LinearLayoutManager(this)
        settingsLayoutManager = LinearLayoutManager(this)
        viewModel.getNavItems()
        initializeNavGraph()
        navAdapter = NavAdapter(
            onClick = { id ->
                when (id.lowercase(Locale.ROOT)) {
                    "pokedex" -> {
                        navController.navigate(R.id.pokemonList)
                    }
                    "moves" -> {
                        navController.navigate(R.id.moveList)
                    }
                    "items" -> {
                        navController.navigate(R.id.berryList)
                    }
                    "berries" -> {
                        navController.navigate(R.id.berryList)
                    }
                    "locations" -> {
                        navController.navigate(R.id.regionList)
                    }
                    "settings" -> {
                        navController.navigate(R.id.userProfile)
                    }
                    "team" -> {
                        navController.navigate(R.id.teamList)
                    }
                    "favourites" -> {
                        navController.navigate(R.id.favList)
                    }
                    "logout" -> {
                    }
                }

                setSelected(id)
            }
        )
        viewModel.getNavItems()
        initializeDrawer()
        setOnClickListeners()
    }

    private fun setSelected(id: String) {
        navAdapter.items.forEachIndexed { index, it ->
            it.isSelected = it.title == id
            navAdapter.notifyItemChanged(index)
        }
    }

    private fun initializeCallbacks() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isOpen) {
                    binding.drawerLayout.close()
                } else {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - backPressedTime > 2000) {
                        Toast.makeText(
                            this@MainActivity,
                            "Press back again to exit",
                            Toast.LENGTH_SHORT
                        ).show()
                        backPressedTime = currentTime
                    } else {
                        finish()
                    }
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun setOnClickListeners() {
        binding.topBar.moreIcon.setOnClickListener {
            binding.drawerLayout.open()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initializeDrawer() {
        navDrawerBinding = NavigationDrawerLayoutBinding.inflate(LayoutInflater.from(this), binding.root as ViewGroup, false).apply {
            navRecycler.layoutManager = navLayoutManager
            settingsRecycler.layoutManager = settingsLayoutManager
            root.setOnClickListener {  }
            navRecycler.adapter = navAdapter
            settingsRecycler.adapter = NavAdapter(
                onClick = { id ->
                    when (id) {
                        "About" -> {
                            //viewModel.navigateToAbout()
                        }
                        "Settings" -> {
                            navController.navigate(R.id.userProfile)
                        }
                        "Logout" -> {
                            preferenceService.removePreference(KeyConstants.EMAIL_KEY)
                            preferenceService.removePreference(KeyConstants.NAME_KEY)
                            fAuth.signOut()
                            navigator.goToLogin()
                            finish()
                        }
                    }
                }
            ).apply {
                items = mutableListOf(
                    NavItem("About", "About"),
                    NavItem("Settings", "Settings"),
                    NavItem("Logout", "Logout")
                )
                notifyDataSetChanged()
            }
        }
        binding.navigationDrawer.removeAllViews()
        viewModel.setUser(User.getInstance())
    }

    private fun initializeNavGraph() {
        navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val graph = navHost.navController.navInflater.inflate(R.navigation.nav_graph_main)
        navHost.navController.graph = graph
        navController = navHost.navController
    }

    override fun bindViewModel() {
        binding.apply {
            lifecycleOwner = this@MainActivity
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun subscribe() {
        viewModel.navItems.observe(this) {
            it.forEach { navItem ->
                navItem.mapId()
                navItem.mapSelectedId()
                if (navItem.title == "Pokedex") {
                    navItem.isSelected = true
                }
            }
            navAdapter.items.addAll(it)
            navAdapter.notifyDataSetChanged()
        }
        viewModel.user.observe(this) {
            navDrawerBinding.profileName.text = it.name.ifEmpty { resources.getString(R.string.welcomeUser) }
            navDrawerBinding.profileImage.setImageToUrl(photoUser.toString())
            binding.navigationDrawer.addView(navDrawerBinding.root)
        }
    }

    companion object {
        private const val USER = "user"
        fun getIntent(context: Context, user: User) = Intent(context, MainActivity::class.java)
            .apply {
                putExtra(USER, user)
            }
    }
    private fun loadData() {
        intent.serializable<User>(USER)?.let {
            user = it
        }
        User.setInstance(user)
    }

    override fun showLoading(show : Boolean) {
        binding.loadingAnimation.isVisible = show
    }
}