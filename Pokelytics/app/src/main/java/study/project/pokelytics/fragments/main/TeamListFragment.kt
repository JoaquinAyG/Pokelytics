package study.project.pokelytics.fragments.main

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import study.project.pokelytics.R
import study.project.pokelytics.activities.MainActivity
import study.project.pokelytics.adapters.TeamAdapter
import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.databinding.FragmentTeamBinding
import study.project.pokelytics.fragments.FragmentBase
import study.project.pokelytics.models.User
import study.project.pokelytics.usecases.GetPokemonMoreInfoUseCase
import study.project.pokelytics.viewmodels.FavViewModel
import study.project.pokelytics.viewmodels.MoreInfoViewModel
import study.project.pokelytics.viewmodels.PokemonListViewModel
import study.project.pokelytics.viewmodels.ViewState
import java.util.Locale

class TeamListFragment : FragmentBase<FragmentTeamBinding>() {

    private val viewModel: PokemonListViewModel by viewModel()
    private lateinit var adapter: TeamAdapter
    private val favViewModel: FavViewModel by viewModel()
    private lateinit var layoutManager: LinearLayoutManager
    private val pokemonMoreInfoUseCase: GetPokemonMoreInfoUseCase by inject()

    override fun bindViewModel() {
        binding.apply {
            //this.lifecycleOwner = this@PokemonListFragment
        }
    }

    override fun initializeView() {
        adapter = TeamAdapter(
            pokemonInterface = createPokemonInterface()
        )
        layoutManager = LinearLayoutManager(context)

        binding.apply {
            pokemonRecycler.layoutManager = layoutManager
            pokemonRecycler.adapter = adapter
        }
    }

    private fun createPokemonInterface(): PokemonViewHolderInterface {
        return object : PokemonViewHolderInterface {
            override fun onFavoriteClick(pokemon: Pokemon) {
                if (User.getInstance().email.isEmpty()) {
                    Toast.makeText(context, "Please login to add favorites", Toast.LENGTH_SHORT).show()
                    return
                }
                if (User.getInstance().addFav(pokemon)) {
                    favViewModel.saveFavs(User.getInstance())
                    Toast.makeText(context, "${pokemon.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }} added to favourites", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Cant add more than 6 pokemon to favourites", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onTeamClick(pokemon: Pokemon) {
                if (User.getInstance().email.isEmpty()) {
                    Toast.makeText(context, "Please login to add pokemon to team", Toast.LENGTH_SHORT).show()
                    return
                }
                if (User.getInstance().addTeam(pokemon)) {
                    favViewModel.saveTeam(User.getInstance())
                    Toast.makeText(context, "${pokemon.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }} added to team", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Cant add more than 6 pokemon to team", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onPokemonClick(pokemon: Pokemon) {
               //viewModel.navigateToPokemonDetail(pokemon)
            }

            override fun createMoreInfoViewModel(): MoreInfoViewModel {
                return MoreInfoViewModel(
                    getPokemonMoreInfoUseCase = pokemonMoreInfoUseCase
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshState(ViewState.IDLE)
    }

    override fun getResourceLayout(): Int = R.layout.fragment_team

    override fun subscribe() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                ViewState.IDLE -> viewModel.getTeamPokemon()
                else -> {}
            }
            (activity as MainActivity).showLoading(it == ViewState.LOADING && adapter.items.isEmpty())
        }

        viewModel.pokemons.observe(viewLifecycleOwner) {
            binding.pokemonRecycler.isVisible = it.isNotEmpty()
            binding.emptyState.isVisible = it.isEmpty()
            it.forEachIndexed { index, pokemon ->
                adapter.items.add(pokemon)
                adapter.notifyItemInserted(layoutManager.itemCount + index)
            }
        }
    }

    interface PokemonViewHolderInterface {
        fun onFavoriteClick(pokemon: Pokemon)
        fun onTeamClick(pokemon: Pokemon)
        fun onPokemonClick(pokemon: Pokemon)
        fun createMoreInfoViewModel(): MoreInfoViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.items.clear()
    }
}