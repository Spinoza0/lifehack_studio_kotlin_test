package com.spinoza.lifehackstudiotestkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spinoza.lifehackstudiotestkotlin.CompaniesAdapter.CompanyViewHolder

class CompaniesAdapter : RecyclerView.Adapter<CompanyViewHolder>() {
    private var companies: List<CompanyItem> = ArrayList()
    var onCompanyClickListener: OnCompanyClickListener? = null

    interface OnCompanyClickListener {
        fun onCompanyClick(company: CompanyItem)
    }

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
                    .load(getUrl())
                    .error(R.drawable.no_logo)
                    .into(holder.imageViewLogo)
                holder.textViewCompanyName.text = getName()
                holder.itemView.setOnClickListener {
                    onCompanyClickListener?.onCompanyClick(this)
                }
            }
        }
    }

    fun setCompanies(companies: List<CompanyItem>) {
        val diffUtilCallback = CompanyItemDiffUtilCallback(this.companies, companies)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        this.companies = companies
        diffResult.dispatchUpdatesTo(this)
    }
}