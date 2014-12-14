package com.szkingdom.frame.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * @简述：<XML写入操作的公共类>
 * @详述：<详细介绍>
 * @author Administrator
 * @since 1.0
 * @see
 */
public class CommonXMLWriter {

	public void xmlWriter(String xmlPath, Map<String, String> map) {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		// 使用Document的 addElement() 方法创建根元素
		Set set = map.keySet();
		Iterator ito = set.iterator();
		while (ito.hasNext()) {
			Element e = doc.addElement(ito.next().toString());

			Element id = e.addElement("id");
			id.setText(map.get(ito.next().toString()));
		}

		// 生成一个FileWriter对象准备写出文件
		FileOutputStream out;
		try {

			out = new FileOutputStream(new File(xmlPath));
			// 生成输出格式化对象
			OutputFormat format = OutputFormat.createPrettyPrint();
			// 解决因编码问题造成的输出中文报异常的问题
			format.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(out);
			writer.write(doc);
			writer.close();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

	}

	public static void main(String[] args) {

	}

}
