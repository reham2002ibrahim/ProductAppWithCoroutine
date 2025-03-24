package com.example.retrofitandroomcoroutine.favoriteProduct

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.retrofitandroomcoroutine.dataLayer.local.ProductDatabase
import com.example.retrofitandroomcoroutine.dataLayer.local.ProductLocalDataSource
import com.example.retrofitandroomcoroutine.dataLayer.model.Product
import com.example.retrofitandroomcoroutine.dataLayer.remote.ProductRemoteDataSource
import com.example.retrofitandroomcoroutine.dataLayer.remote.RetrofitHelper
import com.example.retrofitandroomcoroutine.dataLayer.repo.Repository
import com.example.retrofitandroomcoroutine.favoriteProduct.FavoriteFactory
import com.example.retrofitandroomcoroutine.favoriteProduct.FavoriteViewModel

class FavoriteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val remoteDataSource = ProductRemoteDataSource(RetrofitHelper.apiService)
        val localDataSource = ProductLocalDataSource(ProductDatabase.getInstance(this).getProductDao())
        val repository = Repository.getInstance(remoteDataSource, localDataSource)
        val viewModel = ViewModelProvider(
            this,
            FavoriteFactory(repository)
        ).get(FavoriteViewModel::class.java)

        setContent {
            AllFavProducts(viewModel = viewModel)
        }
    }
}
@Composable
private fun AllFavProducts(viewModel: FavoriteViewModel) {
    viewModel.getFavoriteProducts()
    val favProductState = viewModel.product.observeAsState().value
    val messageState = viewModel.message.observeAsState().value
    when {
        favProductState == null -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        favProductState.isEmpty() -> {
            Text(text = "nod data founded", modifier = Modifier.padding(16.dp), color = Color.Red
            )
        }
        else -> {
            LazyColumn {
                items(favProductState) { product ->
                    FavItem(product = product, viewModel = viewModel)
                }
            }
        }
    }

}

/* @Composable
private fun AllFavProducts(viewModel: FavoriteViewModel) {
    viewModel.getFavoriteProducts()
    val favProductState = viewModel.product.observeAsState().value
    ProductList(favProductState ?: emptyList())


  //  val messageState = viewModel.message.observeAsState()

   if (favProductState.value.isNullOrEmpty()) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn {


            items(favProductState.value!!) { product ->
                FavItem(product = product, viewModel = viewModel)
            }

        }
    }*/

 /*   messageState.value?.let { msg ->
        Text( text = msg,
 modifier = Modifier     .padding(16.dp)    .background(Color.Yellow)
        )
    }
}*/

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FavItem(product: Product, viewModel: FavoriteViewModel) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
//            .background(Color.LightGray)
    ) {
        GlideImage(
            model = product.thumbnail,
            contentDescription = "Product Image",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = product.title ?: "No Title"
            )

            Button(
                onClick = {
                    viewModel.deleteFav(product)
                    Toast.makeText(
                        context,
                        "${product.title} deleted from fav",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier.align(Alignment.End)

            ) {
                Text("Remove from favorite")
            }
        }
    }
}
