package study.project.pokelytics.api.client

import kotlinx.coroutines.flow.flow
import retrofit2.Call
import study.project.pokelytics.models.LocationList

open class PokeApiClient(
    clientConfig: ClientConfig = ClientConfig()
) : PokeApi {

    private val service = PokeApiServiceImpl(clientConfig)

    private fun <T> Call<T>.result(): T {
        return execute().let {
            if (it.isSuccessful) it.body()!! else throw ErrorResponse(it.code(), it.message())
        }
    }

    // region ResourceList Lists

    // region Berries

    override fun getBerryList(offset: Int, limit: Int) = flow {
        val res = service.getBerryList(offset, limit).execute().body()?.results
        val berryList = res?.map {
            getBerry(it.id)
        }
        val itemList = berryList?.map {
            getItem(it.item.id)
        }
        itemList?.let { emit(it) }
    }

    override fun getBerryFirmnessList(offset: Int, limit: Int) = flow {
        emit(service.getBerryFirmnessList(offset, limit).result())
    }

    override fun getBerryFlavorList(offset: Int, limit: Int) = flow {
        emit(service.getBerryFlavorList(offset, limit).result())
    }

    // endregion Berries

    // region Contests

    override fun getContestTypeList(offset: Int, limit: Int) = flow {
        emit(service.getContestTypeList(offset, limit).result())
    }

    override fun getContestEffectList(offset: Int, limit: Int) = flow {
        emit(service.getContestEffectList(offset, limit).result())
    }

    override fun getSuperContestEffectList(offset: Int, limit: Int) = flow {
        emit(service.getSuperContestEffectList(offset, limit).result())
    }

    // endregion Contests

    // region Encounters

    override fun getEncounterMethodList(offset: Int, limit: Int) = flow {
        emit(service.getEncounterMethodList(offset, limit).result())
    }

    override fun getEncounterConditionList(offset: Int, limit: Int) = flow {
        emit(service.getEncounterConditionList(offset, limit).result())
    }

    override fun getEncounterConditionValueList(offset: Int, limit: Int) = flow {
        emit(service.getEncounterConditionValueList(offset, limit).result())
    }

    // endregion

    // region Evolution

    override fun getEvolutionChainList(offset: Int, limit: Int) = flow {
        emit(service.getEvolutionChainList(offset, limit).result())
    }

    override fun getEvolutionTriggerList(offset: Int, limit: Int) = flow {
        emit(service.getEvolutionTriggerList(offset, limit).result())
    }

    // endregion

    //region Games

    override fun getGenerationList(offset: Int, limit: Int) = flow {
        emit(service.getGenerationList(offset, limit).result())
    }

    override fun getPokedexList(offset: Int, limit: Int) = flow {
        emit(service.getPokedexList(offset, limit).result())
    }

    override fun getVersionList(offset: Int, limit: Int) = flow {
        emit(service.getVersionList(offset, limit).result())
    }

    override fun getVersionGroupList(offset: Int, limit: Int) = flow {
        emit(service.getVersionGroupList(offset, limit).result())
    }

    // endregion

    // region Items

    override fun getItemList(offset: Int, limit: Int) = flow {
        emit(service.getItemList(offset, limit).result())
    }

    override fun getItemAttributeList(offset: Int, limit: Int) = flow {
        emit(service.getItemAttributeList(offset, limit).result())
    }

    override fun getItemCategoryList(offset: Int, limit: Int) = flow {
        emit(service.getItemCategoryList(offset, limit).result())
    }

    override fun getItemFlingEffectList(offset: Int, limit: Int) = flow {
        emit(service.getItemFlingEffectList(offset, limit).result())
    }

    override fun getItemPocketList(offset: Int, limit: Int) = flow {
        emit(service.getItemPocketList(offset, limit).result())
    }

    // endregion

    //region Moves

    override fun getMoveList(offset: Int, limit: Int) = flow {
        val res = service.getMoveList(offset, limit).execute().body()?.results
        val moveList = res?.map {
            getMove(it.id)
        }
        moveList?.let { emit(it) }
    }

    override fun getMoveAilmentList(offset: Int, limit: Int) = flow {
        emit(service.getMoveAilmentList(offset, limit).result())
    }

    override fun getMoveBattleStyleList(offset: Int, limit: Int) = flow {
        emit(service.getMoveBattleStyleList(offset, limit).result())
    }

    override fun getMoveCategoryList(offset: Int, limit: Int) = flow {
        emit(service.getMoveCategoryList(offset, limit).result())
    }

    override fun getMoveDamageClassList(offset: Int, limit: Int) = flow {
        emit(service.getMoveDamageClassList(offset, limit).result())
    }

    override fun getMoveLearnMethodList(offset: Int, limit: Int) = flow {
        emit(service.getMoveLearnMethodList(offset, limit).result())
    }

    override fun getMoveTargetList(offset: Int, limit: Int) = flow {
        emit(service.getMoveTargetList(offset, limit).result())
    }

    // endregion

    // region Locations

    override fun getLocationList(locationList: LocationList) = flow {
            emit(locationList.list.map { getLocation(it.id)})
    }

    override fun getLocationAreaList(offset: Int, limit: Int) = flow {
        emit(service.getLocationAreaList(offset, limit).result())
    }

    override fun getPalParkAreaList(offset: Int, limit: Int) = flow {
        emit(service.getPalParkAreaList(offset, limit).result())
    }

    override fun getRegionList(offset: Int, limit: Int) = flow {
        val res = service.getRegionList(offset, limit).execute().body()?.results
        val regionList = res?.map {
            getRegion(it.id)
        }
        regionList?.let { emit(it) }
    }

    // endregion

    // region Machines

    override fun getMachineList(offset: Int, limit: Int) = flow {
        emit(service.getMachineList(offset, limit).result())
    }

    // endregion Machines

    // region Pokemon

    override fun getAbilityList(offset: Int, limit: Int) = flow {
        emit(service.getAbilityList(offset, limit).result())
    }

    override fun getCharacteristicList(offset: Int, limit: Int) = flow {
        emit(service.getCharacteristicList(offset, limit).result())
    }

    override fun getEggGroupList(offset: Int, limit: Int) = flow {
        emit(service.getEggGroupList(offset, limit).result())
    }

    override fun getGenderList(offset: Int, limit: Int) = flow {
        emit(service.getGenderList(offset, limit).result())
    }

    override fun getGrowthRateList(offset: Int, limit: Int) = flow {
        emit(service.getGrowthRateList(offset, limit).result())
    }

    override fun getNatureList(offset: Int, limit: Int) = flow {
        emit(service.getNatureList(offset, limit).result())
    }

    override fun getPokeathlonStatList(offset: Int, limit: Int) = flow {
        emit(service.getPokeathlonStatList(offset, limit).result())
    }

    override fun getPokemonList(offset: Int, limit: Int) = flow {
        val res = service.getPokemonList(offset, limit).execute().body()?.results
        val list = res?.map {
            getPokemon(it.id)
        }
        list?.let { emit(it) }
    }

    override fun getPokemonColorList(offset: Int, limit: Int) = flow {
        emit(service.getPokemonColorList(offset, limit).result())
    }

    override fun getPokemonFormList(offset: Int, limit: Int) = flow {
        emit(service.getPokemonFormList(offset, limit).result())
    }

    override fun getPokemonHabitatList(offset: Int, limit: Int) = flow {
        emit(service.getPokemonHabitatList(offset, limit).result())
    }

    override fun getPokemonShapeList(offset: Int, limit: Int) = flow {
        emit(service.getPokemonShapeList(offset, limit).result())
    }

    override fun getPokemonSpeciesList(offset: Int, limit: Int) = flow {
        emit(service.getPokemonSpeciesList(offset, limit).result())
    }

    override fun getStatList(offset: Int, limit: Int) = flow {
        emit(service.getStatList(offset, limit).result())
    }

    override fun getTypeList(offset: Int, limit: Int) = flow {
        emit(service.getTypeList(offset, limit).result())
    }

    // endregion

    // region Utility

    override fun getLanguageList(offset: Int, limit: Int) = flow {
        emit(service.getLanguageList(offset, limit).result())
    }

    // endregion

    // endregion

    // region Berries

    override fun getBerry(id: Int) = service.getBerry(id).result()

    override fun getBerryFirmness(id: Int) = service.getBerryFirmness(id).result()

    override fun getBerryFlavor(id: Int) = service.getBerryFlavor(id).result()

    // endregion Berries

    // region Contests

    override fun getContestType(id: Int) = service.getContestType(id).result()

    override fun getContestEffect(id: Int) = service.getContestEffect(id).result()

    override fun getSuperContestEffect(id: Int) = service.getSuperContestEffect(id).result()

    // endregion Contests

    // region Encounters

    override fun getEncounterMethod(id: Int) =
        service.getEncounterMethod(id).result()

    override fun getEncounterCondition(id: Int) =
        service.getEncounterCondition(id).result()

    override fun getEncounterConditionValue(id: Int) =
        service.getEncounterConditionValue(id).result()

    // endregion Contests

    // region Evolution

    override fun getEvolutionChain(id: Int) =
        service.getEvolutionChain(id).result()

    override fun getEvolutionTrigger(id: Int) =
        service.getEvolutionTrigger(id).result()

    // endregion Evolution

    // region Games

    override fun getGeneration(id: Int) =
        service.getGeneration(id).result()

    override fun getPokedex(id: Int) =
        service.getPokedex(id).result()

    override fun getVersion(id: Int) =
        service.getVersion(id).result()

    override fun getVersionGroup(id: Int) =
        service.getVersionGroup(id).result()

    // endregion Games

    // region Items

    override fun getItem(id: Int) = service.getItem(id).result()

    override fun getItemAttribute(id: Int) =
        service.getItemAttribute(id).result()

    override fun getItemCategory(id: Int) =
        service.getItemCategory(id).result()

    override fun getItemFlingEffect(id: Int) =
        service.getItemFlingEffect(id).result()

    override fun getItemPocket(id: Int) =
        service.getItemPocket(id).result()

    // endregion Items

    // region Moves

    override fun getMove(id: Int) = service.getMove(id).result()

    override fun getMoveAilment(id: Int) =
        service.getMoveAilment(id).result()

    override fun getMoveBattleStyle(id: Int) =
        service.getMoveBattleStyle(id).result()

    override fun getMoveCategory(id: Int) =
        service.getMoveCategory(id).result()

    override fun getMoveDamageClass(id: Int) =
        service.getMoveDamageClass(id).result()

    override fun getMoveLearnMethod(id: Int) =
        service.getMoveLearnMethod(id).result()

    override fun getMoveTarget(id: Int) =
        service.getMoveTarget(id).result()

    // endregion Moves

    // region Locations

    override fun getLocation(id: Int) =
        service.getLocation(id).result()

    override fun getLocationArea(id: Int) =
        service.getLocationArea(id).result()

    override fun getPalParkArea(id: Int) =
        service.getPalParkArea(id).result()

    override fun getRegion(id: Int) =
        service.getRegion(id).result()

    // endregion Locations

    // region Machines

    override fun getMachine(id: Int) =
        service.getMachine(id).result()

    // endregion Machines

    // region Pokemon

    override fun getAbility(id: Int) =
        service.getAbility(id).result()

    override fun getCharacteristic(id: Int) =
        service.getCharacteristic(id).result()

    override fun getEggGroup(id: Int) =
        service.getEggGroup(id).result()

    override fun getGender(id: Int) = service.getGender(id).result()

    override fun getGrowthRate(id: Int) = service.getGrowthRate(id).result()

    override fun getNature(id: Int) = service.getNature(id).result()

    override fun getPokeathlonStat(id: Int) = service.getPokeathlonStat(id).result()

    override fun getPokemon(id: Int) = service.getPokemon(id).result()

    override fun getPokemonEncounterList(id: Int) = service.getPokemonEncounterList(id).result()

    override fun getPokemonColor(id: Int) = service.getPokemonColor(id).result()

    override fun getPokemonForm(id: Int) = service.getPokemonForm(id).result()

    override fun getPokemonHabitat(id: Int) = service.getPokemonHabitat(id).result()


    override fun getPokemonShape(id: Int) = service.getPokemonShape(id).result()

    override fun getPokemonSpecies(id: Int) = service.getPokemonSpecies(id).result()

    override fun getStat(id: Int) = service.getStat(id).result()

    override fun getType(id: Int) = service.getType(id).result()

    override fun getLanguage(id: Int) = service.getLanguage(id).result()
}
