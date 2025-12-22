package com.freetime.cat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_upgrade, parent, false)
        return UpgradeViewHolder(view)
    }

    override fun onBindViewHolder(holder: UpgradeViewHolder, position: Int) {
        val upgrade = upgrades[position]
        holder.bind(upgrade, onUpgradeClick)
    }

    override fun getItemCount() = upgrades.size

    class UpgradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.upgradeIcon)
        private val text: TextView = itemView.findViewById(R.id.upgradeText)
        private val button: Button = itemView.findViewById(R.id.upgradeButton)

        fun bind(upgrade: UpgradeUI, onUpgradeClick: (Int) -> Unit) {
            text.text = itemView.context.getString(R.string.upgrade_item_text, upgrade.name, upgrade.cost, upgrade.level)
            // set a small icon based on upgrade id (wrap around existing Cat images)
            val resId = when ((upgrade.id % 20) + 1) {
                1 -> R.drawable.cat1
                2 -> R.drawable.cat2
                3 -> R.drawable.cat3
                4 -> R.drawable.cat4
                5 -> R.drawable.cat5
                6 -> R.drawable.cat6
                7 -> R.drawable.cat7
                8 -> R.drawable.cat8
                9 -> R.drawable.cat9
                10 -> R.drawable.cat10
                11 -> R.drawable.cat11
                12 -> R.drawable.cat12
                13 -> R.drawable.cat13
                14 -> R.drawable.cat14
                15 -> R.drawable.cat15
                16 -> R.drawable.cat16
                17 -> R.drawable.cat17
                18 -> R.drawable.cat18
                19 -> R.drawable.cat19
                20 -> R.drawable.cat20
                else -> R.drawable.cat1
            }
            icon.setImageResource(resId)

            button.setOnClickListener { onUpgradeClick(upgrade.id) }
        }
    }
}
