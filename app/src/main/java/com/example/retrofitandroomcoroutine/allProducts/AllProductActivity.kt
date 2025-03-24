package com.example.retrofitandroomcoroutine.allProducts
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.retrofitandroomcoroutine.dataLayer.local.ProductLocalDataSource
import com.example.retrofitandroomcoroutine.dataLayer.local.ProductDatabase
import com.example.retrofitandroomcoroutine.dataLayer.remote.ProductRemoteDataSource
import com.example.retrofitandroomcoroutine.dataLayer.remote.RetrofitHelper
import com.example.retrofitandroomcoroutine.dataLayer.repo.Repository
import com.example.retrofitandroomcoroutine.dataLayer.model.Product
import com.example.retrofitandroomcoroutine.dataLayer.model.Response

class AllProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val remoteDataSource = ProductRemoteDataSource(RetrofitHelper.apiService)
        val localDataSource = ProductLocalDataSource(ProductDatabase.getInstance(this).getProductDao())
        val repository = Repository.getInstance(remoteDataSource, localDataSource)
        val viewModel = ViewModelProvider(
            this,
            AllProductsFactory(repository)
        ).get(AllProductsViewModel::class.java)

        setContent {
            AllProductsScreen(viewModel = viewModel)
        }
    }
}

@Composable
private fun AllProductsScreen(viewModel: AllProductsViewModel) {
    val productState by viewModel.products.collectAsStateWithLifecycle()
    val message by viewModel.message.collectAsStateWithLifecycle()

    Column {
        when (val state = productState) {
            is Response.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
            is Response.Success -> {
                LazyColumn {
                    items(state.data) { product -> ProductItem(product = product, viewModel = viewModel)
                    }
                }
            }
            is Response.Failure -> {
                Text(
                    text = "Error: ${state.error.message}", modifier = Modifier.padding(16.dp).background(Color.Red)
                )
            }
        }

        if (message.isNotEmpty()) {
            Text(
                text = message,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Yellow)
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductItem(product: Product, viewModel: AllProductsViewModel) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
    ) {
        GlideImage(
            model = product.thumbnail,
            contentDescription = "Product Image",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = product.title ?: "No Title")
            Button(
                onClick = {
                    viewModel.addToFavorite(product)
                    Toast.makeText(
                        context,
                        "${product.title} added to fav",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Add to favorite")
            }
        }
    }
}