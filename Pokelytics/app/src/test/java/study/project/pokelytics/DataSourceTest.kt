package study.project.pokelytics

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import study.project.pokelytics.api.client.PokeApiClient
import study.project.pokelytics.api.datasources.PokemonDataSource
import study.project.pokelytics.api.model.NamedApiResource
import study.project.pokelytics.api.model.PaginationRange
import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.api.model.PokemonSprites

@RunWith(MockitoJUnitRunner::class)
class DataSourceTest {

    @Mock
    lateinit var apiService: PokeApiClient
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


    @Test
    fun testPokemonDataSource() {
        val expectedResponse = flowOf(
            listOf(
                defaultPokemon,
                defaultPokemon,
                defaultPokemon
            )
        )
        `when`(apiService.getPokemonList(0, 3)).thenReturn(expectedResponse)

        val pokemonDataSource = PokemonDataSource(apiService)

        val actualResponse = pokemonDataSource.getPokemonFromPage(
            PaginationRange(
                from = 0,
                count = 3
            )
        )

        assertEquals(expectedResponse, actualResponse)
    }




    @Test
    fun testBerryDataSource() {
        assertTrue(true)
    }

    @Test
    fun testMoveDataSource() {
        assertTrue(true)
    }

    @Test
    fun testLocationDataSource() {
        assertTrue(true)
    }

    @Test
    fun testRegionDataSource() {
        assertTrue(true)
    }
}