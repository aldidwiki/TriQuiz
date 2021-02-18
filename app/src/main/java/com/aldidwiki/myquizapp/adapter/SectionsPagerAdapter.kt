package com.aldidwiki.myquizapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aldidwiki.myquizapp.ui.game.QuestionFragment

class SectionsPagerAdapter(parentFragment: Fragment) : FragmentStateAdapter(parentFragment) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return QuestionFragment.newInstance(position + 1)
    }
}