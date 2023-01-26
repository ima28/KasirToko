package com.example.kasirtoko.routes

import com.example.kasirtoko.models.AuthData
import com.example.kasirtoko.models.Product
import com.example.kasirtoko.models.Transaction
import com.example.kasirtoko.models.TransactionItem
import com.example.kasirtoko.reponses.SalesResponse
import com.example.kasirtoko.requests.CreateItemTransactionRequest
import com.example.kasirtoko.requests.CreateProductRequest
import com.example.kasirtoko.requests.CreateTransactionRequest
import com.example.kasirtoko.requests.UpdateProductRequest
import retrofit2.Response
import retrofit2.http.*

interface SalesRoute {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Response<SalesResponse<AuthData>>

    @POST("product")
    suspend fun createProduct(
        @Body request: CreateProductRequest
    ): Response<SalesResponse<Product>>

    @POST("transaksi")
    suspend fun createTransaction(
        @Body request: CreateTransactionRequest
    ): Response<SalesResponse<Transaction>>

    @POST("transaksi/item")
    suspend fun createTransactionItem(
        @Body request: CreateItemTransactionRequest
    ): Response<SalesResponse<TransactionItem>>

    @PUT("product/{product_id}")
    suspend fun updateProduct(
        @Path("product_id") productId: Int?,
        @Body request: UpdateProductRequest
    ): Response<SalesResponse<Product>>

    @DELETE("product/{product_id}")
    suspend fun deleteProduct(
        @Path("product_id") productId: Int
    ): Response<SalesResponse<List<Product>>>

    @GET("product")
    suspend fun getProducts(): Response<SalesResponse<List<Product>>>

    @GET("transaksi")
    suspend fun getTransactions(): Response<SalesResponse<List<Transaction>>>

    @GET("transaksi/item")
    suspend fun getTransactionItems(): Response<SalesResponse<List<TransactionItem>>>

}