package com.nmarchelli.desafiotecnico.ui.view

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.nmarchelli.desafiotecnico.data.model.UserModel
import com.nmarchelli.desafiotecnico.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

const val selectedUser = "selected_user"
const val userList = "user_list"
const val userDetail = "user_detail"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Scaffold { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    NavHost(navController = navController, startDestination = userList) {
                        composable(userList) {
                            UserListScreen(navController)
                        }
                        composable(userDetail) {
                            UserDetailScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserListScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val users by userViewModel.users.collectAsState(initial = emptyList())
    val page by userViewModel.page.collectAsState(initial = 1)

    LaunchedEffect(Unit) {
        userViewModel.getUsers(1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(users) { user ->
                UserItem(user) {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        selectedUser,
                        user
                    )
                    navController.navigate(userDetail)
                }
            }
        }
        Box(contentAlignment = Alignment.Center) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { userViewModel.goToPreviousPage() },
                    enabled = page > 1
                ) {
                    Text(text = "Anterior")
                }
                Text(text = "Página $page", textAlign = TextAlign.Center)
                Button(
                    onClick = { userViewModel.goToNextPage() }
                ) {
                    Text(text = "Siguiente")
                }
            }
        }
    }
}

@Composable
fun UserDetailScreen(navController: NavController) {
    val user = navController.previousBackStackEntry?.savedStateHandle?.get<UserModel>(selectedUser)

    if (user == null) {
        //show error
        return
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Detalle de usuario", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Nombre completo:")
        Text(text = user.name.getFullName(), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Dirección completa:")
        Text(text = user.location.getFullAddress(), fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Fecha de nacimiento:")
        Text(text = (user.dob.date).take(10), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Teléfono:")
        Text(text = user.phone, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Image(
            painter = rememberAsyncImagePainter(user.picture.large),
            modifier = Modifier.size(200.dp),
            contentDescription = "profile photo"
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Volver")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

        }
    }

}

@Composable
fun UserItem(user: UserModel, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
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