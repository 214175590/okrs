/*
 * 文件名：com.szkingdom.frame.xml.XMLParse.java
 * 简述：对XML进行解析操作
 * 详述：工具类，提供对XML解析和获取XML内节点的TEXT
 * 修改内容：[新增]
 * 修改时间：2011-11-10
 * 修改人：yisin
 * 版本：1.0
 */
package com.szkingdom.frame.xml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xerces.xni.parser.XMLParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.szkingdom.frame.init.SystemInitConfigBean;
import com.szkingdom.frame.util.StringUtil;

/**
 * <pre>
 * 简述:该类是对XML操作的工具类
 * 详述:主要提供了以下几个特性(可以继续扩展): 
 * 	<li>根据XML路径获得Document对象</li>
 * 	<li>输入节点的name、id等获得对应的TEXT</li>
 * </pre>
 * 
 * @author yisin
 * @since 1.0
 */
public class CommonXMLParser {
	/**
	 * 获得HashMap对象的引用
	 */
	private List<SystemInitConfigBean> listBean = new ArrayList<SystemInitConfigBean>();

	private SystemDocumentBuilder sdb = new SystemDocumentBuilder();
	/**
	 * 获得Document对象的引用
	 */
	private Document doc;

	/**
	 * <pre>
	 * 构造方法，传入XML路径，获得Document对象
	 * </pre>
	 * 
	 * @author yisin
	 * @param path
	 * @throws XMLParseException
	 * @since 1.0
	 */
	public CommonXMLParser(String path) {
		try {
			doc = sdb.createDocument(new FileReader(path));
			fillHashMap();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 将XML的节点Text放入HashMap中
	 * </pre>
	 * 
	 * @author yisin
	 * @throws XMLParseException
	 * @since 1.0
	 * 
	 */
	private void fillHashMap() throws XMLParseException {
		// 获得根节点
		Element rootElement = doc.getDocumentElement();
		procee(rootElement);
	}

	/**
	 * <pre>
	 * 封装的遍历节点的方法
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param rootElement
	 * @throws XMLParseException
	 */
	private void procee(Element rootElement) throws XMLParseException {
		// 获得根节点下的节点集合nl
		NodeList nl = rootElement.getChildNodes();
		// 遍历节点集合
		for (int i = 0; i < nl.getLength(); i++) {
			// 获得一个节点
			Node node = nl.item(i);
			// 将该节点转为元素
			if (node.getNodeType() == 1) {
				SystemInitConfigBean configBean = new SystemInitConfigBean();
				Element element = (Element) nl.item(i);
				if (element == null
						|| StringUtil.isEmpty(element.getAttribute("id"))) {
					throw new XMLParseException(null,
							"The required element is null.");
				}
				// 取得该元素的name属性
				String idAttr = element.getAttribute("id");
				configBean.setSystemInitAttr(idAttr);
				NodeList childList = node.getChildNodes();
				for (int j = 0; j < childList.getLength(); j++) {
					Node childNode = childList.item(j);
					if (childNode.getNodeType() == 1) {
						String tagName = childNode.getNodeName();
						if ("classname".equals(tagName)) {
							if (StringUtil.isEmpty(childNode.getTextContent())) {
								throw new XMLParseException(null,
										"The required element is null.");
							}
							configBean.setClassName(childNode.getTextContent()
									.replace('\n', ' ').trim());
						} else if ("filepath".equals(tagName)) {
							if (childNode != null
									&& childNode.getNodeType() == 1) {
								configBean.setFilePath(childNode
										.getTextContent().replace('\n', ' ')
										.trim());
							}
						} else if ("iscache".equals(tagName)) {
							if (childNode != null
									&& childNode.getNodeType() == 1) {
								configBean.setIsCache(childNode
										.getTextContent().replace('\n', ' ')
										.trim());
							} else {
								configBean.setIsCache("false");
							}
						}
					}
				}
				listBean.add(configBean);
			}

		}
	}

	/**
	 * <pre>
	 * 获得节点HashMap集合
	 * </pre>
	 * 
	 * @author dingwenxiang
	 * @since 1.0
	 * @return hm
	 */
	public List<SystemInitConfigBean> getListCollection() {
		return listBean;
	}

	public static void main(String[] args) {
		CommonXMLParser xml = new CommonXMLParser("F:/daiwei/pom.xml");
		List<SystemInitConfigBean> list = xml.getListCollection();
		for (SystemInitConfigBean systemInitConfigBean : list) {
			System.out.println(systemInitConfigBean.getClassName());
		}
	}
}
