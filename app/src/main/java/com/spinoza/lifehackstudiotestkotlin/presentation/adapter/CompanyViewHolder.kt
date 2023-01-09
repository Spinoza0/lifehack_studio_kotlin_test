package com.spinoza.lifehackstudiotestkotlin.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spinoza.lifehackstudiotestkotlin.R

class CompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageViewLogo: ImageView = itemView.findViewById(R.id.imageViewLogo)
    val textViewCompanyName: TextView = itemView.findViewById(R.id.textViewCompanyName)
}