package study.project.pokelytics.api.client

import kotlinx.coroutines.flow.Flow
import study.project.pokelytics.api.model.Ability
import study.project.pokelytics.api.model.ApiResourceList
import study.project.pokelytics.api.model.Berry
import study.project.pokelytics.api.model.BerryFirmness
import study.project.pokelytics.api.model.BerryFlavor
import study.project.pokelytics.api.model.Characteristic
import study.project.pokelytics.api.model.ContestEffect
import study.project.pokelytics.api.model.ContestType
import study.project.pokelytics.api.model.EggGroup
import study.project.pokelytics.api.model.EncounterCondition
import study.project.pokelytics.api.model.EncounterConditionValue
import study.project.pokelytics.api.model.EncounterMethod
import study.project.pokelytics.api.model.EvolutionChain
import study.project.pokelytics.api.model.EvolutionTrigger
import study.project.pokelytics.api.model.Gender
import study.project.pokelytics.api.model.Generation
import study.project.pokelytics.api.model.GrowthRate
import study.project.pokelytics.api.model.Item
import study.project.pokelytics.api.model.ItemAttribute
import study.project.pokelytics.api.model.ItemCategory
import study.project.pokelytics.api.model.ItemFlingEffect
import study.project.pokelytics.api.model.ItemPocket
import study.project.pokelytics.api.model.Language
import study.project.pokelytics.api.model.Location
import study.project.pokelytics.api.model.LocationArea
import study.project.pokelytics.api.model.LocationAreaEncounter
import study.project.pokelytics.api.model.Machine
import study.project.pokelytics.api.model.Move
import study.project.pokelytics.api.model.MoveAilment
import study.project.pokelytics.api.model.MoveBattleStyle
import study.project.pokelytics.api.model.MoveCategory
import study.project.pokelytics.api.model.MoveDamageClass
import study.project.pokelytics.api.model.MoveLearnMethod
import study.project.pokelytics.api.model.MoveTarget
import study.project.pokelytics.api.model.NamedApiResourceList
import study.project.pokelytics.api.model.Nature
import study.project.pokelytics.api.model.PalParkArea
import study.project.pokelytics.api.model.PokeathlonStat
import study.project.pokelytics.api.model.Pokedex
import study.project.pokelytics.api.model.Pokemon
import study.project.pokelytics.api.model.PokemonColor
import study.project.pokelytics.api.model.PokemonForm
import study.project.pokelytics.api.model.PokemonHabitat
import study.project.pokelytics.api.model.PokemonShape
import study.project.pokelytics.api.model.PokemonSpecies
import study.project.pokelytics.api.model.Region
import study.project.pokelytics.api.model.Stat
import study.project.pokelytics.api.model.SuperContestEffect
import study.project.pokelytics.api.model.Type
import study.project.pokelytics.api.model.Version
import study.project.pokelytics.api.model.VersionGroup
import study.project.pokelytics.models.LocationList

interface PokeApi {

    fun getBerryList(offset: Int, limit: Int): Flow<List<Item>>

    fun getBerryFirmnessList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getBerryFlavorList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getContestTypeList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getContestEffectList(offset: Int, limit: Int): Flow<ApiResourceList>

    fun getSuperContestEffectList(offset: Int, limit: Int): Flow<ApiResourceList>

    fun getEncounterMethodList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getEncounterConditionList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getEncounterConditionValueList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getEvolutionChainList(offset: Int, limit: Int): Flow<ApiResourceList>

    fun getEvolutionTriggerList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getGenerationList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getPokedexList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getVersionList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getVersionGroupList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getItemList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getItemAttributeList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getItemCategoryList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getItemFlingEffectList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getItemPocketList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getMoveList(offset: Int, limit: Int): Flow<List<Move>>

    fun getMoveAilmentList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getMoveBattleStyleList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getMoveCategoryList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getMoveDamageClassList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getMoveLearnMethodList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getMoveTargetList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getLocationList(locationList: LocationList): Flow<List<Location>>

    fun getLocationAreaList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getPalParkAreaList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getRegionList(offset: Int, limit: Int): Flow<List<Region>>

    fun getMachineList(offset: Int, limit: Int): Flow<ApiResourceList>

    fun getAbilityList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getCharacteristicList(offset: Int, limit: Int): Flow<ApiResourceList>

    fun getEggGroupList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getGenderList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getGrowthRateList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getNatureList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getPokeathlonStatList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getPokemonList(offset: Int, limit: Int): Flow<List<Pokemon>>

    fun getPokemonColorList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getPokemonFormList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getPokemonHabitatList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getPokemonShapeList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getPokemonSpeciesList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getStatList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getTypeList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getLanguageList(offset: Int, limit: Int): Flow<NamedApiResourceList>

    fun getBerry(id: Int): Berry

    fun getBerryFirmness(id: Int): BerryFirmness
    fun getBerryFlavor(id: Int): BerryFlavor
    fun getContestType(id: Int): ContestType
    fun getContestEffect(id: Int): ContestEffect
    fun getSuperContestEffect(id: Int): SuperContestEffect
    fun getEncounterMethod(id: Int): EncounterMethod
    fun getEncounterCondition(id: Int): EncounterCondition
    fun getEncounterConditionValue(id: Int): EncounterConditionValue
    fun getEvolutionChain(id: Int): EvolutionChain
    fun getEvolutionTrigger(id: Int): EvolutionTrigger
    fun getGeneration(id: Int): Generation
    fun getPokedex(id: Int): Pokedex
    fun getVersion(id: Int): Version
    fun getVersionGroup(id: Int): VersionGroup
    fun getItem(id: Int): Item

    fun getItemAttribute(id: Int): ItemAttribute
    fun getItemCategory(id: Int): ItemCategory
    fun getItemFlingEffect(id: Int): ItemFlingEffect
    fun getItemPocket(id: Int): ItemPocket
    fun getMove(id: Int): Move

    fun getMoveAilment(id: Int): MoveAilment
    fun getMoveBattleStyle(id: Int): MoveBattleStyle
    fun getMoveCategory(id: Int): MoveCategory
    fun getMoveDamageClass(id: Int): MoveDamageClass
    fun getMoveLearnMethod(id: Int): MoveLearnMethod
    fun getMoveTarget(id: Int): MoveTarget
    fun getLocation(id: Int): Location
    fun getLocationArea(id: Int): LocationArea
    fun getPalParkArea(id: Int): PalParkArea
    fun getRegion(id: Int): Region
    fun getMachine(id: Int): Machine
    fun getAbility(id: Int): Ability
    fun getCharacteristic(id: Int): Characteristic
    fun getEggGroup(id: Int): EggGroup
    fun getGender(id: Int): Gender
    fun getGrowthRate(id: Int): GrowthRate
    fun getNature(id: Int): Nature
    fun getPokeathlonStat(id: Int): PokeathlonStat
    fun getPokemon(id: Int): Pokemon

    fun getPokemonEncounterList(id: Int): List<LocationAreaEncounter>
    fun getPokemonColor(id: Int): PokemonColor
    fun getPokemonForm(id: Int): PokemonForm
    fun getPokemonHabitat(id: Int): PokemonHabitat
    fun getPokemonShape(id: Int): PokemonShape
    fun getPokemonSpecies(id: Int): PokemonSpecies

    fun getStat(id: Int): Stat
    fun getType(id: Int): Type
    fun getLanguage(id: Int): Language
}
