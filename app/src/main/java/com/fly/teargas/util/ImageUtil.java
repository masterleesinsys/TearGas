package com.fly.teargas.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import com.fly.teargas.MyApplication;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class ImageUtil {
    private static final String TAG = "ImageUtil";
    public static String videoPath = MyApplication.ROOT_PATH + "mediacache/";

    @SuppressLint("UseSparseArrays")
    private static Map<Integer, int[]> sizeType = new HashMap<>();

    static {
        createImageFolder();
        sizeType.put(1, new int[]{800, 480});
        sizeType.put(2, new int[]{480, 320});
        sizeType.put(3, new int[]{320, 240});
    }

    public static boolean createImageFolder() {

        boolean flag = false;
        File file = new File(getDownloadImagePath());
        if (Tools.isSDCardMounted()) {
            file.mkdirs();
            flag = true;
        }
        return flag;
    }

    public static boolean createTempImageFolder() {

        boolean flag = false;
        File file = new File(getTempImagePath());
        if (Tools.isSDCardMounted()) {
            flag = file.mkdirs();
        }
        return flag;
    }

    public static String getImageName(String imageURL) {
        return imageURL.substring(imageURL.lastIndexOf("/") + 1);
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static boolean isExist(String fullPath) {
        File file = new File(fullPath);
        if (file.exists() && file.isFile())
            return true;
        else
            return false;
    }

    public static String getDownloadImagePath() {
        return MyApplication.ROOT_PATH + "download/";
    }

    public static String getTempImagePath() {
        return MyApplication.ROOT_PATH + "tmpimage/";
    }

    /**
     * 加载本地图片
     *
     * @return
     */
    public static Bitmap getLoacalBitmap(String path) {
        try {
            File f = new File(path);
            if (!f.exists() || f.isDirectory())
                return null;

            FileInputStream fis = new FileInputStream(path);
            return BitmapFactory.decodeStream(fis);
        } catch (Exception e) {
            Log.d("ImageUtil", e.toString());
            return null;
        }
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @return
     */
    public static Bitmap readBitMap(Context context, String path) {

        //获取资源图片
        try {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            File f = new File(path);
            if (!f.exists() || f.isDirectory())
                return null;

            FileInputStream fis = new FileInputStream(path);
            return BitmapFactory.decodeStream(fis, null, opt);
        } catch (Exception e) {
            Log.d("ImageUtil", e.toString());
            return null;
        }
    }

    public static Drawable getLoacalDrawable(String path, String srcname) {
        try {
            File f = new File(path);
            if (!f.exists() || f.isDirectory())
                return null;

            FileInputStream fis = new FileInputStream(path);
            return Drawable.createFromStream(fis, srcname);
        } catch (Exception e) {
            Log.d("ImageUtil", e.toString());
            return null;
        }
    }

    public static Bitmap zoomBitmap(Bitmap target) {
        int width = target.getWidth();
        int height = target.getHeight();
        Matrix matrix = new Matrix();
        Bitmap result = Bitmap.createBitmap(target, 0, 0, width, height, matrix, true);
        return result;
    }

    public static boolean saveBitmap(String path, String imgName, Bitmap bitmap) {

        if (bitmap == null) {
            return false;
        } else {
            try {
                File file = new File(path + imgName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos;
                fos = new FileOutputStream(file);
                bitmap.compress(CompressFormat.JPEG, 80, fos);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    // public static Bitmap addPlusPic(Context paramContext, Bitmap bitmap) {
    // int i = bitmap.getWidth();
    // int j = bitmap.getHeight();
    // int k = i + 13;
    // int l = j + 13;
    // Bitmap bitmap1 = Bitmap.createBitmap(k, l, Bitmap.Config.ARGB_8888);
    // Canvas canvas = new Canvas(bitmap1);
    // canvas.drawBitmap(bitmap, 13, 0, null);
    // Bitmap bitmap2 =
    // BitmapFactory.decodeResource(paramContext.getResources(),
    // R.drawable.phone);
    // float f = j - 26;
    // canvas.drawBitmap(bitmap2, 0, f, null);
    // canvas.save(Canvas.ALL_SAVE_FLAG);
    // canvas.restore();
    // if ((bitmap2 != null) && (!(bitmap2.isRecycled())))
    // bitmap2.recycle();
    // return bitmap1;
    // }

    /**
     * TODO 删除文件
     *
     * @param fileName
     * @return
     */
    public static boolean deleteBitmap(String fileName) {
        String path = getDownloadImagePath() + getImageName(fileName);

        return FileUtils.deleteFiles(new File(path));
    }

    public static boolean deleteVideo(String fileName) {
        String path = videoPath + getImageName(fileName);

        return FileUtils.deleteFiles(new File(path));
    }

    public static String getImageString(String imgFilePath, int bigimg) {
        String imageString = null;
        int wlong = 480;
        Bitmap mBitmap = BitmapFactory.decodeFile(imgFilePath);
        if (mBitmap != null) {
            switch (bigimg) {
                case 1:
                    wlong = 800;
                    break;
                case 2:
                    wlong = 480;
                    break;
                case 3:
                    wlong = 320;
                    break;
            }
            Matrix matrix = new Matrix();
            int mWidth = mBitmap.getWidth();
            int mHeight = mBitmap.getHeight();
            if (mWidth >= mHeight) {
                float scaleWidth = (float) wlong / mWidth;
                matrix.postScale(scaleWidth, scaleWidth);//缩放
            } else {
                float scaleWidth = (float) wlong / mHeight;
                matrix.postScale(scaleWidth, scaleWidth);//缩放
            }

//	              float scaleHeight=(float)800/mHeight;
//	              Log.i("scale", scaleWidth+"++++++++++++"+scaleHeight);

            Bitmap newBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            newBitmap.compress(CompressFormat.JPEG, 100, out);
            byte[] bytes = out.toByteArray();
            imageString = Base64.encodeToString(bytes, Base64.DEFAULT);
            System.out.println(imageString);
        }
        return imageString;
    }

    public static Bitmap getImageBitmapString(String imgFilePath, int bigimg) {
        Bitmap imageString = null;
        int wlong = 480;
        Bitmap mBitmap = BitmapFactory.decodeFile(imgFilePath);
        if (mBitmap != null) {
            switch (bigimg) {
                case 1:
                    wlong = 800;
                    break;
                case 2:
                    wlong = 480;
                    break;
                case 3:
                    wlong = 320;
                    break;
            }
            Matrix matrix = new Matrix();
            int mWidth = mBitmap.getWidth();
            int mHeight = mBitmap.getHeight();
            if (mWidth >= mHeight) {
                float scaleWidth = (float) wlong / mWidth;
                matrix.postScale(scaleWidth, scaleWidth);//缩放
            } else {
                float scaleWidth = (float) wlong / mHeight;
                matrix.postScale(scaleWidth, scaleWidth);//缩放
            }

//	              float scaleHeight=(float)800/mHeight;
//	              Log.i("scale", scaleWidth+"++++++++++++"+scaleHeight);

            Bitmap newBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
            imageString = newBitmap;

        }
        return imageString;
    }

    public static String getBitmapToString(Bitmap bitmap, int bigimg) {
        String imageString = null;
        int wlong = 480;
        Bitmap mBitmap = bitmap;
        if (mBitmap != null) {
            switch (bigimg) {
                case 1:
                    wlong = 800;
                    break;
                case 2:
                    wlong = 480;
                    break;
                case 3:
                    wlong = 320;
                    break;
            }
            Matrix matrix = new Matrix();
            int mWidth = mBitmap.getWidth();
            int mHeight = mBitmap.getHeight();
            if (mWidth >= mHeight) {
                float scaleWidth = (float) wlong / mWidth;
                matrix.postScale(scaleWidth, scaleWidth);//缩放
            } else {
                float scaleWidth = (float) wlong / mHeight;
                matrix.postScale(scaleWidth, scaleWidth);//缩放
            }

//	              float scaleHeight=(float)800/mHeight;
//	              Log.i("scale", scaleWidth+"++++++++++++"+scaleHeight);

            Bitmap newBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            newBitmap.compress(CompressFormat.JPEG, 100, out);
            byte[] bytes = out.toByteArray();
            imageString = Base64.encodeToString(bytes, Base64.DEFAULT);
            System.out.println(imageString);
        }
        return imageString;
    }

    public static String getBitmapToString(Bitmap bitmap) {

        String imageString = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 100, out);
            byte[] bytes = out.toByteArray();
            imageString = Base64.encodeToString(bytes, Base64.DEFAULT);
            System.out.println(imageString);
        } catch (Exception e) {
            Log.e(TAG + "(url)", e.toString());
            return "";
        }
        return imageString;
    }

    public static String getFielnameToString(String filepathname) {

        String imageString = null;
        try {
            imageString = Base64.encodeToString(getFileToByte(filepathname), Base64.DEFAULT);
            System.out.println(imageString);
        } catch (Exception e) {
            Log.e(TAG + ".saveBitmap", e.toString());
            return "";
        }
        return imageString;
    }

    public static byte[] getFileToByte(String filepathname) throws IOException {
        byte[] content = null;
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filepathname));
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            in.close();

            content = out.toByteArray();
        } catch (Exception e) {
            Log.e(TAG + ".saveBitmap", e.toString());
        }
        return content;
    }

    public static boolean saveBitmap(String url) {

        try {
            String fileName = ImageUtil.getImageName(url);
            if (fileName == null || fileName.trim().equals(""))
                return true;

            String path = getDownloadImagePath() + fileName;
            if (ImageUtil.isExist(path)) {
                return true;
            }
            FileOutputStream fos = new FileOutputStream(path);
            URL fileUrl = new URL(url);
            Bitmap bitmap = BitmapFactory.decodeStream(fileUrl.openStream());
            bitmap.compress(CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        } catch (Exception e) {
            Log.e(TAG + ".saveBitmap", e.toString());
            return false;
        }
        return true;
    }

    public static boolean saveBitmap(File file, int type) {

        try {
            String path = ImageUtil.getDownloadImagePath() + ImageUtil.getImageName(file.getAbsolutePath());
            File newFile = new File(path);
            if (newFile.exists()) {
                newFile.delete();
            }
            newFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("wty", "e:" + e);
            return false;
        }
        return true;
    }

    public static Bitmap fitSizeImg(File file, int type) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            FileInputStream fis = new FileInputStream(file);
            BitmapFactory.decodeFileDescriptor(fis.getFD(), null, options);
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inSampleSize = computeSampleSize(options, -1, sizeType.get(type)[0] * sizeType.get(type)[1]);
            Log.d("wty", "width:" + options.outWidth + "height:" + options.outHeight + "inSampleSize" + options.inSampleSize);
            SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(BitmapFactory.decodeStream(fis, null, options));
            return softReference.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap fitSizeImg(InputStream fis, int type) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(fis, null, options);
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inSampleSize = computeSampleSize(options, -1, sizeType.get(type)[0] * sizeType.get(type)[1]);
            Log.d("wty", "width:" + options.outWidth + "height:" + options.outHeight + "inSampleSize" + options.inSampleSize);
            SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(BitmapFactory.decodeStream(fis, null, options));
            return softReference.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean saveBitmap(String name, Bitmap bitmap) {

        if (bitmap == null) {
            return false;
        } else {
            try {
                File file = new File(getDownloadImagePath() + name);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos;
                fos = new FileOutputStream(file);
                bitmap.compress(CompressFormat.PNG, 100, fos);
                if (bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    // public static boolean saveBitmap(String url, Image image, Context
    // context) {
    //
    // try {
    // String path = getDownloadImagePath() + ImageUtil.getImageName(url);
    // FileOutputStream fos = new FileOutputStream(path);
    // URL fileUrl = new URL(url);
    // Bitmap bitmap = BitmapFactory.decodeStream(fileUrl.openStream());
    // image.setHeight(bitmap.getHeight());
    // image.setWeight(bitmap.getWidth());
    // NewsDao.saveImage(image, context);
    // bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
    // if (!bitmap.isRecycled()) {
    // bitmap.recycle();
    // }
    // fos.flush();
    // fos.close();
    // } catch (Exception e) {
    // // e.printStackTrace();
    // Log.d("ImageUtil.saveBitmap", e.toString());
    // return false;
    // }
    // return true;
    // }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static Bitmap getHttpBitmap(String url) throws IOException {
        URL myFileUrl = null;
        Bitmap bitmap = null;

        myFileUrl = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
        conn.setConnectTimeout(10000);
        conn.setDoInput(true);
        conn.connect();
        InputStream is = conn.getInputStream();
        try {
            bitmap = BitmapFactory.decodeStream(is);
        } finally {
            is.close();
        }

        return bitmap;
    }

    /**
     * 组合涂鸦图片和源图片
     *
     * @param src       源图片
     * @param watermark 涂鸦图片
     * @return
     */
    public static Drawable doodle(Drawable src, Drawable watermark, int rate) {
        // 另外创建一张图片
        Drawable d = src; // xxx根据自己的情况获取drawable
        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap srcbm = bd.getBitmap();

        d = watermark;
        bd = (BitmapDrawable) d;
        Bitmap watermarkbm = bd.getBitmap();
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(rate, watermarkbm.getWidth(), watermarkbm.getHeight());
        Bitmap newwatermarkbm = Bitmap.createBitmap(watermarkbm, 0, 0, watermarkbm.getWidth(), watermarkbm.getHeight(), matrix, true);

        Bitmap newb = Bitmap.createBitmap(srcbm.getWidth(), srcbm.getHeight(), Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图

        Canvas canvas = new Canvas(newb);
        canvas.drawBitmap(srcbm, 0, 0, null);// 在 0，0坐标开始画入原图片src
        canvas.drawBitmap(newwatermarkbm, (srcbm.getWidth() - newwatermarkbm.getWidth()) / 2 - 2, (srcbm.getHeight() - newwatermarkbm.getHeight()) / 2 - 7, null); // 涂鸦图片画到原图片中间位置
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        watermarkbm.recycle();
        watermarkbm = null;

        newwatermarkbm.recycle();
        newwatermarkbm = null;

        BitmapDrawable newDrawable = new BitmapDrawable(newb);
        return newDrawable;
    }

    /**
     * 质量压缩法：
     */

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 150) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 图片按比例大小压缩方法
     */
    public static Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 320f;//
        float ww = 240f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Config.RGB_565;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//			return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;
    }

    /**
     *  TODO 将bitmap处理为圆形
     * @param bitmap
     * @param StrokeColor   边框颜色
     * @return
     */
    public Bitmap toRoundBitmap(Bitmap bitmap, int StrokeColor) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);

        Paint paint2 = new Paint();
        /* 去锯齿 */
        paint2.setAntiAlias(true);
        paint2.setFilterBitmap(true);
        paint2.setDither(true);
        paint2.setColor(StrokeColor);
        /* 设置paint的　style　为STROKE：空心 */
        paint2.setStyle(Paint.Style.STROKE);
        /* 设置paint的外框宽度 */
        paint2.setStrokeWidth(4);
        canvas.drawCircle(width / 2, height / 2, roundPx-2, paint2);
        return output;
    }
}
