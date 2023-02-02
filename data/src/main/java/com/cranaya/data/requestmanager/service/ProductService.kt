package com.cranaya.data.requestmanager.service

import com.cranaya.data.requestmanager.dto.ProductDto
import com.cranaya.data.requestmanager.dto.ProductResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Endpoint Search Product
const val URL_GET_ITEMS_FROM_TO_SEARCH_QUERY = "sites/MCO/search"
// Endpoint get product detail
const val URL_GET_PRODUCT_DETAIL_BY_ID = "items/{itemId}"

/**
 * Interface to access the Products REST API using Retrofit
 *
 * @author Cristian Anaya
 */
interface ProductService {

    /**
     * Gets the list of products available with the entered name
     * @param query product name to search
     */
    @GET(URL_GET_ITEMS_FROM_TO_SEARCH_QUERY)
    suspend fun searchProducts(
        @Query("q") query: String
    ): Response<ProductResponseDto?>?
            //Response<SearchResponse>

    /**
     * Get the product detail
     * @param itemId product id
     */
    @GET(URL_GET_PRODUCT_DETAIL_BY_ID)
    suspend fun getProductDetail(
        @Path("itemId") itemId: String
    ): Response<ProductDto?>?

}