/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:46 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/4/22, 2:55 PM
 *
 */

package com.example.appsfactory.presentation.top_albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appsfactory.databinding.TopAlbumsListItemBinding
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.presentation.util.inVisible
import com.example.appsfactory.presentation.util.visible

class TopAlbumAdapter(
    private val onItemClicked: (TopAlbum) -> Unit,
    private val onBookmarkClicked: (TopAlbum) -> Unit,
    private val onBookmarkRemoveClicked: (TopAlbum) -> Unit
) : RecyclerView.Adapter<TopAlbumAdapter.MyViewHolder>() {
    private var albumsList = ArrayList<TopAlbum>()
    private var isSelected = false

    inner class MyViewHolder(
        private val binding: TopAlbumsListItemBinding,
        private val onItemClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: TopAlbum) {
            binding.album = album
            binding.executePendingBindings()

            binding.albumInfoCard.setOnClickListener { onItemClicked(bindingAdapterPosition) }
            binding.bookmarkImageBtn.setOnClickListener {
                onBookmarkClicked(album)
                onBookmark()
            }
            binding.bookmarkRemoveBtn.setOnClickListener {
                onBookmarkRemoveClicked(album)
                onBookmarkRemove()
            }
        }

        private fun onBookmark() {
            isSelected = !isSelected

            if (isSelected) {
                binding.bookmarkImageBtn.inVisible()
                binding.bookmarkRemoveBtn.visible()
            } else {
                binding.bookmarkImageBtn.visible()
                binding.bookmarkRemoveBtn.inVisible()
            }
        }

        private fun onBookmarkRemove() {
            binding.bookmarkRemoveBtn.inVisible()
            binding.bookmarkImageBtn.visible()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemBinding =
            TopAlbumsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding) { onItemClicked(albumsList[it]) }
    }

    override fun getItemCount() = albumsList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(albumsList[position])

    fun setData(albums: List<TopAlbum>) {
        albumsList.clear()
        albumsList.addAll(albums)
        notifyDataSetChanged()
    }
}