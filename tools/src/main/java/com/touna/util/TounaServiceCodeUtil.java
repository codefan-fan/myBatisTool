package com.touna.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class TounaServiceCodeUtil {

	public static void serviceCodeCreate(Map<String, Object> paramMap) {
		VelocityEngine ve = new VelocityEngine();

		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());

		ve.init();

		Template interfacevmt = ve.getTemplate((String) paramMap
				.get("interfaceTemplate"));

		Template interfaceImplvmt = ve.getTemplate((String) paramMap
				.get("interfaceImplTemplate"));

		VelocityContext ctx = new VelocityContext();

		ctx.put("interfacePackage", (String) paramMap.get("interfacePackage"));

		ctx.put("javaBeanFullName",
				(String) paramMap.get("domainObjectPackage") + "."
						+ (String) paramMap.get("domainObjectName"));
		ctx.put("javaBeanShortName", (String) paramMap.get("domainObjectName"));
		
		ctx.put("javaMapperFullName", (String) paramMap.get("javaMapperPackage")+"."+(String) paramMap.get("domainObjectName")+"Mapper");

		String domainObjectName = ((String) paramMap.get("domainObjectName"));

		String lowObjectName = domainObjectName.substring(0, 1).toLowerCase()
				+ domainObjectName.substring(1, domainObjectName.length());

		ctx.put("javaBeanShortNameLowCase", lowObjectName);

		String serviceOutputPath = getOutputPath(
				(String) paramMap.get("interfaceProject"),
				(String) paramMap.get("interfacePackage"),
				ctx.get("javaBeanShortName") + "Service");

		String serviceImplOutputPath = getOutputPath(
				(String) paramMap.get("interfaceProject"),
				(String) paramMap.get("interfacePackage") + ".Impl",
				ctx.get("javaBeanShortName") + "ServiceImpl");

		merge(interfacevmt, ctx, serviceOutputPath);

		merge(interfaceImplvmt, ctx, serviceImplOutputPath);

	}

	private static void merge(Template template, VelocityContext ctx,
			String path) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(path);
			template.merge(ctx, writer);
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

	private static String getOutputPath(String targetProject,
			String targetPackage, String domObjectName) {
		String packagePath = targetPackage.replace(".", "\\");
		return targetProject + File.separatorChar + packagePath
				+ File.separatorChar + domObjectName + ".java";
	}
}
