package com.example.everybody.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.everybody.R
import com.example.everybody.databinding.PersonItemBinding
import com.example.everybody.retrofitAPIs.randomuserAPI.User
import com.example.everybody.retrofitAPIs.randomuserAPI.UserDetails

class PersonItemAdapter(private var user:List<User>):RecyclerView.Adapter<PersonItemAdapter.CustomViewHolder>() {
    var onItemClick :((User)->Unit)?=null


    class CustomViewHolder(val binding: PersonItemBinding):RecyclerView.ViewHolder(binding.root)

    fun filteredItems(list: List<User>){
        this.user= list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        return CustomViewHolder(PersonItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return user.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val data = user[position]
        holder.binding.apply {
            personImg.load(data.picture.large)
            personName.text=data.name.title+". "+data.name.first+" "+data.name.last

            personImg.setOnClickListener {
                onItemClick?.invoke(data)

            }
        }

    }

}