package com.hwq.mybill.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hwq.mybill.R;


/**
 *主碎片
 */
public class HomeFragment extends Fragment {

    TextView tvIncome,tvExpend,tvTips,tvList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始获取控件
        tvIncome=getActivity().findViewById(R.id.tvIncome);
        tvExpend=getActivity().findViewById(R.id.tvExpend);
        tvList=getActivity().findViewById(R.id.tvList);
        tvTips=getActivity().findViewById(R.id.tvTips);

        //获取Home渲染的数据
        //+""转字符串以匹配String list...
        //Bundle
        String list=getArguments().get("list") + "";
        String tips=getArguments().get("tips") + "";
        String ip=getArguments().get("ip")+"";
        String ep=getArguments().get("ep")+"";

        //显示数据
        tvIncome.setText("收入\n"+"+"+ip + "元");
        tvExpend.setText("支出\n"+"-"+ep + "元");
        tvTips.setText(tips+"\n");
        tvList.setText(list);

        //设置字体颜色
        tvIncome.setTextColor(getResources().getColor(R.color.black));
        tvExpend.setTextColor(getResources().getColor(R.color.black));
        tvTips.setTextColor(getResources().getColor(R.color.black));
        tvList.setTextColor(getResources().getColor(R.color.black));

    }

}