/**
 * 
 */

package jp.iwatanlab.adk.qsteer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ToggleButton;

/**
 * @author naoki
 */
public class ADKQsteerActivity extends BaseActivity implements OnTouchListener {

    private class CtrlData {
        public byte normal = 0;
        public byte turbo = 0;

        public CtrlData(byte normal, byte turbo) {
            this.normal = normal;
            this.turbo = turbo;
        }
    }

    /** Logのタグ */
    private static final String TAG = ADKQsteerActivity.class.getSimpleName();

    private static final byte CTRL_STOP = 0;
    private static final byte CTRL_FORWARD = 1;
    private static final byte CTRL_BACK = 2;
    private static final byte CTRL_LEFT = 3;
    private static final byte CTRL_RIGHT = 4;
    private static final byte CTRL_TURBO_FORWARD = 5;
    private static final byte CTRL_FORWARD_LEFT = 6;
    private static final byte CTRL_FORWARD_RIGHT = 7;
    private static final byte CTRL_TURBO_FORWARD_LEFT = 8;
    private static final byte CTRL_TURBO_FORWARD_RIGHT = 9;
    private static final byte CTRL_LEFT_BACK = 10;
    private static final byte CTRL_RIGHT_BACK = 11;
    private static final byte CTRL_TURBO_BACK = 12;
    private static final byte CTRL_TURBO_LEFT_BACK = 13;
    private static final byte CTRL_TURBO_RIGHT_BACK = 14;

    private List<CtrlData> ctrlData = new ArrayList<CtrlData>();

    private ToggleButton tglTurbo;

    private byte mBand = 0;

    /**
     * チョロQにコマンドを送信する
     * 
     * @param id コマンド
     */
    private void sendCommand(int id) {
        CtrlData ctrl = ctrlData.get(id);
        byte command = ctrl.normal;
        if (tglTurbo.isChecked()) {
            command = ctrl.turbo;
        }
        byte[] data = {
                mBand, command
        };
        try {
            write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Viewにタッチイベントを登録する
     * 
     * @param id ViewのID
     */
    private void setTouchEvent(int id) {
        View v = findViewById(id);
        v.setOnTouchListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tglTurbo = (ToggleButton) findViewById(R.id.tgl_turbo);

        ctrlData.add(new CtrlData(CTRL_STOP, CTRL_STOP));
        ctrlData.add(new CtrlData(CTRL_FORWARD, CTRL_TURBO_FORWARD));
        ctrlData.add(new CtrlData(CTRL_FORWARD_RIGHT, CTRL_TURBO_FORWARD_RIGHT));
        ctrlData.add(new CtrlData(CTRL_RIGHT, CTRL_RIGHT));
        ctrlData.add(new CtrlData(CTRL_RIGHT_BACK, CTRL_TURBO_RIGHT_BACK));
        ctrlData.add(new CtrlData(CTRL_BACK, CTRL_TURBO_BACK));
        ctrlData.add(new CtrlData(CTRL_LEFT_BACK, CTRL_TURBO_LEFT_BACK));
        ctrlData.add(new CtrlData(CTRL_LEFT, CTRL_LEFT));
        ctrlData.add(new CtrlData(CTRL_FORWARD_LEFT, CTRL_TURBO_FORWARD_LEFT));

        setTouchEvent(R.id.btn_forward);
        setTouchEvent(R.id.btn_right_forward);
        setTouchEvent(R.id.btn_right);
        setTouchEvent(R.id.btn_right_back);
        setTouchEvent(R.id.btn_back);
        setTouchEvent(R.id.btn_left_back);
        setTouchEvent(R.id.btn_left);
        setTouchEvent(R.id.btn_left_forward);
        setTouchEvent(R.id.btn_stop);

    }

    /**
     * ボタンクリック時の動作
     * 
     * @param v
     */
    public void onClickCtrl(View v) {
        // int id = Integer.parseInt((String) v.getTag());
        // sendCommand(id);
    }

    /**
     * タッチ時の動作
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int id = Integer.parseInt((String) v.getTag());

        if (event.getAction() == MotionEvent.ACTION_UP) {
            sendCommand(0);
        } else {
            sendCommand(id);
        }

        return false;
    }
}
