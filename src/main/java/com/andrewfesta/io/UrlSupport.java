/**
 * 
 */
package com.andrewfesta.io;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * For safe, easy I/O of URLs
 * 
 * @author Andy
 *
 */
public class UrlSupport extends StreamSupport {
	
	public String getFilename(URL url) {
		String urlStr = url.getPath();
		int slashIndex = urlStr.lastIndexOf('/');
		return urlStr.substring(slashIndex + 1);
	}
	

	
	public void read(URL url, InputStreamCallback callback) {
		InputStream in = openInputStream(url);
		try {
			callback.doInInputStream(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			closeInputStream(in);
		}
	}
	
	public void read(URL url, ReaderCallback callback) {
		Reader reader = openReader(url);
		try {
			callback.doInReader(reader);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			closeReader(reader);
		}
	}
	
	public void readError(URL url, ReaderCallback callback) {
		Reader reader = openErrorReader(url);
		try {
			callback.doInReader(reader);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			closeReader(reader);
		}
	}
	
	public void read(URL url, String charset, ReaderCallback callback) {
		Reader reader = openReader(url, charset);
		try {
			callback.doInReader(reader);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			closeReader(reader);
		}
	}
	
	public void read(URL url, URLConnectionCallback callback) {
		URLConnection conn = openURLConnection(url);
		try {
			callback.doInURLConnection(conn);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
	}
	
	public String getContentType(URL url) {
		final StringBuffer buffer = new StringBuffer();
		
		read(url, new URLConnectionCallback() {

			public void doInURLConnection(URLConnection conn)
					throws IOException {
				buffer.append(conn.getContentType());
			}});
		
		return buffer.toString();
	}
	
	protected InputStream openInputStream(URL url) {
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private InputStream openInputStreamInternal(URL url) throws IOException {
		//This method is written to output error messages to the console.
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		if (conn.getResponseCode() == 200) {
			return conn.getInputStream();
		}
		System.err.println(readString(conn.getErrorStream()));
		return conn.getInputStream(); //Purposely throw exception here
	}
	
	private InputStream openErrorStreamInternal(URL url) throws IOException {
		//This method is written to output error messages to the console.
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		if (conn.getResponseCode() == 200) {
			return conn.getInputStream();
		}
		return conn.getErrorStream();
	}
	
	protected Reader openReader(URL url) {
		try {
			return new InputStreamReader(openInputStreamInternal(url));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected Reader openErrorReader(URL url) {
		try {
			return new InputStreamReader(openErrorStreamInternal(url));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected Reader openReader(URL url, String charset) {
		try {
			return new InputStreamReader(openInputStreamInternal(url), charset);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
		
	protected URLConnection openURLConnection(URL url) {
		try {
			return url.openConnection();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public byte[] readBytes(URL url) {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		read(url, new CopyInputStreamCallback(out));
		return out.toByteArray();
	}
	
	public char[] readChars(URL url) {
		final CharArrayWriter writer = new CharArrayWriter();
		read(url, new CopyReaderCallback(writer));
		return writer.toCharArray();
	}
	
	public String readString(URL url) {
		return new String(readBytes(url));
	}
	
	public Map<String, String> getQueryParameters(URL url) {
		Map<String, String> params = new HashMap<String, String>();
		
		for (String param: url.getQuery().split("&")) {
			String[] paramParts = param.split("=");
			if (paramParts.length==2) {
				params.put(paramParts[0], paramParts[1]);
			}
		}
		
		return params;
	}
	
}
