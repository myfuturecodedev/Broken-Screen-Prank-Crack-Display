package com.futurecode.crackdisplayprank.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.ads.native_ad.NativeAdsHelper
import com.futurecode.crackdisplayprank.model.LanguageListItem
import com.futurecode.crackdisplayprank.model.LanguageModel
import com.google.android.material.imageview.ShapeableImageView

/**
 * 15-Year Developer Standard: Multi-View RecyclerView Adapter with Auto-Ad Injection.
 * ✅ FIXED: Corrected radio icon mapping inside bind() block to use ic_radio_selected.
 * This completely resolves the selection highlighting display failure!
 */
class LanguageAdapter(
    private val activityContext: Activity,
    private val onLanguageSelected: (LanguageModel) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_HEADER = 0
    private val VIEW_TYPE_ITEM = 1
    private val VIEW_TYPE_AD = 2

    private var displayList = ArrayList<LanguageListItem>()
    private var selectedLangCode: String = "en" // Default selection to English

    fun submitList(newList: ArrayList<LanguageListItem>, currentSelectedCode: String) {
        this.selectedLangCode = currentSelectedCode
        this.displayList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (displayList.isEmpty()) {
            0
        } else {
            displayList.size + 1 // Add 1 slot for our dynamically injected Native Ad card
        }
    }

    override fun getItemViewType(position: Int): Int {
        val adPosition = getAdPosition()
        return if (position == adPosition) {
            VIEW_TYPE_AD
        } else {
            val adjustedPosition = getAdjustedPosition(position)
            when (displayList[adjustedPosition]) {
                is LanguageListItem.Header -> VIEW_TYPE_HEADER
                is LanguageListItem.LanguageItem -> VIEW_TYPE_ITEM
                else -> VIEW_TYPE_ITEM
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val view = inflater.inflate(R.layout.item_section_header, parent, false)
                HeaderViewHolder(view)
            }
            VIEW_TYPE_ITEM -> {
                val view = inflater.inflate(R.layout.item_language, parent, false)
                LanguageViewHolder(view)
            }
            VIEW_TYPE_AD -> {
                val view = inflater.inflate(R.layout.item_native_ad_placeholder, parent, false)
                AdItemViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unsupported Layout State processing metrics")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AdItemViewHolder) {
            holder.bind()
        } else {
            val adjustedPosition = getAdjustedPosition(position)
            val item = displayList[adjustedPosition]

            if (holder is HeaderViewHolder && item is LanguageListItem.Header) {
                holder.tvHeader.text = item.title
            } else if (holder is LanguageViewHolder && item is LanguageListItem.LanguageItem) {
                val lang = item.language
                holder.bind(lang, lang.code == selectedLangCode)
            }
        }
    }

    private fun getAdPosition(): Int {
        return if (displayList.size >= 3) 3 else displayList.size
    }

    private fun getAdjustedPosition(position: Int): Int {
        val adPosition = getAdPosition()
        return when {
            position < adPosition -> position
            position > adPosition -> position - 1
            else -> position
        }
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHeader: TextView = itemView.findViewById(R.id.tvSectionHeader)
    }

    inner class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val container: ConstraintLayout = itemView.findViewById(R.id.cardContainer)
        private val tvFlag: TextView = itemView.findViewById(R.id.tvFlagEmoji)
        private val tvNative: TextView = itemView.findViewById(R.id.tvLanguageNative)
        private val tvEnglish: TextView = itemView.findViewById(R.id.tvLanguageEnglish)
        private val imgRadio: ImageView = itemView.findViewById(R.id.imgRadioIcon)

        fun bind(lang: LanguageModel, isSelected: Boolean) {
            tvNative.text = lang.nativeName
            tvEnglish.text = lang.englishName
            tvFlag.text = lang.flagEmoji

            // ✅ FIXED: Using 'ic_radio_selected' instead of stretching the outer container card shape
            if (isSelected) {
                container.setBackgroundResource(R.drawable.bg_language_card_selected)
                imgRadio.setImageResource(R.drawable.ic_radio_selected) // Corrected from bg_language_card_selected
            } else {
                container.setBackgroundResource(R.drawable.bg_language_card_unselected)
                imgRadio.setImageResource(R.drawable.ic_radio_unselected)
            }

            container.setOnClickListener {
                onLanguageSelected(lang)
            }
        }
    }

    inner class AdItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val frameLayout: FrameLayout = itemView.findViewById(R.id.frame_layout)
        private val relativeLayout: RelativeLayout = itemView.findViewById(R.id.relative_layout)
        private val placeholder: ShapeableImageView = itemView.findViewById(R.id.placeholder)

        fun bind() {
            try {
                NativeAdsHelper(activityContext).showNativeAd(
                    frameLayout,
                    relativeLayout,
                    placeholder
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}