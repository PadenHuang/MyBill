package com.hwq.mybill.fragment;

/*import static java.lang.Thread.sleep;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tracktable.bean.Income;

import java.util.List;*/


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hwq.mybill.R;
import com.hwq.mybill.activity.IndexActivity;
import com.hwq.mybill.bean.Income;

import java.util.List;

public class SearchFragment extends Fragment {

    EditText edtQuery;
    ImageView imgQuery;
    TextView tvRes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        edtQuery=getActivity().findViewById(R.id.edtQuery);
        imgQuery=getActivity().findViewById(R.id.imgQuery);
        tvRes=getActivity().findViewById(R.id.tvRes);

        //搜搜图片黑色
        imgQuery.setColorFilter(getResources().getColor(R.color.black));

        imgQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eq=edtQuery.getText().toString().trim();
                String result="";
                //判空
                if(eq.equals("") || eq.equals(null)) {
                    tvRes.setText("关键词不能为空！");
                    return;
                }
                //回调IndexActivity的方法setQuery
                List<Income> incomes=((IndexActivity)getActivity()).setQuery(eq);

                for (Income item:incomes) {
                    result+="id:"+item.getId()+" 名称:"+item.getUsername()+" ￥:"+item.getMoney()+" 类型:"+item.getType()+" 日期:"+item.getDate()+" 备注:"+item.getNote()+"\n\n";
                }
                //渲染搜索结果
                tvRes.setTextColor(getResources().getColor(R.color.black));
                tvRes.setText(result);
                edtQuery.setText("");
            }
        });
    }
}