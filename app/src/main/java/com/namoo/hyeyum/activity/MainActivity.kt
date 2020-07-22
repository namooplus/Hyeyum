package com.namoo.hyeyum.activity

import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Pair.create
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.room.Database
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.namoo.hyeyum.R
import com.namoo.hyeyum.database.HyeyumEntity
import com.namoo.hyeyum.database.HyeyumDB
import com.namoo.hyeyum.util.ActivityUtil
import com.namoo.hyeyum.util.DatabaseUtil
import com.namoo.hyeyum.util.ResourceUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity()
{
    //라이프사이클
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityUtil.initFlag(this, false)

        registerClickEvent()
        registerChangeEvent()
        initUI()
    }
    override fun onBackPressed()
    {
        if (main_root_view.progress < 0.5)
        {
            if (main_write_editor.text.toString().trim() == "") finish()
            else
            {
                MaterialAlertDialogBuilder(this)
                    .setTitle(ResourceUtil.getString(R.string.main_class_dialog_cancel_title))
                    .setMessage(ResourceUtil.getString(R.string.main_class_dialog_cancel_message))
                    .setPositiveButton(ResourceUtil.getString(R.string.main_class_dialog_cancel_positive)) {_, _ ->
                        finish()
                    }
                    .setNegativeButton(ResourceUtil.getString(R.string.main_class_dialog_cancel_negative)) {_, _ ->
                        main_root_view.transitionToEnd()
                    }
                    .show()
            }
        }
        else reset(false)
    }

    //설정
    private fun registerClickEvent()
    {
        main_list_card.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }
    }
    private fun registerChangeEvent()
    {
        main_root_view.setTransitionListener(object: MotionLayout.TransitionListener
        {
            override fun onTransitionTrigger(motionLayout: MotionLayout?, p1: Int, p2: Boolean, p3: Float)
            {

            }
            override fun onTransitionStarted(motionLayout: MotionLayout?, p1: Int, p2: Int)
            {
                main_write_editor.visibility = View.VISIBLE
            }
            override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float)
            {
                main_write_editor.alpha = when (startId)
                {
                    R.id.main_scene_init -> progress
                    R.id.main_scene_write -> 1f - progress
                    else -> progress
                }
            }
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int)
            {
                main_write_editor.visibility = when (currentId)
                {
                    R.id.main_scene_init -> View.GONE
                    R.id.main_scene_write -> View.VISIBLE
                    else -> View.GONE
                }
            }
        })
    }
    private fun initUI()
    {
        //날짜
        val dateFormat = SimpleDateFormat("yyyy.MM.dd")
        val currentDate = dateFormat.format(Date())

        main_write_date.text = currentDate

        //혜윰 개수
        Thread(Runnable {
            val count = DatabaseUtil.getCount()+1
            runOnUiThread {
                main_hyeyum_state.text = count.toString()
            }
        }).start()
    }

    //클릭 이벤트
    fun close(view: View)
    {
        reset(false)
    }
    fun keep(view: View)
    {
        saveHyeyum(true)
    }
    fun forget(view: View)
    {
        saveHyeyum(false)
    }

    //메소드
    private fun reset(eraseWritingHyeyum: Boolean)
    {
        //키보드 가리기
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(main_write_editor.windowToken, 0)

        //초기 상태
        main_root_view.transitionToStart()

        //작성중인 혜윰 작성 취소
        if (eraseWritingHyeyum) main_write_editor.setText("")
    }
    private fun saveHyeyum(keep: Boolean)
    {
        //DB저장
        val date = main_write_date.text.toString()
        val hyeyum = main_write_editor.text.toString()
        val state = if(keep) DatabaseUtil.STATE_KEEP else DatabaseUtil.STATE_FORGET
        val emotion = ""

        //필터링
        if (hyeyum.trim() == "")
        {
            reset(true)
            Toast.makeText(this, ResourceUtil.getString(R.string.main_class_toast_cancel), Toast.LENGTH_SHORT).show()
            return
        }

        DatabaseUtil.put(date, hyeyum, state, emotion)

        //초기화
        reset(true)

        //갱신
        initUI()

        //안내
        val shake = ObjectAnimator.ofFloat(main_list_card, "translationY", 0f, 25f, -25f, 25f, -25f, 25f, -25f, 9f, -9f, 0f)
        shake.duration = 900
        shake.start()

        Toast.makeText(this, if (keep) ResourceUtil.getString(R.string.main_class_toast_keep) else ResourceUtil.getString(R.string.main_class_toast_forget), Toast.LENGTH_SHORT).show()
    }
}
