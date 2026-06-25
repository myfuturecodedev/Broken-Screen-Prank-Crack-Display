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

//class LanguageAdapter(
//    private val onLanguageSelected: (LanguageModel) -> Unit
//) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    private val VIEW_TYPE_HEADER = 0
//    private val VIEW_TYPE_ITEM = 1
//
//    private var displayList = ArrayList<LanguageListItem>()
//    private var selectedLangCode: String = "en" // Default selection to English
//
//    fun submitList(newList: ArrayList<LanguageListItem>, currentSelectedCode: String) {
//        this.selectedLangCode = currentSelectedCode
//        this.displayList = newList
//        notifyDataSetChanged()
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return when (displayList[position]) {
//            is LanguageListItem.Header -> VIEW_TYPE_HEADER
//            is LanguageListItem.LanguageItem -> VIEW_TYPE_ITEM
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        return if (viewType == VIEW_TYPE_HEADER) {
//            val view = inflater.inflate(R.layout.item_section_header, parent, false)
//            HeaderViewHolder(view)
//        } else {
//            val view = inflater.inflate(R.layout.item_language, parent, false)
//            LanguageViewHolder(view)
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val item = displayList[position]
//        if (holder is HeaderViewHolder && item is LanguageListItem.Header) {
//            holder.tvHeader.text = item.title
//        } else if (holder is LanguageViewHolder && item is LanguageListItem.LanguageItem) {
//            val lang = item.language
//            holder.bind(lang, lang.code == selectedLangCode)
//        }
//    }
//
//    override fun getItemCount(): Int = displayList.size
//
//    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val tvHeader: TextView = itemView.findViewById(R.id.tvSectionHeader)
//    }
//
//    inner class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val container: ConstraintLayout = itemView.findViewById(R.id.cardContainer)
//        private val tvFlag: TextView = itemView.findViewById(R.id.tvFlagEmoji)
//        private val tvNative: TextView = itemView.findViewById(R.id.tvLanguageNative)
//        private val tvEnglish: TextView = itemView.findViewById(R.id.tvLanguageEnglish)
//        private val imgRadio: ImageView = itemView.findViewById(R.id.imgRadioIcon)
//
//        fun bind(lang: LanguageModel, isSelected: Boolean) {
//            tvNative.text = lang.nativeName
//            tvEnglish.text = lang.englishName
//            tvFlag.text = lang.flagEmoji
//
//            // Selected Glow Accent State matching Figma
//            if (isSelected) {
//                container.setBackgroundResource(R.drawable.bg_language_card_selected)
//                imgRadio.setImageResource(R.drawable.bg_language_card_selected)
//            } else {
//                container.setBackgroundResource(R.drawable.bg_language_card_unselected)
//                imgRadio.setImageResource(R.drawable.ic_radio_unselected)
//            }
//
//            container.setOnClickListener {
//                onLanguageSelected(lang)
//            }
//        }
//    }
//}




/**
 * 15-Year Developer Standard: Multi-View RecyclerView Adapter.
 * ✅ FIXED: Merged Native Ads loading engine smoothly using layout view inflaters
 * and support for Activity-level window context references.
 */
class LanguageAdapter(
    private val activityContext: Activity,
    private val onLanguageSelected: (LanguageModel) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_HEADER = 0
    private val VIEW_TYPE_ITEM = 1
    private val VIEW_TYPE_AD = 2 // ✅ Added custom type for Native AD items

    private var displayList = ArrayList<LanguageListItem>()
    private var selectedLangCode: String = "en" // Default selection to English

    fun submitList(newList: ArrayList<LanguageListItem>, currentSelectedCode: String) {
        this.selectedLangCode = currentSelectedCode
        this.displayList = newList
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (displayList[position]) {
            is LanguageListItem.Header -> VIEW_TYPE_HEADER
            is LanguageListItem.LanguageItem -> VIEW_TYPE_ITEM
            is LanguageListItem.AdItem -> VIEW_TYPE_AD // ✅ Dynamic mapping of AD element references
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
                // ✅ Inflation of Ad card placeholder frame layouts inside the Recycler chain
                val view = inflater.inflate(R.layout.item_native_ad_placeholder, parent, false)
                AdItemViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unsupported Layout State processing metrics")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = displayList[position]
        when {
            holder is HeaderViewHolder && item is LanguageListItem.Header -> {
                holder.tvHeader.text = item.title
            }
            holder is LanguageViewHolder && item is LanguageListItem.LanguageItem -> {
                val lang = item.language
                holder.bind(lang, lang.code == selectedLangCode)
            }
            holder is AdItemViewHolder && item is LanguageListItem.AdItem -> {
                // ✅ Bind dynamic native Ad engine instance when card enters visible window
                holder.bind()
            }
        }
    }

    override fun getItemCount(): Int = displayList.size

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

            // Selected Glow Accent State matching Figma
            if (isSelected) {
                container.setBackgroundResource(R.drawable.bg_language_card_selected)
                imgRadio.setImageResource(R.drawable.bg_language_card_selected)
            } else {
                container.setBackgroundResource(R.drawable.bg_language_card_unselected)
                imgRadio.setImageResource(R.drawable.ic_radio_unselected)
            }

            container.setOnClickListener {
                onLanguageSelected(lang)
            }
        }
    }

    // ✅ NEW HOLDER: Safely captures layout widget resources to render Ad loads without UI delays
    inner class AdItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val frameLayout: FrameLayout = itemView.findViewById(R.id.frame_layout)
        private val relativeLayout: RelativeLayout = itemView.findViewById(R.id.relative_layout)
        private val placeholder: ShapeableImageView = itemView.findViewById(R.id.placeholder)

        fun bind() {
            try {
                // Triggers native ads rendering process securely on activity screen window context
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