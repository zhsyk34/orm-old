package com.cat.study;

import org.junit.Test;

import sun.misc.Launcher;

import java.net.URL;

/**
 * Created by Archimedes on 2016/7/6.
 */
public class ClassLoaderTest {

	@Test
	public void test1() {
		URL[] urls = Launcher.getBootstrapClassPath().getURLs();
		for (int i = 0; i < urls.length; i++) {
			System.out.println(urls[i].toExternalForm());
		}
	}

	@Test
	public void test2() {
		System.out.println(System.getProperty("sun.boot.class.path"));
	}

	@Test
	public void test3() {
		ClassLoader loader = ClassLoaderTest.class.getClassLoader();    //获得加载ClassLoaderTest.class这个类的类加载器
		while (loader != null) {
			System.out.println(loader);
			loader = loader.getParent();    //获得父类加载器的引用
		}
		System.out.println(loader);
	}
}
