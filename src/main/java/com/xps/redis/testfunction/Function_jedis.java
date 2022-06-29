package com.xps.redis.testfunction;


import redis.clients.jedis.Jedis;

import java.util.Random;

public class Function_jedis {

    public static void main(String[] args) {
        //phoneCode("17674704234");
        validationCode("17674704234", "686501");
    }

    //  3、输入验证码，点击验证，返回成功或失败
    public static void validationCode(String phone, String code) {
        Jedis jedis = new Jedis("192.168.182.131", 6379);
        String codeKey = "Validation" + phone + "code";
        String redisCode = jedis.get(codeKey);
        if (redisCode.equals(code)) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }
        jedis.close();
    }

    //2每个手机号每天只能输入3次
    public static void phoneCode(String phone) {
        //将验证码放到redis中设置过期时间
        Jedis jedis = new Jedis("192.168.182.131", 6379);
        //手机发送次数的key
        String phoneCountKey = "Validation" + phone;
        //存储验证码的key
        String codeKey = "Validation" + phone + "code";
        String phoneCount = jedis.get(phoneCountKey);
        if (phoneCount == null) {
            //没有发送次数，设置发送次数是1
            jedis.setex(phoneCountKey, 24 * 60 * 60, "1");
        } else if (Integer.parseInt(phoneCount) <= 2) {
            //发送次数+1
            jedis.incr(phoneCountKey);
        } else if (Integer.parseInt(phoneCount) >= 3) {
            System.out.println("今日发送验证码次数超过三次,不能继续发送了");
            jedis.close();
        }
        //发送验证码到redis里面
        String pscode = getCode();
        jedis.setex(codeKey, 120, pscode);
        jedis.close();
    }

    //1、输入手机号，点击发送后随机生成6位数字码，2分钟有效
    public static String getCode() {
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            int randomCode = random.nextInt(10);
            code += randomCode;
        }

        return code;
    }
}
