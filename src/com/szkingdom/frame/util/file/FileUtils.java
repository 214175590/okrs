package com.szkingdom.frame.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * @简述：<一句话介绍>
 * @详述：<详细介绍>
 * @author admin
 * @since 1.0
 * @see
 */
public class FileUtils {

	public static void main(String[] args) {
		System.out.println(getNameToPath("dasdsadsad\\dsadsad\\ddddd.jpg"));
	}

	/**
	 * 复制文件线程
	 * 
	 * @author yisin
	 * @date 2013-1-7 上午10:28:31
	 * @param source
	 * @param target
	 * @see com.szkingdom.frame.util.file.FileUtils#copyDirByThread
	 */
	public static void copyDirByThread(String source, String target, int type) {
		new FileThread(source, target, type).start();
	}

	/**
	 * 删除文件线程
	 * 
	 * @author yisin
	 * @date 2013-1-7 上午10:28:46
	 * @param filePath
	 * @see com.szkingdom.frame.util.file.FileUtils#deleteFileThread
	 */
	public static void deleteFileThread(String filePath) {
		new DeleteFileThread(filePath).start();
	}

	/**
	 * 复制文件夹
	 * 
	 * @author yisin
	 * @date 2013-1-7 上午10:28:15
	 * @param source
	 * @param target
	 * @see com.szkingdom.frame.util.file.FileUtils#copyDir
	 */
	public static void copyDir(String source, String target) {
		if (source != null && target != null
				&& !target.equalsIgnoreCase(source)) {
			File sourceFiles = new File(source);
			File targetFiles = new File(target);
			if (!targetFiles.exists()) {
				targetFiles.mkdirs();
			}
			File tarFile = null;
			File[] files = sourceFiles.listFiles();
			for (File file : files) {
				if (file.isFile()) {
					tarFile = new File(targetFiles.getAbsolutePath()
							+ File.separator + file.getName());
					copyFile(file, tarFile);
				} else {
					copyDir(sourceFiles.getAbsolutePath() + File.separator
							+ file.getName(), targetFiles.getAbsolutePath()
							+ File.separator + file.getName());
				}
			}
		}
	}

