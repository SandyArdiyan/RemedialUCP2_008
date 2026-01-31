package com.example.remidipam.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remidipam.repository.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class LibraryUiState {
    object Idle : LibraryUiState()
    object Loading : LibraryUiState()
    data class Success(val message: String) : LibraryUiState()
    data class Error(val message: String) : LibraryUiState()
}

@HiltViewModel // Ini anotasi biar Hilt (Container) mengenali ViewModel ini
class LibraryViewModel @Inject constructor(
    private val repository: LibraryRepository
) : ViewModel() {

    // StateFlow untuk dipantau oleh UI (Activity/Compose)
    private val _uiState = MutableStateFlow<LibraryUiState>(LibraryUiState.Idle)
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()


    fun deleteCategory(categoryId: Int, deleteBooks: Boolean = false) {

        viewModelScope.launch {
            _uiState.value = LibraryUiState.Loading
            try {
                repository.deleteCategorySecurely(categoryId, deleteBooks)
                _uiState.value = LibraryUiState.Success("Kategori berhasil dihapus")
            } catch (e: Exception) {
                _uiState.value = LibraryUiState.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }


    fun resetState() {
        _uiState.value = LibraryUiState.Idle
    }
}