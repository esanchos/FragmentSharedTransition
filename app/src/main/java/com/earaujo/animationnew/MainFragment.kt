package com.earaujo.animationnew

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet
import com.earaujo.animationnew.DetailFragment.Companion.ARG_POSITION
import com.earaujo.animationnew.adapter.MainAdapter

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        view.findViewById<RecyclerView>(R.id.mainRecyclerView)?.setup()
        postponeEnterTransition()
        prepareTransitions()
        return view
    }

    private fun RecyclerView.setup() {
        this.adapter = MainAdapter(MainActivity.position, ::onItemClick) {
            startPostponedEnterTransition()
        }
    }

    private fun onItemClick(position: Int, viewImage: View, viewText: View) {
        val detailFragment = DetailFragment()
        val transition = TransitionInflater.from(context)
            .inflateTransition(R.transition.image_shared_element_transition)
        detailFragment.sharedElementEnterTransition = transition //getDetailsTransition()
//        detailFragment.sharedElementReturnTransition = transition
        detailFragment.arguments = Bundle().apply { putInt(ARG_POSITION, position) }

        MainActivity.position = position

        activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.setReorderingAllowed(true)
            ?.addSharedElement(viewImage, "${position}_image")
            ?.addSharedElement(viewText, "${position}_text")
            ?.replace(R.id.mainFrameLayout, detailFragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun getDetailsTransition(): TransitionSet {
        return TransitionSet()
            .addTransition(ChangeBounds())
            .addTransition(ChangeTransform())
            .addTransition(ChangeImageTransform())
            .apply {
                ordering = TransitionSet.ORDERING_TOGETHER
                duration = 1000
            }
    }

    private fun prepareTransitions() {
        exitTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.grid_exit_transition)
    }

}
