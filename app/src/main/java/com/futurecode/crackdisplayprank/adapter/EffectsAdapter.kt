package com.futurecode.crackdisplayprank.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.model.EffectItem

class EffectsAdapter(
    private val onEffectClicked: (EffectItem) -> Unit
) : ListAdapter<EffectItem, EffectsAdapter.EffectViewHolder>(EffectDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EffectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_effect_grid, parent, false)
        return EffectViewHolder(view)
    }

    override fun onBindViewHolder(holder: EffectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EffectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivHomeWallpaper: ImageView = itemView.findViewById(R.id.ivHomeWallpaper)

        fun bind(item: EffectItem) {
            ivHomeWallpaper.setImageResource(item.backgroundImageResId)
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onEffectClicked(item)
                }
            }
        }
    }

    private class EffectDiffCallback : DiffUtil.ItemCallback<EffectItem>() {
        override fun areItemsTheSame(oldItem: EffectItem, newItem: EffectItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EffectItem, newItem: EffectItem): Boolean {
            return oldItem == newItem
        }
    }
}