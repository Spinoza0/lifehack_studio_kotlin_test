package com.spinoza.lifehackstudiotestkotlin

import androidx.recyclerview.widget.DiffUtil

class CompanyItemDiffUtilCallback(
    private var oldList: List<CompanyItem>,
    private var newList: List<CompanyItem>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.name == newItem.name && oldItem.img == newItem.img
    }
}