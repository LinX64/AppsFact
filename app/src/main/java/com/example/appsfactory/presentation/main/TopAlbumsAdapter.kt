/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:45 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 1:07 PM
 *
 */

package com.example.appsfactory.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appsfactory.data.source.local.entity.AlbumEntity
import com.example.appsfactory.databinding.AlbumsListItemBinding

class TopAlbumsAdapter(
    private val onItemClicked: (AlbumEntity) -> Unit
) : ListAdapter<AlbumEntity, TopAlbumsAdapter.MyViewHolder>(MyDiffUtilClass()) {

    inner class MyViewHolder(
        private val binding: AlbumsListItemBinding,
        private val onItemClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: AlbumEntity) {
            binding.album = album
            binding.executePendingBindings()

            binding.root.setOnClickListener { onItemClicked(bindingAdapterPosition) }
        }
    }

    private class MyDiffUtilClass : DiffUtil.ItemCallback<AlbumEntity>() {
        override fun areItemsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemBinding =
            AlbumsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding) {
            onItemClicked(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(getItem(position))
}