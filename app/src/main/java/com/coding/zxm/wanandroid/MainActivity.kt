package com.coding.zxm.wanandroid

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.home.HomeFragment
import com.coding.zxm.wanandroid.mine.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val fragments = ArrayList<Fragment>()

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initParamsAndValues() {
        setStatusBarColorNoTranslucent()
        fragments.add(HomeFragment.newInstance())
        fragments.add(Fragment())
        fragments.add(MineFragment.newInstance())
    }

    override fun initViews() {
        vp_home.adapter = HomePageAdapter(fragments, supportFragmentManager)

        vp_home.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                bottom_nav.menu.getItem(position).isChecked = true
            }
        })
        vp_home.offscreenPageLimit = 2

        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    vp_home.currentItem = 0
                    true
                }

                R.id.action_list -> {
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
        bottom_nav.selectedItemId = R.id.action_home

    }


}