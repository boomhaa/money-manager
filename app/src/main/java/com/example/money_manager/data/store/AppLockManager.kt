package com.example.money_manager.data.store

import android.content.Context
import android.os.SystemClock
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.money_manager.domain.repository.AppLockRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AppLockManager @Inject constructor(
    private val repository: AppLockRepository,
    @ApplicationContext context: Context
) : DefaultLifecycleObserver {
    private val _locked = MutableStateFlow(false)
    val locked: StateFlow<Boolean> = _locked.asStateFlow()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val lockEnableState: StateFlow<Boolean> = repository.lockEnable.stateIn(
        scope,
        SharingStarted.Eagerly, false
    )

    private val timeoutState: StateFlow<Int> = repository.timeoutSec.stateIn(
        scope,
        SharingStarted.Eagerly, 0
    )

    private var lastBackgroundAt: Long = 0L

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStop(owner: LifecycleOwner) {
        if (!lockEnableState.value) return
        lastBackgroundAt = SystemClock.elapsedRealtime()

        if (timeoutState.value == 0) {
            _locked.value = true
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        if (!lockEnableState.value) return

        val timeoutSec = timeoutState.value

        val dtSec = ((SystemClock.elapsedRealtime() - lastBackgroundAt) / 1000).toInt()

        if (timeoutSec == 0 || dtSec >= timeoutSec) {
            _locked.value = true
        }
    }

    fun unlock() {
        _locked.value = false
    }
}