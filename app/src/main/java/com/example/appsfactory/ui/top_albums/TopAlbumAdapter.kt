/*
 * *
 *  * Created by Mohsen on 10/26/22, 10:19 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/26/22, 10:19 PM
 *
 */

package com.example.appsfactory.ui.top_albums

import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.example.appsfactory.databinding.TopAlbumsListItemBinding
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.ui.base.BaseListAdapter
import com.example.appsfactory.ui.util.inVisible
import com.example.appsfactory.ui.util.visible

class TopAlbumAdapter(
    private val onItemClicked: (TopAlbum) -> Unit,
    private val onBookmarkClicked: (TopAlbum) -> Unit,
    private val onBookmarkRemoveClicked: (TopAlbum) -> Unit
) : BaseListAdapter<TopAlbum, TopAlbumsListItemBinding>() {

    override fun inflateView(inflater: LayoutInflater, viewType: Int): TopAlbumsListItemBinding {
        return TopAlbumsListItemBinding.inflate(inflater, null, false)
    }

    override fun bind(binding: TopAlbumsListItemBinding, position: Int, item: TopAlbum) {
        with(binding) {
            topAlbum = item
            executePendingBindings()

            setBookmarked(item, binding)

            albumInfoCard.setOnClickListener { onItemClicked(getItem(position)) }
            bookmarkImageBtn.setOnClickListener {
                topAlbum?.let(onBookmarkClicked)

                onBookmark(it)
            }
            bookmarkRemoveBtn.setOnClickListener {
                topAlbum?.let(onBookmarkRemoveClicked)

                onBookmarkRemove()
            }
        }
    }

    private fun setBookmarked(
        item: TopAlbum,
        binding: TopAlbumsListItemBinding
    ) = if (item.isBookmarked == 1) {
        binding.bookmarkImageBtn.inVisible()
        binding.bookmarkRemoveBtn.visible()
    } else {
        binding.bookmarkImageBtn.visible()
        binding.bookmarkRemoveBtn.inVisible()
    }

    private fun TopAlbumsListItemBinding.onBookmark(it: View?) {
        if (it?.isVisible == true) {
            bookmarkImageBtn.inVisible()
            bookmarkRemoveBtn.visible()
        } else {
            bookmarkImageBtn.visible()
            bookmarkRemoveBtn.inVisible()
        }
    }

    private fun TopAlbumsListItemBinding.onBookmarkRemove() {
        bookmarkRemoveBtn.inVisible()
        bookmarkImageBtn.visible()
    }
}
