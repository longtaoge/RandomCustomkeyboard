package org.xiangbalao;

import java.lang.reflect.Method;

import org.xiangbalao.keyboards.KeyboardUtil;
import org.xiangbalao.keyboards.PasTransformationMethod;

import cn.key.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;

public class KeydemoActivity extends Activity {
	private Context mContext;
	private Activity mActivity;
	private EditText edit,edit2;

	@SuppressLint("ClickableViewAccessibility") @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = this;
		mActivity = this;
		edit = (EditText) this.findViewById(R.id.edit);
		edit2=(EditText) findViewById(R.id.edit2);
	//	getWindow().setSoftInputMode(
			//	WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		
		
		Method setShowSoftInputOnFocus = null;
		Method setShowSoftInputOnFocus2 = null;
		try {
			setShowSoftInputOnFocus = edit.getClass().getMethod(
					"setShowSoftInputOnFocus", boolean.class);
			setShowSoftInputOnFocus.setAccessible(true);
			setShowSoftInputOnFocus.invoke(edit, false);
			
			setShowSoftInputOnFocus2 = edit2.getClass().getMethod(
					"setShowSoftInputOnFocus", boolean.class);
			setShowSoftInputOnFocus2.setAccessible(true);
			setShowSoftInputOnFocus2.invoke(edit2, false);
			
			
			
		} catch (SecurityException e) {
			
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

		edit.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				new KeyboardUtil(mActivity, mContext, edit).showKeyboard();
				return false;
			}
		});
		edit.setTransformationMethod(new PasTransformationMethod());
		edit2.setTransformationMethod(new PasTransformationMethod());
		edit2.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				new KeyboardUtil(mActivity, mContext, edit2).showKeyboard();
				return false;
			}
		});

	}
}