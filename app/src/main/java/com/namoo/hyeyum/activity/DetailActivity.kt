package com.namoo.hyeyum.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.namoo.hyeyum.R
import com.namoo.hyeyum.database.HyeyumEntity
import com.namoo.hyeyum.util.ActivityUtil
import com.namoo.hyeyum.util.DatabaseUtil
import com.namoo.hyeyum.util.ResourceUtil
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity()
{
    private val selectedHyeyum = HyeyumEntity()

    //라이프사이클
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        ActivityUtil.initFlag(this, false)

        getData()
        initUI()
    }
    override fun onBackPressed()
    {
        supportFinishAfterTransition()
    }

    //설정
    private fun getData()
    {
        selectedHyeyum.date = intent.getStringExtra("date") ?: ResourceUtil.getString(R.string.hyeyum_error_date)
        selectedHyeyum.state = intent.getIntExtra("state", 0)
        selectedHyeyum.hyeyum = intent.getStringExtra("hyeyum") ?: ""
        selectedHyeyum.emotion = intent.getStringExtra("emotion") ?: ""
    }
    private fun initUI()
    {
        //색상
        when (selectedHyeyum.state)
        {
            DatabaseUtil.STATE_KEEP ->
            {
                detail_card_background.setCardBackgroundColor(ResourceUtil.getColor(R.color.card_bright))
                detail_card_information.setTextColor(ResourceUtil.getColor(R.color.text_semi_dark))
                detail_card_hyeyum.setTextColor(ResourceUtil.getColor(R.color.text_dark))
            }
            DatabaseUtil.STATE_FORGET ->
            {
                detail_card_background.setCardBackgroundColor(ResourceUtil.getColor(R.color.card_dark))
                detail_card_information.setTextColor(ResourceUtil.getColor(R.color.text_semi_bright))
                detail_card_hyeyum.setTextColor(ResourceUtil.getColor(R.color.text_bright))
            }
        }

        //텍스트
        detail_card_information.text = intent.getStringExtra("information")
        detail_card_hyeyum.text = selectedHyeyum.hyeyum
    }

    //클릭이벤트
    fun close(view: View)
    {
        onBackPressed()
    }
}