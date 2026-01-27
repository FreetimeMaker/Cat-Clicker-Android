package com.freetime.catCPP

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UpgradeUI(
    val id: Int,
    val name: String,
    val cost: Int,
    val level: Int
)

data class GameState(
    val money: Long = 0L,
    val clickPower: Int = 1,
    val upgrades: List<UpgradeUI> = emptyList(),
    val currentCatIndex: Int = 0
)

class ClickerGameViewModel : ViewModel() {
    private val nativeGame = ClickerGameJNI()
    
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    // Unity WebGL upgrade names matching the C++ version exactly
    private val upgradeNames = listOf(
        "AutoClicker1",
        "AutoClicker2",
        "Next Button",
        "AutoClicker3",
        "Upgrade Cats per Click",
        "Upgrade Cats per Second"
    )

    private val catImages = listOf(
        R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4, R.drawable.cat5,
        R.drawable.cat6, R.drawable.cat7, R.drawable.cat8, R.drawable.cat9, R.drawable.cat10,
        R.drawable.cat11, R.drawable.cat12, R.drawable.cat13, R.drawable.cat14, R.drawable.cat15,
        R.drawable.cat16, R.drawable.cat17, R.drawable.cat18, R.drawable.cat19, R.drawable.cat20
    )

    init {
        nativeGame.initGame()
        updateGameState()
    }

    fun click() {
        nativeGame.click()
        updateGameState()
    }

    fun purchaseUpgrade(upgradeId: Int) {
        nativeGame.purchaseUpgrade(upgradeId)
        updateGameState()
    }

    fun nextCat() {
        val currentState = _gameState.value
        val nextIndex = (currentState.currentCatIndex + 1) % catImages.size
        _gameState.value = currentState.copy(currentCatIndex = nextIndex)
    }

    fun previousCat() {
        val currentState = _gameState.value
        val prevIndex = (currentState.currentCatIndex - 1 + catImages.size) % catImages.size
        _gameState.value = currentState.copy(currentCatIndex = prevIndex)
    }

    private fun updateGameState() {
        viewModelScope.launch {
            val money = nativeGame.getMoney()
            val clickPower = nativeGame.getClickPower()
            val upgrades = loadUpgrades()
            
            _gameState.value = _gameState.value.copy(
                money = money,
                clickPower = clickPower,
                upgrades = upgrades
            )
        }
    }

    private suspend fun loadUpgrades(): List<UpgradeUI> {
        val count = nativeGame.getUpgradeCount()
        val upgradeList = mutableListOf<UpgradeUI>()
        
        for (i in 0 until count) {
            val upgradeName = if (i < upgradeNames.size) upgradeNames[i] else "Upgrade ${i + 1}"
            upgradeList.add(
                UpgradeUI(
                    id = i,
                    name = upgradeName,
                    cost = nativeGame.getUpgradeCost(i),
                    level = nativeGame.getUpgradeLevel(i)
                )
            )
        }
        return upgradeList
    }
}
