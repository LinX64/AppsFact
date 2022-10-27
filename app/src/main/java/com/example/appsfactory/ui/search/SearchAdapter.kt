/*
 * *
 *  * Created by Mohsen on 10/26/22, 10:11 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/26/22, 10:11 PM
 *
 */

package com.example.appsfactory.ui.search

import android.view.LayoutInflater
import com.example.appsfactory.databinding.ArtistListItemBinding
import com.example.appsfactory.domain.model.artistList.Artist
import com.example.appsfactory.ui.base.BaseListAdapter

class SearchAdapter(val onItemClicked: (Artist) -> Unit) :
    BaseListAdapter<Artist, ArtistListItemBinding>() {

    override fun inflateView(inflater: LayoutInflater, viewType: Int): ArtistListItemBinding {
        return ArtistListItemBinding.inflate(inflater)
    }

    override fun bind(binding: ArtistListItemBinding, position: Int, item: Artist) {
        with(binding) {
            artist = item
            executePendingBindings()

            root.setOnClickListener { onItemClicked(item) }
        }
    }
}