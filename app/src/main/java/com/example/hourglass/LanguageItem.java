package com.example.hourglass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LanguageItem extends MyAppCompatActivity {

    private int text;
    private int imageResId;

    public LanguageItem(int txt, int img) {
        text = txt;
        imageResId = img;
    }

    public int getText() {
        return text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public class CustomAdapter extends ArrayAdapter<LanguageItem> {
        private Context context;
        private int resource;

        public CustomAdapter(Context context, int resource, List<LanguageItem> items) {
            super(context, resource, items);
            this.context = context;
            this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
            View view = convertView;
            if(view == null){
                LayoutInflater inflater = LayoutInflater.from(context);
                view = inflater.inflate(resource,parent,false);
            }

            TextView textView = view.findViewById(R.id.txt);
            ImageView imageView = view.findViewById(R.id.imgView);

            LanguageItem item = getItem(position);
            if(item !=null){
                textView.setText(item.getText());
                imageView.setImageResource(item.getImageResId());
            }
            return view;
        }


    }

//    ListView listView = findViewById(R.id.listView);
//    List<LanguageItem> itemList = new ArrayList<>();
//
//    itemList.add(new Languageitem(R.string.textEnglishLanguage,R.drawable.greece));
//    itemList.add(new LanguageItem(R.string.textGreekLanguage,R.drawable.uk));
}
