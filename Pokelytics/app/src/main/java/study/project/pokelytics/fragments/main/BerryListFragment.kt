package study.project.pokelytics.fragments.main

import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import study.project.pokelytics.R
import study.project.pokelytics.activities.MainActivity
import study.project.pokelytics.adapters.BerryListAdapter
import study.project.pokelytics.api.model.PaginationRange
import study.project.pokelytics.databinding.FragmentPokemonListBinding
import study.project.pokelytics.fragments.FragmentBase
import study.project.pokelytics.viewmodels.BerryViewModel
import study.project.pokelytics.viewmodels.ViewState

class BerryListFragment : FragmentBase<FragmentPokemonListBinding>() {

    private val viewModel: BerryViewModel by viewModel()
    private lateinit var adapter: BerryListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var paginationRange = PaginationRange()

    override fun bindViewModel() {
        binding.apply {
            //this.lifecycleOwner = this@BerryListFragment
        }
    }

    override fun initializeView() {
        adapter = BerryListAdapter()
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
                    viewModel.getBerryList(paginationRange)
                }
                else -> {}
            }
            (activity as MainActivity).showLoading(it == ViewState.LOADING && adapter.items.isEmpty())
        }

        viewModel.berryList.observe(viewLifecycleOwner) {
            it.forEachIndexed { index, berry ->
                adapter.items.add(berry)
                adapter.notifyItemInserted(layoutManager.itemCount + index)
            }

            if(it.isNotEmpty() && it.lastOrNull()?.id != null) {
                if (paginationRange.stop) {
                    return@observe
                }
                paginationRange.next()
                viewModel.getBerryList(paginationRange)
            }
        }
    }
}