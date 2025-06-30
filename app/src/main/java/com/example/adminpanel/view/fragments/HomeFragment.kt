package com.example.adminpanel.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.adminpanel.R
import com.example.adminpanel.adapter.UserAdapter
import com.example.adminpanel.databinding.FragmentHomeBinding
import com.example.adminpanel.viewmodel.UserViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: UserViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        viewModel.userList.observe(viewLifecycleOwner) { list ->
            adapter = UserAdapter(::onItemCLicked)
            binding.recyclerView.adapter = adapter
            adapter.differ.submitList(list)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun onItemCLicked(id: String) {
        val bundle = Bundle()
        bundle.putString("userId", id)
        findNavController().navigate(R.id.action_homeFragment_to_userDetailFragment, bundle)
    }

}