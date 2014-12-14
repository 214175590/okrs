package com.szkingdom.frame.common.handler;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * <pre>
 * 自定义Blob Handler，处理Blob类型数据
 * </pre>
 * 
 * @author yisin
 * @date 2012 2012-11-29
 * @see com.szkingdom.frame.common.handler.BlobTypeHandler
 * 
 */
public class BlobTypeHandler implements TypeHandler<Object> {

	public Object getResult(ResultSet arg0, String arg1) throws SQLException {
		Blob b = arg0.getBlob(arg1);
		String s = "";
		if (b != null) {
			byte[] by = blobToBytes(b);
			s = new String(by);
		}
		return s;
	}

	public Object getResult(CallableStatement arg0, int arg1)
			throws SQLException {
		Blob b = arg0.getBlob(arg1);
		return b;
	}

	public void setParameter(PreparedStatement arg0, int arg1, Object arg2,
			JdbcType arg3) throws SQLException {
		Blob b = (Blob) arg2;
		arg0.setBlob(arg1, b);
	}

	/**
	 * 将blob转为byte[]
	 * 
	 * @param Blob
	 * @return byte[]
	 */
	private byte[] blobToBytes(Blob blob) {
		BufferedInputStream is = null;
		try {
			is = new BufferedInputStream(blob.getBinaryStream());
			byte[] bytes = new byte[(int) blob.length()];
			int len = bytes.length;
			int offset = 0;
			int read = 0;
			while (offset < len
					&& (read = is.read(bytes, offset, len - offset)) >= 0) {
				offset += read;
			}
			return bytes;
		} catch (Exception e) {
			return null;
		} finally {
			try {
				is.close();
				is = null;
			} catch (IOException e) {
				return null;
			}
		}
	}

	public Object getResult(ResultSet arg0, int arg1) throws SQLException {
		Blob b = arg0.getBlob(arg1);
		byte[] by = blobToBytes(b);
		String s = new String(by);
		return s;
	}

}
