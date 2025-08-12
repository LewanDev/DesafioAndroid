package com.nmarchelli.desafiotecnico.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmarchelli.desafiotecnico.data.model.UserModel
import com.nmarchelli.desafiotecnico.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import coil.compose.rememberAsyncImagePainter
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Content()
        }
    }
}

@Composable
fun Content(userViewModel: UserViewModel = hiltViewModel()) {
    val users by userViewModel.users.collectAsState(initial = emptyList())
    val page by userViewModel.page.collectAsState(initial = 1)

    LaunchedEffect(Unit) {
        userViewModel.getUsers(1)
    }

    Column {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(users) { user ->
                UserItem(user)
            }
        }
        Text("Página número $page")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { userViewModel.goToPreviousPage() },
                enabled = page > 1
            ) {
                Text("Anterior")
            }
            Button(
                onClick = { userViewModel.goToNextPage() }
            ) {
                Text("Siguiente")
            }
        }
    }
}

@Composable
fun UserItem(user: UserModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = rememberAsyncImagePainter(user.picture.thumbnail),
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(user.name.getFullName(), fontWeight = FontWeight.Bold)
            Text(user.location.country)
            Text(user.email)
            Text("Edad: ${user.dob.age}")
        }
    }
}