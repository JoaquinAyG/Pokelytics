package study.project.pokelytics.viewholders

import android.annotation.SuppressLint
import study.project.pokelytics.api.model.Item
import study.project.pokelytics.databinding.BerryListItemBinding
import study.project.pokelytics.setImageToUrl

class BerryViewHolder(
    private val binding: BerryListItemBinding,
) : BaseViewHolder<Item>(binding.root) {


    override fun bind(item: Item) {
        drawItem(item)
    }

    @SuppressLint("SetTextI18n")
    private fun drawItem(item: Item) {
        binding.apply {
            name.text = item.name
            item.id.toString().let {
                when (it.length) {
                    1 -> id.text = "#00$it"
                    2 -> id.text = "#0$it"
                    else -> id.text = "#$it"
                }
            }

            item.sprites.default?.let { image.setImageToUrl(it) }
        }
    }
}