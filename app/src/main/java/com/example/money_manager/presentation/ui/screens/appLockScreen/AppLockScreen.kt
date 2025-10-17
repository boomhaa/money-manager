package com.example.money_manager.presentation.ui.screens.appLockScreen

import androidx.compose.runtime.Composable
import com.example.money_manager.data.store.BiometricAuth
import com.example.money_manager.domain.model.AppLockEffect
import com.example.money_manager.presentation.components.NumberPad
import com.example.money_manager.presentation.components.PinDots
import com.example.money_manager.presentation.viewmodel.applockviewmodel.AppLockVewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import android.app.Activity
import android.content.Context
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.launch


@Composable
fun AppLockScreen(
    modifier: Modifier = Modifier,
    viewModel: AppLockVewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effects = viewModel.effects
    val context = LocalContext.current
    val haptics = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    val snackbar = remember { SnackbarHostState() }

    var shake by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (shake) 0.97f else 1f,
        animationSpec = tween(90),
        label = "shakeScale"
    )

    LaunchedEffect(effects) {
        effects.collect { e ->
            when (e) {
                is AppLockEffect.TriggerBiometric -> {
                    val activity = context.findActivityOrNull() ?: return@collect
                    BiometricAuth(context).prompt(
                        fragmentActivity = activity,
                        title = "Вход по биометрии",
                        onSuccess = { viewModel.onBiometricSuccess() },
                        onFallbackToPin = {
                            viewModel.onBiometricFallback()
                            scope.launch {
                                snackbar.showSnackbar("Отменено. Введите PIN")
                            }

                        },
                        onError = { err ->
                            viewModel.onBiometricError(err)
                            scope.launch {
                                snackbar.showSnackbar("Биометрия недоступна: $err")
                            }
                        }
                    )
                }

                is AppLockEffect.ErrorHaptic -> {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    shake = true
                    snapshotFlow { Unit }.collect { shake = false; return@collect }
                }

                is AppLockEffect.UnlockSuccess -> {
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.98f))
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        awaitPointerEvent()
                    }
                }
            }
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
                .scale(scale),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Разблокировка",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(12.dp))

                PinDots(
                    count = state.pinLength,
                    total = state.pinTotal
                )

                AnimatedVisibility(
                    visible = state.error != null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        state.error.orEmpty(),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }

                if (state.cooldownLeftSec > 0) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Повторите попытку через ${state.cooldownLeftSec} c.",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(Modifier.height(16.dp))

                NumberPad(
                    onDigit = { viewModel.onDigit(it) },
                    onBackspace = { viewModel.onBackspace() },
                    onLongBackspace = { viewModel.onClearAll() },
                    bottomLeftContent = {
                        if (state.canUseBiometrics) {
                            TextButton(onClick = { viewModel.onBiometricClick() }) {
                                Text("Биометрия")
                            }
                        }
                    }
                )
            }
        }

        Text(
            text = "Ваши данные защищены",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )

        SnackbarHost(
            hostState = snackbar,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
        )
    }
}


private fun Context.findActivityOrNull(): FragmentActivity? = when (this) {
    is FragmentActivity -> this
    is Activity -> null
    else -> null
}