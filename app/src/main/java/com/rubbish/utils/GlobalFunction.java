package com.rubbish.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.rubbish.MyApp;
import com.rubbish.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlobalFunction {
	public static final String REGEX_IS_MOBILE = "(?is)(^1[3|4|5|7|8][0-9]\\d{4,8}$)";
	public static final String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
    public static void showToast(Context context, int strId) {
		if (context == null) {
			return;
		}
		String content = context.getText(strId).toString();
		showToast(context, content);
	}

	public static String getRubbishName(int type){
		String name = "未知垃圾";
		switch (type){
			case 0:
				name="可回收垃圾";
				break;
			case 1:
				name="有毒有害垃圾";
				break;
			case 2:
				name="厨余(湿)垃圾";
				break;
			case 3:
				name="干垃圾";
				break;
		}
		return name;
	}

	/**
	 * 将图片转换成Base64编码的字符串
	 */
	public static String imageToBase64(String path){
		if(TextUtils.isEmpty(path)){
			return null;
		}
		InputStream is = null;
		byte[] data = null;
		String result = null;
		try{
			is = new FileInputStream(path);
			//创建一个字符流大小的数组。
			data = new byte[is.available()];
			//写入数组
			is.read(data);
			//用默认的编码格式进行编码
			result = Base64.encodeToString(data,Base64.NO_CLOSE);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(null !=is){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return result;
	}

	/**
	 * 将Base64编码转换为图片
	 * @param base64Str
	 * @param path
	 * @return true
	 */
	public static boolean base64ToFile(String base64Str,String path) {
		byte[] data = Base64.decode(base64Str,Base64.NO_WRAP);
		for (int i = 0; i < data.length; i++) {
			if(data[i] < 0){
				//调整异常数据
				data[i] += 256;
			}
		}
		OutputStream os = null;
		try {
			os = new FileOutputStream(path);
			os.write(data);
			os.flush();
			os.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}catch (IOException e){
			e.printStackTrace();
			return false;
		}
	}

	public static void showToast(String content) {
		try {
			Context context = MyApp.getInstance();
			LayoutInflater inflater = LayoutInflater.from(context);
			View layout = inflater.inflate(R.layout.toast, null);
			TextView text = (TextView) layout.findViewById(R.id.toast_text);
			text.setText(content);
			layout.getBackground().setAlpha(125);// 0~255透明度值
			Toast toast = new Toast(context);
			toast.setGravity(Gravity.CENTER, 0, -70);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setView(layout);
			toast.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}

	public static void showToast(int strId) {
		try {
			Context context = MyApp.getInstance();
			LayoutInflater inflater = LayoutInflater.from(context);
			View layout = inflater.inflate(R.layout.toast, null);
			TextView text = (TextView) layout.findViewById(R.id.toast_text);
			String content = context.getResources().getString(strId);
			text.setText(content);
			layout.getBackground().setAlpha(125);// 0~255透明度值
			Toast toast = new Toast(context);
			toast.setGravity(Gravity.CENTER, 0, -70);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setView(layout);
			toast.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}

	//生成随机用户名，数字和字母组成,
	public static String getStringRandom(int length) {

		String val = "";
		Random random = new Random(System.currentTimeMillis());

		//参数length，表示生成几位随机数
		for(int i = 0; i < length; i++) {

			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			//输出字母还是数字
			if( "char".equalsIgnoreCase(charOrNum) ) {
				//输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char)(random.nextInt(26) + temp);
			} else if( "num".equalsIgnoreCase(charOrNum) ) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	public static String EMOTION_REG = "\\[[A-Za-z0-9]*\\]";
	public static Map<String,String> getMessages(String content){
		Map<String,String> messages = null;
		if(!TextUtils.isEmpty(content)){
			Pattern patten = Pattern.compile(EMOTION_REG, Pattern.CASE_INSENSITIVE);
			String[] msgs = content.split(EMOTION_REG);
			messages = new LinkedHashMap<String,String>();
			Matcher matcher = patten.matcher(content);
			int i = 1;
			try {
				while (matcher.find()) {
					String key = matcher.group();
					Log.d("Key", key);
					messages.put(key, msgs[i++]);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return messages;
	}

	public static boolean hasNumAndLetter(String value){
    	return value.matches(regex);
	}

	/**
	 * 检查是否存在SDCard
	 *
	 * @return
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 2 * 获取版本号 3 * @return 当前应用的版本号 4
	 */
	public static long getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName.replace(".","0");
			long versioncode = Long.parseLong(version.replace(".","0"));
			return versioncode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 获取某月实际天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getActualDaysOfMonth(int year,int month){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH, month - 1);//Java月份才0开始算
		return cal.getActualMaximum(Calendar.DATE);
	}
	/**
	 * 金钱格式化
	 * @param money
	 * @return
	 */
	public static String formatMoney(float money){
		DecimalFormat fnum  =   new DecimalFormat("##0.00");
		String sMoney = fnum.format(money);
		return sMoney;
	}


	// 删除文件
	public static void deleteFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param srcFileName
	 *            待复制的文件名
	 * @param overlay
	 *            如果目标文件存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, String destFileName,
                                   boolean overlay) {
		File srcFile = new File(srcFileName);

		// 判断源文件是否存在
		if (!srcFile.exists()) {
			return false;
		} else if (!srcFile.isFile()) {
			return false;
		}

		// 判断目标文件是否存在
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			// 如果目标文件存在并允许覆盖
			if (overlay) {
				// 删除已经存在的目标文件，无论目标文件是目录还是单个文件
				new File(destFileName).delete();
			}
		} else {
			// 如果目标文件所在目录不存在，则创建目录
			if (!destFile.getParentFile().exists()) {
				// 目标文件所在目录不存在
				if (!destFile.getParentFile().mkdirs()) {
					// 复制文件失败：创建目标文件所在目录失败
					return false;
				}
			}
		}

		// 复制文件
		int byteread = 0; // 读取的字节数
		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];

			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 复制整个目录的内容
	 * 
	 * @param srcDirName
	 *            待复制目录的目录名
	 * @param destDirName
	 *            目标目录名
	 * @param overlay
	 *            如果目标目录存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyDirectory(String srcDirName, String destDirName,
                                        boolean overlay) {
		// 判断源目录是否存在
		File srcDir = new File(srcDirName);
		if (!srcDir.exists()) {
			return false;
		} else if (!srcDir.isDirectory()) {
			return false;
		}

		// 如果目标目录名不是以文件分隔符结尾，则加上文件分隔符
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		File destDir = new File(destDirName);
		// 如果目标文件夹存在
		if (destDir.exists()) {
			// 如果允许覆盖则删除已存在的目标目录
			if (overlay) {
				new File(destDirName).delete();
			} else {
				return false;
			}
		} else {
			// 创建目的目录
			System.out.println("目的目录不存在，准备创建。。。");
			if (!destDir.mkdirs()) {
				System.out.println("复制目录失败：创建目的目录失败！");
				return false;
			}
		}

		boolean flag = true;
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 复制文件
			if (files[i].isFile()) {
				flag = copyFile(files[i].getAbsolutePath(), destDirName
						+ files[i].getName(), overlay);
				if (!flag)
					break;
			} else if (files[i].isDirectory()) {
				flag = copyDirectory(files[i].getAbsolutePath(), destDirName
						+ files[i].getName(), overlay);
				if (!flag)
					break;
			}
		}
		if (!flag) {
			return false;
		} else {
			return true;
		}
	}
	
	public static String getFolder(int messageType, String toId) {
		String strFolder = null;
		switch (messageType) {
		case GlobalVariables.MSG_IMAGE:
			strFolder = GlobalVariables.ROOT_PATH + toId + GlobalVariables.IMAGE_FLODER;
			break;
		case GlobalVariables.MSG_AUDIO:
			strFolder = GlobalVariables.ROOT_PATH + toId + GlobalVariables.AUDIO_FOLDER;
			break;
		case GlobalVariables.AVATAR_USER:
			strFolder = GlobalVariables.AVATAR_FOLDER;
			break;
		case GlobalVariables.APK:
			strFolder = GlobalVariables.APK_PATH;
			break;
		case GlobalVariables.TEMP:
			strFolder = GlobalVariables.TEMP_PATH;
			break;
		case GlobalVariables.HTML:
			strFolder = GlobalVariables.HTML_FOLDER;
			break;
		default:
			break;
		}
		File f = new File(strFolder);
		try {
			if (!f.exists()) {
				boolean s = f.mkdirs();
				System.out.println("fdb");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strFolder;
	}

	public static void showToast(Context context, String content) {
		try {
			LayoutInflater inflater = LayoutInflater.from(context);
			View layout = inflater.inflate(R.layout.toast, null);
			TextView text = (TextView) layout.findViewById(R.id.toast_text);
			text.setText(content);
			layout.getBackground().setAlpha(125);// 0~255透明度值
			Toast toast = new Toast(context);
			toast.setGravity(Gravity.CENTER, 0, -70);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setView(layout);
			toast.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	/**
	 * 验证手机号
	 * 
	 * @param mobileNumber
	 * @return
	 */
	public static boolean veriyMobile(String mobileNumber) {
		Pattern p = null;
		Matcher m = null;

		p = Pattern.compile(REGEX_IS_MOBILE);
		m = p.matcher(mobileNumber);

		return m.matches() && (mobileNumber.length() == 11);
	}
	
	/**
	 * 判断是否包含非法字符
	 * 
	 * @param content
	 * @return true 包含非法字符
	 */
	public static boolean isContainsIllegalCharacter(String content) {
		char[] charArray = content.toCharArray();
		for (char c : charArray) {
			if (c == '_' || (c >= 48 && c <= 57) || (c >= 'a' && c <= 'z')
					|| (c >= 'A' && c <= 'Z')) {
				continue;
			}
			return true;
		}

		return false;
	}

	public static boolean containsIllegalCharacter(String content) {
		return !content.matches("[a-zA-Z0-9_\u4e00-\u9fa5]*");
	}

	public static boolean isNetworkAvailable(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm == null)
			return false;
		else
		{   // 获取所有NetworkInfo对象
			NetworkInfo[] networkInfo = cm.getAllNetworkInfo();
			if (networkInfo != null && networkInfo.length > 0)
			{
				for (int i = 0; i < networkInfo.length; i++)
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
						return true;  // 存在可用的网络连接
			}
		}
		return false;
	}

	/**
	 * 获取视频截图
	 * @param filePath
	 * @return
	 */
	public static String getThumbnail(String filePath){
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			retriever.setDataSource(filePath);
			bitmap = retriever.getFrameAtTime();
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (RuntimeException e) {
			e.printStackTrace();
		}
		finally {
			try {
				retriever.release();
			}
			catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		if(bitmap != null){
			return bitmap2File(bitmap,System.currentTimeMillis()+"");
		}else{
			return null;
		}
	}


	/**
	 * Bitmap保存成File
	 *
	 * @param bitmap input bitmap
	 * @param name output file's name
	 * @return String output file's path
	 */
	public static String bitmap2File(Bitmap bitmap, String name) {
		File f = new File(Environment.getExternalStorageDirectory() + name + ".jpg");
		if (f.exists()) f.delete();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			return null;
		}
		return f.getAbsolutePath();
	}

	/**
	 * 日期格式化
	 * @return
	 */
	public static String formatDate(long date){
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

}
