package com.andrewfesta.io;

import java.io.IOException;
import java.net.URLConnection;

public interface URLConnectionCallback {
	
	public void doInURLConnection(URLConnection conn) throws IOException;

}
