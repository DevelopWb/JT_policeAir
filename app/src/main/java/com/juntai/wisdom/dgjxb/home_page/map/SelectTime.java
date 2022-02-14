package com.juntai.wisdom.dgjxb.home_page.map;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.basecomponent.utils.PickerManager;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.utils.DateUtil;

import java.util.Date;

/**
 * @aouther tobato
 * @description 描述  选择时间段
 * @date 2020/4/2 13:57
 */
public class SelectTime extends BaseActivity implements View.OnClickListener {
    private TextView startTimeTv, endTimeTv;
    private String startTime = "", endTime = "";
    private TextView startSearch;
    private String entityInfo;
    private int type;

    @Override
    public int getLayoutView() {
        return R.layout.activity_select_time;
    }

    @Override
    public void initView() {
        startTimeTv = findViewById(R.id.select_start_time);
        endTimeTv = findViewById(R.id.select_end_time);
        startSearch = findViewById(R.id.start_to_search);
        startSearch.setOnClickListener(this);
        startTimeTv.setOnClickListener(this);
        endTimeTv.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", -1);
        switch (type) {
            case HistoryTrack.CAR:
                entityInfo = intent.getStringExtra("carImei");
                setTitleName("时间选择 (车辆)");
                break;
            case HistoryTrack.PEOPLE:
                entityInfo = intent.getStringExtra("peopleId");
                setTitleName("时间选择 (人员)");
                break;
            default:
                setTitleName("时间选择");
                break;
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_start_time:
                //选择时间
                PickerManager.getInstance().showTimePickerView(this, PickerManager.getInstance().getTimeType("minute"), "选择时间", new PickerManager.OnTimePickerTimeSelectedListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        startTime =DateUtil.getDateString(date,"yyyy-MM-dd HH:mm:ss");
                        startTimeTv.setText(startTime);
                    }
                });
                break;
            case R.id.select_end_time:
                //选择时间
                PickerManager.getInstance().showTimePickerView(this, PickerManager.getInstance().getTimeType("minute"), "选择时间", new PickerManager.OnTimePickerTimeSelectedListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        endTime = DateUtil.getDateString(date, "yyyy-MM-dd HH:mm:ss");
                        endTimeTv.setText(endTime);
                    }
                });
                break;
            case R.id.start_to_search:
                if ("".equals(startTime) || "".equals(endTime)) {
                    ToastUtils.toast(mContext, "需要选择开始和结束时间");
                    return;
                }

                if (DateUtil.compareDateSize("yyyy-MM-dd HH:mm:ss",startTime,endTime)) {
                    ToastUtils.toast(mContext, "结束时间不可早于开始时间");
                    return;
                }
                switch (type) {
                    case HistoryTrack.CAR:
                        startActivity(new Intent(this, HistoryTrack.class)
                                .putExtra("type", type)
                                .putExtra("time", HistoryTrack.CUSTOM)
                                .putExtra("start", startTime)
                                .putExtra("carImei",  entityInfo)
                                .putExtra("end", endTime));
                        break;
                    case HistoryTrack.PEOPLE:
                        startActivity(new Intent(this, HistoryTrack.class)
                                .putExtra("type", type)
                                .putExtra("time", HistoryTrack.CUSTOM)
                                .putExtra("start", startTime)
                                .putExtra("peopleId",entityInfo)
                                .putExtra("end", endTime));
                        break;
                }
                finish();

        }
    }
}
