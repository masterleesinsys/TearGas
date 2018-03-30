package com.fly.teargas.util;

import android.content.Context;
import android.util.Log;

import com.fly.teargas.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;


/**
 * TODO文件操作
 * 
 * @author tom Created 2013-5-20.
 * @param <T>
 */
public class FileUtils<T> {
	private static final String TAG = FileUtils.class.getSimpleName();
	public static String basePath = Tools.getSDPath() + "/chenshi/";

	static {
		File file = new File(basePath);
		if (Tools.isSDCardMounted() && !file.isDirectory()) {
			file.mkdirs();
		}
	}

	public static boolean deleteFiles(File file) {

		if (file.isFile() && file.exists()) {
			return file.delete();
		}
		if (file.isDirectory()) {
			File[] arrayOfFile = file.listFiles();
			for (File f : arrayOfFile) {
				if (!f.delete()) {
					return false;
				}
			}
		}
		return true;
	}

	public static String FormetFileSize(long paramLong) {
		String str;
		DecimalFormat localDecimalFormat = new DecimalFormat("#.00");
		if (paramLong < 1024L) {
			str = "0K";
		} else if (paramLong < 1048576L) {
			str = localDecimalFormat.format(paramLong / 1024.0D) + "K";
		} else if (paramLong < 1073741824L) {
			str = localDecimalFormat.format(paramLong / 1048576.0D) + "M";
		} else {
			str = localDecimalFormat.format(paramLong / 1073741824.0D) + "G";
		}
		return str;
	}

	public static long getFileSize(File paramFile) throws Exception {

		long l1 = 0l;
		if (!paramFile.exists()) {
			return 0l;
		} else {
			if (paramFile.isDirectory()) {
				File[] arrayOfFile = paramFile.listFiles();
				for (int i = 0; i < arrayOfFile.length; i++) {
					if (arrayOfFile[i].isDirectory()) {
						l1 += getFileSize(arrayOfFile[i]);
					} else {
						l1 += arrayOfFile[i].length();
					}
				}
			} else {
				l1 += paramFile.length();
			}

		}
		return l1;
	}

	/**
	 * 
	 * TODO 保存对象到本地缓存
	 * 
	 * @param info
	 * @param fileName
	 */
	public static void saveLocalObject(Object info, String fileName) {
		if (info == null)
			return;

		File file = new File(basePath + Tools.hash(fileName));
		if (Tools.isSDCardMounted()) {
			try {
				if (file.exists()) {
					deleteFiles(file);
				}

				FileOutputStream fos = null;
				ObjectOutputStream oos = null;

				try {
					fos = new FileOutputStream(file);
					oos = new ObjectOutputStream(fos);
					oos.writeObject(info);
				} finally {
					oos.close();
					fos.close();
				}
			} catch (Exception ex) {
				Log.e(TAG, ex.getMessage());
			}
		}
	}

	/**
	 * 
	 * TODO 读取本地缓存对象
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 * @throws IllegalAccessException
	 */
	public static Object getLocalObject(String fileName) {
		Object info = null;
		File file = new File(basePath + Tools.hash(fileName));
		if (Tools.isSDCardMounted()) {
			if (file.exists()) {
				try {
					info = new ObjectInputStream(new FileInputStream(file)).readObject();
				} catch (Exception e) {
//					Log.e("FileUtils.getLocalObject", e.getMessage());
					return null;
				}
			}
		}

		return info;
	}

	public static boolean isExists(String fileName) {
		File file = new File(basePath + Tools.hash(fileName));
		if (Tools.isSDCardMounted()) {
			if (file.exists()) {
				return true;
			}
		}

		return false;
	}

	public static void deleteLocalObject(String fileName) {
		Object info = null;
		File file = new File(basePath + Tools.hash(fileName));
		if (Tools.isSDCardMounted()) {
			if (file.isFile() && file.exists()) {
				file.delete();
			}
		}
	}

