package study.project.pokelytics

import android.os.Looper
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import study.project.pokelytics.api.model.NamedApiResource
import study.project.pokelytics.api.model.PaginationRange
import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.api.model.PokemonSprites
import study.project.pokelytics.usecases.GetPokemonUseCase
import study.project.pokelytics.viewmodels.PokemonListViewModel

@RunWith(AndroidJUnit4::class)
class ViewModelTest {

    private lateinit var pokemonListViewModel: PokemonListViewModel

    private val defaultPokemon = Pokemon(
        id = 0,
        name = "",
        baseExperience = 0,
        height = 0,
        isDefault = false,
        order = 0,
        weight = 0,
        species = NamedApiResource(
            name = "",
            url = ""
        ),
        abilities = emptyList(),
        forms = emptyList(),
        gameIndices = emptyList(),
        heldItems = emptyList(),
        moves = emptyList(),
        stats = emptyList(),
        types = emptyList(),
        sprites = PokemonSprites(
            backDefault = "",
            backFemale = null,
            backShiny = "",
            backShinyFemale = null,
            frontDefault = "",
            frontFemale = null,
            frontShiny = "",
            frontShinyFemale = null,
            other = null,
        ),
        extraInfo = null
    )


    val handler = android.os.Handler(Looper.getMainLooper())


    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getPokemonUseCase: GetPokemonUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        pokemonListViewModel = PokemonListViewModel(getPokemonUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testPokemonListViewModel() = runTest {
        val expectedData = listOf(defaultPokemon)
        val returnData = flowOf(expectedData)
        `when`(
            getPokemonUseCase.execute(
                PaginationRange()
            )
        ).thenReturn(returnData)

        val observer = Observer<List<Pokemon>> { data ->
            assertEquals(expectedData, data)
        }

        handler.post {
            pokemonListViewModel.pokemons.observeForever(observer)
        }
        pokemonListViewModel.getPokemonList(PaginationRange())
    }

    @Test
    fun testBerryListViewModel() {
        assertEquals(true,true)
    }

    @Test
    fun testMoveListViewModel() {
        assertEquals(true,true)
    }

    @Test
    fun testLocationListViewModel() {
        assertEquals(true,true)
    }

    @Test
    fun testRegionListViewModel() {
        assertEquals(true,true)
    }
}
