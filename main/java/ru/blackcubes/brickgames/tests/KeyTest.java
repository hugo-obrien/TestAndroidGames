package ru.blackcubes.brickgames.tests;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class KeyTest extends Activity implements View.OnKeyListener {

    StringBuilder allPrinted = new StringBuilder();
    StringBuilder builder = new StringBuilder();
    TextView textView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        textView.setText("Press keys (if you have some)!");
        textView.setOnKeyListener(this);
        textView.setFocusableInTouchMode(true);
        textView.requestFocus();
        setContentView(textView);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        builder.setLength(0);
        switch (event.getAction()) {
            case KeyEvent.ACTION_DOWN: {
                builder.append("down, ");
                allPrinted.append((char) event.getUnicodeChar());
                break;
            }
            case KeyEvent.ACTION_UP: {
                builder.append("up, ");
                break;
            }
        }
        builder.append(event.getKeyCode()).append(", ").append((char) event.getUnicodeChar());
        String text = builder.toString();
        Log.d("KeyTest", text);
        textView.setText(allPrinted.toString() + "\n" + text);

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return false;
        } else {
            return true;
        }
    }
}
