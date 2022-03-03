package com.coding.zxm.wanandroid

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.upgrade.UpgradeManager
import com.coding.zxm.upgrade.network.IUpgradeProvider
import com.coding.zxm.upgrade.network.UpgradeProgressProvider
import com.coding.zxm.wanandroid.gallery.BingWallpapersFragment
import com.coding.zxm.wanandroid.home.HomeFragment
import com.coding.zxm.wanandroid.mine.MineFragment
import com.zxm.utils.core.bar.StatusBarCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val fragments = ArrayList<Fragment>()
    private var mLastReturnTime: Long = 0
    private var mProvider: IUpgradeProvider? = null

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initParamsAndValues() {
        StatusBarCompat.setTranslucentForImageViewInFragment(this, 0, null)
        StatusBarCompat.setStatusBarLightMode(this,false)
        mProvider = UpgradeProgressProvider(this)

        fragments.add(HomeFragment.newInstance())
        fragments.add(BingWallpapersFragment.newInstance())
        fragments.add(MineFragment.newInstance())

    }

    override fun initViews() {
        //检测更新
        UpgradeManager.getInstance().checkUpgrade(mProvider)

        vp_home.adapter = HomePageAdapter(fragments, supportFragmentManager)

        vp_home.offscreenPageLimit = 2

        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    vp_home.currentItem = 0
                    StatusBarCompat.setStatusBarLightMode(this,false)
                    true
                }

                R.id.action_gallery -> {
                    vp_home.currentItem = 1
                    StatusBarCompat.setStatusBarLightMode(this,true)
                    true
                }

                R.id.action_mine -> {
                    vp_home.currentItem = 2
                    StatusBarCompat.setStatusBarLightMode(this,false)
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

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - mLastReturnTime > 5 * 1000L) {
            mLastReturnTime = currentTime
            Toast.makeText(this, getString(R.string.all_exit_tips), Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }

    }

}