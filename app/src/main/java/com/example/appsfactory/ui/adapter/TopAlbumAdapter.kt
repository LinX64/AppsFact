package com.example.appsfactory.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appsfactory.data.model.artistList.Artist
import com.example.appsfactory.data.model.top_albums.Album
import com.example.appsfactory.databinding.ArtistListItemBinding
import com.example.appsfactory.databinding.TopAlbumsListItemBinding

class TopAlbumAdapter(
    private val onItemClicked: (Album) -> Unit,
    private val onBookmarkClicked: (Album) -> Unit
) : RecyclerView.Adapter<TopAlbumAdapter.MyViewHolder>() {
    private var albumsList = ArrayList<Album>()

    inner class MyViewHolder(
        private val binding: TopAlbumsListItemBinding,
        private val onItemClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.album = album
            binding.executePendingBindings()

            binding.root.setOnClickListener { onItemClicked(bindingAdapterPosition) }
            binding.bookmarkImageBtn.setOnClickListener { onItemClicked(bindingAdapterPosition) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemBinding =
            TopAlbumsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding) {
            onItemClicked(albumsList[it])
            onBookmarkClicked(albumsList[it])
        }
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