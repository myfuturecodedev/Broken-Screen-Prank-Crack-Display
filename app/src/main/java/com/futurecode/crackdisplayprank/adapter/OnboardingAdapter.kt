package com.futurecode.crackdisplayprank.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.model.OnboardingModel

class OnboardingAdapter(
    private val pages: List<OnboardingModel>
) : RecyclerView.Adapter<OnboardingAdapter.PageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding_page, parent, false)
        return PageViewHolder(view)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holder.bind(pages[position])
    }

    override fun getItemCount(): Int = pages.size

    class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivBackgroundCrack: ImageView = itemView.findViewById(R.id.ivBackgroundCrack)
       // private val ivMainIllustration: ImageView = itemView.findViewById(R.id.ivMainIllustration)
        private val tvTitleLine1: TextView = itemView.findViewById(R.id.tvTitleLine1)
        private val tvTitleLine2: TextView = itemView.findViewById(R.id.tvTitleLine2)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)

        fun bind(page: OnboardingModel) {
            tvTitleLine1.text = page.titleLine1
            tvTitleLine2.text = page.titleLine2
            tvDescription.text = page.description

            // Render specific images dynamically
           // ivMainIllustration.setImageResource(page.illustrationResId)

            if (page.crackBgResId != 0) {
                ivBackgroundCrack.setImageResource(page.crackBgResId)
                ivBackgroundCrack.visibility = View.VISIBLE
            } else {
                ivBackgroundCrack.visibility = View.GONE
            }
        }
    }
}