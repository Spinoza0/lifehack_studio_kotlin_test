package com.spinoza.lifehackstudiotestkotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spinoza.lifehackstudiotestkotlin.R
import com.spinoza.lifehackstudiotestkotlin.domain.CompanyItem
import com.spinoza.lifehackstudiotestkotlin.presentation.adapter.CompaniesAdapter.CompanyViewHolder

class CompaniesAdapter : RecyclerView.Adapter<CompanyViewHolder>() {
    private var companies: List<CompanyItem> = ArrayList()
    var onCompanyClickListener: ((CompanyItem) -> Unit)? = null

    override fun getItemCount() = companies.size

    class CompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewLogo: ImageView
        val textViewCompanyName: TextView

        init {
            imageViewLogo = itemView.findViewById(R.id.imageViewLogo)
            textViewCompanyName = itemView.findViewById(R.id.textViewCompanyName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CompanyViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.company_item, parent, false)
        )


    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        with(holder) {
            with(companies[position]) {
                Glide.with(itemView)
                    .load(getFullImgUrl())
                    .error(R.drawable.no_logo)
                    .into(holder.imageViewLogo)
                holder.textViewCompanyName.text = name
                holder.itemView.setOnClickListener {
                    onCompanyClickListener?.invoke(this)
                }
            }
        }
    }

    fun setCompanies(companies: List<CompanyItem>) {
        val diffUtilCallback = DiffUtilCallback(this.companies, companies)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        this.companies = companies
        diffResult.dispatchUpdatesTo(this)
    }

    private class DiffUtilCallback(
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
}