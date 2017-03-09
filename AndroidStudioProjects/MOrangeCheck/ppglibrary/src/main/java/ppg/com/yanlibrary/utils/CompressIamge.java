package ppg.com.yanlibrary.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;

/**
 * 压缩图片的
 * @author jie.yang
 *
 */
public class CompressIamge {

	public static Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        newOpts.inJustDecodeBounds = true;//只读边,不读内容  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
  
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        float hh = 800f;//  
        float ww = 480f;//  
        int be = 1;
        if (w > h && w > ww) {  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {  
            be = (int) (newOpts.outHeight / hh);  
        } 
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//设置采样率  
          
        newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设  
        newOpts.inPurgeable = true;// 同时设置才会有效  
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收  
          
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩  
                                    //其实是无效的,大家尽管尝试  
        
        // 把图片压缩并且放入本地
		compressBmpToFile(bitmap, new File(srcPath));
        return bitmap;  
    } 
	
	public static Bitmap compressImageNoFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        newOpts.inJustDecodeBounds = true;//只读边,不读内容  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
  
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        float hh = 800f;//  
        float ww = 480f;//  
        int be = 1;
        if (w > h && w > ww) {  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {  
            be = (int) (newOpts.outHeight / hh);  
        } 
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//设置采样率  
          
        newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设  
        newOpts.inPurgeable = true;// 同时设置才会有效  
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收  
          
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩  
                                    //其实是无效的,大家尽管尝试
        return bitmap;  
    } 
	
	public static void compressBmpToFile(Bitmap bmp, File file){
        if(file.exists()) {
            if(file.isFile())
                file.delete();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        int options = 100;//个人喜欢从80开始,  
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);  
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();  
            options -= 10;  
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);  
        }  
        try {  
            FileOutputStream fos = new FileOutputStream(file);  
            fos.write(baos.toByteArray());  
            fos.flush();  
            fos.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
}
