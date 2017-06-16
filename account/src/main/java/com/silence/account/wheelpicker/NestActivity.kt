
package com.silence.account.wheelpicker

import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.alibaba.fastjson.JSON

import java.util.ArrayList
import com.silence.account.R
import cn.qqtheme.framework.entity.City
import cn.qqtheme.framework.entity.County
import cn.qqtheme.framework.entity.Province
import cn.qqtheme.framework.picker.AddressPicker
import cn.qqtheme.framework.util.ConvertUtils


import kotlinx.android.synthetic.main.activity_nest.*
class NestActivity : BaseActivity() {
    private var picker: AddressPicker? = null

    override public fun getContentView(): View {

        return inflateView(R.layout.activity_nest)
    }

    override fun setContentViewAfter(contentView: View) {
        try {
            val data = ArrayList<Province>()
            val json = ConvertUtils.toString(assets.open("city2.json"))
            data.addAll(JSON.parseArray(json, Province::class.java))
            picker = AddressPicker(this, data)
            picker!!.setShadowVisible(true)
            picker!!.setHideProvince(false)

            //picker!!.setHideCounty(true);  //是否显示最后一个项目
            picker!!.setSelectedItem("贵州", "贵阳", "花溪")
            picker!!.setOffset(3)
            picker!!.setOnWheelListener(object : AddressPicker.OnWheelListener {
                override fun onProvinceWheeled(index: Int, province: Province) {
                    tv_title.text = province.name
                }
                override fun onCityWheeled(index: Int, city: City) {
                    tv_city.text = city.name
                }
                override fun onCountyWheeled(index: Int, county: County) {
                    tv_country.text = county.name
                }
            })
            val viewGroup = findView<ViewGroup>(R.id.wheelview_container)
            viewGroup.addView(picker!!.contentView)

        } catch (e: Exception) {
            tv_title.text = "Fuck youl"
        }

        //不显示输入
        nest_carnumber.setOnClickListener {
            wheelview_container.removeAllViews()
        }
        //显示输入
        tv_title.setOnClickListener {
            wheelview_container.removeAllViews()
            wheelview_container.addView(picker!!.contentView)
        }
    }
}
