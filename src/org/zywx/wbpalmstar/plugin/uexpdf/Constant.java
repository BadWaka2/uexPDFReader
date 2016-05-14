package org.zywx.wbpalmstar.plugin.uexpdf;

/**
 * @author waka
 * @version createTime:2016年4月20日 上午11:46:37
 */
public class Constant {

	// 回调给前端的标识
	public static final int SUCCESS = 1;
	public static final int FAIL = 0;

	// Activity之间跳转的RequestCode
	public static final int REQUEST_CODE_PDF_PREVIEW_ACTIVITY = 10001;// PDFPreviewActivity

	// 关闭Activity本地广播
	public static final String ACTION_LOCAL_BROADCAST_CLOSE_ACTIVITY = "org.zywx.wbpalmstar.plugin.uexpdf.LOCAL_BROADCAST_CLOSE_ACTIVITY";
}
