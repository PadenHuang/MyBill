package com.hwq.mybill.activity;

/*
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tracktable.util.RulesUtil;
import com.example.tracktable.util.ToastUtil;

import org.w3c.dom.Text;
*/

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.hwq.mybill.SQL.MySQLiteOpenHelper;
import com.hwq.mybill.R;
import com.hwq.mybill.util.RulesUtil;
import com.hwq.mybill.util.ToastUtil;

public class ResetPasswordActivity extends AppCompatActivity {


    EditText edtResetPwd,edtPhone;


    MySQLiteOpenHelper SQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edtPhone=findViewById(R.id.edtPhone);
        edtResetPwd=findViewById(R.id.edtResetPwd);
    }


    //提交按钮触发事件
    public void resetPwdClick(View view) {
        //获取新密码和手机号
        String newPwd=edtResetPwd.getText().toString().trim();
        String phone=edtPhone.getText().toString().trim();

        if(newPwd.equals("") || phone.equals("")){
            ToastUtil.toastShort(this,"新密码或者手机号码为空！");
            return;
        }

        if(newPwd.length()<6 ){
            ToastUtil.toastShort(this,"密碼長度不足6位，請重新輸入！");
            return;
        }

        //判斷手機號長度是否符合規定
        if(phone.length()!=11){
            ToastUtil.toastShort(this,"輸入手機號位數不足11位，請重新輸入！");
            return;
        }

        //判断手机号符合某个运营商
        if(RulesUtil.isMobile(phone)==false){
            ToastUtil.toastShort(this,"輸入手機號有誤，請重新輸入！");
            return;
        }

        //获取数据库操作对象
        SQL = new MySQLiteOpenHelper(this);

        int raw= SQL.updateUserPwd(newPwd,phone);
        if(raw>-1){
            ToastUtil.toastShort(this,"密码修改成功");
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}