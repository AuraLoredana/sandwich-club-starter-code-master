package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    int position;
    String[] sandwiches;
    String json;
    Sandwich sandwich;

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            Log.e("null", "Intent null");
            closeOnError();
        }

        position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.e("not found", "position default");
            closeOnError();
            return;
        }

        sandwiches = getResources().getStringArray(R.array.sandwich_details);
        json = sandwiches[position];
        sandwich = null;
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            Log.e("no data", "sandwich null");

            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView mAlsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        TextView mIngredients = (TextView) findViewById(R.id.ingredients_tv);
        TextView mPlaceOfOrigin = (TextView) findViewById(R.id.origin_tv);
        TextView mDescription = (TextView) findViewById(R.id.description_tv);
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mPlaceOfOrigin.setText("No Data");
        } else {
            mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        }
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            mAlsoKnownAs.setText("No Data");
        } else {
            mAlsoKnownAs.setText(listModel(sandwich.getAlsoKnownAs()));
        }


        mDescription.setText(sandwich.getDescription());
        mIngredients.setText(listModel(sandwich.getIngredients()));

    }

    public StringBuilder listModel(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i)).append("\n");
        }
        return stringBuilder;
    }
}