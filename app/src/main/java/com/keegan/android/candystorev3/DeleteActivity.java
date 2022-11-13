package com.keegan.android.candystorev3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {
    private DatabaseManager dbManager;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        updateView();
    }

    public void updateView()
    {
        ArrayList<Candy> candies = dbManager.selectAll();
        RelativeLayout layout = new RelativeLayout(this);
        ScrollView scrollView = new ScrollView(this);
        RadioGroup group = new RadioGroup(this);
        for(Candy candy : candies)
        {
            RadioButton rb = new RadioButton(this);
            rb.setId(candy.getId());
            rb.setText(candy.getName());
            group.addView(rb);
        }

        RadioButtonHandler rbh = new RadioButtonHandler();
        group.setOnCheckedChangeListener(rbh);

        Button backButton = new Button(this);
        backButton.setText(R.string.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DeleteActivity.this.finish();
            }
        });

        scrollView.addView(group);
        layout.addView( scrollView );

        // add back button at bottom
        RelativeLayout.LayoutParams params
                = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT );
        params.addRule( RelativeLayout.ALIGN_PARENT_BOTTOM );
        params.addRule( RelativeLayout.CENTER_HORIZONTAL );
        params.setMargins( 0, 0, 0, 50 );
        layout.addView( backButton, params );

        setContentView( layout );
    }

    private class RadioButtonHandler implements RadioGroup.OnCheckedChangeListener{
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            dbManager.deleteById(checkedId);
            Toast.makeText(DeleteActivity.this, "Candy deleted", Toast.LENGTH_SHORT).show();

            updateView();
        }
    }
}
