package com.example.wwmweaponsxml

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wwmweaponsxml.databinding.ItemWeaponBinding

class WeaponAdapter(
    private val weaponList: List<Weapon>,
    private val onInfoClick: (Weapon) -> Unit,
    private val onDetailClick: (Weapon) -> Unit
) : RecyclerView.Adapter<WeaponAdapter.WeaponViewHolder>() {

    inner class WeaponViewHolder(private val binding: ItemWeaponBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weapon: Weapon) {
            binding.tvWeaponName.text = weapon.name
            binding.tvWeaponType.text = weapon.type
            binding.tvWeaponDesc.text = weapon.description
            binding.ivWeaponImage.setImageResource(weapon.imageResId)
            binding.btnInfo.setOnClickListener { onInfoClick(weapon) }
            binding.btnDetail.setOnClickListener { onDetailClick(weapon) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeaponViewHolder {
        val binding = ItemWeaponBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeaponViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeaponViewHolder, position: Int) {
        holder.bind(weaponList[position])
    }

    override fun getItemCount(): Int = weaponList.size
}