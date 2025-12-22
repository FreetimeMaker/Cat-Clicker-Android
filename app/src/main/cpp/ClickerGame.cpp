#include "ClickerGame.h"
#include <cmath>

ClickerGame::ClickerGame() {
    initializeUpgrades();
}

void ClickerGame::initializeUpgrades() {
    for (int i = 1; i <= 20; i++) {
        upgrades.push_back(std::make_shared<Upgrade>(
            i,
            "Katze " + std::to_string(i),
            10 * i,
            1.5
        ));
    }
}

void ClickerGame::click() {
    money += clickPower;
}

void ClickerGame::purchaseUpgrade(int upgradeId) {
    for (auto& upgrade : upgrades) {
        if (upgrade->id == upgradeId) {
            if (money >= upgrade->getCurrentCost()) {
                money -= upgrade->getCurrentCost();
                upgrade->level++;
                applyUpgrade(upgrade);
            }
            return;
        }
    }
}

void ClickerGame::applyUpgrade(const std::shared_ptr<Upgrade>& upgrade) {
    // Basis-Multiplikator für alle Upgrades
    double powerIncrease = upgrade->multiplier;

    // Spezielle Boni basierend auf Upgrade-ID
    if (upgrade->id % 3 == 0) {
        // Jede 3. Katze gibt +10% Bonus
        powerIncrease += 0.1;
    }
    if (upgrade->id % 5 == 0) {
        // Jede 5. Katze gibt +20% Bonus
        powerIncrease += 0.2;
    }

    clickPower = static_cast<int>(clickPower * powerIncrease);
}

