package com.sfa.ghs.mq;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import org.apache.log4j.Logger;

public class FileUtil {
	public static final Logger logger = Logger.getLogger(FileUtil.class);

	public static final String FILEPATH = "D:/MQ/LogMsg";

	public static void writeFile(String fileName, String context)
			throws IOException {
		String ymd = getYmd(Calendar.getInstance());
		File file = newFile(FILEPATH + File.separator + ymd, fileName);

		FileWriter fw = new FileWriter(file, true);
		fw.write(context);
		fw.close();

		logger.info("File已保存，路径：" + file.getPath());
	}

	public static String getYmd(Calendar cal) {
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		return year + "-" + (month < 10 ? "0" + month : "" + month) + "-"
				+ (day < 10 ? "0" + day : "" + day);
	}

	public static File newFile(String path, String fileName) throws IOException {
		File file = new File(path, fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}
}
