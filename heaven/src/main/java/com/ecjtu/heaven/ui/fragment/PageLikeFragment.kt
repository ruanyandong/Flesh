package com.ecjtu.heaven.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.ecjtu.heaven.R
import com.ecjtu.heaven.db.DatabaseManager
import com.ecjtu.heaven.db.table.impl.LikeTableImplV2
import com.ecjtu.heaven.presenter.PageLikeFragmentDelegate

/**
 * Created by Ethan_Xiang on 2017/9/18.
 */
class PageLikeFragment : Fragment() {

    private var mDelegate: PageLikeFragmentDelegate? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_page_like, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = DatabaseManager.getInstance(activity)?.getDatabase()
        if (db != null) {
            val impl = LikeTableImplV2()
            val list = impl.getAllLikes(db)
            db.close()
            mDelegate = PageLikeFragmentDelegate(activity, list)
        }
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setTitle("收藏")
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            activity.finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        mDelegate?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mDelegate?.onRelease()
    }
}