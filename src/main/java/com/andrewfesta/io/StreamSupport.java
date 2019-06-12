/**
 * 
 */
package com.andrewfesta.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * For safe easy I/O of Streams
 * 
 * @author Andy
 *
 */
public class StreamSupport {
	
public static final int DEFAULT_BUFFER_SIZE = 1024;
	
	private int bufferSize = DEFAULT_BUFFER_SIZE;
	
	
	public StreamSupport(int bufferSize) {
		super();
		this.bufferSize = bufferSize;
	}

	public StreamSupport() {
		this(DEFAULT_BUFFER_SIZE);
	}
	
	public byte[] readBytes(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			copy(in, out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return out.toByteArray();
	}
	
	public char[] readChars(Reader reader) {
		final CharArrayWriter writer = new CharArrayWriter();
		try {
			copy(reader, writer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return writer.toCharArray();
	}
	
	public String readString(InputStream in) {
		return new String(readBytes(in));
	}
	
	protected void copy(InputStream in, OutputStream out) throws IOException {
		/*
		 * we copy this value so it doesn't change during the copy
		 */
		int bufferSize = getBufferSize(); 
		
		byte[] buffer = new byte[bufferSize];
		int bytesRead = 0;
		while (bytesRead != -1) {
			bytesRead = in.read(buffer, 0, bufferSize);
			if (bytesRead>0) {
				out.write(buffer, 0, bytesRead);
			}
		}
	}
	
	protected void copy(Reader in, Writer out) throws IOException {
		/*
		 * we copy this value so it doesn't change during the copy
		 */
		int bufferSize = getBufferSize(); 
		
		char[] buffer = new char[bufferSize];
		int bytesRead = 0;
		while (bytesRead != -1) {
			bytesRead = in.read(buffer, 0, bufferSize);
			if (bytesRead>0) {
				out.write(buffer, 0, bytesRead);
			}
		}
	}
	
	public byte[] serialize(Object obj) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			serialize(obj, out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return out.toByteArray();
	}
	
	protected void serialize(Object obj, OutputStream out) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(obj);
	}
	
	public Object deserialize(byte[] bytes) {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		try {
			return deserialize(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected Object deserialize(InputStream in) throws IOException {
		ObjectInputStream ois = new ObjectInputStream(in);
		try {
			return ois.readObject();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void closeOutputStream(OutputStream out) {
		try {
			out.close();
		} catch (IOException e) {}
	}
	
	protected void closeInputStream(InputStream in) {
		try {
			in.close();
		} catch (IOException e) {}
	}
	
	protected void closeReader(Reader reader) {
		try {
			reader.close();
		} catch (IOException e) {}
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	/**
	 * Default InputStreamCallback for a simple copy
	 * 
	 * @author Andy
	 *
	 */
	protected class CopyInputStreamCallback implements InputStreamCallback {

		OutputStream out;
		
		public CopyInputStreamCallback(OutputStream out) {
			super();
			this.out = out;
		}

		public void doInInputStream(InputStream in) throws IOException {
			copy(in, out);
		}
		
	}
	
	/**
	 * Default ReaderCallback for a simple copy
	 * 
	 * @author Andy
	 *
	 */
	protected class CopyReaderCallback implements ReaderCallback {

		Writer writer;
		
		public CopyReaderCallback(Writer writer) {
			super();
			this.writer = writer;
		}

		public void doInReader(Reader reader) throws IOException {
			copy(reader, writer);
		}
		
	}

}

	