	/**
	 * 复制文件
	 * 
	 * @author yisin
	 * @date 2013-1-7 上午10:28:00
	 * @param source
	 * @param target
	 * @see com.szkingdom.frame.util.file.FileUtils#copyFile
	 */
	public static void copyFile(File source, File target) {
		if (!target.isFile() && !target.exists()) {
			try {
				byte[] by = new byte[1024];
				FileInputStream in = new FileInputStream(source);
				FileOutputStream out = new FileOutputStream(target);
				while (in.read(by) > 0) {
					out.write(by);
				}
				in.close();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获得指定目录的父目录
	 * 
	 * @param dirPath
	 * @param userName
	 * @return
	 */
	public static String[] getParentDir(String dirPath, String userName) {
		String[] parent = new String[0];
		int beginIndex = dirPath.indexOf(userName);
		if ((beginIndex + userName.length() + 1) < dirPath.length()) {
			String dirs = dirPath.substring(beginIndex + userName.length() + 1);
			if (null != dirs && dirs.length() > 0) {
				if (dirs.indexOf("/") != -1) {
					parent = dirs.split("/");
				} else if (dirs.indexOf("\\") != -1) {
					parent = dirs.split("\\\\");
				} else {
					parent = new String[] { dirs };
				}
			}
		}
		return parent;
	}

	/**
	 * 由文件字节数计算文件大小，单位KB,MB
	 * 
	 * @param size
	 * @return
	 */
	public static String fileSize(long size) {
		float fa = size;
		String kb = "0 Kb";
		if (size < (1024 * 1024)) {
			kb = siSheWuRu(fa / 1024) + " Kb";
		} else {
			kb = siSheWuRu(fa / (1024 * 1024)) + " Mb";
		}
		return kb;
	}

	/**
	 * 数值四舍五入
	 * 
	 * @param fa
	 * @return
	 */
	public static String siSheWuRu(float fa) {
		String sa = String.valueOf(fa);
		if (sa.indexOf(".") != -1) {
			int startIndex = sa.indexOf(".");
			int endIndex = startIndex + 3;
			endIndex = endIndex > sa.length() ? sa.length() : endIndex;
			sa = sa.substring(0, endIndex);
		}
		return sa;
	}

	/**
	 * 根据路径获取文件名称
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getNameToPath(String filePath) {
		String fileName = "";
		if (null != filePath) {
			int index = filePath.lastIndexOf("/");
			if (index != -1) {
				fileName = filePath.substring(index);
			} else {
				index = filePath.lastIndexOf("\\");
				if (index != -1) {
					fileName = filePath.substring(index + 1);
				} else {
					index = filePath.lastIndexOf("\\\\");
					if (index != -1) {
						fileName = filePath.substring(index + 2);
					}
				}
			}
		}
		return fileName;

	}

	/**
	 * 创建文件夹-----判断是否存在，不存在则创建
	 * 
	 * @param dirName
	 */
	public static void createDir(String dirName) {
		if (null != dirName) {
			File file = new File(dirName);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
	}

	/**
	 * 从文件全名中获取后缀名 带.
	 */
	public static String getFileZui(String fileName) {
		String zui = "";
		if (null != fileName) {
			int index = fileName.lastIndexOf(".");
			if (index != -1) {
				zui = fileName.substring(index, fileName.length());
			}
		}
		return zui.toLowerCase();
	}

	/**
	 * 从文件全名中获取后缀名 不带.
	 */
	public static String getFileZui2(String fileName) {
		String zui = "";
		if (null != fileName) {
			int index = fileName.lastIndexOf(".");
			if (index != -1) {
				zui = fileName.substring(index + 1, fileName.length());
			}
		}
		return zui.toLowerCase();
	}

	/**
	 * 判断文件类型，返回相应的目录名称
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileGenre(String fileName) {
		String dirs = "other";
		if (null != fileName) {
			String zui = getFileZui(fileName);
			if (null != zui && !"".trim().equals(zui)) {
				if (zui.toLowerCase().equals(".mp3")) {
					dirs = "media";
				} else if (zui.toLowerCase().equals(".wma")) {
					dirs = "media";
				} else if (zui.toLowerCase().equals(".wmv")) {
					dirs = "media";
				} else if (zui.toLowerCase().equals(".avi")) {
					dirs = "media";
				} else if (zui.toLowerCase().equals(".flv")) {
					dirs = "media";
				} else if (zui.toLowerCase().equals(".swf")) {
					dirs = "media";
				} else if (zui.toLowerCase().equals(".rmvb")) {
					dirs = "media";
				} else if (zui.toLowerCase().equals(".jpg")) {
					dirs = "image";
				} else if (zui.toLowerCase().equals(".gif")) {
					dirs = "image";
				} else if (zui.toLowerCase().equals(".png")) {
					dirs = "image";
				} else if (zui.toLowerCase().equals(".bmp")) {
					dirs = "image";
				} else if (zui.toLowerCase().equals(".ico")) {
					dirs = "image";
				} else if (zui.toLowerCase().equals(".txt")) {
					dirs = "text";
				} else if (zui.toLowerCase().equals(".html")) {
					dirs = "text";
				} else if (zui.toLowerCase().equals(".htm")) {
					dirs = "text";
				} else if (zui.toLowerCase().equals(".jsp")) {
					dirs = "text";
				} else if (zui.toLowerCase().equals(".asp")) {
					dirs = "text";
				} else if (zui.toLowerCase().equals(".xml")) {
					dirs = "text";
				} else if (zui.toLowerCase().equals(".rar")) {
					dirs = "application";
				} else if (zui.toLowerCase().equals(".zip")) {
					dirs = "application";
				} else if (zui.toLowerCase().equals(".exe")) {
					dirs = "application";
				} else if (zui.toLowerCase().equals(".iso")) {
					dirs = "application";
				} else if (zui.toLowerCase().equals(".7z")) {
					dirs = "application";
				} else {
					dirs = "archive";
				}
			}
		}
		return dirs;
	}

	public static String getLowerImage(String fileName) {
		if (fileName != null && fileName.indexOf(".") != -1) {
			int index = fileName.lastIndexOf(".");
			fileName = fileName.substring(0, index) + "_lower"
					+ fileName.substring(index);
		}
		return fileName;
	}

	public static String getUpperImage(String fileName) {
		if (fileName != null && fileName.indexOf(".") != -1) {
			int index = fileName.lastIndexOf(".");
			fileName = fileName.substring(0, index) + "_upper"
					+ fileName.substring(index);
		}
		return fileName;
	}

	/**
	 * 设置文件保存路径
	 * 
	 * @param request
	 */
	public static String getFileSavePath(HttpServletRequest request,
			String fileName) {
		String fileSavePath = "";
		String dir = FileUtils.getFileGenre(fileName);
		String dirName = request.getSession().getServletContext()
				.getInitParameter("fileSaveDirName")
				+ "/" + dir;
		String spath = request.getRealPath("");
		int index = spath.lastIndexOf("\\");
		fileSavePath = spath.substring(0, index + 1) + dirName + "/";
		fileSavePath = fileSavePath.replaceAll("\\\\", "/");
		FileUtils.createDir(fileSavePath);
		return fileSavePath + fileName;
	}

	public static boolean writeFile(String filePath) {
		// 以服务器的文件保存地址和原文件名建立上传文件输出流
		try {
			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);
			FileInputStream fis = new FileInputStream(file);
			byte[] b = new byte[1024];
			int nRead = 0;
			while ((nRead = fis.read(b)) != -1) {
				fos.write(b, 0, nRead);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}

class FileThread extends Thread {
	private String source;
	private String target;
	private int type = 0;

	public FileThread() {

	}

	public FileThread(String source, String target, int type) {
		this.source = source;
		this.target = target;
		this.type = type;
	}

	public void run() {
		if (type == 0) {
			FileUtils.copyDir(source, target);
		} else if (type == 1) {
			FileUtils.copyFile(new File(source), new File(target));
		}
	}
}

class DeleteFileThread extends Thread {
	private String filePath;
	private File file;
	private int time = 0;

	public DeleteFileThread() {

	}

	public DeleteFileThread(String filePath) {
		this.filePath = filePath;
		file = new File(filePath);
	}

	public void run() {
		try {
			while (true) {
				if (time < 3) {
					Thread.sleep(1000);
					time++;
				} else {
					if (file != null && file.exists()) {
						System.out.print("被删除文件：" + file.getName());
						System.out.println("\t路径：" + filePath);
						file.delete();
					}
					return;
				}
			}
		} catch (InterruptedException e) {
		}
	}
}
