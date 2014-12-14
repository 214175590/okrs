package com.szkingdom.frame.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.xerces.xni.parser.XMLParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.szkingdom.frame.log.ILogger;
import com.szkingdom.frame.log.LogFactory;
import com.szkingdom.frame.util.StringUtil;

/**
 * <pre>
 * 简述:XML解析器
 * 详述:使用xpath进行xml解析，并以xpath路径存入Map中
 * </pre>
 * 
 * @author yanxuefeng
 * @since 1.0
 */
public class XPathConfigParser {
	private ILogger log = LogFactory.getDefaultLogger(XPathConfigParser.class);
	private Map<String, String> hashMap = new HashMap<String, String>();

	private SystemDocumentBuilder sdb = new SystemDocumentBuilder();
	private XPathFactory xpathFactory;

	private XPath xpath;

	public XPathConfigParser() {
		xpathFactory = XPathFactory.newInstance();
		xpath = xpathFactory.newXPath();
	}

	/**
	 * <pre>
	 * 从Reader中获取输入流并解析文件
	 * </pre>
	 * 
	 * @author yanxuefeng
	 * @since 1.0
	 * @param reader
	 *            Reader对象
	 * 
	 */
	public void parse(Reader reader) {
		try {
			Document doc = sdb.createDocument(reader);
			fillMap(doc,
					process(doc.getLastChild(), "/", new LinkedList<String>()));
		} catch (Exception e) {
			log.error("XPathConfigParser#parse Error!", e);
		}
	}

	/**
	 * <pre>
	 * 从InputStream中获取输入流并解析文件
	 * </pre>
	 * 
	 * @author yanxuefeng
	 * @since 1.0
	 * @param inputStream
	 *            inputStream对象
	 * 
	 */
	public void parse(InputStream inputStream) {
		try {
			Document doc = sdb
					.createDocument(new InputStreamReader(inputStream));
			fillMap(doc,
					process(doc.getLastChild(), "/", new LinkedList<String>()));
		} catch (Exception e) {
			log.error("XPathConfigParser#parse Error!", e);
		}
	}

	/**
	 * <pre>
	 * 将解析到的内容填充到map中去
	 * </pre>
	 * 
	 * @author yisin
	 * @since 1.0
	 * @param doc
	 *            Document对象
	 * @param string
	 *            节点名称
	 */
	public void fillMap(Document doc, String string) throws XMLParseException {
		fillMap(doc, new String[] { string });
	}

	public void fillMap(Document doc, String... string) {
		if (null == string) {
			throw new IllegalArgumentException("parameter is null.");
		}
		for (String key : string) {
			XPathExpression xpathexpress = null;
			String value = null;
			try {
				xpathexpress = xpath.compile(key);
				value = (String) xpathexpress.evaluate(doc,
						XPathConstants.STRING);
			} catch (XPathExpressionException e) {
				throw new XMLParseException(null,
						"Filling values to map failed.");
			}
			if (null != value) {
				hashMap.put(key, value.trim());
			}
		}
	}

	private String[] process(Node node, String path, List<String> list) {
		String tempPath = path;
		if (node instanceof Element) {
			// Element
			String elementName = node.getNodeName();
			tempPath = new StringBuffer(tempPath).append(elementName)
					.append("/").toString();
			NamedNodeMap attributes = node.getAttributes();
			int n = attributes.getLength();
			for (int i = 0; i < n; i++) {
				Node att = attributes.item(i);
				String attrName = att.getNodeName();
				String attrValue = att.getNodeValue();
				if (tempPath.endsWith("/")) {
					int lastIndex = tempPath.lastIndexOf('/');
					tempPath = tempPath.substring(0, lastIndex);
				}
				tempPath = new StringBuffer(tempPath).append("[@")
						.append(attrName).append("='").append(attrValue)
						.append("']/").toString();
			}

			// Children
			NodeList children = node.getChildNodes();
			int childlen = children.getLength();
			for (int i = 0; i < childlen; i++) {
				process(children.item(i), tempPath, list);
			}
		} else if (node instanceof Text) {
			String data = ((Text) node).getData();
			if (data != null && !"".equals(data.trim())) {
				if (tempPath.endsWith("/")) {
					tempPath = tempPath + "text()";
				} else {
					tempPath = tempPath + "/text()";
				}
				list.add(tempPath);
			}
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * <pre>
	 * 根据xpath获取值
	 * </pre>
	 * 
	 * @author yanxuefeng
	 * @since 1.0
	 * @param xpath
	 *            xpath路径
	 */
	public String getValue(String xpath) {
		String value = hashMap.get(xpath);
		if (StringUtil.isEmpty(xpath)) {
			value = xpath;
		}
		return value;
	}

	/**
	 * <pre>
	 * 返回map中是否包含该key值
	 * </pre>
	 * 
	 * @author yanxuefeng
	 * @since 1.0
	 * @param key
	 *            节点名称
	 */
	public boolean contains(String key) {
		return hashMap.containsKey(key);
	}

	public Map<String, String> getMapCollection() {
		return hashMap;
	}

	public static void main(String[] args) {
		XPathConfigParser xpath = new XPathConfigParser();
		try {
			InputStream inputStream = new FileInputStream(new File(
					"F:/daiwei/pom.xml"));
			xpath.parse(inputStream);
			System.out.println(xpath.getValue(""));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
