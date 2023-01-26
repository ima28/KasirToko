package com.example.kasirtoko.repositories

import android.se.omapi.Session
import com.example.kasirtoko.models.Admin
import com.example.kasirtoko.reponses.ErrorResponse
import com.example.kasirtoko.requests.CreateItemTransactionRequest
import com.example.kasirtoko.requests.CreateProductRequest
import com.example.kasirtoko.requests.CreateTransactionRequest
import com.example.kasirtoko.requests.UpdateProductRequest
import com.example.kasirtoko.routes.SalesRoute
import com.example.kasirtoko.utils.safeApiCall
import com.example.kasirtoko.vo.ResultWrapper
import javax.inject.Inject




@Suppress("UNREACHABLE_CODE")
class SalesRepository @Inject constructor(
    private val salesRoute: SalesRoute,
    private val session: Session
) {

    suspend fun login(email: String, password: String): ResultWrapper<Admin> = safeApiCall {
        val response = salesRoute.login(email, password)
        var message = "Unknown Error"
        if (response.isSuccessful) {
            val responseBody = response.body()

            message = responseBody?.message ?: message
            val loginData = responseBody?.data ?: return@safeApiCall ResultWrapper.Error(data = responseBody?.data?.admin, message = message)

            val admin = loginData.admin ?: return@safeApiCall ResultWrapper.androidx.compose.foundation.layout.Box {
                val error = Error(
                    message, responseBody.data.admin
                )
                error
            }
            loginData.token?.let { session.saveUser(admin as Nothing, it) }

            message = responseBody.message ?: "Login Success"
            ResultWrapper.Success(data = session.getAuthUser(), message = message)
        } else {
            val errorBody = ErrorResponse.fromErrorBody(response.errorBody())
            ResultWrapper.Error(data = response.body()?.data?.admin, message = errorBody.message ?: message)
        }
    }

    private fun Error(message: String, cause: Admin?): Error {
        TODO("Not yet implemented")
    }

    suspend fun getProducts() = safeApiCall {
        val response = salesRoute.getProducts()
        var message = "Unknown Error"
        if (response.isSuccessful) {
            val responseBody = response.body()

            val products = responseBody?.data ?: emptyList()

            message = responseBody?.message ?: "Get Products Success"
            ResultWrapper.Success(data = products, message = message)
        } else {
            val errorBody = ErrorResponse.fromErrorBody(response.errorBody())
            ResultWrapper.Error(data = response.body()?.data, message = errorBody.message ?: message)
        }
    }

    suspend fun deleteProduct(productId: Int) = safeApiCall {
        val response = salesRoute.deleteProduct(productId)
        var message = "Unknown Error"
        if (response.isSuccessful) {
            val responseBody = response.body()

            val products = responseBody?.data ?: emptyList()

            message = responseBody?.message ?: "Delete Products Success"
            ResultWrapper.Success(data = products, message = message)
        } else {
            val errorBody = ErrorResponse.fromErrorBody(response.errorBody())
            ResultWrapper.Error(data = response.body()?.data, message = errorBody.message ?: message)
        }
    }

    suspend fun createProduct(request: CreateProductRequest) = safeApiCall {
        request.adminId = session.getAuthUser().id
        val response = salesRoute.createProduct(request)
        var message = "Unknown Error"
        if (response.isSuccessful) {
            val responseBody = response.body()

            message = responseBody?.message ?: message
            val product = responseBody?.data ?: return@safeApiCall ResultWrapper.Error(data = responseBody?.data, message = message)

            message = responseBody.message ?: "Create Product Success"
            ResultWrapper.Success(data = product, message = message)
        } else {
            val errorBody = ErrorResponse.fromErrorBody(response.errorBody())
            ResultWrapper.Error(data = response.body()?.data, message = errorBody.message ?: message)
        }
    }

    suspend fun updateProduct(productId: Int?, request: UpdateProductRequest) = safeApiCall {
        request.adminId = session.getAuthUser().id
        val response = salesRoute.updateProduct(productId, request)
        var message = "Unknown Error"
        if (response.isSuccessful) {
            val responseBody = response.body()

            message = responseBody?.message ?: message
            val product = responseBody?.data ?: return@safeApiCall ResultWrapper.Error(data = responseBody?.data, message = message)

            message = responseBody.message ?: "Update Product Success"
            ResultWrapper.Success(data = product, message = message)
        } else {
            val errorBody = ErrorResponse.fromErrorBody(response.errorBody())
            ResultWrapper.Error(data = response.body()?.data, message = errorBody.message ?: message)
        }
    }

    suspend fun payTransaction(request: CreateTransactionRequest) = safeApiCall {
        request.adminId = session.getAuthUser().id
        val response = salesRoute.createTransaction(request)
        var message = "Unknown Error"
        if (response.isSuccessful) {
            val responseBody = response.body()

            message = responseBody?.message ?: message
            val transaction = responseBody?.data ?: return@safeApiCall ResultWrapper.Error(data = responseBody?.data, message = message)

            message = responseBody.message ?: "Pay Transaction Success"
            ResultWrapper.Success(data = transaction, message = message)
        } else {
            val errorBody = ErrorResponse.fromErrorBody(response.errorBody())
            ResultWrapper.Error(data = response.body()?.data, message = errorBody.message ?: message)
        }
    }

    suspend fun createTransactionItem(request: CreateItemTransactionRequest) = safeApiCall {
        val response = salesRoute.createTransactionItem(request)
        var message = "Unknown Error"
        if (response.isSuccessful) {
            val responseBody = response.body()

            message = responseBody?.message ?: message
            val transactionItem = responseBody?.data ?: return@safeApiCall ResultWrapper.Error(data = responseBody?.data, message = message)

            message = responseBody.message ?: "Create Transaction Success"
            ResultWrapper.Success(data = transactionItem, message = message)
        } else {
            val errorBody = ErrorResponse.fromErrorBody(response.errorBody())
            ResultWrapper.Error(data = response.body()?.data, message = errorBody.message ?: message)
        }
    }
}

private fun Any.Box(function: () -> Error): ResultWrapper<Admin> {
    TODO("Not yet implemented")
}


private fun Session.getAuthUser(): Admin {
    TODO("Not yet implemented")
}

private fun Session.saveUser(admin: Nothing, token: Any) {
    TODO("Not yet implemented")
}

private fun Any.body(): Any {
    TODO("Not yet implemented")
}

private val Any.layout: Any
    get() {
        TODO("Not yet implemented")
    }
private val Any.foundation: Any
    get() {
        TODO("Not yet implemented")
    }
private val Any.compose: Any
    get() {
        TODO("Not yet implemented")
    }
private val ResultWrapper.Companion.androidx: Any
    get() {
        TODO("Not yet implemented")
    }
private val Nothing.token: Any
    get() {
        TODO("Not yet implemented")
    }
private val Nothing.admin: Nothing?
    get() {
        TODO("Not yet implemented")
    }
private val Any.data: Nothing?
    get() {
        TODO("Not yet implemented")
    }
private val Any.message: String?
    get() {
        TODO("Not yet implemented")
    }
private val Any.isSuccessful: Boolean
    get() {
        TODO("Not yet implemented")
    }