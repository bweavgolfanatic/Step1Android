package edu.wm.step1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateStep extends Activity {
	private static final int SELECT_PHOTO = 100;
	private ImageView stepimage;
	private String filePath;
	private Bitmap bitty;
	private RequestParams params;
	byte[] myByteArray;
	private static final int ACTION_TAKE_PHOTO_S = 2;
	private static final int ACTION_TAKE_PHOTO_B = 1;
	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
	private String mCurrentPhotoPath;
	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";
	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
	
	/* Photo album for this application */
	private String getAlbumName() {
		return getString(R.string.album_name);
	}
	
	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			
			storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (! storageDir.mkdirs()) {
					if (! storageDir.exists()){
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}
			
		} else {
			Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
		}
		
		return storageDir;
	}
	
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
		return imageF;
	}

	private File setUpPhotoFile() throws IOException {
		
		File f = createImageFile();
		mCurrentPhotoPath = f.getAbsolutePath();
		
		return f;
	}
	
	private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = stepimage.getWidth();
		int targetH = stepimage.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;
		
		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		
		/* Associate the Bitmap to the ImageView */
		stepimage.setImageBitmap(bitmap);
		stepimage.setVisibility(View.VISIBLE);
	}
	
	private void galleryAddPic() {
	    Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(mCurrentPhotoPath);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
	}
	
	private void dispatchTakePictureIntent(int actionCode) {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		switch(actionCode) {
		case ACTION_TAKE_PHOTO_B:
			File f = null;
			
			try {
				f = setUpPhotoFile();
				mCurrentPhotoPath = f.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			} catch (IOException e) {
				e.printStackTrace();
				f = null;
				mCurrentPhotoPath = null;
			}
			break;

		default:
			break;			
		} // switch

		startActivityForResult(takePictureIntent, actionCode);
	}
	
	private void handleSmallCameraPhoto(Intent intent) {
		Bundle extras = intent.getExtras();
		bitty = (Bitmap) extras.get("data");
		stepimage.setImageBitmap(bitty);
		stepimage.setVisibility(View.VISIBLE);
	}

	private void handleBigCameraPhoto() {

		if (mCurrentPhotoPath != null) {
			setPic();
			galleryAddPic();
			mCurrentPhotoPath = null;
		}

	}
	
	Button.OnClickListener mTakePicOnClickListener = 
			new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
			}
		};

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_step);
		stepimage = (ImageView) findViewById(R.id.imageView1);
		bitty = null;
		Button pictureButton = (Button) findViewById(R.id.browsepics);
		Button finishbutton = (Button) findViewById(R.id.finish);
		Button nextstep = (Button) findViewById(R.id.nextstep);
		Button rotate = (Button) findViewById(R.id.rotate);
		Button takepicture = (Button) findViewById(R.id.takepicture);
		takepicture.setOnClickListener(mTakePicOnClickListener);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {
			mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		}
		
		rotate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (bitty != null){
					Matrix mat = new Matrix();
					mat.postRotate(90);
					bitty = Bitmap.createBitmap(bitty, 0, 0,bitty.getWidth(),bitty.getHeight(), mat, true);
					stepimage = (ImageView) findViewById(R.id.imageView1);
					stepimage.setImageBitmap(bitty);
					
				}
				
			}
		});
		nextstep.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				params = new RequestParams();
				EditText steptextview = (EditText) findViewById(R.id.EditText02);
				String steptext = steptextview.getText().toString();
				params.put("text", steptext);
				Intent intent = getIntent();
				String postid = intent.getStringExtra("pid");
				params.put("post_id", postid);
				ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
				stepimage = (ImageView) findViewById(R.id.imageView1);
				bitty = ((BitmapDrawable)stepimage.getDrawable()).getBitmap();
				bitty.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object   
				byte[] b = baos.toByteArray(); 
				String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
				params.put("pic", encodedImage);
				SplashScreen.myClient.post(getApplicationContext(),"http://step1.herokuapp.com/steps.json",params, new JsonHttpResponseHandler() {
	        		@Override
	        		public void onSuccess(JSONObject responseMSG) {
	                    try {
							String message = responseMSG.getString("message");
							Log.v("msg",message);
							if(message.equals("step created successfully")){
								Log.v("msg", "created new step");
								EditText steptextview = (EditText) findViewById(R.id.EditText02);
								steptextview.setText("");
								ImageView image = (ImageView) findViewById(R.id.imageView1);
					            image.setImageBitmap(null);
					            bitty=null;
					            params=null;
					            
								
								
							}
							else{
								Toast.makeText(getBaseContext(), "Can't Create Post", Toast.LENGTH_SHORT).show(); 
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
					@Override
						public void onFailure(Throwable arg0,String response) {

							Log.d("TAG", "Failure");        
						} 
					});
				
			}
		});
		
		pictureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, SELECT_PHOTO); 
				
			}
		});
		finishbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RequestParams params = new RequestParams();
				Intent intent = getIntent();
				String postid = intent.getStringExtra("pid");
				params.put("post_id", postid);
				Log.v("post_id", postid);
				SplashScreen.myClient.get("http://step1.herokuapp.com/setfinished.json",params, new JsonHttpResponseHandler() {
	        		@Override
	        		public void onSuccess(JSONObject responseMSG) {
	                    try {
							String message = responseMSG.getString("message");
							Log.v("msg",message);
							if(message.equals("finished")){
								Intent i = new Intent(getApplicationContext(), Index.class);     
								startActivity(i);
								finish();
					            
								
								
							}
							else{
								Toast.makeText(getBaseContext(), "Can't Submit", Toast.LENGTH_SHORT).show(); 
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
					@Override
						public void onFailure(Throwable arg0,String response) {

							Log.d("TAG", "Failure");        
						} 
					});
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_step, menu);
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, 
		       Intent imageReturnedIntent) {
		    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

		    switch(requestCode) { 
		    case SELECT_PHOTO:
		        if(resultCode == RESULT_OK){  
		            Uri selectedImage = imageReturnedIntent.getData();
		            String[] filePathColumn = {MediaStore.Images.Media.DATA};

		            Cursor cursor = getContentResolver().query(
		                               selectedImage, filePathColumn, null, null, null);
		            cursor.moveToFirst();

		            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		            filePath = cursor.getString(columnIndex);
		            cursor.close();


		            
		            ImageView image = (ImageView) findViewById(R.id.imageView1);
		            bitty = decodeSampledBitmapFromResource(filePath, 100, 100);
		            image.setImageBitmap(bitty);
		            
		        }
		        
		    case ACTION_TAKE_PHOTO_B: {
				if (resultCode == RESULT_OK) {
					handleBigCameraPhoto();
				}
				break;
			} // ACTION_TAKE_PHOTO_B
		    }
		}
	// Some lifecycle callbacks so that the image can survive orientation change
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(BITMAP_STORAGE_KEY, bitty);
		outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (bitty != null) );
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		bitty = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
		stepimage.setImageBitmap(bitty);
		stepimage.setVisibility(
				savedInstanceState.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ? 
						ImageView.VISIBLE : ImageView.INVISIBLE
		);

	}
	
	public static Bitmap decodeSampledBitmapFromResource(String filePath2,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(filePath2, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(filePath2, options);
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
	}

}
