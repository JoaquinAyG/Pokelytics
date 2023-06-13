package study.project.pokelytics.fragments.info

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import study.project.pokelytics.R
import study.project.pokelytics.activities.ActivityBase
import study.project.pokelytics.activities.InfoActivity
import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.capitalized
import study.project.pokelytics.databinding.FragmentInfoPokemonBinding
import study.project.pokelytics.event.observeEvent
import study.project.pokelytics.fragments.FragmentBase
import study.project.pokelytics.models.PokemonInterface
import study.project.pokelytics.models.User
import study.project.pokelytics.setImageToUrl
import study.project.pokelytics.viewmodels.FavViewModel
import study.project.pokelytics.viewmodels.InfoViewModel
import study.project.pokelytics.viewmodels.MoreInfoViewModel
import java.util.Locale

class InfoPokemonFragment: FragmentBase<FragmentInfoPokemonBinding>() {

    val viewModel: InfoViewModel by viewModel()
    private val moreInfoViewModel: MoreInfoViewModel by viewModel()
    private val favViewModel: FavViewModel by viewModel()
    lateinit var pokemon: Pokemon

    override fun bindViewModel() {}

    override fun initializeView() {}

    private fun setUi() {
        binding.apply {
            root.isVisible = true
            val pokemonInterface = createPokemonInterface()
            setFavorite(pokemon)
            setTeam(pokemon)
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
            favorite.setOnClickListener {
                pokemonInterface.onFavoriteClick(pokemon)
                setFavorite(pokemon)
            }
            team.setOnClickListener {
                pokemonInterface.onTeamClick(pokemon)
                setTeam(pokemon)
            }
            when (pokemon.abilities.size) {
                3 -> {
                    ability1.text = pokemon.abilities[0].ability.name.capitalized()
                    ability2.text = pokemon.abilities[1].ability.name.capitalized()
                    ability3.text = pokemon.abilities[2].ability.name.capitalized()
                }

                2 -> {
                    if (pokemon.abilities[1].isHidden) {
                        ability1.text = pokemon.abilities[0].ability.name.capitalized()
                        ability2.isVisible = false
                        ability3.text = pokemon.abilities.first { it.isHidden }.ability.name.capitalized()
                    } else {
                        ability1.text = pokemon.abilities[0].ability.name.capitalized()
                        ability2.text = pokemon.abilities[1].ability.name.capitalized()
                        abilityHidden.isVisible = false
                        ability3.isVisible = false
                    }
                }

                else -> {
                    ability1.text = pokemon.abilities[0].ability.name.capitalized()
                    ability2.isVisible = false
                    abilityHidden.isVisible = false
                    ability3.isVisible = false
                }
            }
            buildPokemonStats(pokemon)
            seeMovesButton.setOnClickListener {
                val dir = InfoPokemonFragmentDirections.actionToPokemonMoves(pokemon)
                findNavController().navigate(dir)
            }
        }
    }

    private fun buildPokemonStats(pokemon: Pokemon) {
        binding.apply {
            hpValue.text = pokemon.stats[0].baseStat.toString()
            attackValue.text = pokemon.stats[1].baseStat.toString()
            defenseValue.text = pokemon.stats[2].baseStat.toString()
            specialAttackValue.text = pokemon.stats[3].baseStat.toString()
            specialDefenseValue.text = pokemon.stats[4].baseStat.toString()
            speedValue.text = pokemon.stats[5].baseStat.toString()
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
            "white" -> R.drawable.pokemon_white_dark
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

    override fun getResourceLayout(): Int = R.layout.fragment_info_pokemon

    override fun subscribe() {
        moreInfoViewModel.pokemon.observeEvent(viewLifecycleOwner) {
            pokemon = it
            loadExtraInfo(pokemon)
        }
        viewModel.pokemon.observe(viewLifecycleOwner) {
            pokemon = it
            moreInfoViewModel.getPokemonExtraInfo(it)
            setUi()
        }
        viewModel.setPokemon((activity as InfoActivity).loadData())
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

            override fun onPokemonClick(pokemon: Pokemon) {}

            override fun createMoreInfoViewModel(): MoreInfoViewModel {
                return moreInfoViewModel
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
}