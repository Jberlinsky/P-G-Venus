package com.venus.phone;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class TutorialActivity extends Activity implements OnClickListener{
	
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);
        
        findViewById(R.id.scheduleButton).setOnClickListener(this);
        findViewById(R.id.treatmentButton).setOnClickListener(this);
        findViewById(R.id.howtoButton).setOnClickListener(this);
        findViewById(R.id.settingButton).setOnClickListener(this);
        findViewById(R.id.buygelButton).setOnClickListener(this);
        
	}
	
	public void onClick(View v) {
		Intent intent = new Intent();
		//super.onClick(v);
		switch(v.getId()) {
		
		case R.id.scheduleButton:
			intent.setClass(this, DiaryActivity.class).putExtra("tabNumber","1");
			startActivity(intent);
			break;
		case R.id.treatmentButton:
			intent = new Intent(this, DiaryActivity.class).putExtra("tabNumber","2");
			startActivity(intent);
			break;
		case R.id.howtoButton:
			intent = new Intent(this, DiaryActivity.class).putExtra("tabNumber","3");
			startActivity(intent);
			break;
		case R.id.settingButton:
			intent = new Intent(this, DiaryActivity.class).putExtra("tabNumber","4");
			startActivity(intent);
			break;
		case R.id.buygelButton:
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gillettevenus.com/"));
			startActivity(browserIntent);
		}
	}
	
}