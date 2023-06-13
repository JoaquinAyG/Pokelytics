package study.project.pokelytics.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import study.project.pokelytics.api.model.Item
import study.project.pokelytics.databinding.BerryListItemBinding
import study.project.pokelytics.viewholders.BerryViewHolder

class BerryListAdapter : BaseRecyclerAdapter<Item, BerryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BerryViewHolder {
        val binding = BerryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BerryViewHolder(binding)
    }

    override fun onViewAttachedToWindow(holder: BerryViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttached()
    }
}