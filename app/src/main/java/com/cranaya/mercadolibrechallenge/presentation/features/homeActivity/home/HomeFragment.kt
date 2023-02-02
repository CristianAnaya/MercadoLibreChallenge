package com.cranaya.mercadolibrechallenge.presentation.features.homeActivity.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cranaya.domain.model.Product
import com.cranaya.mercadolibrechallenge.R
import com.cranaya.mercadolibrechallenge.core.base.BaseFragment
import com.cranaya.mercadolibrechallenge.databinding.FragmentHomeBinding
import com.cranaya.mercadolibrechallenge.presentation.adapters.ProductListAdapter
import com.cranaya.mercadolibrechallenge.presentation.extention.navigate
import com.cranaya.mercadolibrechallenge.presentation.extention.setExitToFullScreenTransition
import com.cranaya.mercadolibrechallenge.presentation.extention.setReturnFromFullScreenTransition
import com.cranaya.mercadolibrechallenge.presentation.features.homeActivity.productSearch.ProductSearchFragment.Companion.PARAM_QUERY
import com.cranaya.mercadolibrechallenge.presentation.features.homeActivity.productSearch.ProductSearchFragment.Companion.REQUEST_KEY
import dagger.hilt.android.AndroidEntryPoint

/**
 * [Fragment] implementation to display the home screen
 *
 * @author Cristian Anaya
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var productListAdapter: ProductListAdapter

    override fun getLayoutId() = R.layout.fragment_home

    override fun setupView() {
        showOrHideKeyboard(false)
        initObservers()
        Log.d("HomeFragment", "setupView: ")
        setupProductListAdapter()
        
        setExitToFullScreenTransition()
        setReturnFromFullScreenTransition()

        binding.editTextSearch.setOnClickListener { goToSearchView() }
    }

    private fun initObservers() {

        with(viewModel) {
            isLoading.observe(viewLifecycleOwner) { isLoading ->
                showOrHideLoading(isLoading)
            }
            products.observe(viewLifecycleOwner) { products ->
                showResult(products)
            }
            error.observe(viewLifecycleOwner) { message ->
                showError(message)
            }
        }

        viewModel.searchText.observe(viewLifecycleOwner) {
            binding.editTextSearch.setText(it)
        }

        setFragmentResultListener(REQUEST_KEY) { _: String, bundle: Bundle ->
            val query = bundle.getString(PARAM_QUERY).orEmpty()
            viewModel.searchProductByName(query)
        }
    }

    private fun showResult(products: List<Product>) {
        if(products.isNotEmpty()) {
            productListAdapter.submitList(products)
        }
    }

    private fun goToSearchView() {
        val extraInfoForSharedElement = FragmentNavigatorExtras(
            binding.editTextSearch to binding.editTextSearch.transitionName
        )
        navigate(
            HomeFragmentDirections.actionToSearchFragment(binding.editTextSearch.text.toString()),
            extraInfoForSharedElement
        )
    }

    private fun setupProductListAdapter() {
        productListAdapter = ProductListAdapter(
            onProductClicked =  { productId ->
                goToProductDetail(productId)
            })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = productListAdapter

            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    private fun goToProductDetail(id: String) {
        val extraInfoForSharedElement = FragmentNavigatorExtras(
            binding.editTextSearch to binding.editTextSearch.transitionName
        )
        navigate(
            HomeFragmentDirections.actionDetailsFragment(id),
            extraInfoForSharedElement
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}