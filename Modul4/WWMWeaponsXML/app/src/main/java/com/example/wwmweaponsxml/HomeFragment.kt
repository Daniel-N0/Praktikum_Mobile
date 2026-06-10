package com.example.wwmweaponsxml

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels // Import khusus untuk delegate viewModels()
import androidx.lifecycle.lifecycleScope // Import untuk coroutine UI
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wwmweaponsxml.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch // Import untuk menjalankan fungsi asinkron

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeaponViewModel by viewModels {
        WeaponViewModelFactory("Halaman Utama Senjata XML")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLayoutManager()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.weaponList.collect { weapons ->
                setupAdapters(weapons)
            }
        }
    }

    private fun setupLayoutManager() {
        binding.rvHorizontalWeapons.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvWeapons.layoutManager = GridLayoutManager(requireContext(), 2)
        } else {
            binding.rvWeapons.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupAdapters(weapons: List<Weapon>) {
        val weaponAdapter = WeaponAdapter(
            weaponList = weapons,
            onInfoClick = { weapon ->
                viewModel.onExplicitIntentClicked(weapon)

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(weapon.infoUrl))
                intent.setPackage("com.android.chrome")

                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    intent.setPackage(null)
                    startActivity(intent)
                }
            },
            onDetailClick = { weapon ->
                viewModel.onDetailClicked(weapon)

                val bundle = Bundle()
                bundle.putInt("weaponId", weapon.id)
                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            }
        )
        binding.rvWeapons.adapter = weaponAdapter

        // 4. Adapter Horizontal (Datanya dibalik agar bervariasi)
        val horizontalAdapter = WeaponHorizontalAdapter(
            weaponList = weapons.reversed(),
            onDetailClick = { weapon ->
                viewModel.onDetailClicked(weapon) // Panggil fungsi logging

                val bundle = Bundle()
                bundle.putInt("weaponId", weapon.id)
                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            },
            onInfoClick = { weapon ->
                viewModel.onExplicitIntentClicked(weapon)

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(weapon.infoUrl))
                intent.setPackage("com.android.chrome")
                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    intent.setPackage(null)
                    startActivity(intent)
                }
            }
        )
        binding.rvHorizontalWeapons.adapter = horizontalAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}