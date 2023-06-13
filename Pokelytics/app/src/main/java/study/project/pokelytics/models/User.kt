package study.project.pokelytics.models

import study.project.pokelytics.api.model.Pokemon

class User(
    val email: String, val name: String, var favouriteList: String, var team: String
) : java.io.Serializable {

    fun addFav(pokemon: Pokemon): Boolean {
        val list = favouriteList.split(",").toMutableList()
        if (list.contains(pokemon.id.toString())) {
            list.remove(pokemon.id.toString())
        } else {
            list.add(pokemon.id.toString())
        }
        favouriteList = list.filter { it.isNotBlank() }.joinToString(",")
        return true
    }

    fun addTeam(pokemon: Pokemon): Boolean {
        val list = team.split(",").toMutableList()
        if (list.contains(pokemon.id.toString())) {
            list.remove(pokemon.id.toString())
        } else {
            if (list.size >= 6) return false
            list.add(pokemon.id.toString())
        }
        team = list.filter { it.isNotBlank() }.joinToString(",")
        return true
    }

    fun isFavorite(item: Pokemon): Boolean {
        return favouriteList.split(",").contains(item.id.toString())
    }

    fun isTeam(item: Pokemon): Boolean {
        return team.split(",").contains(item.id.toString())
    }

    companion object {
        fun getDefaultUser(): User {
            return User("", "", "", "")
        }

        private var instance = getDefaultUser()
        fun getInstance(): User {
            return instance
        }

        fun setInstance(user: User) {
            instance = user
        }
    }

}