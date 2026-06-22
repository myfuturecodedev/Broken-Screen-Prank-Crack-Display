package com.futurecode.crackdisplayprank.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.model.PopularEffect

class PopularEffectAdapter(
    private val effectsList: List<PopularEffect>,
    private val onEffectSelected: (PopularEffect) -> Unit
) : RecyclerView.Adapter<PopularEffectAdapter.EffectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EffectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_effect, parent, false)
        return EffectViewHolder(view)
    }

    override fun onBindViewHolder(holder: EffectViewHolder, position: Int) {
        holder.bind(effectsList[position])
    }

    override fun getItemCount(): Int = effectsList.size

    inner class EffectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivEffect: ImageView = itemView.findViewById(R.id.ivPopularEffectImg)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvPopularEffectTitle)

        fun bind(effect: PopularEffect) {
            tvTitle.text = effect.title
            ivEffect.setImageResource(effect.imageResId)

            itemView.setOnClickListener {
                onEffectSelected(effect)
            }
        }
    }
}