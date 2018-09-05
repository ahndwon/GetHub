package com.ahndwon.gethub.ui.adapter

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.ahndwon.gethub.R
import com.ahndwon.gethub.api.dao.Content
import com.ahndwon.gethub.ui.viewholder.FileViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_file.view.*

class FileListAdapter : RecyclerView.Adapter<FileViewHolder>() {
    var files: List<Content> = emptyList()
    var onClick: ((View) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        return FileViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return files.count()
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {

        val item = files[position]
        with(holder.itemView) {
            when (item.type) {
//                "file" -> Glide.with(this.context).
//                        load(R.drawable.ic_file).
//                        into(itemFileImageView)
                "dir" -> Glide.with(this.context).
                        load(R.drawable.ic_file_directory).
                        into(itemFileImageView)
                else -> Glide.with(this.context).
                        load(R.drawable.ic_file).
                        into(itemFileImageView)
            }
            itemFileTitle.text = item.name
            onClick?.let {
                setOnClickListener(onClick)
            }
//            setOnClickListener {
//                val bundle = Bundle()
//                if(item.type == "dir") bundle.putString("path", item.path)
//            }

        }
    }
}