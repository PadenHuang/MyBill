package com.hwq.mybill.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hwq.mybill.R;
import com.hwq.mybill.activity.ResetPasswordActivity;


public class MineFragment extends Fragment {


    TextView tvUsername,tvPhone,tvResetPwd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvUsername=getActivity().findViewById(R.id.tvUsername);
        tvPhone=getActivity().findViewById(R.id.tvPhone);
        tvResetPwd=getActivity().findViewById(R.id.tvResetPwd);

        //获取登录时查询的信息作为个人信息显示//Bundle
        String username=getArguments().get("username") + "";
        String phone= (String) getArguments().get("phone");

        System.out.println(phone);

        tvUsername.setText("用户名："+username);
        tvPhone.setText("手机号："+phone);

        //重置密码
        tvResetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }


}