package com.hwq.mybill.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;


import com.hwq.mybill.R;
import com.hwq.mybill.activity.IndexActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class IncomeFragment extends Fragment {

    EditText edtUsername,edtMoney,edtDate,edtNote;
    Button incomeBtn;
    CheckBox expendCheck;
    String username,money,type= "收入",date,note;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_income, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        edtUsername=getActivity().findViewById(R.id.edtUsername);
        edtMoney=getActivity().findViewById(R.id.edtMoney);
        expendCheck=getActivity().findViewById(R.id.expendCheck);
        edtDate=getActivity().findViewById(R.id.edtDate);
        edtNote=getActivity().findViewById(R.id.edtNote);
        incomeBtn=getActivity().findViewById(R.id.incomeBtn);

        //监听expendCheckBox是否选中
        expendCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){//选中
                    type="支出";
                }else{//取消选中
                    type="收入";
                }
            }
        });

        System.out.println(expendCheck.isChecked());

        //获取日期
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String myDate = format.format(new Date());


        //向IndexActivity传输数据
        incomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username=edtUsername.getText().toString().trim();
                money= edtMoney.getText().toString().trim();

                date= myDate;
                note= edtNote.getText().toString().trim();

                //回调IndexActivity里的函数，实现数据Fragment数据传递activity
                ((IndexActivity)getActivity()).setInsertData(username,money,type,date,note);

                //清空setText
                edtUsername.setText("");
                edtMoney.setText("");
                edtNote.setText("");

                username="";
                money="";
                type="";
                date="";
                note="";
            }

        });
    }

}