package org.zywx.wbpalmstar.plugin.uexpdf;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;
import org.zywx.wbpalmstar.engine.EBrowserView;
import org.zywx.wbpalmstar.engine.universalex.EUExBase;
import org.zywx.wbpalmstar.engine.universalex.EUExCallback;
import org.zywx.wbpalmstar.plugin.uexpdf.util.MLog;
import org.zywx.wbpalmstar.plugin.uexpdf.util.FileUtil;

import com.artifex.mupdfdemo.MuPDFActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;

/**
 * uexPDFReader
 * 
 * 基于MuPDF的PDF阅读器插件
 * 
 * @author waka
 * @version createTime:2016年4月20日 上午11:11:16
 */
public class EUExPDFReader extends EUExBase {

	// 回调
	public static final String CB_SET_OPTIONS = "uexPDFReader.cbSetOptions";// 设置配置项回调

	/**
	 * 构造方法
	 * 
	 * @param arg0
	 * @param arg1
	 */
	public EUExPDFReader(Context arg0, EBrowserView arg1) {
		super(arg0, arg1);

		MLog.getIns().i("start");
	}

	/**
	 * clean
	 */
	@Override
	protected boolean clean() {
		return false;
	}

	/**
	 * 打开PDFReader
	 * 
	 * @param params
	 */
	public void openPDFReader(String[] params) {

		MLog.getIns().i("start");

		if (params.length < 1) {
			MLog.getIns().i("params.length < 1");
			return;
		}

		if (params[0].isEmpty()) {
			MLog.getIns().i("params[0].isEmpty()");
			return;
		}

		String path = params[0];
		String absPath = FileUtil.getAbsPath(path, mBrwView);
		String fileName = FileUtil.makeFile(mContext, absPath);
		if (fileName == null) {
			MLog.getIns().i("fileName == null");
			return;
		}

		Uri uri = Uri.fromFile(new File(fileName));
		MLog.getIns().i("uri = " + uri.toString());

		Intent intent = new Intent(mContext, MuPDFActivity.class);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(uri);
		startActivityForResult(intent, Constant.REQUEST_CODE_PDF_PREVIEW_ACTIVITY);
	}

	/**
	 * 设置配置项
	 * 
	 * @param params
	 */
	public void setOptions(String[] params) {

		MLog.getIns().i("start");

		if (params.length < 1) {
			MLog.getIns().e("params.length < 1");
			return;
		}

		MLog.getIns().i("params[0] = " + params[0]);

		try {

			JSONObject jsonObject = new JSONObject(params[0]);
			int isShowTools = Integer.valueOf(jsonObject.optString("isShowTools", "1"));
			Float maxScale = Float.valueOf(jsonObject.optString("maxScale", "3"));

			if (isShowTools == 0) {
				StaticValues.isShowTools = false;
			} else {
				StaticValues.isShowTools = true;
			}
			MLog.getIns().i("StaticValues.isShowTools = " + StaticValues.isShowTools);

			StaticValues.maxScale = maxScale;
			MLog.getIns().i("StaticValues.maxScale = " + StaticValues.maxScale);

			jsCallback(CB_SET_OPTIONS, 0, EUExCallback.F_C_TEXT, Constant.SUCCESS);
			return;

		} catch (JSONException e) {
			e.printStackTrace();
			MLog.getIns().e(e);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			MLog.getIns().e(e);
		}
		jsCallback(CB_SET_OPTIONS, 0, EUExCallback.F_C_TEXT, Constant.FAIL);
	}

	/**
	 * 关闭
	 * 
	 * @param params
	 */
	public void close(String[] params) {

		MLog.getIns().i("start");

		// 发送关闭Activity本地广播
		LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
		Intent intent = new Intent(Constant.ACTION_LOCAL_BROADCAST_CLOSE_ACTIVITY);
		localBroadcastManager.sendBroadcast(intent);

		MLog.getIns().i("end");
	}

}
