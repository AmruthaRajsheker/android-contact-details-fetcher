package com.example.contentprovider;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {

    private EditText editTextContactName;
    private Button buttonSearch;
    private TextView textViewContactName;
    private TextView textViewContactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextContactName = findViewById(R.id.editTextContactName);
        buttonSearch = findViewById(R.id.buttonSearch);
        textViewContactName = findViewById(R.id.textViewContactName);
        textViewContactNumber = findViewById(R.id.textViewContactNumber);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContact();
            }
        });
    }

    private void searchContact() {
        String contactName = editTextContactName.getText().toString().trim();

        // Query the Contacts Provider to retrieve contact details
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?",
                new String[]{contactName},
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // Retrieve name and phone number from cursor
                int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                // Display name and phone number
                textViewContactName.setText("Name: " + cursor.getString(nameIndex));
                textViewContactNumber.setText("Phone Number: " + cursor.getString(numberIndex));
            } else {
                // Handle if contact not found
                textViewContactName.setText("Contact not found");
                textViewContactNumber.setText("");
            }
            cursor.close();
        }
    }
}
