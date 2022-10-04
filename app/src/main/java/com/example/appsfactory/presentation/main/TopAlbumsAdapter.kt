package com.example.appsfactory.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appsfactory.data.source.local.entity.LocalAlbum
import com.example.appsfactory.databinding.AlbumsListItemBinding

class TopAlbumsAdapter(
    private val onItemClicked: (LocalAlbum) -> Unit
) : RecyclerView.Adapter<TopAlbumsAdapter.MyViewHolder>() {
    private var albumsList = ArrayList<LocalAlbum>()

    inner class MyViewHolder(
        private val binding: AlbumsListItemBinding,
        private val onItemClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: LocalAlbum) {
            binding.album = album
            binding.executePendingBindings()

            binding.root.setOnClickListener { onItemClicked(bindingAdapterPosition) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemBinding =
            AlbumsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding) {
            onItemClicked(albumsList[it])
        }
    }

    override fun getItemCount() = albumsList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(albumsList[position])

    fun setData(albums: List<LocalAlbum>) {
        albumsList.clear()
        albumsList.addAll(albums)
        notifyDataSetChanged()
    }
}