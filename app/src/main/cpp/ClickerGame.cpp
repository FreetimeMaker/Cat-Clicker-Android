#include "ClickerGame.h"
#include <cmath>

void ClickerGame::initializeUpgrades() {
    upgrades.push_back(std::make_shared<Upgrade>(0, "Catnip", 25, 4, 1));
    upgrades.push_back(std::make_shared<Upgrade>(1, "Yarn Ball", 100, 4, 1));
    upgrades.push_back(std::make_shared<Upgrade>(2, "Scratching Post", 400, 19, 2));
    upgrades.push_back(std::make_shared<Upgrade>(3, "Feather Toy", 400, 4, 1));
    upgrades.push_back(std::make_shared<Upgrade>(4, "Laser Pointer", 800, 4, 3));
    upgrades.push_back(std::make_shared<Upgrade>(5, "Cardboard Box", 1600, 4, 3));
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
