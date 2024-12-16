package com.example.plantapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.plantapp.ui.theme.PlantAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlantAppTheme {
                PlantsRedirect()
            }
        }
    }
}

@Composable
fun PlantsRedirect() {
    val context = LocalContext.current

    Button(onClick = {
        context.startActivity(Intent(context, PlantsActivity::class.java))
    }) {
        Text(text = "Show List")
    }
}