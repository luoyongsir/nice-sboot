package com.nice.sboot.result.task;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

/**
 * 根据配置文件生成Java代码
 * @author luoyong
 * @date 2019/6/4 20:49
 */
public class CodeMsgToJavaTask extends Task {

	private String outputDirString = null;
	private List<FileSet> fileSets = new LinkedList<>();

	public void setOutputdir(File outputDir) {
		if (!outputDir.exists()) {
			outputDir.mkdirs();
		}
		this.outputDirString = outputDir.toString();
		if (this.outputDirString.indexOf(32) != -1) {
			this.outputDirString = ('"' + this.outputDirString + '"');
		}
	}

	public FileSet createFileset() {
		FileSet localFileSet = new FileSet();
		this.fileSets.add(localFileSet);
		return localFileSet;
	}

	@Override
	public void execute() throws BuildException {
		if (this.fileSets.isEmpty()) {
			throw new BuildException("No fileset specified");
		}
		Iterator<FileSet> fileSetIterator = this.fileSets.iterator();
		FileSet fileSet;
		DirectoryScanner directoryScanner;
		String[] strings;
		File file;
		while (fileSetIterator.hasNext()) {
			fileSet = fileSetIterator.next();
			directoryScanner = fileSet.getDirectoryScanner(getProject());
			directoryScanner.scan();
			strings = directoryScanner.getIncludedFiles();
			for (String str2 : strings) {
				file = new File(fileSet.getDir(getProject()), str2);
				if (file != null) {
					load(file);
				}
			}
		}

		if (!getProperties().isEmpty()) {
			createFile();
		}
	}

	private void createFile() {
		StringBuilder bud = new StringBuilder(10240);
		bud.append("package com.nice.sboot.result;");
		bud.append(System.lineSeparator()).append(System.lineSeparator());
		bud.append("/**").append(System.lineSeparator());
		bud.append(" * 该类在 maven clean 阶段自动生成").append(System.lineSeparator());
		bud.append(" * 如果有修改 code-msg.properties 请执行 maven clean").append(System.lineSeparator());
		bud.append(" * @author luoyong").append(System.lineSeparator());
		bud.append(" */").append(System.lineSeparator());
		bud.append("public enum CodeMsgEnum {").append(System.lineSeparator());
		bud.append("	// 枚举").append(System.lineSeparator());

		// 构建排序好的map，保证每次生成的代码一致
		Map<String, String> map = buildTreeMap();
		int i = 0;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			int code = Integer.parseInt(key.substring(key.indexOf("_") + 1));

			if ("SUCCESS_1".equals(key)) {
				key = "SUCCESS";
			} else if ("FAIL_0".equals(key)) {
				key = "FAIL";
			}

			if (i > 0) {
				bud.append(",").append(System.lineSeparator());
			} else {
				i++;
			}
			bud.append("	").append(key).append("(").append(code).append(", ").append("\"").append(val).append("\")");
		}
		bud.append(";").append(System.lineSeparator()).append(System.lineSeparator());

		bud.append("	private int code;").append(System.lineSeparator());
		bud.append("	private String msg;").append(System.lineSeparator()).append(System.lineSeparator());
		bud.append("	CodeMsgEnum(int code, String msg) {").append(System.lineSeparator());
		bud.append("		this.code = code;").append(System.lineSeparator());
		bud.append("		this.msg = msg;").append(System.lineSeparator());
		bud.append("	}").append(System.lineSeparator()).append(System.lineSeparator());
		bud.append("	public int getCode() {").append(System.lineSeparator());
		bud.append("		return code;").append(System.lineSeparator());
		bud.append("	}").append(System.lineSeparator()).append(System.lineSeparator());
		bud.append("	public String getMsg() {").append(System.lineSeparator());
		bud.append("		return msg;").append(System.lineSeparator());
		bud.append("	}").append(System.lineSeparator()).append(System.lineSeparator());
		bud.append("	/**").append(System.lineSeparator());
		bud.append("	 * 根据 code 获取消息").append(System.lineSeparator());
		bud.append("	 */").append(System.lineSeparator());
		bud.append("	public static String getMsg(int code) {").append(System.lineSeparator());
		bud.append("		for (CodeMsgEnum codeMsgEnum : CodeMsgEnum.values()) {").append(System.lineSeparator());
		bud.append("			if (codeMsgEnum.getCode() == code) {").append(System.lineSeparator());
		bud.append("				return codeMsgEnum.getMsg();").append(System.lineSeparator());
		bud.append("			}").append(System.lineSeparator());
		bud.append("		}").append(System.lineSeparator());
		bud.append("		return null;").append(System.lineSeparator());
		bud.append("	}").append(System.lineSeparator());

		bud.append("}").append(System.lineSeparator()).append(System.lineSeparator());
		File outFile = new File(this.outputDirString + File.separator + "CodeMsgEnum.java");
		try (BufferedWriter writer = Files.newBufferedWriter(outFile.toPath(), StandardCharsets.UTF_8)) {
			writer.append(bud);
		} catch (IOException e) {
			throw new BuildException("输出文件出错" + outFile.toPath(), e);
		}
	}

	private Map<String, String> buildTreeMap() {
		Comparator comparator = new Comparator<String>() {
			@Override
			public int compare(String k1, String k2) {
				Integer code1 = Integer.parseInt(k1.substring(k1.indexOf("_") + 1));
				Integer code2 = Integer.parseInt(k2.substring(k2.indexOf("_") + 1));
				return code1.compareTo(code2);
			}
		};
		Map<String, String> map = new TreeMap<>(comparator);
		for (Map.Entry<Object, Object> entry : getProperties().entrySet()) {
			map.put((String) entry.getKey(), (String) entry.getValue());
		}
		return map;
	}

	/**
	 * 读取配置文件
	 *
	 * @param file
	 */
	private static void load(File file) {
		try (InputStream is = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8)) {
			getProperties().load(isr);
		} catch (IOException e) {
			throw new RuntimeException("读取配置文件" + file.getName() + "出现异常", e);
		}
	}

	private static Properties properties = new Properties();

	private static Properties getProperties() {
		return properties;
	}
}
