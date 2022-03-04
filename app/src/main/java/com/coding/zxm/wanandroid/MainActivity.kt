package com.coding.zxm.wanandroid

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.upgrade.UpgradeManager
import com.coding.zxm.upgrade.network.IUpgradeProvider
import com.coding.zxm.upgrade.network.UpgradeProgressProvider
import com.coding.zxm.wanandroid.databinding.ActivityMainBinding
import com.coding.zxm.wanandroid.gallery.BingWallpapersFragment
import com.coding.zxm.wanandroid.home.HomeFragment
import com.coding.zxm.wanandroid.mine.MineFragment
import com.zxm.utils.core.bar.StatusBarCompat

class MainActivity : BaseActivity() {
    private val fragments = ArrayList<Fragment>()
    private var mLastReturnTime: Long = 0
    private var mProvider: IUpgradeProvider? = null
    private lateinit var mainBinding: ActivityMainBinding

    override fun setContentLayout(): Any {
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        return mainBinding.root
    }


    override fun initParamsAndValues() {
        StatusBarCompat.setTranslucentForImageViewInFragment(this, 0, null)
        StatusBarCompat.setStatusBarLightMode(this, false)
        mProvider = UpgradeProgressProvider(this)

        fragments.add(HomeFragment.newInstance())
        fragments.add(BingWallpapersFragment.newInstance())
        fragments.add(MineFragment.newInstance())

    }

    override fun initViews() {
        //检测更新
        UpgradeManager.getInstance().checkUpgrade(mProvider)
        mainBinding.vpHome.adapter = HomePageAdapter(fragments, supportFragmentManager)

        mainBinding.vpHome.offscreenPageLimit = 2

        mainBinding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    mainBinding.vpHome.currentItem = 0
                    StatusBarCompat.setStatusBarLightMode(this, false)
                    true
                }

                R.id.action_gallery -> {
                    mainBinding.vpHome.currentItem = 1
                    StatusBarCompat.setStatusBarLightMode(this, true)
                    true
                }

                R.id.action_mine -> {
                    mainBinding.vpHome.currentItem = 2
                    StatusBarCompat.setStatusBarLightMode(this, false)
                    true

                }
                else -> false
            }
        }

        mainBinding.vpHome.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
//                val fragment = fragments.get(position) as BaseStatusBarFragment
//                if (position == 2) {
//                    fragment.setFakeStatusColor(R.color.colorWhite)
//                }

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