package com.cranaya.mercadolibrechallenge.presentation.features.homeActivity

import com.cranaya.mercadolibrechallenge.R
import com.cranaya.mercadolibrechallenge.core.base.BaseActivity
import com.cranaya.mercadolibrechallenge.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * [BaseActivity] implementation to display the home screen
 *
 * @author Cristian Anaya
 */
@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun setupView() {
        changeStatusBarIconColors()
    }
}