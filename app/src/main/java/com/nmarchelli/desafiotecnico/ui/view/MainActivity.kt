package com.nmarchelli.desafiotecnico.ui.view

import android.os.Bundle
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.nmarchelli.desafiotecnico.data.model.UserModel
import com.nmarchelli.desafiotecnico.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

const val selectedUser = "selected_user"
const val userList = "user_list"
const val userDetail = "user_detail"
val allNationalities = listOf(
    "AU" to "Australian",
    "BR" to "Brazilian",
    "CA" to "Canadian",
    "CH" to "Swiss",
    "DE" to "German",
    "DK" to "Danish",
    "ES" to "Spanish",
    "FI" to "Finnish",
    "FR" to "French",
    "GB" to "British",
    "IE" to "Irish",
    "IN" to "Indian",
    "IR" to "Iranian",
    "MX" to "Mexican",
    "NO" to "Norwegian",
    "NL" to "Dutch",
    "NZ" to "New Zealand",
    "RS" to "Serbian",
    "TR" to "Turkish",
    "UA" to "Ukrainian",
    "US" to "American"
)

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
                            UserDetailScreen(
                                navController = navController,
                            )
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
    val selectedNats by userViewModel.selectedNationalities.collectAsState()
    val favorites by userViewModel.favorites.collectAsState()

    var showNationalitiesDialog by remember { mutableStateOf(false) }
    var showFavoritesDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        userViewModel.getUsers(1)
    }
    if (showNationalitiesDialog) {
        NationalityDialog(
            nationalities = allNationalities,
            selectedNats = selectedNats,
            onDismissRequest = { showNationalitiesDialog = false }
        )
    }

    if(showFavoritesDialog){
        FavoritesDialog(
            list = favorites,
            onDismissRequest = { showFavoritesDialog = false}
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { showNationalitiesDialog = true }) {
                Text("Filtrar Nacionalidades")
            }
            Button(onClick = { showFavoritesDialog = true }) {
                Text("Ver Favoritos")
            }
        }
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(users) { user ->
                UserItem(
                    user = user,
                    isFavorite = favorites.contains(user.email),
                    onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            selectedUser,
                            user
                        )
                        navController.navigate(userDetail)
                    },
                    onFavoriteClick = { userViewModel.toggleFavorite(user) }
                )
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
fun UserDetailScreen(
    navController: NavController,
) {
    val user = navController.previousBackStackEntry?.savedStateHandle?.get<UserModel>(selectedUser)

    if (user == null) {
        //show error
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Detalle de usuario", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Nombre completo:")
        Text(text = user.name.getFullName(), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Dirección completa:")
        Text(
            text = user.location.getFullAddress(),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
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
    }

}

@Composable
fun UserItem(
    user: UserModel,
    onClick: () -> Unit,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
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
        Spacer(Modifier.width(8.dp))
        IconButton(onClick = { onFavoriteClick() }) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Agregar a favoritos"
            )
        }
    }
}

@Composable
fun FavoritesDialog(
    list: Set<String>,
    userViewModel: UserViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit,
){

}

@Composable
fun NationalityDialog(
    nationalities: List<Pair<String, String>>,
    selectedNats: List<String>,
    onDismissRequest: () -> Unit,
    userViewModel: UserViewModel = hiltViewModel()
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Seleccione nacionalidad",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(nationalities) { nat ->
                        val (code, name) = nat
                        val checked = selectedNats.contains(code)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = checked,
                                onCheckedChange = {
                                    userViewModel.toggleNationality(code)
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = name)
                        }
                    }
                }
                TextButton(onClick = onDismissRequest) {
                    Text("Volver")
                }
            }
        }
    }
}
