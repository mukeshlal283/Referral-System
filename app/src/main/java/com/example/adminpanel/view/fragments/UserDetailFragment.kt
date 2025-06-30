package com.example.adminpanel.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adminpanel.R
import com.example.adminpanel.adapter.PointsAdapter
import com.example.adminpanel.databinding.FragmentUserDetailBinding
import com.example.newapp.model.NewUser
import com.google.firebase.firestore.FirebaseFirestore

class UserDetailFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var userId: String
    private lateinit var adapter: PointsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailBinding.inflate(layoutInflater)
        db = FirebaseFirestore.getInstance()

        userId = arguments?.getString("userId")!!
        fetchUserData()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun fetchUserData() {
        db.collection("users").document(userId).addSnapshotListener {snapshot, error ->
            if (error != null) {
                Log.e("FIRESTORE", "Listen failed", error)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                binding.userEmail.text = snapshot.getString("email")
                binding.userName.text = snapshot.getString("name")
                binding.userId.text = snapshot.getString("id")
                binding.userPhoneNum.text = snapshot.getString("phoneNumber")
                binding.userPoints.text = snapshot.getLong("points").toString()
                val rawList = snapshot.get("newUserList") as List<HashMap<String, Any>>
                val newUserList = mutableListOf<NewUser>()
                rawList.forEach { map ->
                    val user = NewUser(
                        id = map["id"] as? String,
                        name = map["name"] as String,
                        points = map["points"] as Long
                    )
                    newUserList.add(user)
                }
                newUserList.reverse()
                adapter = PointsAdapter()
                binding.recyclerView.adapter = adapter
                adapter.differ.submitList(newUserList)
            }
        }

    }

}