package edu.elon.cs.mobile.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends ActionBarActivity {
	
	// for passing data between activities
	public static final String ANSWER = "theAnswer";
	public static final String ANSWER_SHOWN = "theAnswerWasShown";
	
	private Button mShowAnswerButton;
	private TextView mShowAnswerTextView;
	
	private boolean mAnswerIsTrue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		setAnswerShownResult(false);
		
		// default to not cheating
		mAnswerIsTrue = getIntent().getBooleanExtra(ANSWER, false);
		
		mShowAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
		
		mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
		mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mAnswerIsTrue) {
					mShowAnswerTextView.setText(R.string.true_button);
				} else {
					mShowAnswerTextView.setText(R.string.false_button);
				}
				setAnswerShownResult(true);  // well, they cheated, report it
			}
			
		});
	}
	
	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);  // set some data back
	}
}
