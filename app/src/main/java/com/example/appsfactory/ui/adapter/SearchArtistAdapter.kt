package com.example.appsfactory.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appsfactory.data.model.Artist
import com.example.appsfactory.databinding.ArtistListItemBinding

class SearchArtistAdapter(
    private val onItemClicked: (Artist, String) -> Unit
) : RecyclerView.Adapter<SearchArtistAdapter.MyViewHolder>() {
    private var artists = ArrayList<Artist>()

    inner class MyViewHolder(
        private val binding: ArtistListItemBinding,
        private val onItemClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist) {
            binding.artist = artist
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                onItemClicked(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemBinding =
            ArtistListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding) {
            onItemClicked(artists[it], artists[it].url)
        }
    }

    override fun getItemCount() = artists.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(artists[position])

    fun setData(artistList: List<Artist>) {
        artists.clear()
        artists.addAll(artistList)
        notifyDataSetChanged()
    }
}