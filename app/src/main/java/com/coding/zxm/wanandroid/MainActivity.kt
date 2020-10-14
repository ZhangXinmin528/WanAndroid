package com.coding.zxm.wanandroid

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.home.HomeFragment
import com.coding.zxm.wanandroid.mine.MineFragment
import com.example.kotlinlearning.HomePageAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val fragments = ArrayList<Fragment>()

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initParamsAndValues() {
        setStatusBarColorNoTranslucent()
        fragments.add(HomeFragment.newInstance())
        fragments.add(HomeFragment.newInstance())
        fragments.add(MineFragment.newInstance())
    }

    override fun initViews() {

        vp_home.adapter = HomePageAdapter(fragments, supportFragmentManager)
        vp_home.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                if (fragments.size > position) {
                    bottom_nav.menu.getItem(position).isChecked = true
                }
            }
        })

        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.vp_home -> {
                    vp_home.currentItem = 0
                }

                R.id.list -> {
                    vp_home.currentItem = 1
                }

                R.id.mine -> {
                    vp_home.currentItem = 2

                }
            }
            false
        }
    }
}