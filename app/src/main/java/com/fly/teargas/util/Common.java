package com.fly.teargas.util;

import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * TODO 增加对日期显示格式化 formatDateString
 *
 * @author hcl Modified 2013-5-6
 */
public class Common {
    /**
     * ��֤�ַ��Ƿ���email
     *
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        Pattern pattern = Pattern.compile(
                "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * ��֤�ַ��Ƿ���URL
     *
     * @param str
     * @return
     */
    public static boolean isURL(String str) {
        Pattern pattern = Pattern
                .compile(
                        "^(http://|https://)?((?:[A-Za-z0-9]+-[A-Za-z0-9]+|[A-Za-z0-9]+)\\.)+([A-Za-z]+)[/\\?\\:]?.*$",
                        Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * ��֤�Ƿ����ֻ����
     *
     * @param str
     * @return
     */
    public static boolean isCellphone(String str) {
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * ��֤�Ƿ���QQ����
     *
     * @param str
     * @return
     */
    public static boolean isQQ(String str) {
        Pattern pattern = Pattern.compile("[1-9]{5,10}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        /* ȡ����չ�� */
        String end = fName
                .substring(fName.lastIndexOf(".") + 1, fName.length())
                .toLowerCase();

		/* ����չ������;���MimeType */
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
                || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            type = "audio";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
                || end.equals("jpeg") || end.equals("bmp")) {
            type = "image";
        } else if (end.equals("apk")) {
			/* android.permission.INSTALL_PACKAGES */
            type = "application/vnd.android.package-archive";
        } else {
            type = "*";
        }
		/* ����޷�ֱ�Ӵ򿪣����������б���û�ѡ�� */
        if (end.equals("apk")) {
        } else {
            type += "/*";
        }
        return type;
    }

    /**
     * ��ȡ�ļ�����չ��
     *
     * @param filename �ļ���
     * @param defExt   Ĭ��
     * @return
     */
    public static String getExtension(String filename, String defExt) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');

            if ((i > -1) && (i < (filename.length() - 1))) {
                return filename.substring(i + 1);
            }
        }
        return defExt;
    }

    /**
     * �Ƿ��д洢���ǿɶ���д״̬
     *
     * @return
     */
    public static Boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static String getSDPath() {

        if (isSDCardMounted()) {
            return Environment.getExternalStorageDirectory().toString();
        } else {
            return null;
        }
    }

    public static String formatCurrentDateTime() {
        return formatDateTimeString(new Date());
    }

    public static String formatDateTimeString(Date current) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(current);
        date = formatDateString(date);

        sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(current);

        return date + " " + time;

    }

    public static String formatDateString(Date current) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(current);
        date = formatDateString(date);

        return date;

    }

    public static String formatDateString(String dateStr) {
        String result = dateStr;
        try {
            String formatStr = "yyyy年MM月dd日";
            Date current = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateStr);

            // 昨天判断
            Calendar yesterday = Calendar.getInstance();
            yesterday.add(Calendar.DAY_OF_MONTH, -1);

            if (current.getYear() == date.getYear()
                    && current.getMonth() == date.getMonth()
                    && current.getDay() == date.getDay()) {
                return "今天";
            } else if (sdf.format(yesterday.getTime()).equals(dateStr)) {
                return "昨天";
            } else {
                if (current.getYear() == date.getYear()) {
                    formatStr = "MM月dd日";
                }

                sdf = new SimpleDateFormat(formatStr);

                return sdf.format(date);
            }
        } catch (Exception e) {

        }
        return result;
    }

    public static String getCurrentDateTimeString() {
        Date current = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(current);
    }

    /**
     * TODO 日期字符串转时间类型
     *
     * @param datetime
     * @return
     */
    public static Date getFormatDateTime(String datetime) {
        try {
            String format = "yyyy-MM-dd HH:mm:ss";
            Date current = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(datetime);

            return date;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * TODO 日期字符串转时间类型
     *
     * @param dates
     * @return
     */
    public static Date getFormatDate(String dates) {
        try {
            String format = "yyyy-MM-dd";
            Date current = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(dates);

            return date;
        } catch (Exception ex) {
            return null;
        }
    }

    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern
                    .compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            return m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean isSmallinBigString(String Small, String Big) {
        boolean flag = false;
        try {

            int a = Big.indexOf(Small);

            if (a >= 0) {
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * TODO 将数字设为千分位格式
     *
     * @param Small
     * @return
     */
    public static String formatStringNumberQFW(double Small) {
        NumberFormat numberFormat1 = NumberFormat.getNumberInstance();
        return numberFormat1.format(Small);

    }

    /**
     * TODO 分子除以分母返回2位小数
     *
     * @param no1
     * @return
     */
    public static double getFenZiChuFenMu(double no1, double no2) {
        if (no2 == 0)
            return 0;
        double baifenbi = (double) no1 / no2 * 100;
        BigDecimal b = new BigDecimal(baifenbi);
        double szl = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        return szl;
    }

    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(
                            src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(
                            src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

    /**
     * 处理输入的字符串，将字符串分割成以byteLength为宽度的多行字符串。
     * 根据需要，英文字符的空格长度为0.5，汉字的长度为2（GBK编码下，UTF-8下为3），数字英文字母宽度为1.05。
     *
     * @param str    输入字符串
     * @param length 以byteLength的长度进行分割（一行显示多宽）
     * @return 处理过的字符串
     */

    public static String[] splitStrToArray(String str, int length) {
        int i = 0;
        boolean isNum = true;
        StringBuffer sb = new StringBuffer("");
        char[] c = str.toCharArray();

        for (int k = 0; k < c.length; k++) {
            isNum = true;
            if (c[k] > 255) {
                i += 2;
                isNum = false;
            } else {
                i++;
            }

            if (i >= length + 1) {
                sb.append(" | ");

                if (isNum) {
                    i = 1;
                } else {
                    i = 2;
                }
            }

            sb.append(c[k]);
        }

        return sb.toString().split("\\|");
    }
}
