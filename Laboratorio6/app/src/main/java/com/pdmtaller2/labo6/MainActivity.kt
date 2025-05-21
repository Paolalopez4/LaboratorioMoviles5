package com.pdmtaller2.labo6

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdmtaller2.labo6.ui.theme.Labo6Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Book(val title: String, val editorial: String)

class VMForBooks : ViewModel() {
    var books by mutableStateOf<List<Book>>(emptyList())
        private set

    var laoding by mutableStateOf(false)
        private set

    fun loadBooks(onLoaded: @Composable () -> Unit) {
        viewModelScope.launch {
            laoding = true
            delay(3000)
            books = listOf(
                Book("Libro 1", "Editora A"),
                Book("Libro 2", "Editora B"),
                Book("Libro 3", "Editora C"),
                Book("Libro 4", "Editora D"),
                Book("Libro 5", "Editora E"),
                )
            laoding = false
            onLoaded()
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Labo6Theme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: VMForBooks = VMForBooks()) {
    val scope = rememberCoroutineScope()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    viewModel.loadBooks {
                           
                    }
                },
                enabled = !viewModel.laoding
            ) {
                Text("Cargar libros")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (viewModel.laoding) {
                CircularProgressIndicator()
            } else {
                BookList(booksList = viewModel.books)
            }
        }
    }

@Composable
fun BookList(booksList: List<Book>) {
    LazyColumn {
        items(booksList.size) { book ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Título: ${booksList[book].title}")
                    Text(text = "Editor: ${booksList[book].editorial}")
                }
            }
        }
    }
}

