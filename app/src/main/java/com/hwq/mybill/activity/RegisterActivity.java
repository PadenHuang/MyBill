package com.hwq.mybill.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.hwq.mybill.SQL.MySQLiteOpenHelper;
import com.hwq.mybill.R;
import com.hwq.mybill.bean.User;
import com.hwq.mybill.util.RulesUtil;
import com.hwq.mybill.util.ToastUtil;

import java.util.List;

/**
 * 注册页面
 */
public class RegisterActivity extends AppCompatActivity {

    EditText edtUsername,edtPassword,edtPhone; //用户名、密码、电话
    Button regBtn; //注册按钮
    private MySQLiteOpenHelper SQL; //数据库操作对象——SQL

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局文件
        setContentView(R.layout.register_main);
        //页面初始化
        initView();
        //创建数据库操作对象
        SQL=new MySQLiteOpenHelper(this);
    }

    /**
     * 页面初始化
     */
    private void initView() {
        edtUsername= findViewById(R.id.edtUsername);
        edtPassword= findViewById(R.id.edtPassword);
        edtPhone=findViewById(R.id.edtPhone);
        regBtn=findViewById(R.id.regBtn);
    }

    /**
     * 点击regClick就会响应到这里
     * @param view
     */
    public void regClick(View view) {
        //获取输入字符账户和密码
        String username=edtUsername.getText().toString().trim();//trim()去掉edtName前后字符，保证所需纯正的字符，不需要的字符，like 空格、回车键
        String password=edtPassword.getText().toString().trim();
        String phone=edtPhone.getText().toString().trim();

        //判空
        if(username.equals("")||password.equals("")||phone.equals("")){
            ToastUtil.toastShort(this,"用戶名或密碼或手機號為空！");
            return;
        }

        //判斷用戶是否已經存在 //檢查是否已經存在用戶名
        List<User> users=SQL.queryUserByUsername(username);
        for (User u:users) {
            if(u.getUsername().equals(username)){
                ToastUtil.toastShort(this,"該用戶名已經存在，請重試！");
                return;
            }
        }

        //判断密码长度
        if(password.length()<6 ){
            ToastUtil.toastShort(this,"密碼長度不足6位，請重新輸入！");
            return;
        }

        //判斷手機號長度是否符合規定
        if(phone.length()!=11){
            ToastUtil.toastShort(this,"輸入手機號位數不足11位，請重新輸入！");
            return;
        }

        //判断手机号是否合理
        if(RulesUtil.isMobile(phone) == false){
            ToastUtil.toastShort(this,"輸入手機號有誤，請重新輸入！");
            return;
        }

        //将用户名、密码、手机号存入User对象中
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);

        //插入数据库
        long rowId=SQL.insertUser(user);

        //检查插入结果是否成功
        if (rowId !=-1 ){
            ToastUtil.toastShort(this, "注册成功");
            //跳转登录
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            ToastUtil.toastShort(this, "注册失败！");
        }
    }
}