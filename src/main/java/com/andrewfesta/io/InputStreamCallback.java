package com.andrewfesta.io;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamCallback {
	
	public void doInInputStream(InputStream in) throws IOException;

}
