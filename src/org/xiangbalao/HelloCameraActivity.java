package org.xiangbalao;

import java.io.File;

import org.xiangbalao.came.R;
import org.xiangbalao.utils.LogUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.imageUtil.SmartImageView;

public class HelloCameraActivity extends Activity {
	private static final String LOG_TAG = "HelloCamera";
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	private Button takePicBtn = null;

	private SmartImageView imageView = null;

	private Uri fileUri;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		LogUtil.i(LOG_TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

		takePicBtn = (Button) findViewById(R.id.button1);
		takePicBtn.setOnClickListener(takePiClickListener);

		imageView = (SmartImageView) findViewById(R.id.imageView1);

	//	imageView.setImageDrawable(getResources().getDrawable(R.drawable.abc_btn_check_to_on_mtrl_015));
		if (getOutputMediaFile(MEDIA_TYPE_IMAGE).exists()) {
			
			setImage();
			//imageView.setImageURI(fileUri);
		}

	}

	private final OnClickListener takePiClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			LogUtil.i(LOG_TAG, "Take Picture Button Click");
			// 利用系统自带的相机应用:拍照
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			// create a file to save the image
			fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

			// 此处这句intent的值设置关系到后面的onActivityResult中会进入那个分支，即关系到data是否为null，如果此处指定，则后来的data为null
			// set the image file name
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

			startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		}

	};

	public static final int MEDIA_TYPE_IMAGE = 1;

	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type) {

		File mediaStorageDir = null;
		try {

			mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					"MyCameraApp");

			

		} catch (Exception e) {
			e.printStackTrace();
		
		}

	

		
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + "/test" + ".jpg");
		}

		else {
			return null;
		}

		return mediaFile;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		// 如果是拍照
		if (CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE == requestCode) {
			
			if (RESULT_OK == resultCode) {
			
				// Check if the result includes a thumbnail Bitmap
				if (data != null) {
			

					if (data.hasExtra("data")) {
						Bitmap thumbnail = data.getParcelableExtra("data");
						imageView.setImageBitmap(thumbnail);
					}
				} else {

					// Resize the full image to fit in out image view.
					setImage();
				}
			}
		}

	}

	private void setImage() {
		int width = imageView.getWidth();
		
		if (width==0) {
			width=100;
		}
		int height = imageView.getHeight();
		if (height==0) {
			height=200;
		}

		BitmapFactory.Options factoryOptions = new BitmapFactory.Options();

		factoryOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(fileUri.getPath(), factoryOptions);

		int imageWidth = factoryOptions.outWidth;
		int imageHeight = factoryOptions.outHeight;

		// Determine how much to scale down the image
		int scaleFactor = Math.min(imageWidth / width, imageHeight
				/ height);

		// Decode the image file into a Bitmap sized to fill the
		// View
		factoryOptions.inJustDecodeBounds = false;
		factoryOptions.inSampleSize = scaleFactor;
		factoryOptions.inPurgeable = true;

		Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
				factoryOptions);

		imageView.setImageBitmap(bitmap);
	}

	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

	
	}

}