package com.spinoza.lifehackstudiotestkotlin

import androidx.recyclerview.widget.DiffUtil

class CompanyItemDiffUtilCallback(
    private var oldCompaniesList: List<CompanyItem>,
    private var newCompaniesList: List<CompanyItem>
) : DiffUtil.Callback()
{

    override fun getOldListSize(): Int {
        return oldCompaniesList.size
    }

    override fun getNewListSize(): Int {
        return newCompaniesList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldCompaniesList[oldItemPosition]
        val newItem = newCompaniesList[newItemPosition]
        return oldItem.getId() == newItem.getId()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldCompaniesList[oldItemPosition]
        val newItem = newCompaniesList[newItemPosition]
        return oldItem.getName().equals(newItem.getName()) &&
                oldItem.getImg().equals(newItem.getImg())
    }
}