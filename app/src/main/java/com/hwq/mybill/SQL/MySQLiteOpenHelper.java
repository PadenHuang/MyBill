package com.hwq.mybill.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hwq.mybill.bean.Income;
import com.hwq.mybill.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite——数据库设计
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    //数据库名
    private static final String DB_NAME = "MyBill.db";
    //表——用户表
    private  static final String TABLE_NAME_USER="user";
    //表——收支表
    private static final String TABLE_NAME_INCOME="income";
    //建表SQL——用户表
    private static final String CREATE_TABLE_SQL="create table if not exists " + TABLE_NAME_USER +
            " (id integer primary key autoincrement,username text not null,password text not null,phone text not null ); ";
    //建表SQL——收支表
    private static final String CREATE_TABLE_SQL2=" create table if not exists " + TABLE_NAME_INCOME +
            " (id integer primary key autoincrement,username text,money text not null,date text not null,type text not null,note text not null); ";

    /**
     * 构造方法
     * @param context
     */
    public MySQLiteOpenHelper(Context context){
        //调用父类构造方法——上下文对象、数据库名称、游标工厂（通常是null)、数据库版本
        super(context,DB_NAME,null,1);
    }

    /**
     *
     * 初始化表结构
     * 数据库第一次创建时调用
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL语句执行
        db.execSQL(CREATE_TABLE_SQL);
        db.execSQL(CREATE_TABLE_SQL2);

    }

    /**
     * 对数据库升级时执行
     * @param db
     * @param old
     * @param latest
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int latest) {

    }

    /**
     * 根据username查询user
     * @param username
     * @return
     */
    public List<User> queryUserByUsername(String username){
        //获取可读SQLiteDatabase对象
        SQLiteDatabase db=getReadableDatabase();
        //查询到的用户数组
        List<User> userList=new ArrayList<>();
        //SQL语句——查询
        String sql="select * from user where username=?";
        Cursor cursor= db.rawQuery(sql,new String[]{username});
        //游标取数据
        if(cursor!=null){
            while (cursor.moveToNext()){
                String index=cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String uname=cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String pwd=cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String phonex=cursor.getString(cursor.getColumnIndexOrThrow("phone"));

                User user=new User();
                user.setId(index);
                user.setUsername(uname);
                user.setPassword(pwd);
                user.setPhone(phonex);
                userList.add(user);
            }
            //关闭
            cursor.close();
        }
        return userList;
    }

    /**
     * 插入user
     * @param user
     * @return
     */
    public long insertUser(User user){
        //获取可写数据库对象
        SQLiteDatabase db=getWritableDatabase();

        //键值对//id 自增不用加
        ContentValues values=new ContentValues();
        values.put("username",user.getUsername());
        values.put("password",user.getPassword());
        values.put("phone",user.getPhone());

        //插入
        return db.insert(TABLE_NAME_USER,null,values);

    }


    /**
     * 查询users
     * @return  userList
     */
    public List<User> queryUsers(){
        //获取可写数据库对象
        SQLiteDatabase db=getWritableDatabase();
        //查询到的用户数组
        List<User> userList=new ArrayList<>();

        //获取游标对象
        //查询,columns为空=select *、为new String[]{”username“,"password"} =select username和password
        Cursor cursor= db.query(TABLE_NAME_USER,null,null,null,null,null,null);
        //游标取数据
        if(cursor!=null){
            //游标不断下移
            while (cursor.moveToNext()){

                //根据字符串参数获取对应字段编号后,再取得对应字符串值
                /**cursor.getColumnIndexOrThrow("id")
                 * 返回给定列名的从零开始的索引，如果该列不存在，则抛出IllegalArgumentException。
                 * 如果不确定列是否存在，请使用#getColumnIndex（String）并检查-1，这比捕获异常更有效。
                 */
                String index=cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String uname=cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String pwd=cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String phone=cursor.getString(cursor.getColumnIndexOrThrow("phone"));

                //将查询到的列名数据暂存user对象
                User user=new User();
                user.setId(index);
                user.setUsername(uname);
                user.setPassword(pwd);
                user.setPhone(phone);

                userList.add(user);
            }
            //关闭
            cursor.close();
        }
        return userList;
    }


    //根据phone查询user
    public List<User> queryUser(String phone){
        SQLiteDatabase db=getReadableDatabase();
        //查询到的用户数组
        List<User> userList=new ArrayList<>();

        String sql="select * from user where phone=?";
        Cursor cursor= db.rawQuery(sql,new String[]{phone});
        //游标取数据
        if(cursor!=null){
            while (cursor.moveToNext()){
                String index=cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String uname=cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String pwd=cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String phonex=cursor.getString(cursor.getColumnIndexOrThrow("phone"));

                User user=new User();
                user.setId(index);
                user.setUsername(uname);
                user.setPassword(pwd);
                user.setPhone(phonex);
                userList.add(user);
            }
            //关闭
            cursor.close();
        }
        return userList;
    }

    //重置密码
    public int updateUserPwd(String newPassword,String phone){
        //获取写入数据库函数
        SQLiteDatabase db=getWritableDatabase();
        //查询到的用户数组

        ContentValues values=new ContentValues();
        values.put("password",newPassword);

        //插入
        return db.update(TABLE_NAME_USER,values,"phone=?",new String[]{phone});
    }

    //删除user
    public int deleteUser(String username){
        //获取写入数据库函数
        SQLiteDatabase db=getWritableDatabase();
        //返回int表示删除了几个//"username=?"/"username like?"
        return db.delete(TABLE_NAME_USER,"username=?",new String[]{username});

    }



    //INCOME

    //插入income
    public long insertIncome(Income income){
        //获取写入数据库函数
        SQLiteDatabase db=getWritableDatabase();

        //键值对//id 自增不用加
        ContentValues values=new ContentValues();
        values.put("username",income.getUsername());
        values.put("money",income.getMoney());
        values.put("date",income.getDate());
        values.put("type",income.getType());
        values.put("note",income.getNote());

        //插入
        return db.insert(TABLE_NAME_INCOME,null,values);
    }


