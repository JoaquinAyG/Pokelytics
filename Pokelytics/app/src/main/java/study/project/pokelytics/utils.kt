package study.project.pokelytics

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.io.Serializable
import java.util.Locale

fun ImageView.setImageToUrl(url: String, placeholder: Int = R.drawable.ic_pokeball) {
    if (url.isNotBlank()) {
        Picasso.get()
            .load(url)
            .placeholder(placeholder)
            .into(this)
    } else {
        Picasso.get()
            .load(placeholder)
            .into(this)
    }
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}

fun String.capitalized(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}
