package com.example.themoviedb.ui.core

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<VH : BaseAdapter.BaseViewHolder<Item>, Item> :
    RecyclerView.Adapter<VH>() {

    var items: ArrayList<Item> = ArrayList()

    var onClick: OnClick<Item>? = null

    abstract fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean

    abstract fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean

    abstract fun createHolder(parent: ViewGroup, viewType: Int): VH


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))

        holder.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createHolder(parent, viewType)
    }


    open fun getItem(position: Int): Item {
        return items[position]
    }


    open fun add(newItem: Item) {
        items.add(newItem)
    }

    protected open fun add(newItems: List<Item>) {
        items.addAll(newItems)
    }

    fun clear() {
        items.clear()
    }

    open fun changeAdapterData(newItems: List<Item>) {
        val oldItems = emptyList<Item>().toMutableList()
        oldItems += items
        clear()
        add(newItems)
        val diffUtil = object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val old = oldItems[oldItemPosition]
                val new = getItem(newItemPosition)
                return areItemsTheSame(old, new)
            }

            override fun getOldListSize(): Int {
                return oldItems.size
            }

            override fun getNewListSize(): Int {
                return itemCount
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val old = oldItems[oldItemPosition]
                val new = getItem(newItemPosition)
                return areContentsTheSame(old, new)
            }
        }
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        diffResult.dispatchUpdatesTo(this)
    }


    fun setOnClick(click: (Item?, View) -> Unit, longClick: (Item?, View) -> Unit = { _, _ -> }) {
        onClick = object : OnClick<Item> {
            override fun onClick(item: Item?, view: View) {
                click(item, view)
            }

            override fun onLongClick(item: Item?, view: View) {
                longClick(item, view)
            }
        }
    }

    interface OnClick<Item> {
        fun onClick(item: Item?, view: View)
        fun onLongClick(item: Item?, view: View)
    }

    abstract class BaseViewHolder<Item>(protected val view: View) : RecyclerView.ViewHolder(view) {
        var onClick: OnClick<Item>? = null
        var item: Item? = null

        init {
            view.setOnClickListener {
                onClick?.onClick(item, it)
            }
            view.setOnLongClickListener {
                onClick?.onLongClick(item, it)
                true
            }
        }

        protected abstract fun onBind(item: Item)

        fun bind(item: Item) {
            this.item = item

            onBind(item)
        }

    }
}