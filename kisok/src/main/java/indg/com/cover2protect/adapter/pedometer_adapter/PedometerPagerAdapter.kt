package indg.com.cover2protect.adapter.pedometer_adapter

import indg.com.cover2protect.views.fragments.pedometer.day.DailyStepFragment
import indg.com.cover2protect.views.fragments.pedometer.month.MonthlyStepFragment
import indg.com.cover2protect.views.fragments.pedometer.week.WeeklyStepFragment

class PedometerPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return when (position) {
            0 -> {
                DailyStepFragment()
            }
            1 -> {
                WeeklyStepFragment()
            }

            else -> {
                return MonthlyStepFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "DAILY"
            1 -> "WEEKLY"
            else -> {
                return "MONTHLY"
            }
        }
    }
}