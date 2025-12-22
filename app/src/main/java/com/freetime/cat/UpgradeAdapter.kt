package com.freetime.cat

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class UpgradeAdapter(
    private val onUpgradeClick: (Int) -> Unit
) : RecyclerView.Adapter<UpgradeAdapter.UpgradeViewHolder>() {

    private var upgrades = listOf<UpgradeUI>()

    fun setUpgrades(newUpgrades: List<UpgradeUI>) {
        upgrades = newUpgrades
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpgradeViewHolder {
        val button = Button(parent.context)
        button.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return UpgradeViewHolder(button)
    }

    override fun onBindViewHolder(holder: UpgradeViewHolder, position: Int) {
        val upgrade = upgrades[position]
        holder.bind(upgrade, onUpgradeClick)
    }

    override fun getItemCount() = upgrades.size

    class UpgradeViewHolder(private val button: Button) : RecyclerView.ViewHolder(button) {
        fun bind(upgrade: UpgradeUI, onUpgradeClick: (Int) -> Unit) {
            button.text = "${upgrade.name}\nKosten: ${upgrade.cost} | Level: ${upgrade.level}"
            button.setOnClickListener { onUpgradeClick(upgrade.id) }
        }
    }
}

