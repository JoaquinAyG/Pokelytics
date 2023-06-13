package study.project.pokelytics.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import study.project.pokelytics.databinding.NavItemBinding
import study.project.pokelytics.models.NavItem
import study.project.pokelytics.viewholders.NavItemViewHolder

class NavAdapter(
    private val onClick: (id: String) -> Unit
) : BaseRecyclerAdapter<NavItem, NavItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavItemViewHolder {
        val binding = NavItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NavItemViewHolder(binding, onClick)
    }

}
