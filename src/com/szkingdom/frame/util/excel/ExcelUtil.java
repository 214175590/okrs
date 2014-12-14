package com.szkingdom.frame.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.szkingdom.frame.common.GlobalConstants;
import com.szkingdom.frame.common.reflect.YXObject;
import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.frame.util.DateUtil;
import com.szkingdom.frame.util.StringUtil;
import com.szkingdom.frame.util.file.FileUtils;
import com.zte.zxtswp.lib.file.FileHelper;
import com.zte.zxtswp.lib.file.excel.api.IExcel;

/**
 * 读取Excel2003,2007的辅助类，有问题的地方请及时提出来
 * 
 * @author RyanCai
 * 
 * 
 */
public class ExcelUtil {
	private static ILogger logger = LogFactory
			.getDefaultLogger(ExcelUtil.class);
	private boolean isNotMethod = false;
	private final static String getMedhodStr = "getValueForField";

	public static void main(String[] args) {

	}

	/**
	 * 
	 * @param outPath
	 *            ：文件输出路径
	 * @param head
	 *            ：第一行标题数组
	 * @param listobj
	 *            ：输出内容实体类List集合
	 * @param outattr
	 *            ：实体类中输出属性名称数组
	 */
	public void createExcel(String outPath, String[][] field, List<?> listobj) {
		String suffix = outPath.substring(outPath.lastIndexOf("."));
		if (suffix.equalsIgnoreCase(".xls")) {
			createExcel2003(outPath, field, listobj);
		} else if (suffix.equalsIgnoreCase(".xlsx")) {
			createExcel2007(outPath, field, listobj);
		} else {
			System.out.println("未知输出文件名");
		}
	}

