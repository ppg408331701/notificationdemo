package ppg.com.yanlibrary.utils.Sdcard;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

public class FileUtil {

	public static String createFolder(String path) {
		File outfile = new File(path);
		// 如果文件不存在，则创建一个新文件
		if (!outfile.exists())
			return outfile.mkdirs() ? path : null;
		else
			return path;
	}

	/**
	 * 写对象到文件中。
	 * 
	 * @param outFile
	 * @param object
	 */
	public static void writeObject(String outFile, Object object) {

		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(outFile)));
			out.writeObject(object);
			out.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * 读取对象
	 * 
	 * @param filePath
	 * @return
	 */
	public static Object readObject(String filePath) {
		File inFile = new File(filePath);
		Object o = null;
		try {
			ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(inFile)));
			o = in.readObject();
			in.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return o;
	}

	/**
	 * 获取文件夹大小
	 * 
	 * @param file
	 *            File实例
	 * @return long 单位为B
	 * @throws Exception
	 */
	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				size = size + getFolderSize(fileList[i]);
			} else {
				size = size + fileList[i].length();
			}
		}
		return size;// 1M = 1048576;
	}

	/**
	 * 文件大小单位换算
	 * 
	 * @param length
	 * @return
	 */
	public static String changeUnit(long length) {
		DecimalFormat df = new DecimalFormat("###.##");
		Long sizeLong = length;
		if (sizeLong < 1024)
			return sizeLong + "B";
		else if ((sizeLong = sizeLong / 1024) < 1024)
			return df.format((double) length / 1024) + "KB";
		else if ((sizeLong = sizeLong / 1024) < 1024)
			return df.format((double) length / (1024 * 1024)) + "MB";
		else if ((sizeLong = sizeLong / 1024) < 1024)
			return df.format(length / (1024 * 1024 * 1024)) + "GB";
		return "";
	}

	/**
	 * 删除指定目录下文件及目录
	 * 
	 * @param deleteThisPath
	 * @param filepath
	 * @return
	 */
	public static void deleteFolderFile(String filePath, boolean deleteThisPath)
			throws IOException {
		if (!TextUtils.isEmpty(filePath)) {
			File file = new File(filePath);

			if (file.isDirectory()) {// 处理目录
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFolderFile(files[i].getAbsolutePath(), true);
				}
			}
			if (deleteThisPath) {
				if (!file.isDirectory()) {// 如果是文件，删除
					file.delete();
				} else {// 目录
//					if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
//						file.delete();
//					}
				}
			}
		}
	}
}
