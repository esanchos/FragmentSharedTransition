package com.earaujo.animationnew

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.earaujo.animationnew.model.util.DataGenerator.mainModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private var position = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        postponeEnterTransition()
        position = arguments?.getInt(ARG_POSITION, 0) ?: 0
        ViewCompat.setTransitionName(view.findViewById(R.id.mainImageView), "${position}_image")
        ViewCompat.setTransitionName(view.findViewById(R.id.titleTextView), "${position}_text")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup() {
//            (view as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
//            }
        }
    }

    private fun setup(callback: () -> Unit) {
        titleTextView.text = mainModel[position].name
        Glide
            .with(this)
            .load(mainModel[position].imageUrl)
//            .apply {
//                RequestOptions().dontTransform()
//            }
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    callback()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    callback()
                    return false
                }

            })
            .into(mainImageView)
    }

    companion object {
        const val ARG_POSITION = "arg_position"
    }

}