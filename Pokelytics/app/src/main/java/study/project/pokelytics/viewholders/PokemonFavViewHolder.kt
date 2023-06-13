package study.project.pokelytics.viewholders

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import study.project.pokelytics.R
import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.databinding.PokemonListItemBinding
import study.project.pokelytics.event.observeEvent
import study.project.pokelytics.models.PokemonInterface
import study.project.pokelytics.setImageToUrl
import java.util.Locale

class PokemonFavViewHolder(
    private val binding: PokemonListItemBinding,
    private val pokemonInterface : PokemonInterface
) : BaseViewHolder<Pokemon>(binding.root) {

    private val moreInfoViewModel = pokemonInterface.createMoreInfoViewModel()

    override fun bind(item: Pokemon) {
        drawItem(item)
        moreInfoViewModel.pokemon.observeEvent(this) {
            loadExtraInfo(it)
        }
        moreInfoViewModel.getPokemonExtraInfo(item)
    }

    @SuppressLint("SetTextI18n")
    private fun drawItem(item: Pokemon) {
        binding.apply {
            setFavorite(item)
            setTeam(item)
            root.setOnClickListener {
                pokemonInterface.onPokemonClick(item)
            }
            name.text = item.name
            item.id.toString().let {
                when (it.length) {
                    1 -> id.text = "#00$it"
                    2 -> id.text = "#0$it"
                    else -> id.text = "#$it"
                }
            }
            primaryType.text = item.types[0].type.name
            if (item.types.size > 1) {
                secondaryType.text = item.types[1].type.name
                secondaryType.isVisible = true
            } else {
                secondaryType.isVisible = false
            }
            binding.image.setImageToUrl(
                item.sprites.other?.officialArtwork?.frontDefault ?: "",)
            favorite.setOnClickListener {
                pokemonInterface.onFavoriteClick(item)
                setFavorite(item)
            }
            team.setOnClickListener {
                pokemonInterface.onTeamClick(item)
                setTeam(item)
            }

        }
    }

    private fun loadExtraInfo(item: Pokemon) {
        binding.apply {
            binding.pokemonContainer.setBackgroundResource(getBackground(item.extraInfo?.species?.color?.name ?: ""))
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
            "white" -> R.drawable.pokemon_white
            "yellow" -> R.drawable.pokemon_yellow
            else -> R.drawable.pokemon_green
        }
    }

    private fun setFavorite(item: Pokemon) {
        val isFavorite = moreInfoViewModel.getIsFavorite(item)
        binding.favorite.setImageResource(
            if (isFavorite)
                R.drawable.ic_star
            else
                R.drawable.ic_star_border
        )
    }

    private fun setTeam(item: Pokemon) {
        val isTeam = moreInfoViewModel.getIsTeam(item)
        binding.team.setImageResource(
            if (isTeam)
                R.drawable.ic_pokeball
            else
                R.drawable.ic_outline_circle
        )
    }

}