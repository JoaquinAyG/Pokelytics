package study.project.pokelytics.models

import study.project.pokelytics.api.model.NamedApiResource
import java.io.Serializable

data class LocationList (
    val list : List<NamedApiResource>
): Serializable
