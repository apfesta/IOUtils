package com.andrewfesta.io;

import java.io.IOException;
import java.io.Reader;

public interface ReaderCallback {
	
	public void doInReader(Reader reader) throws IOException;

}
