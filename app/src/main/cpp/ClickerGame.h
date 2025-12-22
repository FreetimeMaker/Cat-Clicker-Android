#pragma once

#include <string>
#include <vector>
#include <memory>
#include <cmath>

struct Upgrade {
    int id;
    std::string name;
    int baseCost;
    int level;
    double multiplier;

    Upgrade(int id, const std::string& name, int cost, double mult)
        : id(id), name(name), baseCost(cost), level(0), multiplier(mult) {}

    int getCurrentCost() const {
        return baseCost * (level + 1);
    }
};

class ClickerGame {
private:
    long long money = 0;
    int clickPower = 1;
    std::vector<std::shared_ptr<Upgrade>> upgrades;

public:
    ClickerGame();
    ~ClickerGame() = default;

    void initializeUpgrades();
    void click();
    void purchaseUpgrade(int upgradeId);

    long long getMoney() const { return money; }
    int getClickPower() const { return clickPower; }

    int getUpgradeCount() const { return upgrades.size(); }
    Upgrade* getUpgrade(int index) {
        if (index >= 0 && index < upgrades.size()) {
            return upgrades[index].get();
        }
        return nullptr;
    }

private:
    void applyUpgrade(const std::shared_ptr<Upgrade>& upgrade);
};

