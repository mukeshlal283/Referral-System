package com.example.adminpanel.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newapp.model.NewUser
import com.example.newapp.model.Users
import com.google.firebase.firestore.FirebaseFirestore

class UserViewModel: ViewModel() {

    private val _userList = MutableLiveData<ArrayList<Users>>()
    val userList: LiveData<ArrayList<Users>> = _userList

    init {
        getUsers()
    }

    private fun getUsers() {
        FirebaseFirestore.getInstance().collection("users")
            .addSnapshotListener {snapshot, error ->
                if (error != null || snapshot == null) {
                    Log.e("UserViewModel", "Firestore error: ${error?.message}")
                    return@addSnapshotListener
                }

                val users = snapshot.documents.mapNotNull { it.toObject(Users::class.java) }
                _userList.value = users as ArrayList<Users>?

            }
    }

}