	public void createExcel2003(String outPath, String[][] field,
			List<?> listobj) {
		HSSFWorkbook wb = new HSSFWorkbook();
		try {
			HSSFSheet sheet1 = wb.createSheet("Sheet1");
			// sheet1.setColumnWidth(3, 5000);
			Row headRow = sheet1.createRow(0);
			// 创建字体样式
			HSSFFont font = wb.createFont();
			font.setFontName("宋体");
			font.setBoldweight((short) 500);
			font.setFontHeight((short) 200);

			font.setColor(HSSFColor.BLACK.index);
			// 创建单元格样式
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
			// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			// 设置边框
			// style.setBottomBorderColor(HSSFColor.RED.index);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);

			style.setFont(font);// 设置字体
			if (null != field && field.length > 0) {
				// 生成Excel的头部
				int cloCount = field.length;// 总共的列数
				Cell hcel = null;
				for (int i = 0; i < cloCount; i++) {
					hcel = headRow.createCell(i);
					hcel.setCellValue(field[i][1]);
					hcel.setCellStyle(style);
				}
				// 生成头部下面的内容
				if (null != listobj && listobj.size() > 0) {
					int rowCount = listobj.size();// 总共的行数
					Object obj = null;
					Row row = null;
					Cell cel = null;
					String fieldName = null, value = null;
					for (int j = 1; j <= rowCount; j++) {
						// 这一行的数据
						obj = listobj.get(j - 1);
						// 从第二行开始生成
						row = sheet1.createRow(j);
						// 遍历生成这一行中的单元格
						for (int i = 0; i < cloCount; i++) {
							cel = row.createCell(i);
							sheet1.setColumnWidth(i, 5000);
							fieldName = field[i][0];
							value = getFieldVlaue(obj, fieldName);
							cel.setCellValue(value);
							cel.setCellStyle(style);
						}
					}

				}
			}
			FileOutputStream fout = new FileOutputStream(outPath);
			wb.write(fout);
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createExcel2007(String outPath, String[][] field,
			List<?> listobj) {
		XSSFWorkbook xwb = new XSSFWorkbook();
		try {
			XSSFSheet sheet1 = xwb.createSheet("Sheet1");
			XSSFRow headrow = sheet1.createRow(0);
			// 创建字体样式
			XSSFFont font = xwb.createFont();
			font.setFontName("宋体");
			font.setBoldweight((short) 550);
			font.setFontHeight((short) 200);
			font.setColor(HSSFColor.BLACK.index);
			// 创建单元格样式
			XSSFCellStyle style = xwb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 设置边框
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			// style.setWrapText(true);
			style.setFont(font);// 设置字体
			if (null != field && field.length > 0) {
				// 生成Excel的头部
				int cloCount = field.length;// 总共的列数
				Cell hcel = null;
				for (int i = 0; i < cloCount; i++) {
					hcel = headrow.createCell(i);
					hcel.setCellValue(field[i][1]);
					hcel.setCellStyle(style);
				}
				// 生成头部下面的内容
				fillSheet(listobj, sheet1, field, style);
			}
			FileOutputStream fout = new FileOutputStream(outPath);
			xwb.write(fout);
			fout.flush();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @简述：<生成Excel2007的报表以下载流的方式输出到页面>
	 * @详述：<详细介绍>
	 * @param response
	 *            ：页面的response响应对象
	 * @param excelName
	 *            ：生成的excel2007文件名
	 * @param fields
	 *            ：excel2007第一行的文字标题及对应的列
	 * @param listobj
	 *            ：内容集合
	 * @注意：数组outattr和head的长度必须相等，并且可以一一对应 例如 outattr中的ID对应着输出Excel头部和head中的"编号"
	 * 
	 */
	public void createExcel2007(HttpServletResponse response, String excelName,
			String[][] fields, List<?> listobj) {
		// 设置当前线程key
		XSSFWorkbook xwb = new XSSFWorkbook();
		try {
			XSSFSheet sheet1 = xwb.createSheet("Sheet1");
			XSSFRow headrow = sheet1.createRow(0);
			// 创建字体样式
			XSSFFont font1 = xwb.createFont();
			font1.setFontName("宋体");
			font1.setFontHeight((short) 200);
			font1.setColor(HSSFColor.BLACK.index);
			// 创建单元格样式
			XSSFCellStyle style1 = xwb.createCellStyle();
			XSSFCellStyle style2 = xwb.createCellStyle();
			style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 设置边框
			style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style2.setBorderTop(HSSFCellStyle.BORDER_THIN);

			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
			style1.setFillForegroundColor(HSSFColor.SEA_GREEN.index); // 填暗红色

			// style.setWrapText(true);
			style1.setFont(font1);// 设置字体
			style2.setFont(font1);// 设置字体
			if (null != fields && fields.length > 0) {
				// 生成Excel的头部
				int cloCount = fields.length;// 总共的列数
				Cell hcel = null;
				for (int i = 0; i < cloCount; i++) {
					hcel = headrow.createCell(i);
					hcel.setCellValue(fields[i][1]);
					hcel.setCellStyle(style1);
				}
				// 生成头部下面的内容
				fillSheet(listobj, sheet1, fields, style2);
			}
			// 设置response的编码方式
			response.setContentType("application/x-msdownload;charset=GB2312");
			// 写明要下载的文件的大小
			// 解决中文乱码
			String exName = StringUtil.toGbk(excelName);
			response.setHeader("Content-Disposition", "attachment;filename="
					+ exName + ".xlsx");
			response.setCharacterEncoding("GB2312");
			OutputStream myout = response.getOutputStream();
			xwb.write(myout);
			myout.flush();
			myout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @简述：<创建错误信息Excel，用于处理下载Excel的时候出错打印错误信息的>
	 * @详述：<详细介绍>
	 * @param outPath
	 * @param head
	 * @param errorInfo
	 * 
	 */
	public static void createErrorExcel(HttpServletResponse response,
			String head, String errorInfo, String excelName) {
		XSSFWorkbook xwb = new XSSFWorkbook();
		try {
			XSSFSheet sheet1 = xwb.createSheet("Sheet1");
			// 创建字体样式
			XSSFFont font = xwb.createFont();
			font.setFontName("宋体");
			font.setBoldweight((short) 550);
			font.setFontHeight((short) 200);
			font.setColor(HSSFColor.RED.index);
			// 创建单元格样式
			XSSFCellStyle style = xwb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 设置边框
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setFont(font);// 设置字体
			if (null != head && errorInfo != null) {
				// 生成Excel的头部
				Row hrow = sheet1.createRow(0);
				Row irow = sheet1.createRow(1);
				// 遍历生成这一行中的单元格
				for (int i = 0; i < 10; i++) {
					Cell cel = hrow.createCell(i);
					Cell cel2 = irow.createCell(i);
					cel.setCellStyle(style);
					cel2.setCellStyle(style);
				}
				// 合并单元格
				sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
				sheet1.autoSizeColumn(0);
				// 合并单元格
				sheet1.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));
				Cell cel3 = hrow.getCell(0);
				cel3.setCellValue(head);
				Cell cel4 = irow.getCell(0);
				cel4.setCellValue(errorInfo);
			}
			// FileOutputStream fout=new FileOutputStream(outPath);
			// xwb.write(fout);
			// fout.close();
			// 设置response的编码方式
			response.setContentType("application/x-msdownload;charset=GB2312");
			// 写明要下载的文件的大小
			// 解决中文乱码
			String exName = StringUtil.toGbk(excelName);
			response.setHeader("Content-Disposition", "attachment;filename="
					+ exName + ".xlsx");
			response.setCharacterEncoding("GB2312");
			OutputStream myout = response.getOutputStream();
			xwb.write(myout);
			myout.flush();
			myout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 填充XSSFSheet
	 * 
	 * @author yisin
	 * @date 2013-4-24 上午11:32:54
	 * @param listobj
	 * @param sheet
	 * @param fields
	 * @param style
	 * @see com.szkingdom.frame.util.excel.ExcelUtil#fillSheet
	 */
	public void fillSheet(List<?> listobj, XSSFSheet sheet, String[][] fields,
			XSSFCellStyle style) {
		isNotMethod = false;
		if (null != listobj && listobj.size() > 0) {
			int cloCount = fields.length;
			int rowCount = listobj.size();// 总共的行数
			Object obj = null;
			Row row = null;
			Cell cel = null;
			String fieldName = null, value = null;
			for (int j = 1; j <= rowCount; j++) {
				// 这一行的数据
				obj = listobj.get(j - 1);
				// 从第二行开始生成
				row = sheet.createRow(j);
				// 遍历生成这一行中的单元格
				for (int i = 0; i < cloCount; i++) {
					cel = row.createCell(i);
					sheet.setColumnWidth(i, 5000);
					fieldName = fields[i][0];
					value = getFieldVlaue(obj, fieldName);
					cel.setCellValue(value);
					cel.setCellStyle(style);
				}
			}
			cloCount = 0;
			rowCount = 0;
			obj = null;
			row = null;
			cel = null;
			fieldName = null;
			value = null;
		}
	}

	/**
	 * 往实体Bean中填充值
	 * 
	 * @param cla
	 *            ：要填充的实体Bean
	 * @param attr
	 *            :实体Bean和Excel文件中的属性数组
	 * @param lst
	 *            ：Excel文件读取后的List
	 * @return
	 */
	public List<?> fillBeanValue(Object cla, String[] attr, List<String[]> lst) {
		List<Object> list = new ArrayList<Object>();
		if (null != lst) {
			for (int i = 1; i < lst.size(); i++) {
				String[] ss = lst.get(i);
				Field[] field = cla.getClass().getDeclaredFields();
				Object obj = null;
				for (int a = 0; a < ss.length; a++) {
					for (Field field2 : field) {
						String attrName = attr[a];
						String fieldName = field2.getName();
						Class fieldType = field2.getType();

						try {
							if (null != fieldName && fieldName.equals(attrName)) {
								// 获得字段的数据类型
								// 获得set方法
								String setmethName = "set"
										+ fieldName.substring(0, 1)
												.toUpperCase()
										+ fieldName.substring(1);
								Method sm = cla.getClass()
										.getMethod(
												setmethName,
												new Class[] { YXObject
														.typeClass(fieldType
																.getName()) });
								Object val = YXObject.excType(fieldType, ss[a]);
								sm.invoke(cla, new Object[] { val });

							}
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				}
				obj = cla;
				list.add(obj);
			}

		} else {
			System.out.println("list 值为空");
		}
		return list;
	}

	/**
	 * 读取Excel文件，
	 * 
	 * @param excelfile
	 *            ：Excel文件
	 * @return Excel文件的每一行是一个字符串数值，整个Excel文件读取后返回一个List<String[]>
	 */
	public List<String[]> readExcel(File excelfile) {
		IExcel excel = FileHelper.createExcel(0);
		String name = excelfile.getName().substring(
				excelfile.getName().lastIndexOf("."));
		List<String[]> results = null;
		try {
			if (".xlsx".equals(name)) {
				results = excel.openExcelX(new FileInputStream(excelfile));
			} else {
				results = excel.openExcel(new FileInputStream(excelfile));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("读取Excel的时候出错了！");
		}

		return results;
	}

	/**
	 * 读取Excel2003
	 * 
	 * @param excelfile
	 *            ：Excel文件
	 * @return：Excel文件的每一行是一个字符串数值，整个Excel文件读取后返回一个List<String[]>
	 */
	public static List<String[]> readExcel2003(File excelfile) {
		List<String[]> results = new ArrayList<String[]>();
		HSSFWorkbook workbook = null;
		if (excelfile.exists()) {
			String fileName = excelfile.getName();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1); // 文件后辍.
			System.out.println("2003=" + suffix);
			FileInputStream fis = null;
			try {
				// 创建对Excel2003工作簿文件的引用
				if (suffix.toLowerCase().equalsIgnoreCase("xls")) {
					fis = new FileInputStream(excelfile);
					workbook = new HSSFWorkbook(fis);
				} else {
					System.out.println("Excel类型错误");
					return results;
				}
				// 创建对工作表的引用。
				// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
				HSSFSheet sheet = workbook.getSheetAt(0);
				// 从头部判断有多少列
				HSSFRow head = sheet.getRow(0);
				int celln = head.getLastCellNum();
				// System.out.println("有几列=" + celln);
				// 也可用getSheetAt(int index)按索引引用，
				// 在Excel文档中，第一张工作表的缺省索引是0，
				// 获得行迭代器
				Iterator<Row> rit = sheet.rowIterator();
				while (rit.hasNext()) {
					Row row = rit.next();
					// 每一行单元格中值的字符串数组
					String[] cvalue = new String[celln];
					// 遍历所有的行中单元格
					int iskonflag = 0;
					for (int i = 0; i < celln; i++) {
						Cell cell = row.getCell(i);
						if (null != cell) {
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_BLANK:
								cvalue[i] = null;
								iskonflag++;
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								boolean b = cell.getBooleanCellValue();
								cvalue[i] = Boolean.toString(b);
								break;
							case Cell.CELL_TYPE_NUMERIC:
								// 先看是否是日期格式
								if (org.apache.poi.ss.usermodel.DateUtil
										.isCellDateFormatted(cell)) {
									// 读取日期格式
									Date date = cell.getDateCellValue();
									SimpleDateFormat sdf = new SimpleDateFormat(
											"yyyy-MM-dd HH:mm:ss");
									cvalue[i] = sdf.format(date);
									// System.out.print(cell.getDateCellValue()
									// + "日期 ");
								} else {
									// 读取数字
									Double dou = cell.getNumericCellValue();
									cvalue[i] = dou.toString();
									// System.out.print(cell.getNumericCellValue()
									// + "数值 ");
								}
								break;
							case Cell.CELL_TYPE_STRING:
								String str = cell.getStringCellValue();
								if (null != str && str.trim().equals("")) {
									cvalue[i] = null;
								} else if (null != str
										&& !str.trim().equals("")) {
									cvalue[i] = str.trim();
								}
								// System.out.println("字符串类型/"+cell.getStringCellValue());
								break;
							case Cell.CELL_TYPE_FORMULA:
								String value = null;
								try {
									value = String
											.valueOf(cell.getStringCellValue())
											.trim().replaceAll(" ", "");
								} catch (IllegalStateException e) {
									value = String
											.valueOf(
													cell.getRichStringCellValue())
											.trim().replaceAll(" ", "");
								}
								cvalue[i] = value;
								// System.out.println("富字符串类型/"+cell.getStringCellValue());
								break;
							default:
								System.out.println("unsuported sell type");
								break;
							}
						} else {
							// 处理空单元格的情况
							cvalue[i] = null;
							iskonflag++;
						}
					}
					if (iskonflag == celln) {
						System.out.println("这一行全是空的");
						break;
					}
					results.add(cvalue);
				}
			} catch (Exception e) {
				System.out.println("~_~!出现异常: " + e);
			} finally {
				try {
					fis.close();
					FileUtils.deleteFileThread(excelfile.getAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("~_~!文件不存在......................");
		}
		return results;
	}

	/**
	 * 读取Excel2007
	 * 
	 * @param excelfile
	 *            ：Excel文件
	 * @return：Excel文件的每一行是一个字符串数值，整个Excel文件读取后返回一个List<String[]>
	 */
	public static List<String[]> readExcel2007(File excelfile) {
		List<String[]> results = new ArrayList<String[]>();
		XSSFWorkbook workbook = null;
		if (excelfile.exists()) {
			String fileName = excelfile.getName();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1); // 文件后辍.
			System.out.println("2007=" + suffix);
			FileInputStream fis = null;
			try {
				// 创建对Excel2003工作簿文件的引用
				if (suffix.toLowerCase().equalsIgnoreCase("xlsx")) {
					fis = new FileInputStream(excelfile);
					workbook = new XSSFWorkbook(fis);
				} else {
					System.out.println("~_~!Excel类型错误");
					return results;
				}
				// 创建对工作表的引用。
				// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
				XSSFSheet sheet = workbook.getSheetAt(0);
				// 从头部判断有多少列
				XSSFRow head = sheet.getRow(0);
				int celln = head.getLastCellNum();
				// System.out.println("有几列=" + celln);
				// 也可用getSheetAt(int index)按索引引用，
				// 在Excel文档中，第一张工作表的缺省索引是0，
				// 获得行迭代器
				Iterator<Row> rit = sheet.rowIterator();
				while (rit.hasNext()) {
					Row row = rit.next();
					// 每一行单元格中值的字符串数组
					String[] cvalue = new String[celln];
					// 遍历所有的行中单元格
					int iskonflag = 0;
					for (int i = 0; i < celln; i++) {
						Cell cell = row.getCell(i);
						if (null != cell) {
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_BLANK:
								cvalue[i] = null;
								iskonflag++;
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								boolean b = cell.getBooleanCellValue();
								cvalue[i] = Boolean.toString(b);
								break;
							case Cell.CELL_TYPE_NUMERIC:
								// 先看是否是日期格式
								if (org.apache.poi.ss.usermodel.DateUtil
										.isCellDateFormatted(cell)) {
									// 读取日期格式
									Date date = cell.getDateCellValue();
									SimpleDateFormat sdf = new SimpleDateFormat(
											"yyyy-MM-dd HH:mm:ss");
									cvalue[i] = sdf.format(date);
									// System.out.print(cell.getDateCellValue()
									// + "日期 ");
								} else {
									// 读取数字
									Double dou = cell.getNumericCellValue();
									cvalue[i] = dou.toString();
									// System.out.print(cell.getNumericCellValue()
									// + "数值 ");
								}
								break;
							case Cell.CELL_TYPE_STRING:
								String str = cell.getStringCellValue();
								if (null != str && str.trim().equals("")) {
									cvalue[i] = null;
								} else if (null != str
										&& !str.trim().equals("")) {
									cvalue[i] = str.trim();
								}
								// System.out.println("字符串类型/"+cell.getStringCellValue());
								break;
							case Cell.CELL_TYPE_FORMULA:
								String value = null;
								try {
									value = String
											.valueOf(cell.getStringCellValue())
											.trim().replaceAll(" ", "");
								} catch (IllegalStateException e) {
									value = String
											.valueOf(
													cell.getRichStringCellValue())
											.trim().replaceAll(" ", "");
								}
								cvalue[i] = value;
								// System.out.println("富字符串类型/"+cell.getStringCellValue());
								break;
							default:
								System.out.println("unsuported sell type");
								break;
							}
						} else {
							// 处理空单元格的情况
							cvalue[i] = null;
							iskonflag++;
						}
					}
					if (iskonflag == celln) {
						System.out.println("这一行全是空的");
						break;
					}
					results.add(cvalue);
				}
			} catch (Exception e) {
				System.out.println("出现异常: " + e.getMessage());
			} finally {
				try {
					fis.close();
					FileUtils.deleteFileThread(excelfile.getAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("~_~!文件不存在....................");
		}
		return results;
	}

	/**
	 * 读取Excel文件，没有使用ZTE的JAR实现
	 * 
	 * @param excelfile
	 *            ：Excel文件
	 * @return Excel文件的每一行是一个字符串数值，整个Excel文件读取后返回一个List<String[]>
	 */
	public static List<String[]> readExcel2(File excelfile) {
		List<String[]> results = null;
		if (null != excelfile) {
			String fname = excelfile.getName();
			String name = fname.substring(fname.lastIndexOf("."));
			try {
				if (".xlsx".equals(name)) {
					results = readExcel2007(excelfile);
				} else {
					results = readExcel2003(excelfile);
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("~_~!读取Excel的时候出错了" + e.getMessage());
			}
		}
		return results;
	}

	/**
	 * 
	 * @param obj
	 *            :要进入获取值的对象
	 * @param fielName
	 *            ：属性名称
	 * @return ：返回该对象Obj中属性名等于fielName的get方法返回值（全部转换 成String类型）
	 */
	public String getFieldVlaue(Object obj, String fielName) {
		String getMethodName = null, val = GlobalConstants.EMPTY_STRING;
		boolean isRun = true;
		Method method = null;
		Class reType = null;
		Object param[] = null;
		Object rvalue = null;
		Class pmClass[] = null;
		// -------------
		if (!isNotMethod) {
			try {
				pmClass = new Class[] { String.class };
				method = obj.getClass()
						.getDeclaredMethod(getMedhodStr, pmClass);
				if (method != null) {
					// 方法的返回类型
					reType = method.getReturnType();
					param = new Object[] { fielName };
					rvalue = method.invoke(obj, param);
					if (null != rvalue) {
						isRun = false;
						if (reType.getName().equals("java.util.Date")) {
							val = DateUtil.SDF_DATETIME.format(rvalue);
						} else {
							val = rvalue.toString();
						}
					}
				}
			} catch (Exception e) {
				isRun = true;
				isNotMethod = true;
			}
		}
		// -------------
		if (isRun) {
			try {
				pmClass = new Class[] {};
				getMethodName = "get"
						+ StringUtil.firstCharToUpperCase(fielName);
				method = obj.getClass().getDeclaredMethod(getMethodName,
						pmClass);
				if (method != null) {
					// 方法的返回类型
					reType = method.getReturnType();
					// 方法的返回值
					param = new Object[] {};
					rvalue = method.invoke(obj, param);
					if (null != rvalue) {
						if (reType.getName().equals("java.util.Date")) {
							val = DateUtil.SDF_DATETIME.format(rvalue);
						} else {
							val = rvalue.toString();
						}
					}
				}
			} catch (Exception e) {
				logger.debug(obj.getClass().getName() + "缺少方法：get"
						+ StringUtil.firstCharToUpperCase(fielName) + "()， "
						+ e.getMessage());
			}
		}
		getMethodName = null;
		method = null;
		reType = null;
		param = null;
		rvalue = null;
		pmClass = null;
		return val;
	}

	/**
	 * 
	 * @描述：<判断对象类型，并返回String值>
	 * @param obj
	 *            Object
	 * @return String
	 */
	public static Object excType(Class obj, String value) {
		String type = obj.getName();
		value = StringUtil.stringUncode(value);
		if (type.equals("java.lang.String")) {
			return value;
		} else if (type.equals("int")) {
			return StringUtil.stringToInt(value);
		} else if (type.equals("float")) {
			return StringUtil.stringToFloat(value, 0.0f);
		} else if (type.equals("double")) {
			return StringUtil.stringToDouble(value, 0.0d);
		} else if (type.equals("java.lang.Double")) {
			return StringUtil.stringToDouble(value, 0.0d);
		} else if (type.equals("java.lang.Float")) {
			return StringUtil.stringToFloat(value, 0.0f);
		} else if (type.equals("java.util.Date")) {
			if (value == null) {
				return null;
			}
			return new Timestamp(DateUtil.parse(value).getTime());
		} else if (type.equals("java.sql.Timestamp")) {
			if (value == null) {
				return null;
			}
			return DateUtil.parseToTimestamp(value, "yyyy-MM-dd HH:mm:ss");
		}
		return null;
	}

	/**
	 * 
	 * @简述：<将EXCEL中读取出来的浮点字符串转换成时间Date>
	 * @详述：<详细介绍>
	 * @param doubstr
	 *            :浮点字符串
	 * 
	 */
	public static Date DoublestrToDate(String doubstr) {
		Date date = null;
		if (null != doubstr) {
			double d = Double.parseDouble(doubstr);
			date = HSSFDateUtil.getJavaDate(d);
		}
		return date;
	}

	/**
	 * 
	 * @简述：<判断即将导入的Excel是否符合正确的模板>
	 * @详述：<将Excel解析的list中的第一行标题和给定的标题进行对比，完全符合则返回true,否则返回false>
	 * @param list
	 *            :解析Excel后得到的字符串数组list
	 * @param excelHead
	 *            :标准的导入模板的Excel表头
	 * @return 完全符合则返回true,否则返回false
	 * 
	 */
	public static boolean isRightInputExcel(List<String[]> list,
			String[] excelHead) {
		boolean b = false;
		int flag = 0;
		if (null != list && excelHead != null) {
			String[] inhead = list.get(0);
			// 先判断2个数组的长度是否相等
			if (inhead.length == excelHead.length) {
				for (int i = 0; i < excelHead.length; i++) {
					if (inhead[i].trim().equals(excelHead[i].trim())) {
						flag += 1;
					}
				}
			}
		}
		if (excelHead != null && flag == excelHead.length) {
			b = true;
		}
		return b;
	}

	/**
	 * 测试读取Excel2007
	 */
	public void testReadExcel2007() {
		// String excelpath = "C://Test//1.xlsx";
		String excelpath = "C://Test//dhgjnew.xlsx";
		ExcelUtil ceu = new ExcelUtil();
		List<String[]> results = ceu.readExcel2007(new File(excelpath));
		for (int i = 0; i < results.size(); i++) {
			String[] str = results.get(i);
			for (int j = 0; j < str.length; j++) {
				System.out.print("  第" + (i + 1) + "行,第" + (j + 1) + "列-"
						+ str[j] + " ");
			}
			System.out.println("");
		}
	}
}
