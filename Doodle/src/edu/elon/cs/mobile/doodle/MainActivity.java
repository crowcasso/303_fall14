package edu.elon.cs.mobile.doodle;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class MainActivity extends Activity {
	
	private Dialog currentDialog;
	private DoodleView doodleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        doodleView = (DoodleView) findViewById(R.id.doodleview);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    		getMenuInflater().inflate(R.menu.main, menu);
    		return true;
    }
    
    public boolean showWidthDialog(MenuItem menuItem) {
    	
    		currentDialog = new Dialog(this);
    		currentDialog.setContentView(R.layout.width_dialog);
    		currentDialog.setTitle(R.string.choose_width);
    		
    		SeekBar widthSeekBar = (SeekBar) currentDialog.findViewById(R.id.seekbarwidth);
    		widthSeekBar.setOnSeekBarChangeListener(widthSeekBarListener);
    		widthSeekBar.setProgress(25);
    		
    		Button widthButton = (Button) currentDialog.findViewById(R.id.buttonwidth);
    		widthButton.setOnClickListener(setWidthButtonListener);
    		
    		currentDialog.show();
    		return true;
    }
    
    private OnSeekBarChangeListener widthSeekBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			ImageView imageView = (ImageView) currentDialog.findViewById(R.id.imageviewwidth);
			
			Paint p = new Paint();
			p.setColor(Color.BLACK);
			
			Bitmap bitmap = Bitmap.createBitmap(250, 250, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			canvas.drawCircle(125, 125, seekBar.getProgress(), p);
			imageView.setImageBitmap(bitmap);
		
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
    	
    };
    
    private OnClickListener setWidthButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			SeekBar seekBar = (SeekBar) currentDialog.findViewById(R.id.seekbarwidth);
			
			// set the width
			doodleView.width = seekBar.getProgress();
			
			currentDialog.dismiss();
			currentDialog = null;
		}
    	
    };
    
    

}










