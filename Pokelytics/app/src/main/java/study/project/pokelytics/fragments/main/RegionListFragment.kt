package study.project.pokelytics.fragments.main

import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import study.project.pokelytics.R
import study.project.pokelytics.activities.MainActivity
import study.project.pokelytics.adapters.RegionListAdapter
import study.project.pokelytics.api.model.PaginationRange
import study.project.pokelytics.databinding.FragmentRegionsListBinding
import study.project.pokelytics.fragments.FragmentBase
import study.project.pokelytics.viewmodels.RegionViewModel
import study.project.pokelytics.viewmodels.ViewState

class RegionListFragment : FragmentBase<FragmentRegionsListBinding>()  {
    private val viewModel: RegionViewModel by viewModel()
    private lateinit var adapter: RegionListAdapter
    private lateinit var layoutManager: FlexboxLayoutManager
    private var paginationRange = PaginationRange()

    override fun bindViewModel() {
        binding.apply {
            //this.lifecycleOwner = this@RegionListFragment
        }
    }

    override fun initializeView() {
        adapter = RegionListAdapter(
            onClick = {
                val action = RegionListFragmentDirections.actionRegionListToLocationList(it.toLocationList())
                findNavController().navigate(action)
            }
        )
        layoutManager = FlexboxLayoutManager(binding.root.context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.SPACE_EVENLY
            alignItems = AlignItems.CENTER
        }

        binding.apply {
            regionRecycler.layoutManager = layoutManager
            regionRecycler.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshState(ViewState.IDLE)
    }

    override fun getResourceLayout(): Int = R.layout.fragment_regions_list

    override fun subscribe() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                ViewState.IDLE -> {
                    adapter.items.clear()
                    adapter.notifyDataSetChanged()
                    paginationRange = PaginationRange()
                    viewModel.getRegionList(paginationRange)
                }
                else -> {}
            }
            (activity as MainActivity).showLoading(it == ViewState.LOADING && adapter.items.isEmpty())
        }

        viewModel.regionList.observe(viewLifecycleOwner) {
            it.forEachIndexed { index, region ->
                adapter.items.add(region)
                adapter.notifyItemInserted(layoutManager.itemCount + index)
            }

            if(it.isNotEmpty() && it.lastOrNull()?.id != null) {
                if (paginationRange.stop) {
                    return@observe
                }
                paginationRange.next()
                viewModel.getRegionList(paginationRange)
            }
        }
    }
}