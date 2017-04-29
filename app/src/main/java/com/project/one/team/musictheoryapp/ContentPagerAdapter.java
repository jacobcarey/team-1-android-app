package com.project.one.team.musictheoryapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * <p>Pager Adapter for holding {@link ContentFragment ContentFragments} and
 * {@link QuizLinkFragment QuizLinkFragments}.</p>
 *
 * <p>Passed to a {@link android.support.v4.view.ViewPager ViewPager} to render the
 * Fragments/pages.</p>
 *
 * @author Team One
 */

public class ContentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public ContentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
