#include <jni.h>
#include "ClickerGame.h"

static ClickerGame* gameInstance = nullptr;

extern "C" {

JNIEXPORT void JNICALL
Java_com_freetime_cat_ClickerGameJNI_initGame(JNIEnv* env, jobject obj) {
    if (!gameInstance) {
        gameInstance = new ClickerGame();
    }
}

JNIEXPORT void JNICALL
Java_com_freetime_cat_ClickerGameJNI_click(JNIEnv* env, jobject obj) {
    if (gameInstance) {
        gameInstance->click();
    }
}

JNIEXPORT void JNICALL
Java_com_freetime_cat_ClickerGameJNI_purchaseUpgrade(JNIEnv* env, jobject obj, jint upgradeId) {
    if (gameInstance) {
        gameInstance->purchaseUpgrade(upgradeId);
    }
}

JNIEXPORT jlong JNICALL
Java_com_freetime_cat_ClickerGameJNI_getMoney(JNIEnv* env, jobject obj) {
    return gameInstance ? gameInstance->getMoney() : 0;
}

JNIEXPORT jint JNICALL
Java_com_freetime_cat_ClickerGameJNI_getClickPower(JNIEnv* env, jobject obj) {
    return gameInstance ? gameInstance->getClickPower() : 1;
}

JNIEXPORT jint JNICALL
Java_com_freetime_cat_ClickerGameJNI_getUpgradeCount(JNIEnv* env, jobject obj) {
    return gameInstance ? gameInstance->getUpgradeCount() : 0;
}

JNIEXPORT jstring JNICALL
Java_com_freetime_cat_ClickerGameJNI_getUpgradeName(JNIEnv* env, jobject obj, jint index) {
    if (gameInstance) {
        Upgrade* upgrade = gameInstance->getUpgrade(index);
        if (upgrade) {
            return env->NewStringUTF(upgrade->name.c_str());
        }
    }
    return env->NewStringUTF("");
}

JNIEXPORT jint JNICALL
Java_com_freetime_cat_ClickerGameJNI_getUpgradeCost(JNIEnv* env, jobject obj, jint index) {
    if (gameInstance) {
        Upgrade* upgrade = gameInstance->getUpgrade(index);
        if (upgrade) {
            return upgrade->getCurrentCost();
        }
    }
    return 0;
}

JNIEXPORT jint JNICALL
Java_com_freetime_cat_ClickerGameJNI_getUpgradeLevel(JNIEnv* env, jobject obj, jint index) {
    if (gameInstance) {
        Upgrade* upgrade = gameInstance->getUpgrade(index);
        if (upgrade) {
            return upgrade->level;
        }
    }
    return 0;
}

}

