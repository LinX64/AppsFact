/*
 * *
 *  * Created by Mohsen on 10/26/22, 4:07 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/26/22, 4:07 PM
 *
 */

package com.example.appsfactory.ui.main

import android.view.LayoutInflater
import com.example.appsfactory.data.source.local.entity.TopAlbumEntity
import com.example.appsfactory.databinding.AlbumsListItemBinding
import com.example.appsfactory.ui.base.BaseListAdapter

class TopAlbumAdapter(val onItemClick: (TopAlbumEntity) -> Unit) :
    BaseListAdapter<TopAlbumEntity, AlbumsListItemBinding>() {

    override fun inflateView(inflater: LayoutInflater, viewType: Int): AlbumsListItemBinding {
        return AlbumsListItemBinding.inflate(inflater)
    }

    override fun bind(binding: AlbumsListItemBinding, position: Int, item: TopAlbumEntity) {
        with(binding) {
            album = item
            executePendingBindings()

            root.setOnClickListener { onItemClick(item) }
        }
    }
}