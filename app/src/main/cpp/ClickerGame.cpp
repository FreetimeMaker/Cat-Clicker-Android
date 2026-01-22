#include "ClickerGame.h"
#include <cmath>

ClickerGame::ClickerGame() {
    initializeUpgrades();
}

void ClickerGame::initializeUpgrades() {
    upgrades.push_back(std::make_shared<Upgrade>(0, "Catnip", 10, 10, 1.1));
    upgrades.push_back(std::make_shared<Upgrade>(1, "Yarn Ball", 50, 8, 1.5));
    upgrades.push_back(std::make_shared<Upgrade>(2, "Scratching Post", 250, 5, 2.0));
    upgrades.push_back(std::make_shared<Upgrade>(3, "Feather Toy", 1000, 3, 3.0));
    upgrades.push_back(std::make_shared<Upgrade>(4, "Laser Pointer", 5000, 2, 5.0));
    upgrades.push_back(std::make_shared<Upgrade>(5, "Cardboard Box", 25000, 1, 10.0));
}

void ClickerGame::click() {
    money += clickPower;
}

void ClickerGame::purchaseUpgrade(int upgradeIndex) {
    if (upgradeIndex >= 0 && upgradeIndex < static_cast<int>(upgrades.size())) {
        auto& upgrade = upgrades[upgradeIndex];
        if (upgrade->level < upgrade->maxLevel && money >= upgrade->getCurrentCost()) {
            money -= upgrade->getCurrentCost();
            upgrade->level++;
            applyUpgrade(upgrade);
        }
    }
}

void ClickerGame::applyUpgrade(const std::shared_ptr<Upgrade>& upgrade) {
    clickPower = static_cast<int>(clickPower * upgrade->multiplier);
}
