package com.summer.tools.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
@Component
@SuppressWarnings("unused")
public class ResourceUtil {

	@Resource
	private ResourceLoader resourceLoader;

	/**
	 * 读取classPath 目录下的资源文件
	 * @param uri 文件路径 格式: classpath:/dirName/../fileName.suffix
	 * @return 文件内容
	 */
	public String readString(String uri){
		org.springframework.core.io.Resource resource = resourceLoader.getResource(uri);
		StringBuilder sb = new StringBuilder(100);
		try (InputStream is = resource.getInputStream(); InputStreamReader isr = new InputStreamReader(is); BufferedReader br = new BufferedReader(isr)) {

			String data;
			while((data = br.readLine()) != null) {
				sb.append(data);
			}
		} catch (Exception ex) {
			log.error("File not found", ex);
		}
		return sb.toString();
	}
}
