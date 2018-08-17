package com.lijian.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
	
	public static int getInt(HttpServletRequest request,String key){
		try{
			//Integer.decode    将 String 解码为 Integer。接受通过以下语法给出的十进制、十六进制和八进制数字
			return Integer.decode(request.getParameter(key));
			
		}catch(Exception e){
			return -1;
		}
	}
	public static long getLong(HttpServletRequest request,String key){
		try{
			// valueOf(String)返回保持指定 String 的值的 Long 对象
			return Long.valueOf(request.getParameter(key));
			
		}catch(Exception e){
			return -1;
		}
	}
	public static double getDouble(HttpServletRequest request,String key){
		try{
			
			return Double.valueOf(request.getParameter(key));
			
		}catch(Exception e){
			return -1;
		}
	}
	public static String getString(HttpServletRequest request,String key){
		try{
			String result = request.getParameter(key);
			if(result!=null){
				result = result.trim();
				return result;
			}
			if("".equals(result)){
				return null;
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}return null;
	}
	

}
