package com.nice.sboot.base.utils.io;

import com.nice.sboot.base.exception.RunException;
import com.nice.sboot.base.utils.text.Charsets;
import org.apache.commons.lang3.Validate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 关于文件的工具集.
 *
 * 主要是调用JDK自带的Files工具类。 固定encoding为UTF8.
 *
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class FileUtil {

	/**
	 * 简单写入String到File.
	 */
	public static void write(final CharSequence data, final File file) {
		Validate.notNull(file);
		Validate.notNull(data);

		try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), Charsets.UTF_8)) {
			writer.append(data);
		} catch (IOException e) {
			throw new RunException(e);
		}
	}

	/**
	 * 追加String到File.
	 */
	public static void append(final CharSequence data, final File file) {
		Validate.notNull(file);
		Validate.notNull(data);

		try (BufferedWriter writer = Files
				.newBufferedWriter(file.toPath(), Charsets.UTF_8, StandardOpenOption.APPEND)) {
			writer.append(data);
		} catch (IOException e) {
			throw new RunException(e);
		}
	}

	/**
	 * 创建文件或更新时间戳.
	 *
	 * @see {@link com.google.common.io.Files#touch}
	 */
	public static void touch(File file) {
		try {
			com.google.common.io.Files.touch(file);
		} catch (IOException e) {
			throw new RunException(e);
		}
	}

	/**
	 * 确保目录存在, 如不存在则创建
	 */
	public static void makeSureDirExists(String dirPath) {
		makeSureDirExists(getPath(dirPath));
	}

	/**
	 * 确保目录存在, 如不存在则创建
	 */
	public static void makeSureDirExists(File file) {
		Validate.notNull(file);
		makeSureDirExists(file.toPath());
	}

	/**
	 * 确保目录存在, 如不存在则创建.
	 *
	 * @see {@link Files#createDirectories}
	 *
	 */
	public static void makeSureDirExists(Path dirPath) {
		Validate.notNull(dirPath);
		try {
			Files.createDirectories(dirPath);
		} catch (IOException e) {
			throw new RunException(e);
		}
	}

	/**
	 * 确保父目录及其父目录直到根目录都已经创建.
	 *
	 */
	public static void makeSureParentDirExists(File file) {
		Validate.notNull(file);
		makeSureDirExists(file.getParentFile());
	}

	/**
	 * 判断文件是否存在, from Jodd.
	 *
	 * @see {@link Files#exists}
	 * @see {@link Files#isRegularFile}
	 */
	public static boolean isFileExists(String fileName) {
		if (fileName == null) {
			return false;
		}
		return isFileExists(getPath(fileName));
	}

	/**
	 * 判断文件是否存在, from Jodd.
	 *
	 * @see {@link Files#exists}
	 * @see {@link Files#isRegularFile}
	 */
	public static boolean isFileExists(File file) {
		if (file == null) {
			return false;
		}
		return isFileExists(file.toPath());
	}

	/**
	 * 判断文件是否存在, from Jodd.
	 *
	 * @see {@link Files#exists}
	 * @see {@link Files#isRegularFile}
	 */
	public static boolean isFileExists(Path path) {
		if (path == null) {
			return false;
		}
		return Files.exists(path) && Files.isRegularFile(path);
	}

	private static Path getPath(String filePath) {
		return Paths.get(filePath);
	}

	private FileUtil() {
	}
}
