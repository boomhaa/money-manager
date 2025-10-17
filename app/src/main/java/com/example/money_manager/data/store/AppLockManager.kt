package com.example.money_manager.data.store

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.money_manager.domain.repository.AppLockRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLockManager @Inject constructor(
    private val repository: AppLockRepository,
) : DefaultLifecycleObserver {
    private val _locked = MutableStateFlow(false)
    val locked: StateFlow<Boolean> = _locked.asStateFlow()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val timeoutState: StateFlow<Int> = repository.timeoutSec.stateIn(
        scope,
        SharingStarted.Eagerly, 0
    )
    private var lastBackgroundAt: Long = 0L

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        Log.d("AppLockManager", "Observer registered")
    }

    override fun onStop(owner: LifecycleOwner) {
        scope.launch {
            val isLockEnabled = repository.lockEnable.firstOrNull() ?: false
            if (!isLockEnabled) return@launch

            lastBackgroundAt = SystemClock.elapsedRealtime()
            if (timeoutState.value == 0) {
                _locked.value = true
            }
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        scope.launch {
            val isLockEnabled = repository.lockEnable.firstOrNull() ?: false
            if (isLockEnabled) {
                val timeoutSec = timeoutState.value
                val dtSec = secondsSinceBackground()

                if (timeoutSec == 0 || dtSec >= timeoutSec || lastBackgroundAt == 0L) {
                    _locked.value = true
                }
            }
        }
    }

    fun unlock() {
        _locked.value = false
    }

    private fun secondsSinceBackground(): Int =
        if (lastBackgroundAt == 0L) 0
        else ((SystemClock.elapsedRealtime() - lastBackgroundAt) / 1000).toInt()
}