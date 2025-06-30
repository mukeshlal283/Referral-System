package com.example.adminpanel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.databinding.UsersItemViewBinding
import com.example.newapp.model.Users

class UserAdapter(val onItemClick: (String) -> Unit) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(val binding: UsersItemViewBinding): RecyclerView.ViewHolder(binding.root)

    val DiffUtil = object : DiffUtil.ItemCallback<Users>() {
        override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, DiffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(UsersItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = differ.currentList[position]

        holder.binding.apply {
            userId.text = data.id
            name.text = data.name
            email.text = data.email
            referralCode.text = data.referralCode
            points.text = data.points.toString()
        }

        holder.itemView.setOnClickListener {
            onItemClick(data.id)
        }
    }

}