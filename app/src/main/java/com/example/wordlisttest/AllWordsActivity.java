package com.example.wordlisttest;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AllWordsActivity extends AppCompatActivity {

    private ListView listView;
    private final String AUTHORITY = "content://com.example.wordslist.provider/AllWords";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_words);

        List<Word> allWords = new ArrayList<Word>();
        final Uri uri = Uri.parse(AUTHORITY);
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if(cursor.moveToFirst()){
            do {
                System.out.println("start");
                Word allword = new Word();
                String word = cursor.getString(cursor.getColumnIndex("word"));
                String translation = cursor.getString(cursor.getColumnIndex("translation"));
                String example = cursor.getString(cursor.getColumnIndex("example"));
                String exampleTran = cursor.getString(cursor.getColumnIndex("exampleTran"));
                allword.setWord(word);
                allword.setTranslation(translation);
                if (example != null) {
                    allword.setExample(example);
                }
                if (exampleTran != null) {
                    allword.setExampleTran(exampleTran);
                }
                allWords.add(allword);

            } while (cursor.moveToNext());
        }

        cursor.close();
        String[] expRe = new String[allWords.size()];
        for (int i = 0; i < allWords.size(); i++) {
            expRe[i] = allWords.get(i).getWord() + ":" + allWords.get(i).getTranslation();
        }
        listView = findViewById(R.id.allwords);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AllWordsActivity.this, android.R.layout.simple_list_item_1, expRe);
        listView.setAdapter(adapter);
        //设置listView的item的点击事件，通过AlertDialog显示单词全部信息和编辑选项
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AllWordsActivity.this);
                System.out.println(position);
                String text = listView.getItemAtPosition(position).toString();
                final String[] Text = text.split(":");
                String[] selectionArgs = {Text[0]};
                Cursor all = getContentResolver().query(uri,null, "word = ?", selectionArgs, null);
                all.moveToFirst();
                final String example = all.getString(all.getColumnIndex("example"));
                final String exampleTran = all.getString(all.getColumnIndex("exampleTran"));
                builder.setTitle(Text[0]);
                builder.setMessage("释义：" + Text[1] + "\n例句：" + example + "\n翻译：" + exampleTran);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(AllWordsActivity.this, "确定",
//                                        Toast.LENGTH_LONG).show();

                    }
                });
                builder.setNeutralButton("编辑", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                                Toast.makeText(AllWordsActivity.this, "编辑",
//                                        Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AllWordsActivity.this, EditAllActivity.class);
                        intent.putExtra("word", Text[0]);
                        intent.putExtra("translation", Text[1]);
                        intent.putExtra("example", example);
                        intent.putExtra("exampleTran", exampleTran);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                                Toast.makeText(AllWordsActivity.this, "添加至生词表",
//                                        Toast.LENGTH_LONG).show();
                        String[] selectionArgs = {Text[0]};
                        getContentResolver().delete(uri, "word = ?", selectionArgs);
                        Intent intent = new Intent(AllWordsActivity.this, AllWordsActivity.class);
                        startActivity(intent);
                        finish();//关闭自己
                    }
                });
                builder.show();

            }
        });
    }
}
