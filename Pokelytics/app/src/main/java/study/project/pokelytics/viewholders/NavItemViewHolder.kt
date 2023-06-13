package study.project.pokelytics.viewholders

import study.project.pokelytics.R
import study.project.pokelytics.databinding.NavItemBinding
import study.project.pokelytics.models.NavItem

class NavItemViewHolder(
    val binding: NavItemBinding,
    val onClick: (id: String) -> Unit
) : BaseViewHolder<NavItem>(binding.root) {

    override fun bind(item: NavItem) {
        binding.apply {
            title.text = item.title
            title.setTextColor(root.resources.getColor(
                if(item.isSelected)
                    R.color.red
                else
                    R.color.black
            ))
            icon.setBackgroundResource(
                if (item.isSelected)
                    item.iconSelectedId
                else
                    item.iconResId
            )
            root.setOnClickListener {
                onClick(item.title)
            }
        }
    }

}
