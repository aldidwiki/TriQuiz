package com.aldidwiki.myquizapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aldidwiki.myquizapp.helper.Constant
import com.aldidwiki.myquizapp.ui.game.QuestionFragment

class SectionsPagerAdapter(parentFragment: Fragment) : FragmentStateAdapter(parentFragment) {
    override fun getItemCount(): Int = Constant.QUESTION_COUNT

    override fun createFragment(position: Int): Fragment {
        return QuestionFragment.newInstance(position + 1)
    }
}