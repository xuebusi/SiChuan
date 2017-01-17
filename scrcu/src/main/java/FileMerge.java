import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileMerge {

	public static void main(String[] args) throws Exception {
		merge();
	}

	public static void merge() throws Exception {
		String target = "D:\\transwarp\\四川农信\\数据\\ebcpData\\ebcpData";
		for (int i = 1; i <= 4; i++) {
			String path = "D:\\transwarp\\四川农信\\数据\\ebcpData\\ebcpData00" + i;
			List<File> fileList = listAllFile(path);
			for (File file : fileList) {
				String name = file.getName();
				String p = file.getAbsolutePath().replace(path, "").replace(name, "");
				String targetFilePath = target + p + "\\" + name;
				File targetFile = new File(targetFilePath);
				if (targetFile.exists()) {
					int readcount;
					byte buffer[] = new byte[1024];
					FileOutputStream writer = new FileOutputStream(targetFilePath,true);
					FileInputStream reader = new FileInputStream(file);
					while ((readcount = reader.read(buffer)) != -1) {
						writer.write(buffer);
					}
					reader.close();
					writer.close();
				} else {
					FileUtils.copyFile(file, new File(targetFilePath));
				}

			}

		}
	}

	/**
	 * 列举文件夹下所有文件
	 * 
	 * @param path
	 *            路径
	 * @return 所有文件
	 */
	public static List<File> listAllFile(String path) {
		List<File> list = new ArrayList<File>();
		File file = new File(path);
		File[] childFiles = file.listFiles();
		if (childFiles == null) {
			return null;
		}
		for (File child : childFiles) {
			if (child.isDirectory()) {
				list.addAll(listAllFile(child.getPath()));
			} else {
				list.add(child);
			}
		}
		return list;
	}
}
