//created by Lance Santiago

package com.example.aron.comp304_teamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    public TextView calorieMaintain;
    public TextView weightLoss;

    public Spinner sp;



    public RadioGroup radioGroup_Gender;
    public RadioGroup radioGroup_Height;
    public RadioGroup radioGroup_Weight;

    public RadioButton rb_Metric;
    public RadioButton rb_Imperial;

    public Button button;
    public Button resetButton;

    public Spinner spinner;

    public String gender; //store gender choice

    int calorieValue; // total calories

    public boolean isKilo = false;
    public boolean isMetric = false;
    public boolean isFemale = false;
    public boolean isPressed = false;

    final double convertToPounds = 2.20462;
    final int femaleDeduction = 150; // if isFemale is true, deduct with this value

    //get values from edittexts
    int getAge;
    double getWeight;
    double getHeight;
    int getHeightFeet;
    int getHeightInch;

    //age
    int youngAge = 18;
    int middleAge = 35;
    int oldAge = 50;

    //weight - pounds
    double lightPound = 100.0;
    double mediumPound = 160.0;
    double heavyPound = 210.0;

    //weight - kilo
    double lightKilo = 45.0;
    double mediumKilo = 54.0;
    double heavyKilo = 75.0;

    //height - metric
    double small = 150.0;
    double medium = 165.0;
    double tall = 180.0;

    //height - imperial
    double feetSmall = 5.0;
    double feetMedium = 6.0;
    double feetTall = 7.0;

    double inches = 12.0;


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

        calorieMaintain = findViewById(R.id.text_MaintainWeight);
        weightLoss = findViewById(R.id.text_WeightLoss);

        button = findViewById(R.id.calculate_Calories);
        resetButton = findViewById(R.id.resetButton);

        String[] getExerciseArray = getResources().getStringArray(R.array.spinner_CalorieIntake);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, getExerciseArray);
        sp = findViewById(R.id.spinner_ExerciseRoutine);
        sp.setAdapter(adapter);


        calorieMaintain.setVisibility(View.INVISIBLE);
        weightLoss.setVisibility(View.INVISIBLE);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculateCalories(v);
            }
        });

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
                        //Toast.makeText(CalorieIntakeActivity.this, gender, Toast.LENGTH_SHORT).show();
                    }
                    if (rb2.isChecked())
                    {
                        gender = rb2.getText().toString();
                        isFemale = true;
                        //Toast.makeText(CalorieIntakeActivity.this, gender, Toast.LENGTH_SHORT).show();
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
                        isMetric = true;
                    }
                    if (rb_Imperial.isChecked())
                    {
                        heightText_Metric.setVisibility(View.GONE);
                        height.setVisibility(View.GONE);
                        heightText_Feet.setVisibility(View.VISIBLE);
                        height_Feet.setVisibility(View.VISIBLE);
                        heightText_Inch.setVisibility(View.VISIBLE);
                        height_Inch.setVisibility(View.VISIBLE);
                        isMetric = false;
                    }
                }
            }
        });
    }
    public void CalculateCalories(View view)
    {
        isPressed = true;
        //exercise level spinner and putting string array
        String[] getExerciseArray = getResources().getStringArray(R.array.spinner_CalorieIntake);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, getExerciseArray);
        sp = findViewById(R.id.spinner_ExerciseRoutine);
        sp.setAdapter(adapter);

        calorieMaintain.setVisibility(View.VISIBLE);
        weightLoss.setVisibility(View.VISIBLE);
        resetButton.setVisibility(View.VISIBLE);




        getAge = Integer.parseInt(age.getText().toString());

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
            getHeightFeet = 0;
            getHeightInch = 0;
        }
        else
        {
            getHeightFeet = Integer.parseInt(height_Feet.getText().toString());
            getHeightInch = Integer.parseInt(height_Inch.getText().toString());
            getHeight = 0;
        }


        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calorieValue = 0;
                calorieValue = calorieValue + 1000;
                switch (position)
                {
                    //Daily
                    case 0:
                        //young age
                        if(getAge >= youngAge && getAge <= middleAge)
                        {
                            //light weight - short height
                            if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 950;
                            }
                            //light weight - medium height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches ))
                            {
                                calorieValue = calorieValue + 1350;
                            }
                            //light weight - tall height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1600;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //medium weight - short height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1100;
                            }
                            //medium weight - medium height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1250;
                            }
                            //medium weight - tall height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1450;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //heavy weight - short height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1300;
                            }
                            //heavy weight - medium height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1550;
                            }
                            //heavy weight - tall height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1800;
                            }

                        }
                        //middle aged
                        else if(getAge > middleAge && getAge < oldAge)
                        {
                            //light weight - short height
                            if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 800;
                            }
                            //light weight - medium height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches ))
                            {
                                calorieValue = calorieValue + 1200;
                            }
                            //light weight - tall height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1450;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //medium weight - short height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1000;
                            }
                            //medium weight - medium height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1150;
                            }
                            //medium weight - tall height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1350;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //heavy weight - short height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1200;
                            }
                            //heavy weight - medium height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1400;
                            }
                            //heavy weight - tall height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1650;
                            }
                        }
                        //senior
                        else if(getAge > oldAge)
                        {
                            //light weight - short height
                            if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 600;
                            }
                            //light weight - medium height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches ))
                            {
                                calorieValue = calorieValue + 800;

                            }
                            //light weight - tall height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1000;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //medium weight - short height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 750;
                            }
                            //medium weight - medium height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 900;
                            }
                            //medium weight - tall height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1100;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //heavy weight - short height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 900;
                            }
                            //heavy weight - medium height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1150;
                            }
                            //heavy weight - tall height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1400;
                            }
                        }
                        break;
                        //More than 4 times per week
                    case 1:
                        //young age
                        if(getAge >= youngAge && getAge <= middleAge)
                        {
                            //light weight - short height
                            if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 800;
                            }
                            //light weight - medium height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches ))
                            {
                                calorieValue = calorieValue + 1100;
                            }
                            //light weight - tall height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1350;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //medium weight - short height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 950;
                            }
                            //medium weight - medium height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1100;
                            }
                            //medium weight - tall height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1250;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //heavy weight - short height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1050;
                            }
                            //heavy weight - medium height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1300;
                            }
                            //heavy weight - tall height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1500;
                            }
                        }
                        //middle aged
                        else if(getAge > middleAge && getAge < oldAge)
                        {
                            //light weight - short height
                            if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 750;
                            }
                            //light weight - medium height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches ))
                            {
                                calorieValue = calorieValue + 1050;
                            }
                            //light weight - tall height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1300;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //medium weight - short height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 900;
                            }
                            //medium weight - medium height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1050;
                            }
                            //medium weight - tall height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1250;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //heavy weight - short height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1050;
                            }
                            //heavy weight - medium height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1250;
                            }
                            //heavy weight - tall height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1400;
                            }
                        }
                        //senior
                        else if(getAge > oldAge)
                        {
                            //light weight - short height
                            if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 550;
                            }
                            //light weight - medium height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches ))
                            {
                                calorieValue = calorieValue + 700;
                            }
                            //light weight - tall height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 850;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //medium weight - short height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 650;
                            }
                            //medium weight - medium height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 850;
                            }
                            //medium weight - tall height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1000;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //heavy weight - short height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 800;
                            }
                            //heavy weight - medium height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1050;
                            }
                            //heavy weight - tall height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1250;
                            }
                        }
                        break;
                        //5 Times per week
                    case 2:
                        //young age
                        if(getAge >= youngAge && getAge <= middleAge)
                        {
                            //light weight - short height
                            if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 700;
                            }
                            //light weight - medium height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches ))
                            {
                                calorieValue = calorieValue + 1000;
                            }
                            //light weight - tall height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1300;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //medium weight - short height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 900;
                            }
                            //medium weight - medium height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1000;
                            }
                            //medium weight - tall height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1150;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //heavy weight - short height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1000;
                            }
                            //heavy weight - medium height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1200;
                            }
                            //heavy weight - tall height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1350;
                            }
                        }
                        //middle aged
                        else if(getAge > middleAge && getAge < oldAge)
                        {
                            //light weight - short height
                            if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 700;
                            }
                            //light weight - medium height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches ))
                            {
                                calorieValue = calorieValue + 900;
                            }
                            //light weight - tall height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1200;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //medium weight - short height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 800;
                            }
                            //medium weight - medium height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 900;
                            }
                            //medium weight - tall height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1050;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //heavy weight - short height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 900;
                            }
                            //heavy weight - medium height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1000;
                            }
                            //heavy weight - tall height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1250;
                            }
                        }
                        //senior
                        else if(getAge > oldAge)
                        {
                            //light weight - short height
                            if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 475;
                            }
                            //light weight - medium height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches ))
                            {
                                calorieValue = calorieValue + 600;
                            }
                            //light weight - tall height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 750;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //medium weight - short height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 600;
                            }
                            //medium weight - medium height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 800;
                            }
                            //medium weight - tall height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 900;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //heavy weight - short height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 750;
                            }
                            //heavy weight - medium height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 975;
                            }
                            //heavy weight - tall height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1050;
                            }
                        }
                        break;
                        //3 times per week
                    case 3:
                        //young age
                        if(getAge >= youngAge && getAge <= middleAge)
                        {
                            //light weight - short height
                            if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 600;
                            }
                            //light weight - medium height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches ))
                            {
                                calorieValue = calorieValue + 850;
                            }
                            //light weight - tall height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1100;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //medium weight - short height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 800;
                            }
                            //medium weight - medium height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 900;
                            }
                            //medium weight - tall height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1000;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //heavy weight - short height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 900;
                            }
                            //heavy weight - medium height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1100;
                            }
                            //heavy weight - tall height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1200;
                            }
                        }
                        //middle aged
                        else if(getAge > middleAge && getAge < oldAge)
                        {
                            //light weight - short height
                            if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 650;
                            }
                            //light weight - medium height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches ))
                            {
                                calorieValue = calorieValue + 825;
                            }
                            //light weight - tall height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1100;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //medium weight - short height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 725;
                            }
                            //medium weight - medium height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 800;
                            }
                            //medium weight - tall height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 975;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //heavy weight - short height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 800;
                            }
                            //heavy weight - medium height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 850;
                            }
                            //heavy weight - tall height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 1125;
                            }
                        }
                        //senior
                        else if(getAge > oldAge)
                        {
                            //light weight - short height
                            if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 425;
                            }
                            //light weight - medium height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches ))
                            {
                                calorieValue = calorieValue + 525;
                            }
                            //light weight - tall height
                            else if ((getWeight >= lightPound && getWeight < mediumPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 650;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //medium weight - short height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 550;
                            }
                            //medium weight - medium height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 700;
                            }
                            //medium weight - tall height
                            else if ((getWeight > mediumPound && getWeight < heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 800;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //heavy weight - short height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= small && getHeight < medium || getHeightFeet == feetSmall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 700;
                            }
                            //heavy weight - medium height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight >= medium && getHeight <= tall || getHeightFeet == feetMedium && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 850;
                            }
                            //heavy weight - tall height
                            else if ((getWeight >= heavyPound) &&
                                    (getHeight > tall || getHeightFeet == feetTall && getHeightInch < inches))
                            {
                                calorieValue = calorieValue + 900;
                            }
                        }
                        break;
                }
                if (isFemale)
                {
                    calorieValue = calorieValue - femaleDeduction;
                }
                //Toast.makeText(CalorieIntakeActivity.this, String.valueOf(calorieValue) + " pt2", Toast.LENGTH_SHORT).show();
                calorieMaintain.setText(getString(R.string.maintainWeight) + "\n" + calorieValue);

                double cutCalories = calorieValue * 0.80;
                weightLoss.setText(getString(R.string.weightLoss) + "\n" + (int)cutCalories);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setVisibility(View.GONE);

    }

    public void Reset(View view)
    {
        age.setText("");
        weight.setText("");
        if(isMetric)
        {
            height.setText("");
        }
        else
        {
            height_Feet.setText("");
            height_Inch.setText("");
        }

        radioGroup_Gender.clearCheck();
        radioGroup_Height.clearCheck();
        radioGroup_Weight.clearCheck();

        sp.setSelection(0);

        calorieMaintain.setVisibility(View.INVISIBLE);
        weightLoss.setVisibility(View.INVISIBLE);
        button.setVisibility(View.VISIBLE);

    }

}

