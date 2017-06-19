package com.example.calculator;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import static com.example.calculator.R.layout.activity_home_screen;
import static com.example.calculator.R.layout.activity_variables_screen;
import static com.example.calculator.R.string.cos_function;
import static com.example.calculator.R.string.e_power_function;
import static com.example.calculator.R.string.ln_function;
import static com.example.calculator.R.string.log_function;
import static com.example.calculator.R.string.sin_function;
import static com.example.calculator.R.string.tan_function;

/* TO DO:
 * include cursor?
 * edit text in helpscreen (help_content)
 * do something about the variables screen (just reset the buttons' texts?)
 * ASK ABOUT how to implement VariablesScreen (change all the buttons' texts again?)
 * -not practical because selecting the other options in the dropdown menu only sets the texts for three buttons
 * ALSO DON'T FORGET ABOUT THE CONVERT SETTING (same problems with VariablesScreen)
 * --basically, this is a screen that contains all the numeric values from VariablesScreen, the 4 basic math. operations, and a bunch of new functions
 *
 * **the help screen is just a pop-up window that displays information on how to use the app
 */

public class HomeScreen extends AppCompatActivity
{
    private PopupWindow helpScreen;
    private LayoutInflater layoutInflater;
    private ConstraintLayout constraintLayout;
    //how to implement inverse?
    //-1) just display "inv"
    //-2) display something (like "inverse") and turn that into "inv" in the code
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(activity_home_screen);

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraint);
        //include scroll bar for MainScreen if necessary?

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
        abnormalButtonListenter((Button) findViewById(R.id.buttonInverse));
        abnormalButtonListenter((Button) findViewById(R.id.buttonePower_sin));
        abnormalButtonListenter((Button) findViewById(R.id.buttonLn_cos));
        abnormalButtonListenter((Button) findViewById(R.id.buttonLog_tan));

        //misc. buttons
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

    void normalButtonListener(final Button button)
    {
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                TextView tv = (TextView)findViewById(R.id.MainScreen);
                tv.append(button.getText());
            }
        });
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
                    case "e^x":     functionDisplay = "e^";
                                    break;
                    case "1/x":     functionDisplay = "inv"; //CHANGE?
                                    break;
                    case "ln(x)":   functionDisplay = "ln"; //CHANGE? provide parenthesis and set cursor in the middle--PROBLEM: can user move cursor?
                                    break;
                    case "log(x)":  functionDisplay = "log"; //same consideration as above
                                    break;
                    case "sin(x)":  functionDisplay = "sin"; //same consideration as above
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

    //inflates menu when clicked
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_elements, menu);
        return true;
    }

    //click listener for menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.action_settings_help:
                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.help_screen,null);

                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                int width = dm.widthPixels;
                int height = dm.heightPixels;

                helpScreen = new PopupWindow(container,width,height,true);
                helpScreen.showAtLocation(constraintLayout, Gravity.NO_GRAVITY, Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL);

                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        helpScreen.dismiss();
                        return true;
                    }
                });
                return true;
            case R.id.action_settings_default:
                final Button button1 = (Button) findViewById(R.id.buttonePower_sin);
                button1.setText(e_power_function);
                final Button button2 = (Button) findViewById(R.id.buttonLn_cos);
                button2.setText(ln_function);
                final Button button3 = (Button) findViewById(R.id.buttonLog_tan);
                button3.setText(log_function);
                return true;
            case R.id.action_settings_trig:
                final Button button4 = (Button) findViewById(R.id.buttonePower_sin);
                button4.setText(sin_function);
                final Button button5 = (Button) findViewById(R.id.buttonLn_cos);
                button5.setText(cos_function);
                final Button button6 = (Button) findViewById(R.id.buttonLog_tan);
                button6.setText(tan_function);
                return true;
            case R.id.action_settings_variables:
                setContentView(activity_variables_screen);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
