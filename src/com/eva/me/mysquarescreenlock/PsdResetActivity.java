package com.eva.me.mysquarescreenlock;

import com.eva.me.mysquarescreenlock.listener.OnFlingCompleteListener;
import com.eva.me.mysquarescreenlock.unlock.util.PasswordUtil;
import com.eva.me.mysquarescreenlock.view.FlingRelativeLayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PsdResetActivity extends Activity {

	private final String TAG = "PsdResetActivity";
	private Button btnConfirm, btnClear;
	private Context context;
	private FlingRelativeLayout flingRelativeLayout;
	private TextView tvTopInfo, tvPsdReveal;

	private void showToast(String str, Context context) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_psd_validate);
		init();
	}

	private void init() {
		//initialize
		context = PsdResetActivity.this;
		//init Views...
		btnClear = (Button) findViewById(R.id.btnClear);
		btnConfirm = (Button) findViewById( R.id.btnConfirm);
		flingRelativeLayout = (FlingRelativeLayout) findViewById(R.id.flingRelativeLayout);
		tvTopInfo = (TextView) findViewById(R.id.tvTopInfo);
		tvPsdReveal = (TextView) findViewById(R.id.tvPsdReveal);
		
		//init UI
		tvTopInfo.setText("请输入您的手势密码");
		tvPsdReveal.setText("");
		
		
		showToast("请输入您的手势密码~", context);
		
		//通过listener进行实时的系统界面更新
		flingRelativeLayout.setOnFlingCompleteListener(new OnFlingCompleteListener() {
			
			@Override
			public void onFlingComplete(int curDirection) {
				//每次手势结束时候触发
				if (!PasswordUtil.curPsd.equals("")) {
					tvPsdReveal.setText(""+PasswordUtil.curPsd);
				}
			}
		});
		
		btnClear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//clear current input gesture info
				PasswordUtil.curPsd = "";
				tvPsdReveal.setText("");
				showToast("输入手势清除成功", context);
			}
		});
		
		btnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//store current input PSD , REMEMBER to tell user which is your real Psd
				//首先要判断是否为空，如果为空，要判断一下才好
				if (PasswordUtil.curPsd.equals("")) {
					showToast("密码不能为空，请输入您的密码", context);
					return;
				}
				showToast("密码设置成功，请记住您的密码："+PasswordUtil.curPsd, context);
				PasswordUtil.setPsd(PasswordUtil.curPsd, context);
				PasswordUtil.curPsd= "";
				tvPsdReveal.setText("");
				finish();
			}
		});
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.psd_reset, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
