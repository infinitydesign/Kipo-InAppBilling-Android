package io.idco.kipobilling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.Button_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send user to purchase page (1000 rial)
                startActivity(new Intent(MainActivity.this, BuyActivity.class));
            }
        });
    }
}
