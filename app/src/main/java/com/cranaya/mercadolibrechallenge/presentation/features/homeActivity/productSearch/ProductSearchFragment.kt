package com.cranaya.mercadolibrechallenge.presentation.features.homeActivity.productSearch

import android.app.Activity
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.cranaya.domain.model.ProductSearchHistoryModel
import com.cranaya.mercadolibrechallenge.R
import com.cranaya.mercadolibrechallenge.core.base.BaseFragment
import com.cranaya.mercadolibrechallenge.databinding.FragmentProductSearchBinding
import com.cranaya.mercadolibrechallenge.presentation.adapters.ProductSearchHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * [BaseFragment] implementation for the products search screen
 *
 * @author Cristian Anaya
 */
@AndroidEntryPoint
class ProductSearchFragment : BaseFragment<FragmentProductSearchBinding>() {

    private val viewModel: ProductSearchViewModel by viewModels()
    private val args: ProductSearchFragmentArgs by navArgs()

    private lateinit var searchHistoryAdapter: ProductSearchHistoryAdapter

    override fun getLayoutId(): Int = R.layout.fragment_product_search

    override fun setupView() {
        Log.d("ProductSearchFragment", "setupView: ")
        requestInputFocus()
        setupSearchAdapter()
        setupObservers()
        getSearchHistory()
        searchProduct()

        binding.editTextQuery.setText(args.query)
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
            showOrHideKeyboard(false)
        }
    }

    private fun getSearchHistory() {
        viewModel.getSearchHistory()
    }

    private fun setupObservers() {
        with(viewModel) {
            error.observe(viewLifecycleOwner) { errorMessage ->
                showError(errorMessage)
            }
            productsSearchHistoryModel.observe(viewLifecycleOwner) { productsSearchHistory ->
                showResult(productsSearchHistory)
            }
            isValidSearch.observe(viewLifecycleOwner) { isValid ->
                doSearch(isValid)
            }
        }
    }

    private fun showResult(searchList: List<ProductSearchHistoryModel>) {
        searchHistoryAdapter.submitList(searchList)
    }

    private fun doSearch(isValid: Boolean) {
        if(isValid) {
            searchProduct(viewModel.searchText)
        }
    }
    private fun searchProduct(productName: String)  {
        viewModel.insertOrUpdateSearch(
            ProductSearchHistoryModel(
                title = productName,
                timestamp = System.currentTimeMillis()
            )
        )
        viewModel.resetValues()
        goToHomeScreen(productName)
    }

    private fun searchProduct() {
        binding.editTextQuery.setOnEditorActionListener { editText, actionId, _ ->
            return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                validateSearchText(editText.text.toString())
                true
            } else {
                false
            }
        }
    }

    private fun validateSearchText(searchText: String) {
        viewModel.validateSearchText(searchText)
    }

    private fun setupSearchAdapter() {
        with(binding) {
            searchHistoryAdapter = ProductSearchHistoryAdapter(
                onArrowClicked = { productName ->
                    editTextQuery.setText(productName.title)
                    requestInputFocus()
                },
                onHistoryClicked = { productName ->
                    viewModel.insertOrUpdateSearch(productName)
                    goToHomeScreen(productName.title)
                    showOrHideKeyboard(false)
                })
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = searchHistoryAdapter
            }
        }
    }

    private fun requestInputFocus() {
        binding.editTextQuery.requestFocus()
    }

    private fun goToHomeScreen(productName: String) {
        setFragmentResult(REQUEST_KEY, bundleOf(PARAM_QUERY to productName))
        findNavController().popBackStack()
    }

    private fun removeObservers() {
        with(viewModel) {
            productsSearchHistoryModel.removeObservers(viewLifecycleOwner)
            error.removeObservers(viewLifecycleOwner)
            isValidSearch.removeObservers(viewLifecycleOwner)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
    }

    companion object {
        const val REQUEST_KEY = "SearchFragmentResult"
        const val PARAM_QUERY = "query"
    }

}