package com.example.wwmweaponsxml

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wwmweaponsxml.databinding.ItemWeaponHorizontalBinding

class WeaponHorizontalAdapter(
    private val weaponList: List<Weapon>,
    private val onDetailClick: (Weapon) -> Unit,
    private val onInfoClick: (Weapon) -> Unit
) : RecyclerView.Adapter<WeaponHorizontalAdapter.HorizontalViewHolder>() {

    inner class HorizontalViewHolder(private val binding: ItemWeaponHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weapon: Weapon) {
            binding.tvHorizontalName.text = weapon.name
            binding.ivHorizontalImage.setImageResource(weapon.imageResId)
            binding.root.setOnClickListener { onDetailClick(weapon) }
            binding.btnHorizontalInfo.setOnClickListener { onInfoClick(weapon) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val binding = ItemWeaponHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HorizontalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        holder.bind(weaponList[position])
    }

    override fun getItemCount(): Int = weaponList.size
}