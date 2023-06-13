package study.project.pokelytics.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import study.project.pokelytics.models.LoginCredentials
import study.project.pokelytics.models.User

class FirebaseHelper(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {

    fun subscribeToLoginListener(
        params: LoginCredentials,
        onResult: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        firebaseAuth.signInWithEmailAndPassword(params.email, params.password).addOnCompleteListener {
            if (!it.isSuccessful) {
                it.exception?.let { it1 -> onError(it1) }
            } else {
                onResult()
            }
        }
    }

    fun subscribeToAnonymousLoginListener(
        onResult: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        firebaseAuth.signInAnonymously().addOnCompleteListener {
            if (!it.isSuccessful) {
                it.exception?.let { it1 -> onError(it1) }
            } else {
                onResult()
            }
        }
    }


    fun subscribeToRegisterListener(
        params: LoginCredentials,
        onResult: (User) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        firebaseAuth.createUserWithEmailAndPassword(params.email, params.password).addOnCompleteListener {
            if (!it.isSuccessful) {
                it.exception?.let { it1 -> onError(it1) }
            } else {
                //TODO: Return user from firebase instead of creating a new one
                onResult(User(params.email, "", "", ""))
                firebaseFirestore.collection("users").document(params.email).set(
                    hashMapOf(
                        "favouriteList" to "",
                        "team" to "",
                        "name" to params.name
                    )
                )
            }
        }
    }

    fun subscribeToLogoutListener(
        onResult: () -> User,
        onError: (Throwable) -> Unit = {}
    ) {
        firebaseAuth.signOut()
        onResult()
    }

    fun <T : Any>subscribeToKeyResponse(
        collection: String,
        document: String,
        key: String = "response",
        onResult: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        firebaseFirestore.collection(collection).document(document).get().addOnSuccessListener {
            val response = it.get(key)
            try {
                onResult(response as T)
            } catch (e: Exception) {
                onError(Exception("Type error: ${e.message}"))
            }
        }
    }

    fun saveFavs(
        user: User,
        onResult: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        firebaseFirestore.collection("users").document(user.email).update(
            hashMapOf<String, Any>(
                "favouriteList" to user.favouriteList,
            )
        ).addOnCompleteListener {
            if (!it.isSuccessful) {
                it.exception?.let { it1 -> onError(it1) }
            } else {
                onResult()
            }
        }
    }

    fun saveTeam(
        user: User,
        onResult: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        firebaseFirestore.collection("users").document(user.email).update(
            hashMapOf<String, Any>(
                "team" to user.team,
            )
        ).addOnCompleteListener {
            if (!it.isSuccessful) {
                it.exception?.let { it1 -> onError(it1) }
            } else {
                onResult()
            }
        }
    }
}