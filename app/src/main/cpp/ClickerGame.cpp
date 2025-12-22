#include "ClickerGame.h"
#include <cmath>

ClickerGame::ClickerGame() {
    initializeUpgrades();
}

void ClickerGame::initializeUpgrades() {
    // Use 0-based IDs to match Kotlin/Java indexing
    for (int i = 0; i < 20; i++) {
        upgrades.push_back(std::make_shared<Upgrade>(
            i,
            "Cat " + std::to_string(i + 1),
            10 * (i + 1),
            1.5
        ));
    }
}

void ClickerGame::click() {
    money += clickPower;
}

void ClickerGame::purchaseUpgrade(int upgradeIndex) {
    // upgradeIndex is 0-based (as in Kotlin/RecyclerView)
    if (upgradeIndex >= 0 && upgradeIndex < static_cast<int>(upgrades.size())) {
        auto& upgrade = upgrades[upgradeIndex];
        if (money >= upgrade->getCurrentCost()) {
            money -= upgrade->getCurrentCost();
            upgrade->level++;
            applyUpgrade(upgrade);
        }
    }
}

void ClickerGame::applyUpgrade(const std::shared_ptr<Upgrade>& upgrade) {
    // Base multiplier for all upgrades
    double powerIncrease = upgrade->multiplier;

    // Special bonuses based on upgrade ID
    if (upgrade->id % 3 == 0) {
        // Every 3rd cat gives +10% bonus
        powerIncrease += 0.1;
    }
    if (upgrade->id % 5 == 0) {
        // Every 5th cat gives +20% bonus
        powerIncrease += 0.2;
    }

    clickPower = static_cast<int>(clickPower * powerIncrease);
}
