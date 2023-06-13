package study.project.pokelytics.fragments.main

import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.inject
import study.project.pokelytics.R
import study.project.pokelytics.adapters.LocationListAdapter
import study.project.pokelytics.databinding.FragmentLocationsListBinding
import study.project.pokelytics.fragments.FragmentBase
import study.project.pokelytics.usecases.GetLocationUseCase
import study.project.pokelytics.viewmodels.MoreInfoLocationViewModel

class LocationsListFragment : FragmentBase<FragmentLocationsListBinding>()  {

    private lateinit var adapter: LocationListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private val getLocationUseCase: GetLocationUseCase by inject()

    override fun bindViewModel() {
        binding.apply {
            //this.lifecycleOwner = this@LocationsListFragment
        }
    }

    override fun initializeView() {
        adapter = LocationListAdapter(
            createLocationsInterface()
        )
        layoutManager = LinearLayoutManager(context)
        val args: LocationsListFragmentArgs by navArgs()
        adapter.items = args.locationList.list.toMutableList()
        binding.apply {
            pokemonRecycler.layoutManager = layoutManager
            pokemonRecycler.adapter = adapter
        }
    }

    override fun getResourceLayout(): Int = R.layout.fragment_locations_list

    override fun subscribe() {}

    interface LocationsViewHolderInterface {
        fun createMoreInfoViewModel(): MoreInfoLocationViewModel
    }

    private fun createLocationsInterface(): LocationsViewHolderInterface {
        return object : LocationsViewHolderInterface {
            override fun createMoreInfoViewModel(): MoreInfoLocationViewModel {
                return MoreInfoLocationViewModel(
                    getLocationUseCase = getLocationUseCase
                )
            }
        }
    }
}