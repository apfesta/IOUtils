/**
 * 
 */
package com.andrewfesta.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Andy
 *
 */
public interface OutputStreamCallback {
	
	public void doInOutputStream(OutputStream out) throws IOException;

}
