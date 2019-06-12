package com.andrewfesta.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.junit.Test;


public class FileSupportTest {
	
	FileSupport io = new FileSupport();
	
	final static String testData1 = "Lorem ipsum dolor sit amet, consectetur adipiscing " +
		"elit. Maecenas id metus vitae dolor porta imperdiet ut ac " +
		"erat. Integer et lectus eget libero egestas gravida. Aliquam " +
		"molestie mattis egestas. In ut massa eget ante placerat " +
		"eleifend eget ac quam. Ut quis felis gravida mauris mattis " +
		"fringilla. Vivamus id nunc sapien. Nunc vitae turpis odio, " +
		"vitae convallis nibh. Aliquam accumsan nunc ac neque posuere " +
		"tincidunt. In hac habitasse platea dictumst. Nam accumsan " +
		"pretium arcu, vitae pharetra nibh feugiat in. Cras nisl enim, " +
		"auctor id tempor eget, cursus a est. Morbi aliquam ante et " +
		"sapien faucibus convallis. ";

	final static String testData2 = "Maecenas scelerisque malesuada varius. Class aptent" +
		" taciti sociosqu ad litora torquent per conubia nostra, per " +
		"inceptos himenaeos. Nullam et neque et ante laoreet tempor non" +
		" a ligula. Aenean dui nunc, venenatis vitae condimentum eget, " +
		"tempus a ipsum. Quisque iaculis, nibh et pharetra malesuada, " +
		"diam leo dignissim urna, quis eleifend dui dui ut est. Nam " +
		"erat nunc, consectetur eget pharetra in, dignissim dapibus " +
		"eros. Phasellus bibendum quam id elit pellentesque nec rutrum " +
		"nisl viverra. Phasellus lacinia luctus odio vitae vehicula. " +
		"Vestibulum sed mauris dolor, eget sodales ipsum. Nulla eget " +
		"justo lacus, et laoreet diam. Nulla dignissim ligula nec " +
		"sapien dictum ut aliquam libero luctus. Maecenas eu turpis " +
		"quis nisl porttitor cursus ut vitae ipsum. Aliquam tortor " +
		"nibh, bibendum scelerisque gravida eu, porttitor ut mauris. " +
		"Nam aliquam luctus enim in molestie. Praesent in rutrum velit. " +
		"Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
		"Nullam rutrum arcu tincidunt leo varius mattis. Vestibulum " +
		"ante ipsum primis in faucibus orci luctus et ultrices posuere " +
		"cubilia Curae; Suspendisse dictum ultricies tristique.";
	
	final static String lineSeparator =	"\n";
	
	final static String testData = testData1 + lineSeparator + lineSeparator + testData2;
	
	@Test
	public void testCopy() {
	
		ByteArrayInputStream in = new ByteArrayInputStream(testData.getBytes());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
			io.copy(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(testData, out.toString());
	}
	
	@Test
	public void testWriteRead() {
		File dir = new File("target/out/"+this.getClass().getName());
		dir.mkdirs();
		
		File file = new File(dir, "test.txt");
		
		io.writeString(file, testData);
		
		String results = io.readString(file);
		
		assertEquals(testData, results);
	}

}
