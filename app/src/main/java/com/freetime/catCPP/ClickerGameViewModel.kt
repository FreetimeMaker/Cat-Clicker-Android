package com.freetime.catCPP

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class GameState(
    val money: Long = 0,
    val currentCatIndex: Int = 0,
    val upgrades: List<Upgrade> = emptyList()
)

data class Upgrade(
    val id: Int,
    val name: String,
    val cost: Long,
    val level: Int
)

class ClickerGameViewModel : ViewModel() {

    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState

    init {
        // Initialize with some default upgrades
        _gameState.value = GameState(
            upgrades = listOf(
                Upgrade(0, "Catnip", 10, 0),
                Upgrade(1, "Yarn Ball", 100, 0),
                Upgrade(2, "Scratching Post", 500, 0),
                Upgrade(3, "Feather Toy", 2000, 0),
                Upgrade(4, "Laser Pointer", 10000, 0),
                Upgrade(5, "Cardboard Box", 50000, 0),
            )
        )
    }

    fun click() {
        viewModelScope.launch {
            _gameState.value = _gameState.value.copy(
                money = _gameState.value.money + 1
            )
        }
    }

    fun purchaseUpgrade(upgradeId: Int) {
        viewModelScope.launch {
            val upgrade = _gameState.value.upgrades.find { it.id == upgradeId }
            if (upgrade != null && _gameState.value.money >= upgrade.cost) {
                val newUpgrades = _gameState.value.upgrades.map {
                    if (it.id == upgradeId) {
                        it.copy(level = it.level + 1, cost = (it.cost * 1.5).toLong())
                    } else {
                        it
                    }
                }
                _gameState.value = _gameState.value.copy(
                    money = _gameState.value.money - upgrade.cost,
                    upgrades = newUpgrades
                )
            }
        }
    }

    fun previousCat() {
        viewModelScope.launch {
            _gameState.value = _gameState.value.copy(
                currentCatIndex = (_gameState.value.currentCatIndex - 1 + 20) % 20
            )
        }
    }

    fun nextCat() {
        viewModelScope.launch {
            _gameState.value = _gameState.value.copy(
                currentCatIndex = (_gameState.value.currentCatIndex + 1) % 20
            )
        }
    }
}
