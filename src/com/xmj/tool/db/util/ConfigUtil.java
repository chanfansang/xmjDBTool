package com.xmj.tool.db.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;



public class ConfigUtil {
	
	private Properties pro = null;
	private Object lock = new Object();
	private File conf;
	private long lastModify = 0L;


	/**
	 * 
	 * 为了测试用的 ConfigUtil.
	 * 
	 * @param path
	 */
	public ConfigUtil(String path) {
		conf = new File(path);
		load();
	}

	
	
	public void load() {
		//log.debug("加载文件路径:" + conf.getAbsolutePath());

		FileInputStream in = null;
		try {
			synchronized (lock) {
				pro = new Properties();
				in = new FileInputStream(conf);
				pro.load(in);
				lastModify = conf.lastModified();
			}
		} catch (Exception e) {
			//log.error(e.getMessage(), e);
			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e1) {
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					//log.error("配置文件输入关闭异常�?", e);
				}
			}
		}

	}

	public void set(String key, String value) {

		// 如果文件有修改，重新加载
		if (conf.lastModified() != lastModify) {
			load();
		}

		pro.setProperty(key, value);

		//if (log.isDebugEnabled()) {
		//	log.debug("配置项写入：配置项目前状态\n" + pro.toString());
			// System.out.println("配置项写入：配置项目前状态\n" + pro.toString());
		//}

		FileOutputStream out = null;

		try {
			synchronized (lock) {
				out = new FileOutputStream(conf);
				pro.store(out, "");
				lastModify = conf.lastModified();
			}
		} catch (FileNotFoundException e) {
			//log.error("写入配置文件失败,文件不存在异常！", e);
		} catch (IOException e) {
			//log.error("写入配置文件失败�?", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					//log.error("配置文件输出关闭异常�?", e);
				}
			}
		}
	}

	public String get(String key, String def) {
		String ret = def;

		// 如果文件有修改，重新加载
		if (conf.lastModified() != lastModify) {
			load();
		}

		synchronized (lock) {
			if (pro != null) {
				// 读取�?
				ret = pro.getProperty(key, def);
				//if (log.isDebugEnabled()) {
				//	log.debug("读取配置文件值key�?" + key + "，value�?" + ret);
				//}
				if (ret == null || ret.trim().length() == 0) {
					return def;
				}
			}
		}
		return ret;
	}

	public int get(String keyName, int def) {
		return Integer.parseInt(get(keyName, String.valueOf(def)));
	}

	public double get(String keyName, double def) {
		return Double.parseDouble(get(keyName, String.valueOf(def)));
	}

	public float get(String keyName, float def) {
		return Float.parseFloat(get(keyName, String.valueOf(def)));
	}

	public long get(String keyName, long def) {
		return Integer.parseInt(get(keyName, String.valueOf(def)));
	}

	public boolean get(String keyName, boolean def) {
		return Boolean.parseBoolean(get(keyName, String.valueOf(def)));
	}

	public static void main(String[] args) {
		ConfigUtil configUtil = new ConfigUtil("G:/workspace/1_Element2Zip/cfg/cfg.properties");
		System.out.println(configUtil.get("cfg.common", ""));
		
	}

	public static void main1(String[] a) throws InterruptedException {
		File file = new File("D://aa.txt");
		for (int i = 0; i < 3; i++) {
			System.out.println(file.lastModified());
			Thread.sleep(30000);
		}
	}
}

