package com.example.remidipam.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.remidipam.R

@Composable
fun DeleteOptionDialog(
    categoryName: String,
    onDismiss: () -> Unit,
    onDelete: (deleteBooks: Boolean) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.dialog_delete_title)) },
        text = {
            Column {
                Text("Kategori '$categoryName' mungkin memiliki buku.")
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(R.string.dialog_delete_message))
            }
        },
        confirmButton = {
            // Opsi 1: Hapus Kategori + Hapus Buku
            Button(
                onClick = { onDelete(true) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(stringResource(R.string.option_delete_all))
            }
        },
        dismissButton = {
            // Opsi 2: Hapus Kategori Saja (Buku jadi "Tanpa Kategori")
            TextButton(onClick = { onDelete(false) }) {
                Text(stringResource(R.string.option_uncategorize))
            }
        }
    )
}