/*    //查询单个Income
//    public List<Income> queryIncome(String username){
//        //以读写的方式打开数据库
//        SQLiteDatabase db=getWritableDatabase();
//        //查询到的数组
//        List<Income> incomeList=new ArrayList<>();
//
//        //查询,columns为空=select *、为new String[]{”username“,"password"} =select username和password
//        Cursor cursor= db.query(TABLE_NAME_INCOME,new String[]{"username"},"username=?",new String[]{username.toString()},null,null,null,null);
//        //游标取数据
//        if(cursor!=null){
//            while (cursor.moveToNext()){
//
//                String uname=cursor.getString(cursor.getColumnIndexOrThrow("username"));
//                String money=cursor.getString(cursor.getColumnIndexOrThrow("money"));
//                String date=cursor.getString(cursor.getColumnIndexOrThrow("date"));
//                String type=cursor.getString(cursor.getColumnIndexOrThrow("type"));
//                String note=cursor.getString(cursor.getColumnIndexOrThrow("note"));
//
//                Income income=new Income();
//                income.setUsername(uname);
//                income.setMoney(money);
//                income.setDate(date);
//                income.setType(type);
//                income.setNote(note);
//
//                incomeList.add(income);
//            }
//            //关闭
//            cursor.close();
//        }
//        return incomeList;
//    }*/


    //Income条件模糊查询
    public List<Income> queryIncome(String username){

        //连接数据库库，获取方式只读
        SQLiteDatabase db=getReadableDatabase();
        List<Income> incomes=new ArrayList<>();

        //通过遍历Cursor游标获取数据，new String[]{username}为username=？
        String sql="select * from income where username like ? ";
        Cursor cursor=db.rawQuery(sql,new String[]{username});

        while (cursor.moveToNext()){

                String index=cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String uname=cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String money=cursor.getString(cursor.getColumnIndexOrThrow("money"));
                String date=cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String type=cursor.getString(cursor.getColumnIndexOrThrow("type"));
                String note=cursor.getString(cursor.getColumnIndexOrThrow("note"));

                Income income=new Income();
                income.setId(index);
                income.setUsername(uname);
                income.setMoney(money);
                income.setDate(date);
                income.setType(type);
                income.setNote(note);

                incomes.add(income);
            }
        //关闭
        cursor.close();
        return incomes;
    }


    //查询Incomes
    public List<Income> queryIncomes(){
        //获取写入数据库函数
        SQLiteDatabase db=getWritableDatabase();
        //查询到的数组
        List<Income> incomeList=new ArrayList<>();

        //查询,columns为空=select *、为new String[]{”username“,"password"} =select username和password
        Cursor cursor= db.query(TABLE_NAME_INCOME,null,null,null,null,null,null);
        //游标取数据
        if(cursor!=null){
            while (cursor.moveToNext()){
                String index=cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String uname=cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String money=cursor.getString(cursor.getColumnIndexOrThrow("money"));
                String date=cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String type=cursor.getString(cursor.getColumnIndexOrThrow("type"));
                String note=cursor.getString(cursor.getColumnIndexOrThrow("note"));

                Income income=new Income();
                income.setId(index);
                income.setUsername(uname);
                income.setMoney(money);
                income.setDate(date);
                income.setType(type);
                income.setNote(note);

                incomeList.add(income);
            }
            //关闭
            cursor.close();
        }
        return incomeList;
    }


    //删除Income
    public int deleteIncome(String username){
        //获取写入数据库函数
        SQLiteDatabase db=getWritableDatabase();
        //返回int表示删除了几个Income//"username=?"/"username like?"
        return db.delete(TABLE_NAME_INCOME,"username=?",new String[]{username});

    }


}
