package com.example.justjava;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    boolean hasWhippedCream;
    boolean hasChocolate;

    //Context context = getApplicationContext();
    String text = "Invalid Entry";
    int duration = Toast.LENGTH_SHORT;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*TextView text = new TextView(this);
        text.setText("Wow!");
        text.setTextSize(22);
        text.setTextColor(Color.RED);
        setContentView(text);*/

        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        int price = calculatePrice();
        String msg = createOrderSummary(price);
        //displayMessage(msg);




        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT , "COFFEE ORDER");
        intent.putExtra(Intent.EXTRA_TEXT , msg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }



    }

    public int calculatePrice() {
        int price_per_cup = 5;

        if(hasWhippedCream)
            price_per_cup += 1;
        if(hasChocolate)
            price_per_cup += 2;

        return quantity * price_per_cup;
    }

//    private void displayMessage(String price) {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(price);
//    }

    private String createOrderSummary(int number) {
        String msg;
        String name;

        EditText txt = (EditText) findViewById(R.id.name_view);

        name = txt.getText().toString();
        msg = getString(R.string.name) + name + "\n" + getString(R.string.add_whipped_cream) + hasWhippedCream +
                "\n" + getString(R.string.add_chocolate)+ hasChocolate + "\nQuantity : " + quantity + "\nTotal = $" + number + "\n" + getString(R.string.thank_you);
        return msg;
    }

    public void increment(View view) {
        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity > 0)
            quantity = quantity - 1;
        else
            Toast.makeText(getApplicationContext() , text , duration).show();
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    public void whippedCreamCheckBox(View v) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.hasWhippedCream);
        hasWhippedCream = whippedCream.isChecked();
    }

    public void choclateCheckBox(View v) {
        CheckBox choclate = (CheckBox) findViewById(R.id.hasChoclate);
        hasChocolate = choclate.isChecked();
    }
}
