package study.project.pokelytics.api.model

data class Machine(
    val id: Int,
    val item: NamedApiResource,
    val move: NamedApiResource,
    val versionGroup: NamedApiResource
)
