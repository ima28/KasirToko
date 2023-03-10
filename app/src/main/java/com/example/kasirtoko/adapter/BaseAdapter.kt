package com.example.kasirtoko.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter {

    abstract class Recyclerview<Model, VH : RecyclerView.ViewHolder?> : RecyclerView.Adapter<VH>() {
        protected var currentList = mutableListOf<Model>()
        var onItemClickCallback: OnItemClickCallback<Model>? = null

        fun setOnItemClickListener(callback: (view: View, item: Model, position: Int)->Unit) {
            this.onItemClickCallback = object : OnItemClickCallback<Model> {
                override fun onItemClick(view: View, item: Model, position: Int) {
                    callback(view, item, position)
                }
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        fun submitList(list: List<Model>) {
            currentList.clear()
            currentList.addAll(list)
            notifyDataSetChanged()
        }

        override fun getItemCount() = currentList.size
    }

    abstract class Listadapter <Model, VH : RecyclerView.ViewHolder>(diffCallback: DiffUtil.ItemCallback<Model>) : ListAdapter<Model, VH>(diffCallback) {

        protected var onItemClickCallback: OnItemClickCallback<Model>? = null

        fun setOnItemClickListener(listener: (view: View, model: Model, position: Int)->Unit) {
            onItemClickCallback = object : OnItemClickCallback<Model> {
                override fun onItemClick(view: View, item: Model, position: Int) {
                    listener(view, item, position)
                }
            }
        }

    }

    interface OnItemClickCallback<Item> {
        fun onItemClick(view: View, item: Item, position: Int)
    }
}