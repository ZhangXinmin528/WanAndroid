package com.coding.zxm.wanandroid

import androidx.fragment.app.Fragment
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.gallery.BingWallpapersFragment
import com.coding.zxm.wanandroid.home.HomeFragment
import com.coding.zxm.wanandroid.mine.MineFragment
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val fragments = ArrayList<Fragment>()

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initParamsAndValues() {

        setStatusBarColor()
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
    }


}