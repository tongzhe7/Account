package com.silence.account.activity

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView

import com.silence.account.R
import com.silence.account.adapter.CommonAdapter
import com.silence.account.application.AccountApplication
import com.silence.account.dao.ExpenseCatDao
import com.silence.account.dao.IncomeCatDao
import com.silence.account.model.ExpenseCat
import com.silence.account.model.IncomeCat
import com.silence.account.utils.Constant
import com.silence.account.utils.T

import java.util.ArrayList

//import butterknife.Bind;

import kotlinx.android.synthetic.main.activity_category.*

class CategoryAty : BaseActivity(), AdapterView.OnItemClickListener {

    private var mResId: Int = 0
    private var mExpenseCat: ExpenseCat? = null
    private var mIncomeCat: IncomeCat? = null
    private var mType: String? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        title = getString(R.string.add_category)
        showBackwardView(true)
        showForwardView(true)
        setContentView(R.layout.activity_category)
        val extra = intent.getParcelableExtra<Parcelable>(Constant.UPDATE_CAT)
        if (extra != null) {
            title = getString(R.string.modify_category)
            if (extra is ExpenseCat) {
                mType = Constant.TYPE_EXPENSE
                mExpenseCat = extra
                et_add_category.setText(mExpenseCat!!.name)
                icon_add_category.setImageResource(mExpenseCat!!.imageId)
                mResId = mExpenseCat!!.imageId
            } else {
                mType = Constant.TYPE_INCOME
                mIncomeCat = extra as IncomeCat
                et_add_category.setText(mIncomeCat!!.name)
                icon_add_category.setImageResource(mIncomeCat!!.imageId)
                mResId = mIncomeCat!!.imageId
            }
        } else {
            mResId = R.mipmap.icon_shouru_type_qita
            mType = intent.getStringExtra(Constant.TYPE_CATEGORY)
            title = getString(R.string.add_category)
        }
        val commonAdapter = object : CommonAdapter<Int>(initData(), R.layout.item_add_category) {
            override fun bindView(holder: CommonAdapter.ViewHolder, obj: Int?) {
                holder.setImageResource(R.id.item_add_category, obj!!)
            }
        }
        grid_add_category.adapter = commonAdapter
        grid_add_category.onItemClickListener = this
    }

    private fun initData(): List<Int> {
        val resId = intArrayOf(R.mipmap.icon_zhichu_type_canyin, R.mipmap.maicai, R.mipmap.icon_zhichu_type_yanjiuyinliao, R.mipmap.icon_zhichu_type_shuiguolingshi, R.mipmap.baojian, R.mipmap.ad, R.mipmap.anjie, R.mipmap.baobao, R.mipmap.baoxian, R.mipmap.baoxiao, R.mipmap.chuanpiao, R.mipmap.daoyou, R.mipmap.dapai, R.mipmap.dianfei, R.mipmap.dianying, R.mipmap.fangdai, R.mipmap.fangzu, R.mipmap.fanka, R.mipmap.feijipiao, R.mipmap.fuwu, R.mipmap.gonggongqiche, R.mipmap.haiwaidaigou, R.mipmap.huankuan, R.mipmap.huazhuangpin, R.mipmap.huochepiao, R.mipmap.huwaishebei, R.mipmap.icon_add_1, R.mipmap.icon_add_2, R.mipmap.icon_add_3, R.mipmap.icon_add_4, R.mipmap.icon_add_5, R.mipmap.icon_add_6, R.mipmap.icon_add_7, R.mipmap.icon_add_8, R.mipmap.icon_add_9, R.mipmap.icon_add_10, R.mipmap.icon_add_11, R.mipmap.icon_add_12, R.mipmap.icon_add_13, R.mipmap.icon_add_14, R.mipmap.icon_add_15, R.mipmap.icon_add_16, R.mipmap.icon_add_17, R.mipmap.icon_add_18, R.mipmap.icon_add_19, R.mipmap.icon_add_20, R.mipmap.icon_shouru_type_gongzi, R.mipmap.icon_shouru_type_hongbao, R.mipmap.icon_shouru_type_jiangjin, R.mipmap.icon_shouru_type_jianzhiwaikuai, R.mipmap.icon_shouru_type_jieru, R.mipmap.icon_shouru_type_linghuaqian, R.mipmap.icon_shouru_type_shenghuofei, R.mipmap.icon_shouru_type_touzishouru, R.mipmap.icon_zhichu_type_baoxiaozhang, R.mipmap.icon_zhichu_type_canyin, R.mipmap.icon_zhichu_type_chongwu, R.mipmap.icon_zhichu_type_gouwu, R.mipmap.icon_zhichu_type_jiaotong, R.mipmap.icon_zhichu_type_jiechu, R.mipmap.icon_zhichu_type_jujia, R.mipmap.icon_zhichu_type_meirongjianshen, R.mipmap.icon_zhichu_type_renqingsongli, R.mipmap.icon_zhichu_type_shoujitongxun, R.mipmap.icon_zhichu_type_shuji, R.mipmap.icon_zhichu_type_taobao, R.mipmap.icon_zhichu_type_yanjiuyinliao, R.mipmap.icon_zhichu_type_yiban, R.mipmap.icon_zhichu_type_yule, R.mipmap.jiushui, R.mipmap.juechu, R.mipmap.kuzi, R.mipmap.lifa, R.mipmap.lingqian, R.mipmap.lingshi, R.mipmap.lvyoudujia, R.mipmap.majiang, R.mipmap.mao, R.mipmap.naifen, R.mipmap.party, R.mipmap.quxian, R.mipmap.richangyongpin, R.mipmap.shuifei, R.mipmap.shumachanpin, R.mipmap.sijiache, R.mipmap.tingchefei, R.mipmap.tuikuan, R.mipmap.wanfan, R.mipmap.wangfei, R.mipmap.wanggou, R.mipmap.wanju, R.mipmap.weixiubaoyang, R.mipmap.wuye, R.mipmap.xianjin, R.mipmap.xiaochi, R.mipmap.xiaojingjiazhang, R.mipmap.xiezi, R.mipmap.xinyongkahuankuan, R.mipmap.xizao, R.mipmap.xuefei, R.mipmap.yan, R.mipmap.yaopinfei, R.mipmap.yifu, R.mipmap.yinhangshouxufei, R.mipmap.yiwaiposun, R.mipmap.yiwaisuode, R.mipmap.youfei, R.mipmap.youxi, R.mipmap.yuegenghuan, R.mipmap.yundongjianshen, R.mipmap.zhifubao, R.mipmap.zhongfan, R.mipmap.zhuanzhang, R.mipmap.zhusu, R.mipmap.zuojifei)
        val resIds = ArrayList<Int>(resId.size)
        for (aResId in resId) {
            resIds.add(aResId)
        }
        return resIds
    }

    override fun onForward() {

        //获取类别名称
        val name = et_add_category.text.toString().trim()
        if (!TextUtils.isEmpty(name)) {
            if (TextUtils.equals(mType, Constant.TYPE_INCOME)) {
                val incomeCatDao = IncomeCatDao(this)
                if (mIncomeCat != null) {
                    //如果更新成功，提示用户成功，并关闭窗口
                    if (incomeCatDao.update(IncomeCat(mIncomeCat!!.id,
                            name, mResId, AccountApplication.sUser))) {
                        T.showShort(this, getString(R.string.modify_succeed))
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        T.showShort(this, getString(R.string.modify_fail))
                    }
                } else {
                    if (incomeCatDao.addCategory(IncomeCat(mResId, name,
                            AccountApplication.sUser))) {
                        T.showShort(this, getString(R.string.save_succeed))
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        T.showShort(this, getString(R.string.save_fail))
                    }
                }
            } else if (TextUtils.equals(mType, Constant.TYPE_EXPENSE)) {
                val expenseCatDao = ExpenseCatDao(this)
                if (mExpenseCat != null) {
                    if (expenseCatDao.update(ExpenseCat(mExpenseCat!!.id,
                            name, mResId, AccountApplication.sUser))) {
                        T.showShort(this, getString(R.string.modify_succeed))
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        T.showShort(this, getString(R.string.modify_fail))
                    }
                } else {
                    if (expenseCatDao.addCategory(ExpenseCat(mResId, name, AccountApplication.sUser))) {
                        T.showShort(this, getString(R.string.save_succeed))
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        T.showShort(this, getString(R.string.save_fail))
                    }
                }
            }
        } else {
            T.showShort(this, getString(R.string.input_category_name))
        }
    }

    override val subActivity: Activity
        get() = this


    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        mResId = parent.getItemAtPosition(position) as Int
        icon_add_category.setImageResource(mResId)
    }
}