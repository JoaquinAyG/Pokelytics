package study.project.pokelytics.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import study.project.pokelytics.api.model.NamedApiResource
import study.project.pokelytics.databinding.LocationListItemBinding
import study.project.pokelytics.fragments.main.LocationsListFragment
import study.project.pokelytics.viewholders.LocationViewHolder

class LocationListAdapter(
    private val locationsViewHolderInterface: LocationsListFragment.LocationsViewHolderInterface
) : BaseRecyclerAdapter<NamedApiResource, LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = LocationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(
            binding,
            locationsViewHolderInterface
        )
    }

    override fun onViewAttachedToWindow(holder: LocationViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttached()
    }
}