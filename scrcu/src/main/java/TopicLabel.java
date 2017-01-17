import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;

public class TopicLabel {

	public static void main(String[] args) throws Exception {
		gen2();
	}

	private static void gen1() throws Exception {
		List<String> lines = FileUtils.readLines(new File("C:\\Users\\Administrator\\Documents\\test\\test.txt"));
		Map<String, String> map = new HashMap<>();
		for (String str : lines) {
			String[] strArray = str.split("	");
			String key = strArray[3].toLowerCase();
			String value = strArray[0].toLowerCase();
			map.put(key, value);
		}
		System.out.println(JSON.toJSONString(map));
	}
	private static void gen2() throws Exception {
		List<String> lines = FileUtils.readLines(new File("C:\\Users\\Administrator\\Documents\\test\\test.txt"));
		Map<String, String> map = new HashMap<>();
		for (String str : lines) {
			String[] strArray = str.split("	");
			String key = strArray[3].toLowerCase();
			String value = strArray[1].toLowerCase();
			map.put(key, value);
		}
		System.out.println(JSON.toJSONString(map));
	}
}
