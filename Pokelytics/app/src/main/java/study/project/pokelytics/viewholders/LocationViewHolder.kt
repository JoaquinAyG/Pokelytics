package study.project.pokelytics.viewholders

import android.annotation.SuppressLint
import study.project.pokelytics.api.model.Location
import study.project.pokelytics.api.model.NamedApiResource
import study.project.pokelytics.capitalized
import study.project.pokelytics.databinding.LocationListItemBinding
import study.project.pokelytics.event.observeEvent
import study.project.pokelytics.fragments.main.LocationsListFragment

class LocationViewHolder(
    private val binding: LocationListItemBinding,
    locationsViewHolderInterface: LocationsListFragment.LocationsViewHolderInterface
) : BaseViewHolder<NamedApiResource>(binding.root) {


    val viewModel = locationsViewHolderInterface.createMoreInfoViewModel()

    override fun bind(item: NamedApiResource) {
        drawItem(item)
        subscribe()
        viewModel.getLocationExtraInfo(item)
    }

    private fun subscribe() {
        viewModel.location.observeEvent(this) {
            drawItem(it)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun drawItem(location: Location) {
        binding.apply {
            areas.text = location.areas.joinToString(separator = ", ") { area ->
                area.name.capitalized()
            }.replace("-", " ").ifEmpty { "No Areas" }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun drawItem(location: NamedApiResource) {
        binding.apply {
            name.text = location.name.replace("-", " ").capitalized()
            location.id.toString().let {
                when (it.length) {
                    1 -> id.text = "#00$it"
                    2 -> id.text = "#0$it"
                    else -> id.text = "#$it"
                }
            }
        }
    }
}