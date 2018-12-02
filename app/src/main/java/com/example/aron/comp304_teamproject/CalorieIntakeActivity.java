//created by Lance Santiago

package com.example.aron.comp304_teamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CalorieIntakeActivity extends AppCompatActivity {

    public EditText age;
    public EditText weight;
    public EditText height;
    public EditText height_Feet;
    public EditText height_Inch;

    public TextView heightText_Metric;
    public TextView heightText_Feet;
    public TextView heightText_Inch;


    public RadioGroup radioGroup_Gender;
    public RadioGroup radioGroup_Height;
    public RadioGroup radioGroup_Weight;

    public Spinner spinner;

    public String gender; //store gender choice

    public boolean isKilo = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_intake);

        age = findViewById(R.id.editText_Age);
        weight = findViewById(R.id.editText_Weight);
        height = findViewById(R.id.editText_Height_Metric);
        height_Feet = findViewById(R.id.editText_Height_Imperial_Feet);
        height_Inch = findViewById(R.id.editText_Height_Imperial_Inch);

        heightText_Metric = findViewById(R.id.heightText_Metric);
        heightText_Feet = findViewById(R.id.heightText_Feet);
        heightText_Inch = findViewById(R.id.heightText_Inch);


        //exercise level spinner and putting string array
        String[] getExerciseArray = getResources().getStringArray(R.array.spinner_CalorieIntake);

        spinner = findViewById(R.id.spinner_ExerciseRoutine);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, getExerciseArray);
        spinner.setAdapter(adapter);

        //gender radio buttons
        radioGroup_Gender = findViewById(R.id.gender_Group);

        radioGroup_Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb1 = findViewById(R.id.gender_Male);
                RadioButton rb2 = findViewById(R.id.gender_Female);

                if(checkedId != 1)
                {
                    if(rb1.isChecked())
                    {
                        gender = rb1.getText().toString();
                        Toast.makeText(CalorieIntakeActivity.this, gender, Toast.LENGTH_SHORT).show();
                    }
                    if (rb2.isChecked())
                    {
                        gender = rb2.getText().toString();
                        Toast.makeText(CalorieIntakeActivity.this, gender, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //weight unit radio buttons
        radioGroup_Weight = findViewById(R.id.weight_Unit);

        radioGroup_Weight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb1 = findViewById(R.id.weight_Pounds);
                RadioButton rb2 = findViewById(R.id.weight_Kilo);

                if(checkedId != 1)
                {
                    if(rb1.isChecked())
                    {
                        isKilo=false;
                    }
                    if(rb2.isChecked())
                    {
                        isKilo=true;
                    }
                }
            }
        });

        //height measurement unit radio buttons

        radioGroup_Height = findViewById(R.id.height_Type);
        radioGroup_Height.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb1 = findViewById(R.id.height_Metric);
                RadioButton rb2 = findViewById(R.id.height_Imperial);
                if(checkedId != 1)
                {
                    if(rb1.isChecked())
                    {
                        heightText_Metric.setVisibility(View.VISIBLE);
                        height.setVisibility(View.VISIBLE);
                        heightText_Feet.setVisibility(View.GONE);
                        height_Feet.setVisibility(View.GONE);
                        heightText_Inch.setVisibility(View.GONE);
                        height_Inch.setVisibility(View.GONE);
                    }
                    if (rb2.isChecked())
                    {
                        heightText_Metric.setVisibility(View.GONE);
                        height.setVisibility(View.GONE);
                        heightText_Feet.setVisibility(View.VISIBLE);
                        height_Feet.setVisibility(View.VISIBLE);
                        heightText_Inch.setVisibility(View.VISIBLE);
                        height_Inch.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    public void CalculateCalories(View view)
    {

    }
}
