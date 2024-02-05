package ru.klauz42.yetanotheronlinestore.data

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.klauz42.yetanotheronlinestore.domain.models.ProductsRepository
import ru.klauz42.yetanotheronlinestore.domain.models.entities.FilterTag
import ru.klauz42.yetanotheronlinestore.domain.models.entities.Product
import ru.klauz42.yetanotheronlinestore.domain.models.entities.getString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockedProductsRepository @Inject constructor(
    private val context: Context
) : ProductsRepository {

    override fun getProductsFlow(): Flow<List<Product>> = flow {
        emit(getMockedData())
    }

    override fun getProducts(): List<Product> {
        return getMockedData()
    }

    override fun getFilteredByTagProducts(tag: FilterTag): List<Product> {
        val tagSting = tag.getString()
        return getMockedData().filter { product ->
            product.tags.contains(tagSting)
        }
    }

    override fun getProductById(id: String): Flow<Product?> = flow {
        emit(getMockedData().firstOrNull { it.id == id })
    }

    override fun getProductsById(idList: List<String>): Flow<List<Product>> = flow {
        val allProducts = getMockedData()

        val filteredProducts = allProducts.filter { product ->
            idList.contains(product.id)
        }

        emit(filteredProducts)
    }

    private fun readJsonFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }


    data class ItemsWrapper(
        val items: List<Product>
    )

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val type = Types.newParameterizedType(List::class.java, Product::class.java)
    private val jsonAdapter = moshi.adapter(ItemsWrapper::class.java)

    private fun getMockedData(): List<Product> {
        val json = readJsonFromAssets(context, "data_mock.json")
        return jsonAdapter.fromJson(json)?.items ?: listOf()
    }
}