	public static Object getPrivateObject(String fileName) {
		String path = MyApplication.getInstance().getCachePath();
		Object info = null;
		File file = new File(path + Tools.hash(fileName));
		if (file.exists()) {
			try {
				info = new ObjectInputStream(new FileInputStream(file)).readObject();
			} catch (Exception e) {
//				Log.e("FileUtils.getPrivateObject", e.getMessage());
				return null;
			}
		}

		return info;
	}

	public static void savePrivateObject(Object info, String fileName) {
		if (info == null)
			return;

		String path = MyApplication.getInstance().getCachePath();
		File file = new File(path + Tools.hash(fileName));
		try {
			if (file.exists()) {
				deleteFiles(file);
			}

			FileOutputStream fos = null;
			ObjectOutputStream oos = null;

			try {
				fos = new FileOutputStream(file);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(info);
			} finally {
				oos.close();
				fos.close();
			}
			
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage());
		}
	}

	public static boolean isPrivateExists(String fileName) {
		try {
			String path = MyApplication.getInstance().getCachePath();
			File file = new File(path + Tools.hash(fileName));
			if (file.exists()) {
				return true;
			}
		} catch (Exception ex) {

		}

		return false;
	}

	public static void deletePrivateObject(String fileName) {
		try {
			String path = MyApplication.getInstance().getCachePath();
			Object info = null;
			File file = new File(path + Tools.hash(fileName));
			if (file.isFile() && file.exists()) {
				file.delete();
			}
		} catch (Exception ex) {

		}
	}
	
	//文件拷贝
    //要复制的目录下的所有非子目录(文件夹)文件拷贝
    public static int CopySdcardFile(String fromFile, String toFile)
    {
         
        try
        {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0)
            {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;
             
        } catch (Exception ex)
        {
            return -1;
        }
    }
    
    public static int copy(String fromFile, String toFile)
    
    	{
        //要复制的文件目录
        File[] currentFiles;
        File root = new File(fromFile);
        //如同判断SD卡是否存在或者文件是否存在
        //如果不存在则 return出去
        if(!root.exists())
        {
            return -1;
        }
        //如果存在则获取当前目录下的全部文件 填充数组
        currentFiles = root.listFiles();
         
        //目标目录
        File targetDir = new File(toFile);
        //创建目录
        if(!targetDir.exists())
        {
            targetDir.mkdirs();
        }
        //遍历要复制该目录下的全部文件
        for(int i= 0;i<currentFiles.length;i++)
        {
            if(currentFiles[i].isDirectory())//如果当前项为子目录 进行递归
            {
                copy(currentFiles[i].getPath() + "/", toFile + currentFiles[i].getName() + "/");
                 
            }else//如果当前项为文件则进行文件拷贝
            {
                CopySdcardFile(currentFiles[i].getPath(), toFile + currentFiles[i].getName());
            }
        }
        return 0;
    }
   //拷贝assent文件夹下指定的文件夹到目的文件夹
	public static int CopyAssets(Context context, String assetDir, String dir) {
		
		String[] files;
		try {
			files = context.getResources().getAssets().list(assetDir);
		} catch (IOException e1) {
			return -1;
		}
		File mWorkingPath = new File(dir);
		// if this directory does not exists, make one.
		if (!mWorkingPath.exists()) {
			if (!mWorkingPath.mkdirs()) {
				Log.e("--CopyAssets--", "cannot create directory.");
			}
		}
//		else{//存在即退出
//			
//			return 0;
//		}

		for (int i = 0; i < files.length; i++) {
			try {
				String fileName = files[i];
				// we make sure file name not contains '.' to be a folder.
				if (!fileName.contains(".")) {
					if (0 == assetDir.length()) {
						CopyAssets(context, fileName, dir + fileName + "/");
					} else {
						CopyAssets(context, assetDir + "/" + fileName, dir + fileName
								+ "/");
					}
					continue;
				}
				File outFile = new File(mWorkingPath, fileName);
				if (outFile.exists())
					outFile.delete();
				InputStream in = null;
				if (0 != assetDir.length())
					in = context.getAssets().open(assetDir + "/" + fileName);
				else
					in = context.getAssets().open(fileName);
				OutputStream out = new FileOutputStream(outFile);

				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				in.close();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
    
}
