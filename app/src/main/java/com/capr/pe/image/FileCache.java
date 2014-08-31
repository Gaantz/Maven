package com.capr.pe.image;

import java.io.File;

import android.content.Context;

public class FileCache {

	private File cacheDir;

	public FileCache(Context context) {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			File sdCard = android.os.Environment.getExternalStorageDirectory();

			cacheDir = new File(sdCard, "Dipoo" + File.separator + "cache");
		} else
			cacheDir = context.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}

	public File getFile(String url) {
		String filename = String.valueOf(url.hashCode());
		// Another possible solution (thanks to grantland)
		// String filename = URLEncoder.encode(url);
		File f = new File(cacheDir, filename);
		return f;

	}

	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}

}