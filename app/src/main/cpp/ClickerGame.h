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
    int maxLevel;
    double multiplier;

    Upgrade(int id, std::string name, int cost, int maxLvl, double mult)
        : id(id), name(std::move(name)), baseCost(cost), level(0), maxLevel(maxLvl), multiplier(mult) {}

    [[nodiscard]] int getCurrentCost() const {
        return baseCost * (level + 1);
    }
};

class ClickerGame {
private:
    long long money = 0;
    int clickPower = 1;
    std::vector<std::shared_ptr<Upgrade>> upgrades;

public:
    ~ClickerGame() = default;

    void initializeUpgrades();
    void click();
    void purchaseUpgrade(int upgradeId);

    [[nodiscard]] long long getMoney() const { return money; }
    [[nodiscard]] int getClickPower() const { return clickPower; }

    [[nodiscard]] int getUpgradeCount() const { return static_cast<int>(upgrades.size()); }
    Upgrade* getUpgrade(int index) {
        if (index >= 0 && index < static_cast<int>(upgrades.size())) {
            return upgrades[index].get();
        }
        return nullptr;
    }

private:
    void applyUpgrade(const std::shared_ptr<Upgrade>& upgrade);
};
