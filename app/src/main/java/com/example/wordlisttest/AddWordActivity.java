package com.example.wordlisttest;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class AddWordActivity extends AppCompatActivity {

    private final String AUTHORITY = "content://com.example.wordslist.provider/AllWords";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_words);

        Button save = findViewById(R.id.save);
        save.setOnClickListener(new Save());
    }

    class Save implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            TextView word = findViewById(R.id.editword);
            TextView translation = findViewById(R.id.editTranslation);
            TextView example = findViewById(R.id.editexample);
            TextView exampleTran = findViewById(R.id.editexampleTran);
            Uri uri = Uri.parse(AUTHORITY);

            if(!word.getText().toString().isEmpty() && !translation.getText().toString().isEmpty()) {
                String[] selectionArgs = {word.getText().toString()};
                final Cursor cursor = getContentResolver().query(uri, null, "word = ?", selectionArgs, null,null);
                if(cursor.moveToFirst()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddWordActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage(word.getText().toString() + "在单词表中已存在");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(AllWordsActivity.this, "确定",
//                                        Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.show();
                }else{
                    ContentValues values = new ContentValues();
                    values.put("word", word.getText().toString());
                    values.put("translation", translation.getText().toString());
                    values.put("example", example.getText().toString());
                    values.put("exampleTran", exampleTran.getText().toString());
                    getContentResolver().insert(uri, values);
                    Toast.makeText(AddWordActivity.this, "添加成功",
                            Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(AddWordActivity.this, "单词和翻译处不能为空",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

}
