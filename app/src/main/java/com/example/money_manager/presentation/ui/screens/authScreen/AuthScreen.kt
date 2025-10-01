package com.example.money_manager.presentation.ui.screens.authScreen

import android.annotation.SuppressLint
import android.graphics.drawable.shapes.Shape
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.money_manager.presentation.components.BeautifulButton
import com.example.money_manager.presentation.navigation.Screens
import com.example.money_manager.presentation.viewmodel.authviewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    googleSignInClient: GoogleSignInClient,
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn
            .getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { token ->
                viewModel.signInWithGoogle(token)
            }
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }

    LaunchedEffect(uiState.value.user) {
        if (uiState.value.user != null) {
            navController.navigate(Screens.Home.route)
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Авторизация",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.value.isLoading -> {
                    CircularProgressIndicator()
                }

                uiState.value.user != null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        uiState.value.user?.photoUrl.let { url ->
                            Image(
                                painter = rememberAsyncImagePainter(url),
                                contentDescription = "Profile picture",
                                modifier = Modifier.size(100.dp)
                            )
                        }

                        Text(text = "Имя: ${uiState.value.user?.displayName ?: "Не указано"}")
                        Text(text = "Email: ${uiState.value.user?.email ?: "Не указано"}")
                        Button(onClick = { viewModel.signOut() }) {
                            Text("Выйти")
                        }
                    }
                }

                else -> {
                    Column(Modifier.padding(16.dp)) {
                        BeautifulButton(text = "Войти с помощью Google",
                            onClick = {
                                val signInIntent = googleSignInClient.signInIntent
                                launcher.launch(signInIntent)
                            },
                            modifier = Modifier.width(300.dp)
                        )
                        Spacer(Modifier.height(16.dp))

                        BeautifulButton(
                            text = "Продолжить как гость",
                            onClick = {
                                viewModel.continueAsGuest()
                                navController.navigate(Screens.Home.route)
                            },
                            isPrimary = false,
                            modifier = Modifier.width(300.dp)
                        )
                    }
                }
            }
        }
    }
}