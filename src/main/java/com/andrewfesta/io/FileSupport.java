/**
 * 
 */
package com.andrewfesta.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;


/**
 * For safe, easy I/O of Files
 * 
 * @author Andy
 */
public class FileSupport extends StreamSupport {
	
	public void write(File file, OutputStreamCallback callback) {
		OutputStream out = openOutputStream(file);
		try {
			callback.doInOutputStream(out);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeOutputStream(out);
		}
	}
	
	public void read(File file, InputStreamCallback callback) {
		InputStream in = openInputStream(file);
		try {
			callback.doInInputStream(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			closeInputStream(in);
		}
	}
	
	public void read(File file, ReaderCallback callback) {
		Reader reader = openReader(file);
		try {
			callback.doInReader(reader);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			closeReader(reader);
		}
	}
	
	public void read(File file, String charset, ReaderCallback callback) {
		Reader reader = openReader(file, charset);
		try {
			callback.doInReader(reader);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			closeReader(reader);
		}
	}
	
	public byte[] readBytes(File file) {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		read(file, new CopyInputStreamCallback(out));
		return out.toByteArray();
	}
	
	public char[] readChars(File file) {
		final CharArrayWriter writer = new CharArrayWriter();
		read(file, new CopyReaderCallback(writer));
		return writer.toCharArray();
	}
	
	public String readString(File file) {
		return new String(readBytes(file));
	}
	
	public void write(File file, final InputStream in) {
		write(file, new OutputStreamCallback() {

			public void doInOutputStream(OutputStream out) throws IOException {
				copy(in, out);
			}});
	}
	
	public void writeBytes(File file, byte[] bytes) {
		final ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		write(file,in);
	}
	
	public void writeString(File file, String string) {
		writeBytes(file, string.getBytes());
	}
	
	protected OutputStream openOutputStream(File file) {
		try {
			return new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected InputStream openInputStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected Reader openReader(File file) {
		try {
			return new FileReader(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected Reader openReader(File file, String charset) {
		return new InputStreamReader(openInputStream(file));
	}

}
