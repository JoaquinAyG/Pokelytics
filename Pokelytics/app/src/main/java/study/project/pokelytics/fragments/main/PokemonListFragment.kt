package study.project.pokelytics.fragments.main

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import study.project.pokelytics.R
import study.project.pokelytics.activities.ActivityBase
import study.project.pokelytics.activities.MainActivity
import study.project.pokelytics.adapters.PokemonListAdapter
import study.project.pokelytics.api.model.PaginationRange
import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.capitalized
import study.project.pokelytics.databinding.FragmentPokemonListBinding
import study.project.pokelytics.fragments.FragmentBase
import study.project.pokelytics.models.PokemonInterface
import study.project.pokelytics.models.User
import study.project.pokelytics.usecases.GetPokemonMoreInfoUseCase
import study.project.pokelytics.viewmodels.FavViewModel
import study.project.pokelytics.viewmodels.MoreInfoViewModel
import study.project.pokelytics.viewmodels.PokemonListViewModel
import study.project.pokelytics.viewmodels.ViewState

class PokemonListFragment : FragmentBase<FragmentPokemonListBinding>() {

    private val viewModel: PokemonListViewModel by viewModel()
    private lateinit var adapter: PokemonListAdapter
    private val favViewModel: FavViewModel by viewModel()
    private lateinit var layoutManager: LinearLayoutManager
    private var paginationRange = PaginationRange()
    private val pokemonMoreInfoUseCase: GetPokemonMoreInfoUseCase by inject()

    override fun bindViewModel() {
        binding.apply {
            //this.lifecycleOwner = this@PokemonListFragment
        }
    }

    override fun initializeView() {
        adapter = PokemonListAdapter(
            pokemonInterface = createPokemonInterface()
        )
        layoutManager = LinearLayoutManager(context)

        binding.apply {
            pokemonRecycler.layoutManager = layoutManager
            pokemonRecycler.adapter = adapter
        }
    }

    private fun createPokemonInterface(): PokemonInterface {
        return object : PokemonInterface {
            override fun onFavoriteClick(pokemon: Pokemon) {
                if (User.getInstance().email.isEmpty()) {
                    alertFavsTeam()
                    return
                }
                if (User.getInstance().addFav(pokemon)) {
                    favViewModel.saveFavs(User.getInstance())
                    Toast.makeText(context, "${pokemon.name.capitalized()} added to favourites", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Cant add more than 6 pokemon to favourites", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onTeamClick(pokemon: Pokemon) {
                if (User.getInstance().email.isEmpty()) {
                    alertFavsTeam()
                    return
                }
                if (User.getInstance().addTeam(pokemon)) {
                    favViewModel.saveTeam(User.getInstance())
                    Toast.makeText(context, "${pokemon.name.capitalized()} added to team", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Cant add more than 6 pokemon to team", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onPokemonClick(pokemon: Pokemon) {
                (activity as ActivityBase<*>).navigator.goToInfoPage(pokemon)
            }

            override fun createMoreInfoViewModel(): MoreInfoViewModel {
                return MoreInfoViewModel(
                    getPokemonMoreInfoUseCase = pokemonMoreInfoUseCase
                )
            }
        }
    }

    fun alertFavsTeam(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(resources.getString(R.string.loginTitle))
        builder.setMessage(resources.getString(R.string.loginQuestion))
        builder.setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
            (activity as ActivityBase<*>).navigator.goToLogin()
            activity?.finish()
        }
        builder.setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->}
        builder.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshState(ViewState.IDLE)
        adapter.notifyDataSetChanged()
    }

    override fun getResourceLayout(): Int = R.layout.fragment_pokemon_list

    override fun subscribe() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                ViewState.IDLE -> viewModel.getPokemonList(paginationRange)
                else -> {}
            }
            (activity as MainActivity).showLoading(it == ViewState.LOADING && adapter.items.isEmpty())
        }

        viewModel.pokemons.observe(viewLifecycleOwner) {
            it.forEachIndexed { index, pokemon ->
                adapter.items.add(pokemon)
                adapter.notifyItemInserted(layoutManager.itemCount + index)
            }

            if(it.isNotEmpty() && it.lastOrNull()?.id != null) {
                if (paginationRange.stop) {
                    return@observe
                }
                paginationRange.next()
                viewModel.getPokemonList(paginationRange)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.items.clear()
    }
}