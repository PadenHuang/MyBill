package com.hwq.mybill.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类——判断手机号是否合理
 */
public class RulesUtil {

    public static boolean isMobile(String number) {


        // 正则表达式，数据效验
        // 需要真实运营商号码段
        // 手机号长度必须符合11位
        // 输入的手机号必须位数字类型
        // CTRL + ALT + L 格式化

        Pattern pattern = null;
        Matcher matcher = null;
        boolean flag = false;
        //判斷是否存在手機號服務商

        //正则表达式，首位為1，第二位為运营商段3、4、5、7、8，輸入0-9，數量九個
        pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        matcher = pattern.matcher(number);
        flag = matcher.matches();

        //判断运营商
                // 中国移动
//        if (!number.equals("") && number.substring(0, 3).equals("134")
//                || number.substring(0, 3).equals("135")
//
//                || number.substring(0, 3).equals("136")
//
//                || number.substring(0, 3).equals("137")
//
//                || number.substring(0, 3).equals("138")
//
//                || number.substring(0, 3).equals("139")
//
//                || number.substring(0, 3).equals("147")
//
//                || number.substring(0, 3).equals("150")
//
//                || number.substring(0, 3).equals("151")
//
//                || number.substring(0, 3).equals("152")
//
//                || number.substring(0, 3).equals("157")
//
//                || number.substring(0, 3).equals("158")
//
//                || number.substring(0, 3).equals("159")
//
//                || number.substring(0, 3).equals("178")
//
//                || number.substring(0, 3).equals("182")
//
//                || number.substring(0, 3).equals("183")
//
//                || number.substring(0, 3).equals("184")
//
//                || number.substring(0, 3).equals("187")
//
//                || number.substring(0, 3).equals("188")
//                // 中国联通
//                || number.substring(0, 3).equals("130")
//
//                || number.substring(0, 3).equals("131")
//
//                || number.substring(0, 3).equals("132")
//
//                || number.substring(0, 3).equals("145")
//
//                || number.substring(0, 3).equals("155")
//
//                || number.substring(0, 3).equals("156")
//
//                || number.substring(0, 3).equals("175")
//
//                || number.substring(0, 3).equals("176")
//
//                || number.substring(0, 3).equals("185")
//
//                || number.substring(0, 3).equals("186")
//                //电信
//                || number.substring(0, 3).equals("133")
//
//                || number.substring(0, 3).equals("149")
//
//                || number.substring(0, 3).equals("153")
//
//                || number.substring(0, 3).equals("177")
//
//                || number.substring(0, 3).equals("180")
//
//                || number.substring(0, 3).equals("181")
//
//                || number.substring(0, 3).equals("189")
//                //"虚拟运营商"
//                || number.substring(0, 3).equals("170")) {
//                 //TODO:
//        }

        return flag;
    }
}
