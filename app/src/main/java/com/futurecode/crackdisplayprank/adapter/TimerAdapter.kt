package com.futurecode.crackdisplayprank.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.futurecode.crackdisplayprank.databinding.ItemTimerBinding
import com.futurecode.crackdisplayprank.model.TimerOption

//class TimerAdapter(
//    private val onClick: (Int) -> Unit
//) : ListAdapter<TimerOption,
//        TimerAdapter.TimerVH>(DiffUtilCallback()) {
//
//    inner class TimerVH(
//        private val binding: ItemTimerBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(item: TimerOption) {
//
//            binding.txtTimer.text = item.title
//
//            binding.root.strokeColor =
//                if (item.isSelected)
//                    Color.CYAN
//                else
//                    Color.TRANSPARENT
//
//            binding.root.setOnClickListener {
//                onClick(adapterPosition)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): TimerVH {
//
//        return TimerVH(
//            ItemTimerBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(
//        holder: TimerVH,
//        position: Int
//    ) {
//        holder.bind(getItem(position))
//    }
//}
//
//class DiffUtilCallback :
//    DiffUtil.ItemCallback<TimerOption>() {
//
//    override fun areItemsTheSame(
//        oldItem: TimerOption,
//        newItem: TimerOption
//    ) = oldItem.title == newItem.title
//
//    override fun areContentsTheSame(
//        oldItem: TimerOption,
//        newItem: TimerOption
//    ) = oldItem == newItem
//}