package com.example.materialdesign_pictureoftheday.ui.recycler_activity

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}