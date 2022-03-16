package com.salazar.proj.SalazarFileConverter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.salazar.proj.io.DSLtoJSONConverter;

public class DSLtoJSONConverterTest {
	

	 ClassLoader classLoader = getClass().getClassLoader();
	@Test
	public void testConvert() {
		String file = classLoader.getResource("com/salazar/proj/SalazarFileConverter/test.txt").getFile().substring(1);		
		DSLtoJSONConverter converter = new DSLtoJSONConverter();
		List<String> result = converter.convert(file);
		assertEquals("Size of the list should be 3", 3, result.size());	
	   
	}

	@Test
	public void testWrite() {
		String file = classLoader.getResource("com/salazar/proj/SalazarFileConverter/res.txt").getFile().substring(1);		
		DSLtoJSONConverter converter = new DSLtoJSONConverter();
		List<String> input = new ArrayList<String>();
		input.add("{\"firstName\":\"Wolfgang\", \"lastName\":\"Mozart\", \"gender\":\"Male\", \"middleName\":\"Amadeus\", \"dateOfBirth\":\"1756-01-27\", \"salary\":\"1000\"}");
		input.add("{\"firstName\":\"Albert\", \"lastName\":\"Einstein\", \"gender\":\"Male\", \"middleName\":\"\", \"dateOfBirth\":\"1955/04/18\", \"salary\":\"2000\"}");
		input.add("{\"firstName\":\"Marie, Salomea\", \"lastName\":\"Curie\", \"gender\":\"Female\", \"middleName\":\"Skłodowska |\", \"dateOfBirth\":\"04-07-1934\", \"salary\":\"3000\"}");
		converter.write(file,input);
		assertEquals( true,new File(file).exists());	
	}

	@Test
	public void testFormatIfDate() {
		DSLtoJSONConverter converter = new DSLtoJSONConverter();
		String output = converter.formatIfDate("2022-10-10");
		assertEquals("2022-10-10",output);	
		output = converter.formatIfDate("2022/10/10");
		assertEquals("2022-10-10",output);
		output = converter.formatIfDate("10-10-2022");
		assertEquals("2022-10-10",output);
		output = converter.formatIfDate("10/10/2022");
		assertEquals("2022-10-10",output);
	}

	@Test
	public void testMapToSring() {
		Map<String, String> input = new HashMap<String,String>();
		input.put("firstName","Wolfgang");
		input.put("lastName","Mozart");
		DSLtoJSONConverter converter = new DSLtoJSONConverter();
		String output = converter.mapToString(input);
		assertEquals("Output of mapToString should be \"{\\\"firstName\\\":\\\"Wolfgang\\\", \\\"lastName\\\":\\\"Mozart\\\"}\"", "{\"firstName\":\"Wolfgang\", \"lastName\":\"Mozart\"}", output);	
	}

}
