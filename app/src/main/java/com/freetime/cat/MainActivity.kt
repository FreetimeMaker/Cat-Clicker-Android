package com.freetime.cat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ClickerGameViewModel
    private lateinit var upgradeAdapter: UpgradeAdapter
    private lateinit var catsDisplay: TextView
    private lateinit var powerDisplay: TextView
    private lateinit var clickButton: ImageButton

    companion object {
        init {
            System.loadLibrary("cat")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ClickerGameViewModel::class.java)

        catsDisplay = findViewById(R.id.catsDisplay)
        powerDisplay = findViewById(R.id.powerDisplay)
        clickButton = findViewById(R.id.clickImageButton)

        val recyclerView = findViewById<RecyclerView>(R.id.upgradesRecycler)
        upgradeAdapter = UpgradeAdapter { upgradeId ->
            viewModel.purchaseUpgrade(upgradeId)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = upgradeAdapter

        // Observer für Cats
        viewModel.money.observe(this) { cats ->
            catsDisplay.text = getString(R.string.cats_display, cats)
        }

        // Observer für Click Power
        viewModel.clickPower.observe(this) { power ->
            powerDisplay.text = getString(R.string.power_display, power)
        }

        // Observer für Upgrades
        viewModel.upgrades.observe(this) { upgrades ->
            upgradeAdapter.setUpgrades(upgrades)
            // Wenn Upgrade mit id 2 (3. Upgrade) Level >= 1, ändere Bild
            val specialUpgrade = upgrades.find { it.id == 2 }
            if (specialUpgrade != null && specialUpgrade.level >= 1) {
                clickButton.setImageResource(R.drawable.cat_upgraded)
            } else {
                clickButton.setImageResource(R.drawable.cat_default)
            }
        }

        // Click Button Listener
        clickButton.setOnClickListener {
            viewModel.click()
        }
    }
}