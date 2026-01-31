package com.example.remidipam.ui.screens.category

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.remidipam.R
import com.example.remidipam.entity.Category
import com.example.remidipam.ui.components.DeleteOptionDialog
import com.example.remidipam.ui.viewmodel.LibraryUiState
import com.example.remidipam.ui.viewmodel.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    viewModel: LibraryViewModel = hiltViewModel() // Inject VM otomatis
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // State untuk Dialog Hapus
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedCategoryForDelete by remember { mutableStateOf<Category?>(null) }

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is LibraryUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }
            is LibraryUiState.Success -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Manajemen Buku & Kategori") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Navigasi ke Tambah Kategori */ }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah")
            }
        }
    ) { padding ->

        Box(modifier = Modifier.padding(padding)) {

            val dummyCategories = listOf(
                Category(1, "Teknologi", null),
                Category(2, "Pemrograman", 1), // Anak dari Teknologi
                Category(3, "Fiksi", null)
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(dummyCategories) { category ->
                    CategoryItem(
                        category = category,
                        onDeleteClick = {
                            selectedCategoryForDelete = category
                            showDeleteDialog = true
                        }
                    )
                }
            }

            // Tampilkan Loading jika sedang proses
            if (uiState is LibraryUiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

    if (showDeleteDialog && selectedCategoryForDelete != null) {
        DeleteOptionDialog (
            categoryName = selectedCategoryForDelete!!.name,
            onDismiss = { showDeleteDialog = false },
            onDelete = { deleteBooks ->
                // Panggil ViewModel untuk hapus aman
                viewModel.deleteCategory(selectedCategoryForDelete!!.id, deleteBooks)
                showDeleteDialog = false
            }
        )
    }
}

@Composable
fun CategoryItem(category: Category, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = category.name, style = MaterialTheme.typography.titleMedium)
                if (category.parentId != null) {
                    Text(
                        text = "Sub-kategori",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}