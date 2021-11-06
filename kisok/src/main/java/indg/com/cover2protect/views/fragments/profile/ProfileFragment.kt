package indg.com.cover2protect.views.fragments.profile


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


import indg.com.cover2protect.R
import indg.com.cover2protect.viewmodel.mainprofileviewmodel.ProfileViewModel
import indg.com.cover2protect.databinding.FragmentProfileBinding
import javax.inject.Inject
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_profile.*
import com.ramotion.cardslider.CardSliderLayoutManager
import com.ramotion.cardslider.CardSnapHelper
import indg.com.cover2protect.BR
import indg.com.cover2protect.adapter.article.ArticleAdapter
import indg.com.cover2protect.base.BaseFragmentBinding
import indg.com.cover2protect.helper.SaveSharedPreference
import indg.com.cover2protect.model.articles.Data
import indg.com.cover2protect.model.profile.Profile
import indg.com.cover2protect.util.cards.SliderAdapter
import indg.com.cover2protect.views.activity.health_profile.HealthProfileActivity
import android.net.Uri


class ProfileFragment : BaseFragmentBinding<FragmentProfileBinding>(), View.OnClickListener, indg.com.cover2protect.presenter.Profile_Data_Response, indg.com.cover2protect.presenter.ArticleInterface {

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.fragment_profile

    @Inject
    lateinit var viewmodel: ProfileViewModel
    private var binding: FragmentProfileBinding? = null
    private var dialog: Dialog? = null
    private var adapter_article: ArticleAdapter? = null
    private val pics = intArrayOf(R.drawable.health_img, R.drawable.hospital_img, R.drawable.pharmacy_img)
    private val names = arrayOf("Health", "Hospital", "Pharmacy")
    private val sliderAdapter = SliderAdapter(pics, 5, OnCardClickListener())
    private var layoutManger: CardSliderLayoutManager? = null
    private var recyclerView: RecyclerView? = null
    private var currentPosition: Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding
        activity!!.title = "Home"
        viewmodel!!.setNavigator(this)
        BindData()
        binding!!.buynowImg.setOnClickListener(this)

    }

    private fun BindData() {
        setdata()
        getImage()
        dialog = Dialog(activity)
        if (SaveSharedPreference.getDeviceStatus(activity)) {
            binding!!.hbcImg.setImageResource(R.drawable.device_green)
            binding!!.connectedStatus.text = "Connected"
        }
        else {
            binding!!.hbcImg.setImageResource(R.drawable.device_red)
            binding!!.connectedStatus.text = "Not Connected"
        }
        setdata_article()
        binding!!.viewallImg.setOnClickListener {

        }
        gethhidata()
        initRecyclerView()
    }


    override fun Onselected(data: Data) {

    }

    companion object {
        fun newInstance(): androidx.fragment.app.Fragment {
            return ProfileFragment()
        }
    }

    override fun onResponse(data: Profile) {
        hideLoading()

    }

    override fun onError(msg: String) {
        hideLoading()

    }


    private var mBottomSheetDialog: BottomSheetDialog? = null

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.buynow_img -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://cover2protect.com/buynow/")))
            }
        }
    }




    private fun getImage() {
        viewmodel.GetProfileImage().observe(this, androidx.lifecycle.Observer {
            if (!it.isNullOrEmpty()) {
                Glide.with(this!!.activity!!)
                        .load(it)
                        .into(binding!!.profileImg)


            } else {
                binding!!.profileImg.setImageResource(R.drawable.user_img)
            }
        })
    }

    private fun gethhidata() {
        viewmodel.GetHHI().observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                binding!!.hhiTv.text = it.score.toString() + "/" + it.total.toString()
            }
        })
    }

    private fun setdata_article() {
        viewmodel.GetArticles().observe(this, androidx.lifecycle.Observer {
            if (it!!.isNotEmpty()) {
                adapter_article = ArticleAdapter(activity!!, it)
                binding!!.articleRv.adapter = adapter_article
                adapter_article!!.setListener(this)
            }
        })


    }

    private fun setdata() {
        showLoading()
        viewmodel.GetProfileData().observe(this, androidx.lifecycle.Observer {
            hideLoading()
            if (it != null) {
                this.binding!!.model = it
                if (!it!!.data!!.bmi.isNullOrEmpty()) {
                    var data = it!!.data!!.bmi.toDouble()
                    when {
                        data <= 24.9 -> bmi_color_img.setImageResource(R.drawable.bmi_green)
                        data in 25.0..29.9 -> bmi_color_img.setImageResource(R.drawable.bmiyellow)
                        else -> bmi_color_img.setImageResource(R.drawable.bmi_red)
                    }
                }

            }
        })

    }

    private fun initRecyclerView() {
        binding!!.recyclerView.adapter = sliderAdapter
        binding!!.recyclerView.setHasFixedSize(true)
        binding!!.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange()
                }
            }
        })
        layoutManger = binding!!.recyclerView.getLayoutManager() as CardSliderLayoutManager?

        CardSnapHelper().attachToRecyclerView(binding!!.recyclerView)
    }


    private fun onActiveCardChange() {
        val pos = layoutManger!!.getActiveCardPosition()
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return
        }

        onActiveCardChange(pos)
    }


    private inner class OnCardClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            val lm = binding!!.recyclerView!!.getLayoutManager() as CardSliderLayoutManager?

            if (lm!!.isSmoothScrolling) {
                return
            }

            val activeCardPosition = lm.activeCardPosition
            if (activeCardPosition == RecyclerView.NO_POSITION) {
                return
            }

            val clickedPosition = binding!!.recyclerView!!.getChildAdapterPosition(view)
            if (clickedPosition == activeCardPosition) {
                var data = names[activeCardPosition % names.size]
                if (data == "Health") {
                    val intent = Intent(activity!!, HealthProfileActivity::class.java)
                    startActivity(intent)
                }


            } else if (clickedPosition > activeCardPosition) {
                binding!!.recyclerView!!.smoothScrollToPosition(clickedPosition)
                onActiveCardChange(clickedPosition)
            }
        }
    }

    private fun onActiveCardChange(pos: Int) {
        val animH = intArrayOf(R.anim.slide_in_right, R.anim.slide_out_left)
        val animV = intArrayOf(R.anim.slide_in_top, R.anim.slide_out_bottom)

        val left2right = pos < currentPosition
        if (left2right) {
            animH[0] = R.anim.slide_in_left
            animH[1] = R.anim.slide_out_right

            animV[0] = R.anim.slide_in_bottom
            animV[1] = R.anim.slide_out_top
        }

        setNameText(names[pos % names.size], left2right)


        currentPosition = pos
    }

    private fun setNameText(s: String, left2right: Boolean) {
        binding!!.nameHint.text = s
    }


}
