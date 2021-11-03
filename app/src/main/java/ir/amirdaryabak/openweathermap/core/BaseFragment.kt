package ir.amirdaryabak.openweathermap.core

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ir.amirdaryabak.openweathermap.R

abstract class BaseFragment(layoutId: Int): Fragment(layoutId) {

    fun showSnackbar(
        text: String = "خطا در برقراری ارتباط",
        actionText: String = "تلاش مجدد",
        duration: Int = Snackbar.LENGTH_LONG,
        callFunction: () -> Unit = {}
    ) {
        Snackbar.make(
            requireView(), text,
            duration
        ).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            .setAction(actionText) {
                callFunction.invoke()
            }.apply {
                setActionTextColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
            }
            .show()
    }
}