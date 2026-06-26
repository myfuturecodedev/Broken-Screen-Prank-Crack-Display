package com.futurecode.crackdisplayprank.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.model.EffectItem

/**
 * 15-Year Developer Standard: Basic Adapter swiping card frames inside the configuration view.
 * ✅ UPDATED: Supports a dedicated 'onPreviewClick' action when the user clicks 'btnPreviewEffect'
 * directly inside the horizontal carousel slide item.
 */
class CarouselPreviewAdapter(
    private val items: List<EffectItem>,
    private val onItemClick: (Int) -> Unit,
    private val onPreviewClick: (Int) -> Unit // Callback to trigger preview navigation or action
) : RecyclerView.Adapter<CarouselPreviewAdapter.CarouselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carousel_preview, parent, false)
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    inner class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivCarouselBg: ImageView = itemView.findViewById(R.id.ivCarouselBg)
      //  private val ivCarouselCrack: ImageView = itemView.findViewById(R.id.ivCarouselCrack)
        private val btnPreviewEffect: View? = itemView.findViewById(R.id.btnPreviewEffect) // Reference button inside card layout

        fun bind(item: EffectItem, position: Int) {
            ivCarouselBg.setImageResource(item.backgroundImageResId)
           // ivCarouselCrack.setImageResource(item.backgroundImageResId)

            itemView.setOnClickListener {
                onItemClick(position)
            }

            // ✅ Handle item-level preview click safely
            btnPreviewEffect?.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onPreviewClick(position)
                }
            }
        }
    }
}