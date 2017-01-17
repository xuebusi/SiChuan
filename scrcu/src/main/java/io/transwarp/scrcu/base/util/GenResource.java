package io.transwarp.scrcu.base.util;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class GenResource {

	public static void main(String[] args) throws Exception {
		// id,title,icon,pid,res_id
		List<String> list = FileUtils.readLines(new File("C:\\Users\\Administrator\\Desktop\\resource.txt"));
		// insert into `sys_res`(`id`,`pid`,`cname`,`ak`,`seq`)
		// values
		for (int i = 0; i < list.size(); i++) {
			String resource = list.get(i);
			String[] array = resource.split(",");
			System.out.println("(" + 0 + ",'" + array[2] + "',''," + 0 + "," + array[0] + "),");

		}

	}

	public static void genResource() throws Exception {
		List<String> list = FileUtils.readLines(new File("C:\\Users\\Administrator\\Desktop\\resource.txt"));
		// insert into `sys_res`(`id`,`pid`,`cname`,`ak`,`seq`)
		// values
		for (int i = 0; i < list.size(); i++) {
			String resource = list.get(i);
			int seq = 0;
			String[] array = resource.split(",");
			System.out.println("(" + array[0] + "," + array[1] + ",'" + array[2] + "','" + array[3] + "'," + seq + "),");

		}
	}

}
