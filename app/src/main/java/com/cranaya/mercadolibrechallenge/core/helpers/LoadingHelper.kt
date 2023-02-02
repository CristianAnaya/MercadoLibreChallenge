package com.cranaya.mercadolibrechallenge.core.helpers

import android.app.Dialog
import android.content.Context
import com.cranaya.mercadolibrechallenge.R

/**
 * Class in charge of managing loading throughout the app
 */
object LoadingHelper {
    private var loading: Dialog? = null

    /**
     * @param isLoading indicates if the loading should be displayed
     */
    fun showOrHideLoading(context: Context, isLoading: Boolean) {
        if(isLoading) {
            create(context)
            show()
        } else {
            hide()
        }
    }

    private fun create(context: Context) {
        loading = context.createDialogWithoutBackground(R.layout.loading_component)
    }

    private fun show() {
        loading?.show()
    }

    private fun hide() {
        loading?.dismiss()
    }

}