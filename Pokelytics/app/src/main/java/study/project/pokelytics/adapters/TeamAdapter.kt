package study.project.pokelytics.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.databinding.PokemonTeamItemBinding
import study.project.pokelytics.models.PokemonInterface
import study.project.pokelytics.viewholders.TeamViewHolder

class TeamAdapter(
    private val pokemonInterface : PokemonInterface
) : BaseRecyclerAdapter<Pokemon, TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = PokemonTeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding, pokemonInterface)
    }

    override fun onViewAttachedToWindow(holder: TeamViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttached()
    }
}