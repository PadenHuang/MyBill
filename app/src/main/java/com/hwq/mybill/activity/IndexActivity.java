package com.hwq.mybill.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hwq.mybill.fragment.HomeFragment;
import com.hwq.mybill.fragment.IncomeFragment;
import com.hwq.mybill.fragment.MineFragment;
import com.hwq.mybill.SQL.MySQLiteOpenHelper;
import com.hwq.mybill.R;
import com.hwq.mybill.fragment.SearchFragment;
import com.hwq.mybill.bean.Income;
import com.hwq.mybill.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 主页面
 */
public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    //声明
    private LinearLayout llhome,llincome,llsearch,llmine;
    private ImageView homeImg,incomeImg,searchImg,mineImg;
    private TextView tvhome,tvincome,tvsearch,tvmine;


    private String list="";
    private String tips="";
    private double ip=0.00;
    private double ep=0.00;

    //要切换的页面fragment
    HomeFragment fgHome;
    IncomeFragment fgIncome;
    MineFragment fgMine;
    SearchFragment fgSearch;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private MySQLiteOpenHelper SQL;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        //初始化按钮和事件监听
        initView();
        initEvent();
    }

    /**
     * 初始化按钮和事件监听
     */
    private void initView() {
        llhome=findViewById(R.id.llhome);
        llincome=findViewById(R.id.llincome);
        llmine=findViewById(R.id.llmine);
        llsearch=findViewById(R.id.llsearch);

        homeImg=findViewById(R.id.homeImg);
        incomeImg=findViewById(R.id.incomeImg);
        mineImg=findViewById(R.id.mineImg);
        searchImg=findViewById(R.id.searchImg);

        tvhome=findViewById(R.id.tvhome);
        tvincome=findViewById(R.id.tvincome);
        tvmine=findViewById(R.id.tvmine);
        tvsearch=findViewById(R.id.tvsearch);

        initSelect();

    }

    private void initSelect(){
        //初始未选中
        homeImg.setSelected(false);
        incomeImg.setSelected(false);
        mineImg.setSelected(false);
        searchImg.setSelected(false);

        tvhome.setTextColor(getResources().getColor(R.color.grey));
        tvincome.setTextColor(getResources().getColor(R.color.grey));
        tvmine.setTextColor(getResources().getColor(R.color.grey));
        tvsearch.setTextColor(getResources().getColor(R.color.grey));
    }

    private void initEvent() {
        //getSupportFragmentManager()主要用于支持 3.0以下android系统API版本，3.0以上系统可以直接调用getFragmentManager()
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        //初始化首页
        if(fgHome==null) fgHome=new HomeFragment();

        getHomeData();
        //Bundle用于数据从activity传输到fragment的方法，存放：putString(key,value);获取：String name=getArguments().get(key) + "";
        Bundle bundle=new Bundle();
        bundle.putString("list",list);
        bundle.putString("tips",tips);
        bundle.putString("ip",ip+"" );
        bundle.putString("ep",ep+"");

        fgHome.setArguments(bundle);
        fragmentTransaction.replace(R.id.index_fragment,fgHome).commit();
        //选中之后图片自会切换
        homeImg.setSelected(true);
        //文字不会自动切换
        tvhome.setTextColor(getResources().getColor(R.color.white_blue));

        llhome.setOnClickListener(this);
        llincome.setOnClickListener(this);
        llmine.setOnClickListener(this);
        llsearch.setOnClickListener(this);


    }

    private void getHomeData() {
        //首页数据渲染调取HomeFragment
        //执行income查询
        SQL= new MySQLiteOpenHelper(this);
        List<Income> incomes=SQL.queryIncomes();

        list="";
        ip=0.00;//收入
        ep=0.00;//支出
        tips="";
        String flag = "";

        double ipb = 0.00;
        double epb = 0.00;

        if(incomes!=null){
            for (Income i:incomes) {
                //判断是否等于避免==，否则出错
                if(i.getType().equals("收入") && i.getMoney()!=null){
                    ip+=Double.parseDouble(i.getMoney());
                    list=list + "id:"+i.getId()+" 名称:"+i.getUsername()+" ￥:"+"+"+i.getMoney()+" 类型:"+i.getType()+" 日期:"+i.getDate()+" 备注:"+i.getNote()+"\n\n";
                }

                if(i.getType().equals("支出") && i.getMoney()!=null){
                    ep+=Double.parseDouble(i.getMoney());
                    list=list + "id:"+i.getId()+" 名称:"+i.getUsername()+" ￥:"+"-"+i.getMoney()+" 类型:"+i.getType()+" 日期:"+i.getDate()+" 备注:"+i.getNote()+"\n\n";
                }
            }
        }else {
            tips="提示：暂无数据";
            return;
        }

        //返回对当前收支分析、建议
        ipb =(ip/(ip+ep))*100;
        epb =(ep/(ip+ep))*100;
        if(ipb > (epb +5)){
           flag ="0";
        }
        if(ipb <= (epb +5) && ipb > epb){
            flag ="1";
        }
        if(ipb <= epb && ipb > (epb -5)){
            flag ="2";
        }
        if(ipb <= (epb -5)){
            flag ="3";
        }
        switch (flag){
            case "0":
                tips="分析:当前收支状态良好！^o^";
                break;
            case "1":
                tips="分析:当前收支状态不错！^_^";
                break;
            case "2":
                tips="分析:当前收支状态槽糕！-_-";
                break;
            case "3":
                tips="分析:当前收支状态很槽糕！'^'";
                break;
            default:
                break;
        }

    }


    public Bundle getMineData(){
        //获取登录时的数据作为个人信息页面
        Intent intent=getIntent();
        String uname=intent.getStringExtra("username");
        String phone=intent.getStringExtra("phone");

        Bundle b1=new Bundle();
        b1.putString("username",uname);
        b1.putString("phone",phone);
        return b1;
    }


    @Override
    public void onClick(View view) {
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        initSelect();
        //页面跳转
        switch (view.getId()){
            case R.id.llhome:
                //选中之后图片自会切换
                homeImg.setSelected(true);
                //文字不会自动切换
                tvhome.setTextColor(getResources().getColor(R.color.white_blue));

                //不存在先创建
                System.out.println("fragmentManager 首页");
                if(fgHome==null) fgHome=new HomeFragment();
                getHomeData();
                Bundle bundle=new Bundle();
                bundle.putString("list",list);
                bundle.putString("tips",tips);
                bundle.putString("ip",ip+"" );
                bundle.putString("ep",ep+"");
                fgHome.setArguments(bundle);

                fragmentTransaction.replace(R.id.index_fragment,fgHome);
                break;

            case R.id.llincome:
                System.out.println("fragmentManager 收入");
                //选中之后图片会切换
                incomeImg.setSelected(true);
                //文字不会自动切换
                tvincome.setTextColor(getResources().getColor(R.color.white_blue));

                if(fgIncome == null){
                    fgIncome= new IncomeFragment();
                }
                fragmentTransaction.replace(R.id.index_fragment,fgIncome);
                break;



            case R.id.llsearch:
                System.out.println("fragmentManager 搜索");
                //选中之后图片自会切换
                searchImg.setSelected(true);
                //文字不会自动切换
                tvsearch.setTextColor(getResources().getColor(R.color.white_blue));

                if(fgSearch ==null){
                    fgSearch= new SearchFragment();
                }
                fragmentTransaction.replace(R.id.index_fragment,fgSearch);
                break;


            case R.id.llmine:
                System.out.println("我的");
                //选中之后图片自会切换
                mineImg.setSelected(true);
                //文字不会自动切换
                tvmine.setTextColor(getResources().getColor(R.color.white_blue));

                if(fgMine == null ){
                    fgMine= new MineFragment();
                }
                fgMine.setArguments(getMineData());
                fragmentTransaction.replace(R.id.index_fragment,fgMine);
                break;


            default:
                break;
        }
        //提交才会显示
        fragmentTransaction.commit();
    }


    //接收IncomeFragment的数据
    public void setInsertData(String username,String money,String type,String date,String note){

        SQL=new MySQLiteOpenHelper(this);

        //不输入用户名时，调用登录时保存的账户名
        if(username.equals("")){
            Intent intent=getIntent();
            username=intent.getStringExtra("username");
        }

        //判空
        if(date.equals("")){
            SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            date=format.format(new Date());
        }
        if(username.equals("")||money.equals("")||note.equals("")){
            ToastUtil.toastShort(this,"填写完整！");
            return;
        }

        //将用户名和密码存入对象中
        Income income=new Income();
        income.setUsername(username);
        income.setMoney(money);
        income.setType(type);
        income.setDate(date);
        income.setNote(note);

        //插入数据库
        long rowId=SQL.insertIncome(income);

        //检查插入结果是否成功
        if (rowId !=-1 ){
            ToastUtil.toastShort(this, "提交成功");
        }else {
            ToastUtil.toastShort(this, "提交失败！");
        }
    }

    //条件查询
    public List<Income> setQuery(String username){
        SQL=new MySQLiteOpenHelper(this);
        List<Income> incomes=SQL.queryIncome(username);
        if(incomes == null) ToastUtil.toastShort(this,"数据为空！");
        return incomes;
    }

}