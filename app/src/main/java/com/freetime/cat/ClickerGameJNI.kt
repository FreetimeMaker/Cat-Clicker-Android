package com.freetime.cat

class ClickerGameJNI {
    external fun initGame()
    external fun click()
    external fun purchaseUpgrade(upgradeId: Int)
    external fun getMoney(): Long
    external fun getClickPower(): Int
    external fun getUpgradeCount(): Int
    external fun getUpgradeName(index: Int): String
    external fun getUpgradeCost(index: Int): Int
    external fun getUpgradeLevel(index: Int): Int

    companion object {
        init {
            System.loadLibrary("cat")
        }
    }
}

