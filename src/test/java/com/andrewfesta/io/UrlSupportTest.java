package com.andrewfesta.io;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;


public class UrlSupportTest {
	
	UrlSupport io = new UrlSupport();
	
	@Test
	public void testRead() throws MalformedURLException, URISyntaxException {
		URL url = new URL("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		
		io.read(url, new URLConnectionCallback() {

			public void doInURLConnection(URLConnection conn)
					throws IOException {
				assertEquals("image/png", conn.getContentType());
				
			}});
	}
	
	@Test
	public void testGetFilename() throws MalformedURLException {
		URL url1 = new URL("http://farm7.static.flickr.com/6007/5919963492_ea102a55d7_z.jpg");
		URL url2 = new URL("http://www.google.com/intl/en_com/images/srpr/logo3w.png");
		
		assertEquals("5919963492_ea102a55d7_z.jpg",io.getFilename(url1));
		assertEquals("logo3w.png",io.getFilename(url2));
	}
	
	@Test
	public void testGetParameters() throws MalformedURLException {
		URL url = new URL("http://www.kickball365.com/forum/viewtopic.php?f=711&t=14541&p=389227#p389227");
		
		Map<String, String> params = io.getQueryParameters(url);
		assertEquals("711", params.get("f"));
		assertEquals("14541", params.get("t"));
		assertEquals("389227", params.get("p"));
	}
	
	@Ignore
	@Test
	public void testOpenReaderForum() throws MalformedURLException {
		URL url = new URL("http://www.kickball365.com/forum/feed.php?f=772");
		
		io.openReader(url, null);
	}
	
	@Ignore
	@Test
	public void testOpenReaderBlog() throws MalformedURLException {
		URL url = new URL("http://www.kickball365.com/feed");
		
		io.openReader(url, null);
	}

}
