package com.cranaya.mercadolibrechallenge.core.base

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.cranaya.mercadolibrechallenge.core.helpers.interfaces.IErrorUser
import com.cranaya.mercadolibrechallenge.core.helpers.LoadingHelper
import com.cranaya.mercadolibrechallenge.core.helpers.hideKeyboard
import com.cranaya.mercadolibrechallenge.core.helpers.showKeyboard
import es.dmoral.toasty.Toasty

/**
 * Generic class for [Fragment]
 *
 * @author Cristian Anaya
 */
abstract class BaseFragment<B: ViewDataBinding> : Fragment(), IErrorUser {
    lateinit var binding: B
    private var loading: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun setupView()

    fun showOrHideKeyboard(show: Boolean) {
        with(requireActivity()) {
            if(show)
                showKeyboard()
            else
                hideKeyboard()
        }
    }

    /**
     * Function that allows to show or hide the load on the screen
     */
    fun showOrHideLoading(isLoading: Boolean) {
        LoadingHelper.showOrHideLoading(requireContext(), isLoading)
    }

    /**
     * Show the errors generated during the actions made by the user
     */
    override fun showError(errorMessage: String) {
        if(errorMessage.isNotEmpty()) {
            Toasty.error(requireContext(), errorMessage).show()
        }
    }

    /**
     * Change the color of the status bar icons to black
     * to be able to contrast with the yellow color of the app
     */
    fun changeStatusBarIconColors() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.setDecorFitsSystemWindows(false)
        }
    }


}