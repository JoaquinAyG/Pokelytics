package study.project.pokelytics.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import study.project.pokelytics.api.model.Move
import study.project.pokelytics.databinding.MoveListItemBinding
import study.project.pokelytics.viewholders.MoveViewHolder

class MoveListAdapter : BaseRecyclerAdapter<Move, MoveViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoveViewHolder {
        val binding = MoveListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoveViewHolder(binding)
    }

    override fun onViewAttachedToWindow(holder: MoveViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttached()
    }
}