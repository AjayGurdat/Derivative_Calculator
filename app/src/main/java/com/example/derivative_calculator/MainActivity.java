package com.example.derivative_calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextInput = (EditText) findViewById(R.id.editTextInput);
        EditText editTextDiffVar = (EditText) findViewById(R.id.editTextDiffVar);
        Button buttonCompute = (Button) findViewById(R.id.buttonCompute);
        TextView output = (TextView) findViewById(R.id.textViewOutput);

        buttonCompute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editTextInput.getText().toString();
                String diffVar = editTextDiffVar.getText().toString();

                String answer = "";
                try {
                    answer = Compute.getAnswer(input, diffVar);
                } catch (InvalidInputException e) {
                    output.setText(e.getMessage());
                }

                output.setText(answer);

            }
        });

    }
}