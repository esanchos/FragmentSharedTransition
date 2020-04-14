package com.earaujo.animationnew.adapter

import android.graphics.drawable.Drawable
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.earaujo.animationnew.R
import com.earaujo.animationnew.model.util.DataGenerator.mainModel
import kotlinx.android.synthetic.main.item_main_list.view.*

class MainAdapter(
    private val clickedPosition: Int?,
    private val callback: ((Int, View, View) -> Unit)?,
    private val loadReady: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        getViewHolder(parent, LayoutInflater.from(parent.context))

    private fun getViewHolder(parent: ViewGroup, inflater: LayoutInflater) =
        BrandVH(inflater.inflate(R.layout.item_main_list, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as BrandVH).bind()

    override fun getItemCount() = mainModel.size

    inner class BrandVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            ViewCompat.setTransitionName(itemView.mainImageView, "${layoutPosition}_image")
            ViewCompat.setTransitionName(itemView.titleTextView, "${layoutPosition}_text")
            itemView.titleTextView.text = mainModel[layoutPosition].name
            Glide
                .with(itemView.context)
                .load(mainModel[layoutPosition].imageUrl)
//                .apply {
//                    RequestOptions().dontTransform().dontAnimate()
//                }
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        if (layoutPosition == 3) loadReady()
//                        clickedPosition?.let { if (adapterPosition == it) loadReady() }
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        if (layoutPosition == 3) loadReady()
//                        clickedPosition?.let { if (adapterPosition == it) loadReady() }
                        return false
                    }

                })
                .into(itemView.mainImageView)
            itemView.setOnClickListener {
                callback?.let {
                    it(
                        layoutPosition,
                        itemView.mainImageView,
                        itemView.titleTextView
                    )
                }
            }
        }
    }

}