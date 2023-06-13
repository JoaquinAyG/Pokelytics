package study.project.pokelytics.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import study.project.pokelytics.api.model.Region
import study.project.pokelytics.databinding.RegionListItemBinding
import study.project.pokelytics.viewholders.RegionViewHolder

class RegionListAdapter(
    private val onClick : (Region) -> Unit
) : BaseRecyclerAdapter<Region, RegionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val binding = RegionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RegionViewHolder(binding, onClick)
    }

    override fun onViewAttachedToWindow(holder: RegionViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttached()
    }
}