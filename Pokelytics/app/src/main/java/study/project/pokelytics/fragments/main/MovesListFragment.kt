package study.project.pokelytics.fragments.main

import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import study.project.pokelytics.R
import study.project.pokelytics.activities.ActivityBase
import study.project.pokelytics.adapters.MoveListAdapter
import study.project.pokelytics.api.model.PaginationRange
import study.project.pokelytics.databinding.FragmentPokemonListBinding
import study.project.pokelytics.fragments.FragmentBase
import study.project.pokelytics.viewmodels.MoveViewModel
import study.project.pokelytics.viewmodels.ViewState

class MovesListFragment : FragmentBase<FragmentPokemonListBinding>() {

    private val viewModel: MoveViewModel by viewModel()
    private lateinit var adapter: MoveListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var paginationRange = PaginationRange()

    override fun bindViewModel() {
        binding.apply {
            //this.lifecycleOwner = this@MovesListFragment
        }
    }

    override fun initializeView() {
        adapter = MoveListAdapter()
        layoutManager = LinearLayoutManager(context)

        binding.apply {
            pokemonRecycler.layoutManager = layoutManager
            pokemonRecycler.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshState(ViewState.IDLE)
    }

    override fun getResourceLayout(): Int = R.layout.fragment_pokemon_list

    override fun subscribe() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                ViewState.IDLE -> {
                    adapter.items.clear()
                    adapter.notifyDataSetChanged()
                    paginationRange = PaginationRange()
                    viewModel.getMoveList(paginationRange)
                }
                else -> {}
            }
            (activity as ActivityBase<*>).showLoading(it == ViewState.LOADING && adapter.items.isEmpty())
        }

        viewModel.moveList.observe(viewLifecycleOwner) {
            it.forEachIndexed { index, move ->
                adapter.items.add(move)
                adapter.notifyItemInserted(layoutManager.itemCount + index)
            }

            if(it.isNotEmpty() && it.lastOrNull()?.id != null) {
                if (paginationRange.stop) {
                    return@observe
                }
                paginationRange.next()
                viewModel.getMoveList(paginationRange)
            }
        }
    }
}