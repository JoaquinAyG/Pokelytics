package study.project.pokelytics.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.databinding.PokemonListItemBinding
import study.project.pokelytics.models.PokemonInterface
import study.project.pokelytics.viewholders.PokemonFavViewHolder

class PokemonFavAdapter(
    private val pokemonInterface : PokemonInterface
) : BaseRecyclerAdapter<Pokemon, PokemonFavViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonFavViewHolder {
        val binding = PokemonListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonFavViewHolder(binding, pokemonInterface)
    }

    override fun onViewAttachedToWindow(holder: PokemonFavViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttached()
    }
}