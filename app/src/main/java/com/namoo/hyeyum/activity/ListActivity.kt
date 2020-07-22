package com.namoo.hyeyum.activity

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.namoo.hyeyum.App.Companion.context
import com.namoo.hyeyum.R
import com.namoo.hyeyum.adapter.HyeyumAdapter
import com.namoo.hyeyum.database.HyeyumEntity
import com.namoo.hyeyum.util.ActivityUtil
import com.namoo.hyeyum.util.DatabaseUtil
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity()
{
    //라이프사이클
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        ActivityUtil.initFlag(this, false)

        loadHyeyumTask().execute()
    }

    //설정

    //클릭이벤트
    fun back(view: View)
    {
        finish()
    }

    //쓰레드
    private inner class loadHyeyumTask() : AsyncTask<Void, Void, List<HyeyumEntity>>()
    {
        override fun onPreExecute()
        {
            super.onPreExecute()
        }
        override fun doInBackground(vararg params: Void?): List<HyeyumEntity>
        {
            return DatabaseUtil.get()
        }
        override fun onPostExecute(hyeyumList: List<HyeyumEntity>)
        {
            val adapter = HyeyumAdapter(this@ListActivity, hyeyumList)

            list_hyeyum_list.adapter = adapter
            list_hyeyum_list.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            list_hyeyum_list.setHasFixedSize(true)
        }
    }
}