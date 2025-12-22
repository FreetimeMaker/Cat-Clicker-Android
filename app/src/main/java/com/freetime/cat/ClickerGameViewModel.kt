package com.freetime.cat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class UpgradeUI(
    val id: Int,
    val name: String,
    val cost: Int,
    val level: Int
)

class ClickerGameViewModel : ViewModel() {
    private val nativeGame = ClickerGameJNI()
    
    private val _money = MutableLiveData(0L)
    val money: LiveData<Long> = _money

    private val _clickPower = MutableLiveData(1)
    val clickPower: LiveData<Int> = _clickPower

    private val _upgrades = MutableLiveData<List<UpgradeUI>>(emptyList())
    val upgrades: LiveData<List<UpgradeUI>> = _upgrades

    private val catNames = listOf(
        "AutoClicker1",
        "AutoClicker2",
        "Next Button",
        "AutoClicker3",
        "Upgrade Cats Per Second",
        "Upgrade Cats Per Click"
    )

    init {
        nativeGame.initGame()
        loadUpgrades()
        updateGameState()
    }

    fun click() {
        nativeGame.click()
        updateGameState()
    }

    fun purchaseUpgrade(upgradeId: Int) {
        nativeGame.purchaseUpgrade(upgradeId)
        updateGameState()
        loadUpgrades()
    }

    private fun updateGameState() {
        _money.value = nativeGame.getMoney()
        _clickPower.value = nativeGame.getClickPower()
    }

    private fun loadUpgrades() {
        viewModelScope.launch {
            val count = nativeGame.getUpgradeCount()
            val upgradeList = mutableListOf<UpgradeUI>()
            for (i in 0 until count) {
                val catName = if (i < catNames.size) catNames[i] else "Upgrade ${i + 1}"
                upgradeList.add(
                    UpgradeUI(
                        id = i,
                        name = catName,
                        cost = nativeGame.getUpgradeCost(i),
                        level = nativeGame.getUpgradeLevel(i)
                    )
                )
            }
            _upgrades.value = upgradeList
        }
    }
}
