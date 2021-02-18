package com.aldidwiki.myquizapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aldidwiki.myquizapp.ui.game.QuestionFragment

class SectionsPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return QuestionFragment.newInstance(position + 1)
    }
}