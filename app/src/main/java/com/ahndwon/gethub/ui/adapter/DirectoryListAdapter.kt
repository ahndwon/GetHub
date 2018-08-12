package com.ahndwon.gethub.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.ahndwon.gethub.ui.viewholder.DirectoryViewHolder
import kotlinx.android.synthetic.main.fragment_code_files.view.*
import kotlinx.android.synthetic.main.item_directory.view.*

class DirectoryListAdapter : RecyclerView.Adapter<DirectoryViewHolder>() {
    var directoryList = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryViewHolder {
        return DirectoryViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return directoryList.count()
    }

    override fun onBindViewHolder(holder: DirectoryViewHolder, position: Int) {
        val item = directoryList[position]

        with(holder.itemView) {
            if(item == "") itemDirectoryTextView.text = "."
            else {
                val tokens = item.split("/")
                itemDirectoryTextView.text = tokens[tokens.size - 1]
            }
        }
    }
}