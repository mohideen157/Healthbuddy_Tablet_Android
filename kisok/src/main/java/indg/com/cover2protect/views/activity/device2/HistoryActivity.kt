package indg.com.cover2protect.views.activity.device2

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import indg.com.cover2protect.BR
import indg.com.cover2protect.R
import indg.com.cover2protect.base.BaseActivityBinding
import indg.com.cover2protect.databinding.ActivityHistory2Binding
import indg.com.cover2protect.util.bottom_navigation.C2PBottomNavigation
import indg.com.cover2protect.viewmodel.Device2.HomeViewModel
import indg.com.cover2protect.views.fragments.History.AccountFragment.AccountFragment
import indg.com.cover2protect.views.fragments.History.DeviceFragment.DeviceFragment
import indg.com.cover2protect.views.fragments.History.ExcerciseFragment.ExcerciseFragment
import indg.com.cover2protect.views.fragments.History.HomeFragment.HomeFragment
import kotlinx.android.synthetic.main.activity_history2.*
import javax.inject.Inject

class HistoryActivity : BaseActivityBinding<ActivityHistory2Binding>() , HasSupportFragmentInjector {
    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.activity_history2

    companion object {
        private const val ID_HOME = 1
        private const val ID_EXCERCISE = 2
        private const val ID_DEVICE = 3
        private const val ID_ACCOUNT = 4
    }

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    @Inject
    lateinit var viewModel:HomeViewModel

    private var binding:ActivityHistory2Binding?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        setSupportActionBar(toolbar)
        title = "Device Detail"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        toolbar.setNavigationOnClickListener { arrow -> onBackPressed() }
        if (savedInstanceState == null) {
            navigateToFragment(HomeFragment.newInstance())
        }
        bottomNavigation.add(C2PBottomNavigation.Model(ID_HOME, R.drawable.ic_home))
        bottomNavigation.add(C2PBottomNavigation.Model(ID_EXCERCISE, R.drawable.systolic))
        bottomNavigation.add(C2PBottomNavigation.Model(ID_DEVICE, R.drawable.ic_directions_run_black_24dp))
        bottomNavigation.add(C2PBottomNavigation.Model(ID_ACCOUNT, R.drawable.heartcount))

        bottomNavigation.setOnShowListener {
            when (it.id) {
                ID_HOME -> {
                    navigateToFragment(HomeFragment.newInstance())

                }
                ID_EXCERCISE -> {
                    navigateToFragment(ExcerciseFragment.newInstance())

                }
                ID_DEVICE -> {
                    navigateToFragment(DeviceFragment.newInstance())

                }
                ID_ACCOUNT -> {
                    navigateToFragment(AccountFragment.newInstance())

                }
                else -> ""
            }
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {

        return fragmentDispatchingAndroidInjector
    }


    private fun navigateToFragment(fragmentToNavigate: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.contentlayout, fragmentToNavigate)
        fragmentTransaction.setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.commit()
    }

}
