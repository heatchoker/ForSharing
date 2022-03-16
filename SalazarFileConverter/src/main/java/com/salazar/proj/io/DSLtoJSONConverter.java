package com.salazar.proj.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DSLtoJSONConverter {

	private String[] headers(String path) throws IOException {

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			return br.readLine().split(",");
		}
	}

	public List<String> convert(String path) {

		List<String> result = null;

		try (Stream<String> stream = Files.lines(Paths.get(path))) {
			String[] headers = headers(path);
			result = stream.skip(1).map(line -> line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1)).map(data -> {
				Map<String, String> map = new HashMap<>();

				for (int i = 0; i < headers.length; i++) {

					map.put(headers[i], formatIfDate(data[i]));

				}

				return mapToString(map);
			}).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;

	}

	public void write(String path, List<String> contents) {

		try {
			Files.write(Paths.get(path), contents);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String formatIfDate(String date) {
		if (date.length() != 10)
			return date;
		String pattern1 = "\\d{2}-\\d{2}-\\d{4}$";// 02-02-2002
		String pattern2 = "\\d{2}/\\d{2}/\\d{4}$";
		String pattern3 = "\\d{4}/\\d{2}/\\d{2}$";
		StringBuilder dateBldr = new StringBuilder("");
		if (date.matches(pattern1) || date.matches(pattern2)) {
			dateBldr.append(date.substring(6) + "-" + date.substring(0, 2) + "-" + date.substring(3, 5));
			// dateBldr.append(date.substring(0,3)+"-"+date.substring(5,7)+"-"+date.substring(8));
		} else if (date.matches(pattern3)) {
			dateBldr.append(date.substring(0, 4) + "-" + date.substring(5, 7) + "-" + date.substring(8));
		}else {
			return date;
		}
		return dateBldr.toString();
	}

	public String mapToString(Map<String, String> map) {
		StringBuilder mStr = new StringBuilder("{");
		for (String key : map.keySet()) {
			String value = map.get(key).replaceAll("\"", "");
			if(value.matches("[0-9]+"))
				mStr.append("\"" + key + "\"" + ":"  + value + ", ");
			else
				mStr.append("\"" + key + "\"" + ":" + "\"" + value + "\"" + ", ");
		}
		mStr.delete(mStr.length() - 2, mStr.length()).append("}");
		return mStr.toString();
	}

	

}
