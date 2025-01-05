package com.example.plantapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.plantapp.database.plant.Plant
import com.example.plantapp.database.plant.PlantRepository
import com.example.plantapp.database.plant.PlantViewModel
import com.example.plantapp.ui.theme.PlantAppTheme

class PlantsActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val repository = PlantRepository(MyApp.database.plantDao())
            val viewModel = PlantViewModel(repository)
            PlantAppTheme {
                PlantScreen(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantScreen(viewModel: PlantViewModel) {
    val showAddPlantDialog = remember { mutableStateOf(false) }
    var sortType by remember { mutableStateOf(SortType.NAME) } // Default sorting by name
    var sortOrder by remember { mutableStateOf(SortOrder.DESCENDING) } // Default ascending order
    var showSortTypeDropdown by remember { mutableStateOf(false) } // State for sorting criteria dropdown
    var showSortOrderDropdown by remember { mutableStateOf(false) } // State for sorting order dropdown

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "List of plants") },
                actions = {
                    Box {
                        IconButton(onClick = { showSortTypeDropdown = true }) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = "Sort Order"
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = showSortTypeDropdown,
                            onDismissRequest = { showSortTypeDropdown = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    sortType = SortType.NAME
                                    showSortTypeDropdown = false
                                },
                                text = { Text("Sort by Name") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    sortType = SortType.SOWING_DATE
                                    showSortTypeDropdown = false
                                },
                                text = { Text("Sort by Sowing Date") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    sortType = SortType.GROWING_TIME
                                    showSortTypeDropdown = false
                                },
                                text = { Text("Sort by Growing Time") }
                            )
                        }
                    }

                    Box {
                        IconButton(onClick = { showSortOrderDropdown = true }) {
                            Icon(
                                imageVector = Icons.Default.SortByAlpha,
                                contentDescription = "Sort Order"
                            )
                        }

                        DropdownMenu(
                            expanded = showSortOrderDropdown,
                            onDismissRequest = { showSortOrderDropdown = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    sortOrder = SortOrder.ASCENDING
                                    showSortOrderDropdown = false
                                },
                                text = { Text("Ascending") }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    sortOrder = SortOrder.DESCENDING
                                    showSortOrderDropdown = false
                                },
                                text = { Text("Descending") }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddPlantDialog.value = true
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Item")
            }
        }
    ) { innerPadding ->
        // Sorting logic based on sortType and sortOrder
        val sortedPlants = when (sortType) {
            SortType.NAME -> {
                if (sortOrder == SortOrder.ASCENDING) {
                    viewModel.plants.value.sortedBy { it.name }
                } else {
                    viewModel.plants.value.sortedByDescending { it.name }
                }
            }
            SortType.SOWING_DATE -> {
                if (sortOrder == SortOrder.ASCENDING) {
                    viewModel.plants.value.sortedBy { it.sowingDate }
                } else {
                    viewModel.plants.value.sortedByDescending { it.sowingDate }
                }
            }
            SortType.GROWING_TIME -> {
                if (sortOrder == SortOrder.ASCENDING) {
                    viewModel.plants.value.sortedBy { it.sowingDate + it.growingTime }
                } else {
                    viewModel.plants.value.sortedByDescending { it.sowingDate + it.growingTime }
                }
            }
        }

        // Display the sorted plants
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp, 4.dp)
        ) {
            items(sortedPlants.size) { index ->
                PlantItem(sortedPlants[index])
            }
        }

        // Add Plant Dialog
        if (showAddPlantDialog.value) {
            AddPlantDialog(
                onDismiss = { showAddPlantDialog.value = false },
                onAddPlant = { item ->
                    viewModel.addPlant(item)
                }
            )
        }
    }
}

@Composable
fun PlantItem(plant: Plant) {
    val remainingDays = (plant.sowingDate + plant.growingTime - System.currentTimeMillis()) / (24 * 60 * 60 * 1000L)
    val remainingDaysText = if (remainingDays < 0) {
        "Grown"
    } else {
        "${remainingDays} days"
    }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = plant.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Sowing date: ${convertMillisToDate(plant.sowingDate)}")
            Text(text = "Remaining time: $remainingDaysText")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlantDialog(
    onDismiss: () -> Unit,
    onAddPlant: (Plant) -> Unit
) {
    var plantName by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var growingDays by remember { mutableStateOf("") }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        selectedDate = it
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add plant") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = plantName,
                    onValueChange = {
                        plantName = it
                        isError = false
                    },
                    label = { Text("Name") },
                    isError = isError,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = selectedDate.let { convertMillisToDate(it) },
                    onValueChange = { },
                    label = { Text("Sowing date") },
                    placeholder = { Text("MM/DD/YYYY") },
                    trailingIcon = {
                        Icon(Icons.Default.DateRange, contentDescription = "Select date")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(selectedDate) {
                            awaitEachGesture {
                                awaitFirstDown(pass = PointerEventPass.Initial)
                                val upEvent =
                                    waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                if (upEvent != null) {
                                    showDatePicker = true
                                }
                            }
                        }
                )

                OutlinedTextField(
                    value = growingDays,
                    onValueChange = {
                        growingDays = it
                        isError = false
                    },
                    label = { Text("Growing time (days)") },
                    isError = isError,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (showDatePicker) {
                    DatePickerModal(
                        onDateSelected = {
                            if (it != null) {
                                selectedDate = it
                            }
                        },
                        onDismiss = { showDatePicker = false }
                    )
                }

                if (isError) {
                    Text(
                        text = "Fill in all the fields",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (plantName.isNotBlank()) {
                        val days = growingDays.toIntOrNull() ?: 0
                        val growingTime = days * 24 * 60 * 60 * 1000L
                        onAddPlant(
                            Plant(
                                name = plantName,
                                sowingDate = selectedDate,
                                growingTime = growingTime
                            )
                        )
                        onDismiss()
                    } else {
                        isError = true
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}