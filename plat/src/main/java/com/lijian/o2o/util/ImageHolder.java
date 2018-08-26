package com.lijian.o2o.util;

import java.io.InputStream;

//图片刘的抽象
public class ImageHolder {
//图片 名
		private String imageName;
//		图片刘
		private InputStream image;

		public  ImageHolder(){

		}
		public ImageHolder(String imageName, InputStream image) {
			this.imageName = imageName;
			this.image = image;
		}

		public String getImageName() {
			return imageName;
		}

		public void setImageName(String imageName) {
			this.imageName = imageName;
		}

		public InputStream getImage() {
			return image;
		}

		public void setImage(InputStream image) {
			this.image = image;
		}

	

}
