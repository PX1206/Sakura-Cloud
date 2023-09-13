package com.sakura.common.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtil {
	/**
	 * zip解压
	 *
	 * @param srcFile     zip源文件
	 * @param destDirPath 解压后的目标文件夹
	 * @throws RuntimeException 解压失败会抛出运行时异常
	 */
	public static List<String> unZip(File srcFile, String destDirPath) throws RuntimeException {
		//记录解压出来的所有文件名
		List<String> filesName = new ArrayList<>();
		long start = System.currentTimeMillis();
		// 判断源文件是否存在
		if (!srcFile.exists()) {
			throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
		}
		// 开始解压
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(srcFile, Charset.forName("GBK"));
			Enumeration<?> entries = zipFile.entries();

			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				//添加进filesName
				filesName.add(entry.getName());
				System.out.println("解压文件:" + entry.getName());
				// 如果是文件夹，就创建个文件夹
				if (entry.isDirectory()) {
					String dirPath = destDirPath + "/" + entry.getName();
					File dir = new File(dirPath);
					dir.mkdirs();
				} else {
					// 如果是文件，就先创建一个文件，然后用io流把内容copy过去
					File targetFile = new File(destDirPath + "/" + entry.getName());
					// 保证这个文件的父文件夹必须要存在
					if (!targetFile.getParentFile().exists()) {
						targetFile.getParentFile().mkdirs();
					}
					targetFile.createNewFile();
					// 将压缩文件内容写入到这个文件中
					InputStream is = zipFile.getInputStream(entry);
					FileOutputStream fos = new FileOutputStream(targetFile);
					int len;
					byte[] buf = new byte[1024];
					while ((len = is.read(buf)) != -1) {
						fos.write(buf, 0, len);
					}
					// 关流顺序，先打开的后关闭
					fos.close();
					is.close();
				}
			}
			long end = System.currentTimeMillis();
			System.out.println("解压完成，耗时：" + (end - start) + " ms");
		} catch (Exception e) {
			throw new RuntimeException("unzip error from ZipUtils", e);
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return filesName;
	}


	/**
	 * 删除文件
	 *
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return false;
		}
		file.delete();
		return true;
	}

	public static Boolean deleteFileDirectory(File file) {
		//判断文件不为null或文件目录存在
		if (file == null || !file.exists()) {
			System.out.println("文件删除失败,请检查文件是否存在以及文件路径是否正确");
			return false;
		}
		//获取目录下子文件
		File[] files = file.listFiles();
		//遍历该目录下的文件对象
		if (files != null) {
			for (File f : files) {
				//判断子目录是否存在子目录,如果是文件则删除
				if (f.isDirectory()) {
					//递归删除目录下的文件
					deleteFileDirectory(f);
				} else {
					//文件删除
					f.delete();
					//打印文件名
					System.out.println("文件名：" + f.getName());
				}
			}
		}
		//文件夹删除
		file.delete();
		return true;
	}

}
