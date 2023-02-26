package com.hwq.mybill.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;


import com.hwq.mybill.SQL.MySQLiteOpenHelper;
import com.hwq.mybill.R;
import com.hwq.mybill.bean.User;
import com.hwq.mybill.util.ToastUtil;

import java.util.List;

/**
 * MainActivity——界面交互
 */
public class MainActivity extends AppCompatActivity {

    EditText edtUsername,edtPassword; //用户名、密码
    Button loginBtn;//登录
    TextView tvReset,tvReg;  //忘记密码，注册
    CheckBox checkBox1; //显示密码
    private MySQLiteOpenHelper SQL; //数据库操作对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //加载布局文件
        setContentView(R.layout.activity_main);
        //页面初始化
        initView();
        //创建数据库操作对象
        SQL=new MySQLiteOpenHelper(this);
    }

    /**
     * 页面初始化
     */
    private void initView() {
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        loginBtn=findViewById(R.id.loginBtn);
        tvReset=findViewById(R.id.tvReset);
        tvReg=findViewById(R.id.tvReg);
        checkBox1=(CheckBox) findViewById(R.id.checkBox1);


        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //如果选中，显示密码
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });
    }




    /**
     * 登录
     * 登录按钮响应方式——在布局文件中指定onClick属性的方式设置点击事件
     * @param view
     */
    public void loginClick(View view) {

        //获取账号密码//去掉特殊字符，like 换行、空格
        String uname=edtUsername.getText().toString().trim();
        String pwd=edtPassword.getText().toString().trim();
        System.out.println("输入的数据\n用户："+uname+"密码："+pwd);

        //处理——账户名或密码为空
        if(uname.equals("")||pwd.equals("")){
            ToastUtil.toastShort(this,"账户名或密码為空！");
            return;//中断程序，停止运行
        }



        //清空输入框——点击登录按钮之后即刻清除
        edtUsername.setText("");
        edtPassword.setText("");

        //数据库查询
        List<User> users=SQL.queryUsers();


        for (User u:users) {
           //账号密码正确
            if(u.getUsername().equals(uname)&&u.getPassword().equals(pwd)){
               //登录成功跳转index主页
               Intent intent=new Intent(this, IndexActivity.class);

               //传值username和phone==》IndexActivity（getMineData()）==》MineFragment
               intent.putExtra("username",uname);
               intent.putExtra("phone",u.getPhone());

               startActivity(intent);

               //打印
               System.out.println("输出的数据\n用户："+u.getUsername()+"号码："+u.getPhone()+"\n功能正常");

               ToastUtil.toastShort(this,"登录成功");
               return;
           }
        }
        //账号密码不正确
        ToastUtil.toastShort(this,"账户名或密码错误！");
    }

    /**
     * 注册
     * 注册按钮响应方式——在布局文件中指定onClick属性的方式设置点击事件
     * @param view
     */
    public void regClick(View view){
        Intent intent=new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //重置密码
    public void resetUserPwdClick(View view) {
        //跳转重置密码页面
        Intent intent=new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
    }


}