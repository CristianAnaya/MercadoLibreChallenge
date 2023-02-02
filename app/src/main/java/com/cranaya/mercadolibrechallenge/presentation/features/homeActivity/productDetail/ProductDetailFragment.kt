package com.cranaya.mercadolibrechallenge.presentation.features.homeActivity.productDetail


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import androidx.viewpager.widget.ViewPager
import com.cranaya.domain.model.Product
import com.cranaya.mercadolibrechallenge.R
import com.cranaya.mercadolibrechallenge.core.Constants.ARGUMENT_PRODUCT_ID
import com.cranaya.mercadolibrechallenge.core.base.BaseFragment
import com.cranaya.mercadolibrechallenge.databinding.FragmentProductDetailBinding
import com.cranaya.mercadolibrechallenge.presentation.adapters.ImagesSliderAdapter
import com.cranaya.domain.model.Product.Picture
import com.cranaya.domain.model.Product.Attribute
import com.cranaya.mercadolibrechallenge.presentation.adapters.ProductAttributesAdapter

import dagger.hilt.android.AndroidEntryPoint

/**
 * [BaseFragment] implementation to display the detail of a product
 *
 * @author Cristian Anaya
 */
@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {
    private val viewModel: ProductDetailViewModel by viewModels()
    private lateinit var productAttributesAdapter: ProductAttributesAdapter

    override fun getLayoutId(): Int = R.layout.fragment_product_detail

    override fun setupView() {
        setSearchTransitionOnEnter()
        setupProductAttributesAdapter()
        setupObservers()
        getProductDetail()

        with(binding.toolbar) {
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            isLoading.observe(viewLifecycleOwner) { isLoading ->
                showOrHideLoading(isLoading)
            }
            productModel.observe(viewLifecycleOwner) { product ->
                setProductInfo(product!!)
            }
            error.observe(viewLifecycleOwner) { errorMessage ->
                showError(errorMessage)
            }
        }
    }

    private fun getProductDetail() {
        viewModel.getProductDetailById(getProductId())
    }

    private fun getProductId(): String {
        return arguments?.getString(ARGUMENT_PRODUCT_ID, "") ?: ""
    }


    private fun setProductInfo(product: Product) {
        with(binding) {
            title.text = product.title
            conditionAndSoldAmount.text = resources.getQuantityString(R.plurals.condition_and_sold_amount, product.soldQuantity!!, product.condition, product.soldQuantity)
            price.text = getString(R.string.price, "$", product.price.toInt())
            stockTitle.text = getString(R.string.available_stock)
            titleAttributes.text = getString(R.string.title_attributes)
            stockCount.text = resources.getQuantityString(R.plurals.stock_count, product.availableQuantity!!, product.availableQuantity)
            setupSlider(product.pictures ?: emptyList())
            showAttributes(product.attributes)
        }
    }

    private fun setupProductAttributesAdapter() {
        productAttributesAdapter = ProductAttributesAdapter()
        binding.productDetailAttributes.apply {
            adapter = productAttributesAdapter
        }
    }

    private fun showAttributes(attributes: List<Attribute>) {
        productAttributesAdapter.submitList(attributes)
    }

    private fun setupSlider(pictures: List<Picture>) {
        with(binding) {
            imageSlider.adapter = ImagesSliderAdapter(requireContext(), pictures)
            imageSlider.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    imageAmountAndIndex.text = getString(R.string.image_amount_and_index, position+1, pictures.size)
                }
                override fun onPageSelected(position: Int) {
                }
            })
            imageAmountAndIndex.text = getString(R.string.image_amount_and_index, 1, pictures.size)
        }
    }

    private fun setSearchTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.search_transition)
    }

    private fun removeObservers() {
        with(viewModel) {
            productModel.removeObservers(viewLifecycleOwner)
            isLoading.removeObservers(viewLifecycleOwner)
            error.removeObservers(viewLifecycleOwner)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
    }
}