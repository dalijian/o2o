package com.lijian.o2o.util;

public class PathUtil {
	private static String seperator = System.getProperty("file.separator");;

	/**
	 * 拿到项目图片的根路径
	 * 
	 * @return
	 */
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/o2o/image/";

		} else {
			basePath = "/home/xiangze/image/";

		}

		basePath = basePath.replace("/", seperator);
		return basePath;
	}

	/**
	 * 根据项目需求 拿到项目图片的子路径
	 * 
	 * @param shopId
	 * @return
	 */
	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/images/item/shop/" + shopId + "/";
		return imagePath.replace("/", seperator);

	}

}
