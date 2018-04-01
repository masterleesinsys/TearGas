package com.fly.teargas.util;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.JSONObject;

import org.apaches.commons.codec.binary.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES加密 解密
 */
public class DESUtil {
    private static byte[] iv = {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};

    /**
     * 数据加密
     */
    public static String encrypt(String encryptString, String encryptKey) {
        String result = "";
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
            result = new String(Base64.encodeBase64(encryptedData), "utf-8");
        } catch (Exception e) {
            LogUtils.e(e.toString());
            Placard.showInfo(e.toString());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 数据解密
     */
    public static String decrypt(String decryptString, String decryptKey) {
        String result = "";
        try {
            byte[] byteMi = Base64.decodeBase64(decryptString.getBytes());
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte decryptedData[] = cipher.doFinal(byteMi);
            result = new String(decryptedData);
        } catch (Exception e) {
            LogUtils.e(e.toString());
            Placard.showInfo(e.toString());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 晨梦加密
     */
    public static String chenMengEncrypt(String token) {
        String result = "";
        try {
            //密匙使用日期 日小时秒6位-----这样的话相当于这个数据包的有效期为1分钟（注密匙长度必须是8位长度，字母数字或组合都可）
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("ddHHmm");
            String key = sdf.format(new Date());
            //将数据加密    密匙=fly+key
            result = DESUtil.encrypt(token, "wy" + key);
        } catch (Exception e) {
            LogUtils.e(e.toString());
            Placard.showInfo(e.toString());
            e.printStackTrace();
        }
        //返回加密后的结果
        return result;
    }

    /**
     * 晨梦解密
     */
    public static String chenMengDecrypt(String data) throws Exception {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("ddHHmm");
        String key = sdf.format(new Date());
        //使用fly+key解密
        String result = DESUtil.decrypt(data, "wy" + key);
        return result;
    }

    /**
     * 解密示例
     */
    public static void decryption(String[] args) throws Exception {
        //拿到数据包
        String data = "6hfAFRMdJWtNS/1I765ZWQTaeaTGNpjM7aH+TDG92wfGl5Ct5Op28p4dmk1dyacIRqH5SmufygkgGwq0ETThIdZ6QZI2yaKc";
        //密匙使用日期 日小时秒6位-----这样的话相当于这个数据包的有效期为1分钟（注密匙长度必须是8位长度，字母数字或组合都可）
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("ddHHmm");
        String key = sdf.format(new Date());
        //使用fly+key解密
        String encrypt = DESUtil.decrypt(data, "wy" + key);
        //打印解密后的数据包
        System.out.println(encrypt);
    }

    /**
     * 加密示例
     */
    public static void encryption(String[] args) throws Exception {
        //使用用户名和token生成一个Json对象
        JSONObject jo = new JSONObject();
        jo.put("username", "peihongbo");
        jo.put("token", "26e948f0f9fa431bb161c42ee918fd1c");
        //密匙使用日期 日小时秒6位-----这样的话相当于这个数据包的有效期为1分钟（注密匙长度必须是8位长度，字母数字或组合都可）
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("ddHHmm");
        String key = sdf.format(new Date());
        //将数据加密    密匙=fly+key
        String encrypt = DESUtil.encrypt(jo.toJSONString(), "wy" + key);
        //打印获取到的数据包
        System.out.println(encrypt);
    }

}