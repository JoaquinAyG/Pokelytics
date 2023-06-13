package study.project.pokelytics.viewholders

import android.annotation.SuppressLint
import android.widget.ImageView
import study.project.pokelytics.R
import study.project.pokelytics.api.model.Region
import study.project.pokelytics.databinding.RegionListItemBinding

class RegionViewHolder(
    private val binding: RegionListItemBinding,
    private val onClick : (Region) -> Unit,
) : BaseViewHolder<Region>(binding.root) {


    override fun bind(item: Region) {
        drawRegion(item)
    }

    @SuppressLint("SetTextI18n")
    private fun drawRegion(region: Region) {
        binding.apply {
            name.text = region.name
            setImage(image, region.name)
            root.setOnClickListener {
                onClick(region)
            }
        }
    }

    private fun setImage(image: ImageView, name: String) {
        image.setImageResource(
            when (name) {
                "johto" -> R.mipmap.region_johto
                "hoenn" -> R.mipmap.region_hoenn
                "sinnoh" -> R.mipmap.region_sinnoh
                "unova" -> R.mipmap.region_teselia
                "kalos" -> R.mipmap.region_kalos
                "alola" -> R.mipmap.region_alola
                "galar" -> R.mipmap.region_galar
                "hisui" -> R.mipmap.region_hisui
                "paldea" -> R.mipmap.region_paldea
                else -> R.mipmap.region_kanto
            }
        )
    }


}