package com.example.retrofitandroomcoroutine

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retrofitandroomcoroutine.allProducts.AllProductActivity
import com.example.retrofitandroomcoroutine.favoriteProduct.FavoriteActivity
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
              HomeScreen()
        }
    }
}

@Preview
@Composable
private fun HomeScreen() {
    val context = LocalContext.current

    Column(

        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Button(onClick = {

            val intent = Intent(context, AllProductActivity::class.java).apply {
            }
            context.startActivity(intent)
        }) {
            Text(text = "Get all products")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val intent = Intent(context, FavoriteActivity::class.java).apply {
            }
            context.startActivity(intent)
        }) {
            Text(text = "Get favorite products")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {

            exitProcess(0)
        }) {
            Text(text = "Exit")
        }
    }
}
