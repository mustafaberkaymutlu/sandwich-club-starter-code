package com.udacity.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    public static Intent newIntent(@NonNull Context context, int position) {
        final Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_POSITION, position);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        final int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        final String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        final String json = sandwiches[position];
        final Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(@NonNull Sandwich sandwich) {
        final ImageView ivIngredients = findViewById(R.id.image_iv);
        final TextView tvOrigin = findViewById(R.id.origin_tv);
        final TextView tvAlsoKnownAs = findViewById(R.id.also_known_tv);
        final TextView tvDescription = findViewById(R.id.description_tv);
        final TextView tvIngredients = findViewById(R.id.ingredients_tv);

        if (!TextUtils.isEmpty(sandwich.getImage())) {
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ivIngredients);
        }

        tvOrigin.setText(sandwich.getPlaceOfOrigin());
        tvAlsoKnownAs.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        tvDescription.setText(sandwich.getDescription());
        tvIngredients.setText(TextUtils.join(", ", sandwich.getIngredients()));

        setTitle(sandwich.getMainName());
    }
}
