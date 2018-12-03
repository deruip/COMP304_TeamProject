//created by Lance Santiago

package com.example.aron.comp304_teamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    public RadioButton rb_Metric;
    public RadioButton rb_Imperial;

    public Spinner spinner;

    public String gender; //store gender choice

    int calorieValue; // total calories

    public boolean isKilo = false;
    public boolean isMetric = false;
    public boolean isFemale = false;

    final double convertToPounds = 2.20462;
    final int femaleDeduction = 150; // if isFemale is true, deduct with this value

    //number checks

    //age
    int youngAge = 18;
    int middleAge = 35;
    int oldAge = 50;

    //weight - pounds
    int lightPound = 100;
    int mediumPound = 160;
    int heavyPound = 210;

    //weight - kilo
    int lightKilo = 45;
    int mediumKilo = 54;
    int heavyKilo = 75;

    //height - metric
    int small = 150;
    int medium = 165;
    int tall = 180;

    //height - imperial
    int feetSmall = 5;
    int feetMedium = 6;
    int feetTall = 7;

    int inches = 12;







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
                        isFemale = false;
                        Toast.makeText(CalorieIntakeActivity.this, gender, Toast.LENGTH_SHORT).show();
                    }
                    if (rb2.isChecked())
                    {
                        gender = rb2.getText().toString();
                        isFemale = true;
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
                rb_Metric = findViewById(R.id.height_Metric);
                rb_Imperial = findViewById(R.id.height_Imperial);
                if(checkedId != 1)
                {
                    if(rb_Metric.isChecked())
                    {
                        heightText_Metric.setVisibility(View.VISIBLE);
                        height.setVisibility(View.VISIBLE);
                        heightText_Feet.setVisibility(View.GONE);
                        height_Feet.setVisibility(View.GONE);
                        heightText_Inch.setVisibility(View.GONE);
                        height_Inch.setVisibility(View.GONE);
                        isMetric = false;
                    }
                    if (rb_Imperial.isChecked())
                    {
                        heightText_Metric.setVisibility(View.GONE);
                        height.setVisibility(View.GONE);
                        heightText_Feet.setVisibility(View.VISIBLE);
                        height_Feet.setVisibility(View.VISIBLE);
                        heightText_Inch.setVisibility(View.VISIBLE);
                        height_Inch.setVisibility(View.VISIBLE);
                        isMetric = true;
                    }
                }
            }
        });
    }



    public void CalculateCalories(View view)
    {

        //get and store values from user input
        final int getAge;
        final double getWeight;
        final double getHeight;
        final int getHeightFeet;
        final int getHeightInch;


        getAge =  Integer.parseInt(age.getText().toString());


        if(isKilo)
        {
            getWeight = Double.parseDouble(weight.getText().toString()) * convertToPounds;
        }
        else
        {
            getWeight = Integer.parseInt(weight.getText().toString());
        }

        if(isMetric)
        {
            getHeight = Integer.parseInt(height.getText().toString());
        }
        else
        {
            getHeightFeet = Integer.parseInt(height_Feet.getText().toString());
            getHeightInch = Integer.parseInt(height_Inch.getText().toString());
        }



        calorieValue = calorieValue + 1000;


        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position)
                {
                    //Daily Exercise
                    case 0:
                        //young age
                        if(getAge >= youngAge && getAge <= middleAge)
                        {
                            //light weight - short height
                            if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 950;
                                if (isFemale)
                                {
                                    calorieValue = calorieValue - femaleDeduction;
                                }
                            }
                            //light weight - medium height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches ))
                            {
                                calorieValue = calorieValue + 1350;
                                if (isFemale)
                                {
                                    calorieValue = calorieValue - femaleDeduction;
                                }
                            }
                            //light weight - tall height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1600;
                                if (isFemale)
                                {
                                    calorieValue = calorieValue - femaleDeduction;
                                }
                            }
                ////////////////////////////////////////////////////////////////////////
                            //medium weight - short height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1100;
                                if (isFemale)
                                {
                                    calorieValue = calorieValue - femaleDeduction;
                                }
                            }
                            //medium weight - medium height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1250;
                                if (isFemale)
                                {
                                    calorieValue = calorieValue - femaleDeduction;
                                }
                            }
                            //medium weight - tall height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1450;
                                if (isFemale)
                                {
                                    calorieValue = calorieValue - femaleDeduction;
                                }
                            }

                            ////////////////////////////////////////////////////////////////////////
                            //heavy weight - short height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1300;
                                if (isFemale)
                                {
                                    calorieValue = calorieValue - femaleDeduction;
                                }
                            }
                            //heavy weight - medium height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1550;
                                if (isFemale)
                                {
                                    calorieValue = calorieValue - femaleDeduction;
                                }
                            }
                            //heavy weight - tall height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1800;
                                if (isFemale)
                                {
                                    calorieValue = calorieValue - femaleDeduction;
                                }
                            }
                        }
                }
            }
        });
    }
}

