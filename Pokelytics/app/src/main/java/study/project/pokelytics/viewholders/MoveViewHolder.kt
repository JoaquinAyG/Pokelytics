package study.project.pokelytics.viewholders

import android.annotation.SuppressLint
import android.widget.ImageView
import study.project.pokelytics.R
import study.project.pokelytics.api.model.Move
import study.project.pokelytics.databinding.MoveListItemBinding

class MoveViewHolder(
    private val binding: MoveListItemBinding,
) : BaseViewHolder<Move>(binding.root) {


    override fun bind(item: Move) {
        drawMove(item)
    }

    @SuppressLint("SetTextI18n")
    private fun drawMove(move: Move) {
        binding.apply {
            name.text = move.name
            move.id.toString().let {
                when (it.length) {
                    1 -> id.text = "#00$it"
                    2 -> id.text = "#0$it"
                    else -> id.text = "#$it"
                }
            }
            if (move.power == null || move.power <0){
                power.text = "---"
            }else{
                power.text = move.power.toString()
            }
            setImage(image, move.damageClass.name)
        }
    }

    private fun setImage(image: ImageView, name: String) {
        image.setImageResource(
            when (name) {
                "physical" -> R.mipmap.ic_physical
                "special" -> R.mipmap.ic_special
                else -> R.mipmap.ic_status
            }
        )
    }


}