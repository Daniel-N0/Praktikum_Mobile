package com.example.wwmweaponsxml

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wwmweaponsxml.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weaponId = arguments?.getInt("weaponId")
        val weapon = WeaponDataSource.dummyWeapons.find { it.id == weaponId }

        weapon?.let {
            binding.tvDetailName.text = it.name
            binding.tvDetailType.text = it.type
            binding.tvDetailDesc.text = it.description
            binding.ivDetailImage.setImageResource(it.imageResId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}