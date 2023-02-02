package com.cranaya.mercadolibrechallenge.presentation.extention

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.cranaya.mercadolibrechallenge.R


fun Fragment.navigate(destination: NavDirections, extraInfo: FragmentNavigator.Extras) = with(findNavController()) {
    currentDestination?.getAction(destination.actionId)
        ?.let {
            navigate(destination, extraInfo)
        }
}

fun Fragment.setExitToFullScreenTransition() {
    exitTransition =
        TransitionInflater.from(requireContext()).inflateTransition(R.transition.list_exit_transition)
}

fun Fragment.setReturnFromFullScreenTransition() {
    reenterTransition =
        TransitionInflater.from(requireContext()).inflateTransition(R.transition.list_return_transition)
}
