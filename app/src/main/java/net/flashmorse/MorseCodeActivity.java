package net.flashmorse;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import android.graphics.Color;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import android.support.design.widget.FloatingActionButton;

import java.util.ArrayList;

public class MorseCodeActivity extends Activity {

    private EditText etmorsetext;
    private TextView morsechar;
    private Button btntextclear;
    private FloatingActionButton fabtnflash;
    private ListView lvmorsetexts;

    private boolean isplay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flashmorse);

        btntextclear = (Button) findViewById(R.id.btn_textclear);
        etmorsetext = (EditText) findViewById(R.id.et_morsetext);
        fabtnflash = (FloatingActionButton) findViewById(R.id.fabtn_flash);
        lvmorsetexts = (ListView) findViewById(R.id.lv_morsetexts);

        fabtnflash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String morsetext = etmorsetext.getText().toString();
                items.clear();

                for(int i = 0, l = morsetext.length(); i < l; i++) {
                    int morsedrawid = getResources().getIdentifier("draw_morsecode_" + morsetext.charAt(i), "drawable", "net.flashmorse");
                    ImageSpan imgspa = new ImageSpan(MorseCodeActivity.this, morsedrawid);

                    SpannableString sptext = new SpannableString(Character.toString(morsetext.charAt(i)));
                    sptext.setSpan(imgspa, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    MorseText mt = new MorseText();
                    mt.setMorseCode(sptext);
                    items.add(mt);
                }

                lvmorsetexts.setAdapter(mListAdapter);
            }
        });

        btntextclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btntextclear.setTextColor(Color.GRAY);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btntextclear.setTextColor(Color.parseColor("#e5e5e5"));
                        etmorsetext.setText("");
                    }
                }, 510);
            }
        });
    }

    private static class MorseText {
        private SpannableString morseCode;

        public SpannableString getMorseCode() {
            return morseCode;
        }

        public void setMorseCode(SpannableString morseCode) {
            this.morseCode = morseCode;
        }
    }

    private ArrayList<MorseText> items = new ArrayList<MorseText>();
    private BaseAdapter mListAdapter = new BaseAdapter() {
        class ViewHolder {
            public TextView textView;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.listview_item_morsetext, container, false);

                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.tv_morsecodetext);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textView.setText(items.get(position).getMorseCode());
            return convertView;
        }
    };
}
