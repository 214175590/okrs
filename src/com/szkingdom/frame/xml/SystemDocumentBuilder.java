package com.szkingdom.frame.xml;

import java.io.IOException;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 
 * <pre>
 * 系统Document对象封装
 * </pre>
 * 
 * @author yisin
 * @date 2012-12-11 下午02:15:29
 * @see com.szkingdom.frame.xml.SystemDocumentBuilder
 * 
 */
public class SystemDocumentBuilder {

	/**
	 * 从一个reader对象中获取Document对象
	 */
	public Document createDocument(Reader reader)
			throws ParserConfigurationException, FactoryConfigurationError,
			SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// 不验证XML文件
		factory.setValidating(false);

		// 不感知命名空间
		factory.setNamespaceAware(false);

		// 忽略注释说明
		factory.setIgnoringComments(true);
		// 忽略元素内容中的空格
		factory.setIgnoringElementContentWhitespace(true);

		// 将CDATA节点转换成text节点
		factory.setCoalescing(true);

		// 解析器将扩展实体引用节点
		factory.setExpandEntityReferences(true);

		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new InputSource(reader));
	}
}
