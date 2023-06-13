package study.project.pokelytics.usecases

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import study.project.pokelytics.models.LoginCredentials
import study.project.pokelytics.models.User

class ResetPasswordUseCase(
    private val fAuth: FirebaseAuth
) : FlowUseCase<User, LoginCredentials>() { //Unit, LoginCredentials(mail, pass)

    override suspend fun execute(params: LoginCredentials): Flow<User> = flow {

        val email = params.email

        fAuth.sendPasswordResetEmail(email).addOnCompleteListener{
            if (!it.isSuccessful) {
                throw(Exception(""))
            }
        }
        //donde esta el emit tengo que recoger el usuario de bd el usuario entero
        //añado en Models>user los datos que recoja :)

        //emit(User("añskdñ", "dñkxn"))
    }
}