package com.lijian.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	/**
	 * 将 transfer    spring 上传 文件流       CommonsMultipartFile   转换成 File
	 * @param 
	 * @return
	 */
	public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile){
		File newFile = new File(cFile.getOriginalFilename());
		try{
			cFile.transferTo(newFile);
			
		}catch(IllegalStateException e){
			logger.error(e.toString());
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
			logger.error(e.toString());
		}
		return newFile;
		
	}
	/**
	 * 处理缩略图 返回图片相对路径
	 * @param targetAddr
	 *
	 * @return
	 */
	public  static String generateThumbnail(ImageHolder imageHolder,String targetAddr){
		String realFileName=getRandomFileName();
		String extension = getFileExtension(imageHolder.getImageName());
		makeDirPath(targetAddr);
//		logger.debug(thumbnail.read(new byte[1024])+"");
		//由于相当路径 抛出 Can't read input file! Can't read input file!
		//该用绝对路径 可以
		
		File waterFile = new File ("D:/watermark.jpg");
		
		String relativeAddr = targetAddr+realFileName+extension;
		logger.debug("current relativeAddr is :"+ relativeAddr);
		File dest = new File(PathUtil.getImgBasePath()+ relativeAddr);
		logger.debug("current complete addr is:"+PathUtil.getImgBasePath()+relativeAddr);
		logger.debug("basePath is:"+basePath);
		try{
			/*Thumbnails.of(thumbnail).size(1000,1000)
			.toFile(dest);*/
			Thumbnails.of(imageHolder.getImage()).size(200, 200)
					.watermark(Positions.BOTTOM_LEFT, ImageIO.read(waterFile), 0.25f)
					.outputQuality(0.8f).toFile(dest);
		
		}catch(IOException e){
			e.printStackTrace();
			logger.error(e.toString());
		}
		return relativeAddr;
	}

	private static void makeDirPath(String targetAddr) {
		String realFileParentPath=PathUtil.getImgBasePath()+targetAddr;
		
		File file = new File(realFileParentPath);
		if(!file.exists()){
			file.mkdirs();
		}

	}

	private static String getFileExtension(String fileName) {
	
		return fileName.substring(fileName.lastIndexOf("."));
		

	}

	public  static String getRandomFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Random r = new Random();
		int rannum = r.nextInt(89999) + 10000;
		String newTimeStr = dateFormat.format(new Date());

		return newTimeStr + rannum;
	}

	//删除图片物理地址
	public static void deleteFileOrPath(String storePath) {

		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if (fileOrPath.exists()) {
			if (fileOrPath.isDirectory()) {
				for (File i : fileOrPath.listFiles()) {
					i.delete();
				}
				fileOrPath.delete();
			}
			if (fileOrPath.isFile()) {
				fileOrPath.delete();
			}
		}
	}

	public static void main(String[] args) throws IOException {

		System.out.println("basePath-->" + basePath);
		Thumbnails.of(new File("src/test/resources/小黄人_1.jpeg")).size(1000, 1000)
				.watermark(Positions.BOTTOM_LEFT, ImageIO.read(new File("src/test/resources/watermark.jpg")), 0.25f)
				.outputQuality(0.8f).toFile("src/test/resources/xiaohangrennew.jpg");
	}

    public static String generateNormalImg(ImageHolder imageHolder, String targetAddr) {
		String realFileName=getRandomFileName();
		String extension = getFileExtension(imageHolder.getImageName());
		makeDirPath(targetAddr);
//		logger.debug(thumbnail.read(new byte[1024])+"");
		//由于相当路径 抛出 Can't read input file! Can't read input file!
		//该用绝对路径 可以

		File waterFile = new File ("D:/watermark.jpg");

		String relativeAddr = targetAddr+realFileName+extension;
		logger.debug("current relativeAddr is :"+ relativeAddr);
		File dest = new File(PathUtil.getImgBasePath()+ relativeAddr);
		logger.debug("current complete addr is:"+PathUtil.getImgBasePath()+relativeAddr);
		logger.debug("basePath is:"+basePath);
		try{
			/*Thumbnails.of(thumbnail).size(1000,1000)
			.toFile(dest);*/
			Thumbnails.of(imageHolder.getImage()).size(1000, 1000)
					.watermark(Positions.BOTTOM_LEFT, ImageIO.read(waterFile), 0.25f)
					.outputQuality(0.9f).toFile(dest);

		}catch(IOException e){
			e.printStackTrace();
			logger.error(e.toString());
		}
		return relativeAddr;
    }
}
