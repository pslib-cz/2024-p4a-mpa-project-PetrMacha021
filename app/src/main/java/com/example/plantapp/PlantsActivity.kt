package com.example.plantapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plantapp.database.plant.Plant
import com.example.plantapp.database.plant.PlantRepository
import com.example.plantapp.database.plant.PlantViewModel
import com.example.plantapp.ui.theme.PlantAppTheme

class PlantsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val repository = PlantRepository(MyApp.database.plantDao())
            val viewModel = PlantViewModel(repository)
            PlantAppTheme {
                Scaffold { innerPadding ->
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        items(viewModel.plants.value.size) { index ->
                            PlantItem(viewModel.plants.value[index])
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlantItem(plant: Plant) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Row {
            Text(plant.name, modifier = Modifier.weight(1f))
        }
    }
}