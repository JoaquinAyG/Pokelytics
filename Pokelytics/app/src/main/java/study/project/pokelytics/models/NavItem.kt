package study.project.pokelytics.models

import com.google.gson.annotations.SerializedName
import study.project.pokelytics.R
import java.util.Locale

data class NavItem(
    @SerializedName("title")
    val title: String,
    @SerializedName("icon")
    val icon: String,
    var iconResId: Int = 0,
    var iconSelectedId: Int = 0,
    var isSelected : Boolean = false
    ) {
    init {
        if (iconResId == 0) {
            mapId()
        }
        if (iconSelectedId == 0) {
            mapSelectedId()
        }
    }

    fun mapSelectedId() {
        this.iconSelectedId = when(icon.lowercase(Locale.ROOT)) {
            "home" -> R.drawable.ic_home_selected
            "pokedex" -> R.drawable.ic_pokeball_selected
            "berries" -> R.drawable.ic_berries_selected
            "moves" -> R.drawable.ic_sword_selected
            "items" -> R.drawable.ic_items_selected
            "locations" -> R.drawable.ic_location_selected
            "settings" -> R.drawable.ic_settings_selected
            "favourites" -> R.drawable.ic_star_selected
            "team" -> R.drawable.ic_pc_selected
            else -> R.drawable.ic_pokeball_selected
        }
    }

    fun mapId() {
        this.iconResId = when(icon.lowercase(Locale.ROOT)) {
            "home" -> R.drawable.ic_home
            "pokedex" -> R.drawable.ic_pokeball
            "berries" -> R.drawable.ic_berries
            "moves" -> R.drawable.ic_sword
            "items" -> R.drawable.ic_items
            "locations" -> R.drawable.ic_location
            "settings" -> R.drawable.ic_settings
            "favourites" -> R.drawable.ic_star
            "team" -> R.drawable.ic_pc
            "logout" -> R.drawable.ic_logout
            else -> R.drawable.ic_pokeball
        }
    }
}