package com.freetime.catCPP

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileWriter
import java.io.IOException

class UpgradeExporter {
    
    companion object {
        init {
            System.loadLibrary("cat")
        }
        
        // Native methods
        private external fun exportUpgradesToFile(filename: String, gamePtr: Long)
        private external fun exportUpgradesToUnityFormat(filename: String, gamePtr: Long)
        private external fun getUpgradesAsJSON(gamePtr: Long): String
        private external fun exportUnityUpgrades()
        
        // Export current game state to file
        fun exportCurrentUpgrades(context: Context, gamePtr: Long): Boolean {
            return try {
                val fileName = "cat_clicker_upgrades_${System.currentTimeMillis()}.json"
                val file = File(context.getExternalFilesDir(null), fileName)
                
                exportUpgradesToFile(file.absolutePath, gamePtr)
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
        
        // Export in Unity WebGL format
        fun exportUnityFormatUpgrades(context: Context, gamePtr: Long): Boolean {
            return try {
                val fileName = "unity_upgrades_${System.currentTimeMillis()}.json"
                val file = File(context.getExternalFilesDir(null), fileName)
                
                exportUpgradesToUnityFormat(file.absolutePath, gamePtr)
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
        
        // Get upgrades as JSON string
        fun getUpgradesJSON(gamePtr: Long): String {
            return try {
                getUpgradesAsJSON(gamePtr)
            } catch (e: Exception) {
                e.printStackTrace()
                "{}"
            }
        }
        
        // Export default Unity upgrades to Downloads folder
        fun exportDefaultUnityUpgrades(): Boolean {
            return try {
                exportUnityUpgrades()
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
        
        // Save JSON string to file
        fun saveJSONToFile(context: Context, json: String, filename: String): Boolean {
            return try {
                val file = File(context.getExternalFilesDir(null), filename)
                FileWriter(file).use { writer ->
                    writer.write(json)
                }
                true
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }
        
        // Check if external storage is available
        fun isExternalStorageWritable(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }
        
        // Get external files directory
        fun getExternalFilesDir(context: Context): File? {
            return context.getExternalFilesDir(null)
        }
    }
}
