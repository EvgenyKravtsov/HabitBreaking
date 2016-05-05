package com.evgenykravtsov.habitbreaking.view.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.evgenykravtsov.habitbreaking.R;
import com.evgenykravtsov.habitbreaking.model.RegistrationData;
import com.evgenykravtsov.habitbreaking.model.exception.InvalidRegistrationDataException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = RegistrationActivity.class.getSimpleName();

    private static final int MAX_AGE = 120; // years

    ////

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.registration_screen_email_field) EditText emailField;
    @Bind(R.id.registration_screen_gender_radio_group) RadioGroup genderRadioGroup;
    @Bind(R.id.registration_screen_age_field) EditText ageField;

    ////

    @OnClick(R.id.registration_screen_registration_button)
    public void onClickRegistrationButton() {
        try {
            RegistrationData registrationData = getRegistrationDataFromViews();

            // TODO Delete test code
            Log.d(TAG, registrationData.getEmail() + "\n"
                + registrationData.getGender() + "\n"
                + registrationData.getAge());
        } catch (Exception e) {
            showSnackbar(getString(R.string.invalid_registration_data_message));
        }
    }

    ////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        prepareToolbar();
    }

    ////

    private void prepareToolbar() {
        toolbar.setTitle(getString(R.string.registration_screen_toolbar_title));
        toolbar.setNavigationIcon(R.drawable.navigation_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(RegistrationActivity.this);
            }
        });
        setSupportActionBar(toolbar);
    }

    private RegistrationData getRegistrationDataFromViews() throws InvalidRegistrationDataException {
        RegistrationData registrationData = new RegistrationData();

        String email = emailField.getText().toString();
        if (!validateEmail(email)) {
            throw new InvalidRegistrationDataException();
        }
        registrationData.setEmail(email);

        int genderButtonId = genderRadioGroup.getCheckedRadioButtonId();
        View genderButton = genderRadioGroup.findViewById(genderButtonId);
        int genderIndex = genderRadioGroup.indexOfChild(genderButton);
        if (!validateGender(genderIndex)) {
            throw new InvalidRegistrationDataException();
        }
        registrationData.setGender(genderIndex);

        int age = Integer.parseInt(ageField.getText().toString());
        if (!validateAge(age)) {
            throw new InvalidRegistrationDataException();
        }
        registrationData.setAge(age);

        return registrationData;
    }

    private boolean validateEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validateGender(int genderIndex) {
        return genderIndex == 0 || genderIndex == 1;
    }

    private boolean validateAge(int age) {
        return age < MAX_AGE;
    }

    private void showSnackbar(String message) {
        View view = findViewById(android.R.id.content);
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        }
    }
}
