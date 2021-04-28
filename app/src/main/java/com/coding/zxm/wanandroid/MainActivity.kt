package com.coding.zxm.wanandroid

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.gallery.BingWallpapersFragment
import com.coding.zxm.wanandroid.home.HomeFragment
import com.coding.zxm.wanandroid.mine.MineFragment
import com.zxm.utils.core.bar.StatusBarCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val fragments = ArrayList<Fragment>()

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initParamsAndValues() {
        StatusBarCompat.setTranslucentForImageViewInFragment(this, 0, null)

        fragments.add(HomeFragment.newInstance())
        fragments.add(BingWallpapersFragment.newInstance())
        fragments.add(MineFragment.newInstance())
    }

    override fun initViews() {
        vp_home.adapter = HomePageAdapter(fragments, supportFragmentManager)

        vp_home.offscreenPageLimit = 2

        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    vp_home.currentItem = 0
                    true
                }

                R.id.action_gallery -> {
                    vp_home.currentItem = 1
                    true
                }

                R.id.action_mine -> {
                    vp_home.currentItem = 2
                    true

                }
                else -> false
            }
        }

        vp_home.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                val fragment = fragments.get(position) as BaseStatusBarFragment
                if (position == 2) {
                    fragment.setFakeStatusColor(R.color.color_tool_bar_primary)
                }

            }

        })
    }


}