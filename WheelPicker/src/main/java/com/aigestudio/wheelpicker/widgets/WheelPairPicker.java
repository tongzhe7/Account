package com.aigestudio.wheelpicker.widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.model.City;

import java.util.ArrayList;
import java.util.List;


/**
 * WheelAreaPicker
 * Created by Administrator on 2016/9/14 0014.
 */
public class WheelPairPicker extends LinearLayout implements IWheelPairPicker {
    private static final float ITEM_TEXT_SIZE = 18;
    private static final String SELECTED_ITEM_COLOR = "#353535";
    private static final int CITY_INITIAL_INDEX = 0;

    private Context mContext;

    //private List<Province> mProvinceList;
    private List<City> mCityList;
    private List<String> mCityName;



    private LayoutParams mLayoutParams;

    public WheelPicker mWPProvince, mWPCity, mWPArea;

    public WheelPairPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayoutParams();
        initView(context);
        //mProvinceList = getJsonDataFromAssets(mAssetManager);
        mCityList = getJsonDataFromAssets();

        obtainCityData();
        addListenerToWheelPicker();
    }
    private List<City> getJsonDataFromAssets() {
        City city = new City();
        city.setName("工资发放");
        City city1 = new City();
        city1.setName("收货款");

        List<String> areas = new ArrayList<>();

        areas.add("01");

        city.setArea(areas);

        List<String> areas2 = new ArrayList<>();

        areas.add("02");
        city1.setArea(areas2);

        List<City> cities = new ArrayList<>();

        cities.add( city);
        cities.add( city1);

        return cities;
    }


    private void initLayoutParams() {
        mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.setMargins(5, 5, 5, 5);
        mLayoutParams.width = 0;
    }

    private void initView(Context context) {
        setOrientation(HORIZONTAL);

        mContext = context;


        mCityName = new ArrayList<>();
        mWPCity = new WheelPicker(context);
        mWPArea = new WheelPicker(context);

        initWheelPicker(mWPCity, 1.5f);
        initWheelPicker(mWPArea, 1.5f);
    }

    private void initWheelPicker(WheelPicker wheelPicker, float weight) {
        mLayoutParams.weight = weight;
        wheelPicker.setItemTextSize(dip2px(mContext, ITEM_TEXT_SIZE));
        wheelPicker.setSelectedItemTextColor(Color.parseColor(SELECTED_ITEM_COLOR));
        wheelPicker.setCurved(true);
        wheelPicker.setLayoutParams(mLayoutParams);
        addView(wheelPicker);
    }

    private void obtainCityData() {
        for (City city : mCityList) {
            mCityName.add(city.getName());
        }
        mWPCity.setData(mCityName);
        setAreaData(CITY_INITIAL_INDEX);
    }

    private void addListenerToWheelPicker() {

        mWPCity.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                //获取城市对应的城区的名字
                mWPArea.setData(mCityList.get(position).getArea());
            }
        });
    }

    private void setAreaData(int position) {
//        //获得该省所有城市的集合
//        mAreaList = mProvinceList.get(position).getCity();
//        //获取所有city的名字
//        //重置先前的城市集合数据
//        mCityName.clear();
//        for (City city : mCityList)
//            mCityName.add(city.getName());
//        mWPCity.setData(mCityName);
//        mWPCity.setSelectedItemPosition(0);
        //获取第一个城市对应的城区的名字
        //重置先前的城区集合的数据
        mWPArea.setData(mCityList.get(0).getArea());
        mWPArea.setSelectedItemPosition(0);
    }

  //  @Override
//    public String getProvince() {
//        return mProvinceList.get(mWPProvince.getCurrentItemPosition()).getName();
//    }

    @Override
    public String getCity() {
        return mCityList.get(mWPCity.getCurrentItemPosition()).getName();
    }

    @Override
    public String getArea() {
        return mCityList.get(mWPCity.getCurrentItemPosition()).getArea().get(mWPArea.getCurrentItemPosition());
    }

    @Override
    public void hideArea() {
        this.removeViewAt(2);
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
