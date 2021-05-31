package com.ccakir.base.extensions

import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController


fun Fragment.showToast(message: String) {
    Toast.makeText(
        requireContext(),
        message,
        Toast.LENGTH_SHORT
    ).show()
}

fun Fragment.navigateTo(direction: NavDirections) {
    findNavController().navigate(direction)
}

fun Fragment.navigateTo(uri: Uri, clearBackstack: Boolean = false) {
    val navOptions = NavOptions.Builder().setPopUpTo(
        findNavController().graph.startDestination, true
    ).build()

    if (clearBackstack)
        findNavController().navigate(uri, navOptions)
    else
        findNavController().navigate(uri)
}