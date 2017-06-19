//BUTTONS DON'T WORK IN THE TRIG SCREEN--WHY?
package com.example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.calculator.R.layout.activity_home_screen;
import static com.example.calculator.R.layout.activity_trig_screen;

public class TrigScreen extends HomeScreen
{
    //only thing needed to be done is to make sure the trig functions print the correct things onto the main screen
    //when switching 'screens', make sure 'Main Screen' stays the same no matter which setting the app is in
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(activity_trig_screen);

        //setting button action listeners for the normal buttons (buttons that just display stuff)
        //numeric buttons
        normalButtonListener((Button) findViewById(R.id.button0));
        normalButtonListener((Button) findViewById(R.id.button1));
        normalButtonListener((Button) findViewById(R.id.button2));
        normalButtonListener((Button) findViewById(R.id.button3));
        normalButtonListener((Button) findViewById(R.id.button4));
        normalButtonListener((Button) findViewById(R.id.button5));
        normalButtonListener((Button) findViewById(R.id.button6));
        normalButtonListener((Button) findViewById(R.id.button7));
        normalButtonListener((Button) findViewById(R.id.button8));
        normalButtonListener((Button) findViewById(R.id.button9));
        //function buttons
        normalButtonListener((Button) findViewById(R.id.buttonAdd));
        normalButtonListener((Button) findViewById(R.id.buttonSubtract));
        normalButtonListener((Button) findViewById(R.id.buttonMultiply));
        normalButtonListener((Button) findViewById(R.id.buttonDivide));
        normalButtonListener((Button) findViewById(R.id.buttonPower));
        normalButtonListener((Button) findViewById(R.id.buttonFRAC));
        normalButtonListener((Button) findViewById(R.id.buttonE));

        //abnormal function (functions with "x" in its text) buttons
        //CONSIDERATIONS: see the function definition below
        abnormalButtonListenter((Button) findViewById(R.id.buttonInverse));
        abnormalButtonListenter((Button) findViewById(R.id.buttonSin));
        abnormalButtonListenter((Button) findViewById(R.id.buttonCos));
        abnormalButtonListenter((Button) findViewById(R.id.buttonTan));

        //misc.
        normalButtonListener((Button) findViewById(R.id.buttonOpenParenth));
        normalButtonListener((Button) findViewById(R.id.buttonClosedParenth));
        normalButtonListener((Button) findViewById(R.id.buttoneConstant));
        normalButtonListener((Button) findViewById(R.id.buttonpiConstant));

        //the dot and negative button
        final Button buttonDotNegative = (Button) findViewById(R.id.buttonDotNegative);
        buttonDotNegative.setOnClickListener(new View.OnClickListener() //tap to delete one character
        {
            public void onClick(View v)
            {
                TextView tv = (TextView)findViewById(R.id.MainScreen);
                tv.append(".");
            }
        });
        buttonDotNegative.setOnLongClickListener(new View.OnLongClickListener() //hold to clear
        {
            @Override
            public boolean onLongClick(View v)
            {
                TextView tv = (TextView)findViewById(R.id.MainScreen);
                tv.append("-");
                return true; //ensures app doesn't close after holding this button
            }
        });

        //the del/clear button
        final Button buttonDelClear = (Button) findViewById(R.id.buttonDelClear);
        buttonDelClear.setOnClickListener(new View.OnClickListener() //tap to delete one character
        {
            public void onClick(View v)
            { //action listener for tapping the button
                TextView tv = (TextView)findViewById(R.id.MainScreen);
                CharSequence input = tv.getText();
                input = input.subSequence(0, input.length()-1); //makes a substring of everything inside 'input' except the last character
                tv.setText(input);
            }
        });
        buttonDelClear.setOnLongClickListener(new View.OnLongClickListener() //hold to clear
        {
            @Override
            public boolean onLongClick(View v)
            { //action listener for holding the button
                TextView tv = (TextView)findViewById(R.id.MainScreen);
                tv.setText("");
                return true; //ensures app doesn't close after holding this button
            }
        });

        //the =/Enter button
        //take the stuff on MainScreen, pass to Calculate class, get the answer, and setText to 'answer'
    }

    private void abnormalButtonListenter(final Button button)
    {
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                TextView tv = (TextView)findViewById(R.id.MainScreen);
                String functionName = (String) button.getText();
                CharSequence functionDisplay;
                switch(functionName)
                {
                    case "1/x":     functionDisplay = "inv"; //CHANGE?
                                    break;
                    case "sin(x)":  functionDisplay = "sin"; //CHANGE? provide parenthesis and set cursor in the middle--PROBLEM: can user move cursor?
                                    break;
                    case "cos(x)":  functionDisplay = "cos"; //same consideration as above
                                    break;
                    case "tan(x)":  functionDisplay = "tan"; //same consideration as above
                                    break;
                    default:        functionDisplay = "?";
                }
                tv.append(functionDisplay);
            }
        });
    }
}
