package study.project.pokelytics.fragments.info

import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import study.project.pokelytics.R
import study.project.pokelytics.adapters.MoveListAdapter
import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.capitalized
import study.project.pokelytics.databinding.FragmentPokemonMovesBinding
import study.project.pokelytics.fragments.FragmentBase
import study.project.pokelytics.setImageToUrl
import study.project.pokelytics.viewmodels.MoveViewModel
import study.project.pokelytics.viewmodels.ViewState
import java.util.Locale

class PokemonMovesListFragment : FragmentBase<FragmentPokemonMovesBinding>() {

    private val viewModel: MoveViewModel by viewModel()
    private lateinit var adapter: MoveListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var pokemon: Pokemon

    override fun bindViewModel() {
        binding.apply {
            //this.lifecycleOwner = this@MovesListFragment
        }
    }

    private fun setUi() {
        binding.apply {
            root.isVisible = true
            name.text = pokemon.name
            pokemon.id.toString().let {
                when (it.length) {
                    1 -> id.text = "#00$it"
                    2 -> id.text = "#0$it"
                    else -> id.text = "#$it"
                }
            }
            primaryType.text = pokemon.types[0].type.name.capitalized()
            if (pokemon.types.size > 1) {
                secondaryType.text = pokemon.types[1].type.name.capitalized()
                secondaryType.isVisible = true
            } else {
                secondaryType.isVisible = false
            }
            binding.image.setImageToUrl(
                pokemon.sprites.other?.officialArtwork?.frontDefault ?: "",)
            pokemonContainer.setBackgroundResource(getBackground(pokemon.extraInfo?.species?.color?.name ?: ""))
        }
    }

    private fun getBackground(color: String): Int {
        return when (color.lowercase(Locale.ROOT)) {
            "black" -> R.drawable.pokemon_black
            "blue" -> R.drawable.pokemon_blue
            "brown" -> R.drawable.pokemon_brown
            "gray" -> R.drawable.pokemon_gray
            "pink" -> R.drawable.pokemon_pink
            "purple" -> R.drawable.pokemon_purple
            "red" -> R.drawable.pokemon_red
            "white" -> R.drawable.pokemon_white_dark
            "yellow" -> R.drawable.pokemon_yellow
            else -> R.drawable.pokemon_green
        }
    }

    override fun initializeView() {
        val args: PokemonMovesListFragmentArgs by navArgs()
        pokemon = args.pokemon
        adapter = MoveListAdapter()
        layoutManager = LinearLayoutManager(context)

        binding.apply {
            movesRecycler.layoutManager = layoutManager
            movesRecycler.adapter = adapter
        }

        setUi()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshState(ViewState.IDLE)
        viewModel.getMoveFromList(pokemon.moves.joinToString(",") { it.move.id.toString() })
    }

    override fun getResourceLayout(): Int = R.layout.fragment_pokemon_moves

    override fun subscribe() {
        viewModel.moveList.observe(viewLifecycleOwner) {
            it.forEachIndexed { index, move ->
                adapter.items.add(move)
                adapter.notifyItemInserted(layoutManager.itemCount + index)
            }
        }
    }
}