package com.example.appsfactory.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appsfactory.R
import com.example.appsfactory.data.model.top_albums.Album
import com.example.appsfactory.databinding.TopAlbumsListItemBinding
import com.example.appsfactory.util.inVisible
import com.example.appsfactory.util.visible

class TopAlbumAdapter(
    private val onItemClicked: (Album, Boolean) -> Unit,
    private val onBookmarkClicked: (Album) -> Unit,
    private val onBookmarkRemoveClicked: (Album) -> Unit
) : RecyclerView.Adapter<TopAlbumAdapter.MyViewHolder>() {
    private var albumsList = ArrayList<Album>()
    private var isSelected = false

    inner class MyViewHolder(
        private val binding: TopAlbumsListItemBinding,
        private val onItemClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
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
        return MyViewHolder(itemBinding) { onItemClicked(albumsList[it], isSelected) }
    }

    override fun getItemCount() = albumsList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(albumsList[position])

    fun setData(albums: List<Album>) {
        albumsList.clear()
        albumsList.addAll(albums)
        notifyDataSetChanged()
    }
}