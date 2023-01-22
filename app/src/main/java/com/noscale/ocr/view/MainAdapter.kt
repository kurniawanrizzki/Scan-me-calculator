package com.noscale.ocr.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.noscale.ocr.data.Expression
import com.noscale.ocr.databinding.ItemMainBinding

class MainAdapter : ListAdapter<Expression, MainAdapter.MainViewHolder>(MainItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        newInstance(LayoutInflater.from(parent.context), parent)

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.bind(getItem(position))

    class MainViewHolder(private val binding: ItemMainBinding) : ViewHolder(binding.root) {
        fun bind(item: Expression) = with(binding) {
            tvTitle.text = "input: ${item.name}"
            tvSubtitle.text = "result: ${item.result}"
        }
    }

    class MainItemCallback : DiffUtil.ItemCallback<Expression>() {
        override fun areItemsTheSame(oldItem: Expression, newItem: Expression): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Expression, newItem: Expression): Boolean =
            oldItem == newItem
    }

    companion object {
        fun newInstance(
            inflater: LayoutInflater,
            parent: ViewGroup
        ): MainViewHolder = MainViewHolder(
            ItemMainBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }
}