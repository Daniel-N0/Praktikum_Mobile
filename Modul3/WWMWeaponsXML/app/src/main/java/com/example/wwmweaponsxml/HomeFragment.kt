package com.example.wwmweaponsxml

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wwmweaponsxml.databinding.FragmentHomeBinding
import android.content.res.Configuration
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weaponAdapter = WeaponAdapter(
            weaponList = WeaponDataSource.dummyWeapons,
            onInfoClick = { weapon ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(weapon.infoUrl))
                startActivity(intent)
            },
            onDetailClick = { weapon ->
                val bundle = Bundle()
                bundle.putInt("weaponId", weapon.id)
                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            }
        )
        binding.rvWeapons.adapter = weaponAdapter

        val horizontalAdapter = WeaponHorizontalAdapter(
            weaponList = WeaponDataSource.dummyWeapons.reversed(),
            onDetailClick = { weapon ->
                val bundle = Bundle()
                bundle.putInt("weaponId", weapon.id)
                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            },
            onInfoClick = { weapon ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(weapon.infoUrl))
                startActivity(intent)
            }
        )
        binding.rvHorizontalWeapons.adapter = horizontalAdapter

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}