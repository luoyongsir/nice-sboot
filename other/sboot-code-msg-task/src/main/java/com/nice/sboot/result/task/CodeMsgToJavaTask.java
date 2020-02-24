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
	private String author = "code-msg-task";
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

	public void setAuthor(String author) {
		if (author != null) {
			this.author = author;
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
		String pn = this.outputDirString;
		pn = pn.substring(pn.indexOf("java") + 5);
		pn = pn.replace(File.separatorChar, '.');

		StringBuilder bud = new StringBuilder(10240);
		budHeader(bud, pn);

		// 构建排序好的map，保证每次生成的代码一致
		Map<String, String> map = buildTreeMap();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			int code = Integer.parseInt(key.substring(key.indexOf("_") + 1));

			bud.append(",").append(System.lineSeparator());
			bud.append("	").append(key).append("(").append(code).append(", ").append("\"").append(val).append("\")");
		}
		bud.append(";").append(System.lineSeparator()).append(System.lineSeparator());

		budFooter(bud);

		File outFile = new File(this.outputDirString + File.separator + "CodeMsgEnum.java");
		try (BufferedWriter writer = Files.newBufferedWriter(outFile.toPath(), StandardCharsets.UTF_8)) {
			writer.append(bud);
		} catch (IOException e) {
			throw new BuildException("输出文件出错" + outFile.toPath(), e);
		}
	}

	private void budHeader(StringBuilder bud, String pn) {// @formatter:off
		bud.append("package ").append(pn).append(";").append(System.lineSeparator());
		bud.append("// @formatter:off").append(System.lineSeparator());
		bud.append("import java.util.HashMap;").append(System.lineSeparator());
		bud.append("import java.util.Map;").append(System.lineSeparator()).append(System.lineSeparator());
		bud.append("/**").append(System.lineSeparator());
		bud.append(" * 该类在 maven pre-clean/validate 阶段根据配置文件自动生成，禁止手动修改").append(System.lineSeparator());
		bud.append(" * 如果有修改配置文件请执行 maven clean").append(System.lineSeparator());
		bud.append(" * @author ").append(this.author).append(System.lineSeparator());
		bud.append(" */").append(System.lineSeparator());
		bud.append("public enum CodeMsgEnum {").append(System.lineSeparator());
		bud.append("	// 枚举").append(System.lineSeparator());
		bud.append("	FAIL(0, \"失败\"),").append(System.lineSeparator());
		bud.append("	SUCCESS(1, \"成功\")");
	}

	private void budFooter(StringBuilder bud) {
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
		bud.append("	private static Map<Integer, String> msgMap;").append(System.lineSeparator());
		bud.append("	static {").append(System.lineSeparator());
		bud.append("		msgMap = new HashMap<>((int) (CodeMsgEnum.values().length / 0.75) + 1);").append(System.lineSeparator());
		bud.append("		for (CodeMsgEnum codeMsgEnum : CodeMsgEnum.values()) {").append(System.lineSeparator());
		bud.append("			msgMap.put(codeMsgEnum.getCode(), codeMsgEnum.getMsg());").append(System.lineSeparator());
		bud.append("		}").append(System.lineSeparator());
		bud.append("	}").append(System.lineSeparator()).append(System.lineSeparator());
		bud.append("	/**").append(System.lineSeparator());
		bud.append("	 * 根据 code 获取 msg").append(System.lineSeparator());
		bud.append("	 */").append(System.lineSeparator());
		bud.append("	public static String getMsg(int code) {").append(System.lineSeparator());
		bud.append("		return msgMap.get(code);").append(System.lineSeparator());
		bud.append("	}").append(System.lineSeparator());
		bud.append("}// @formatter:on").append(System.lineSeparator());
	}// @formatter:on

	private Map<String, String> buildTreeMap() {
		Comparator comparator = (Comparator<String>) (k1, k2) -> {
			Integer code1 = Integer.parseInt(k1.substring(k1.indexOf("_") + 1));
			Integer code2 = Integer.parseInt(k2.substring(k2.indexOf("_") + 1));
			if (!Objects.equals(k1, k2) && Objects.equals(code1, code2)) {
				throw new RuntimeException("code重复，请修改：" + k1 + " 或 " + k2);
			}
			return code1.compareTo(code2);
		};
		Map<String, String> map = new TreeMap<>(comparator);
		for (Map.Entry<Object, Object> entry : getProperties().entrySet()) {
			String key = (String) entry.getKey();
			if (!key.contains("_")) {
				key = "CODE_" + key;
			}
			map.put(key.toUpperCase(), (String) entry.getValue());
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
