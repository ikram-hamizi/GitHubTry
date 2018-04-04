package com.example.usp05.githubtry;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SuccessfulRegistratonTest {

    // This string needs to be different for each iteration of this test run on the same emulator
    // i.e., there cannot be a user with this string for its username already in the database
    private String uname = "espresso355";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatButton = onView(allOf(withId(R.id.signUpButton), withText("Sign Up"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction editText = onView(allOf(withId(R.id.usernameRegister),isDisplayed()));
        editText.perform(click());

        ViewInteraction editText2 = onView(allOf(withId(R.id.usernameRegister),isDisplayed()));
        editText2.perform(replaceText(uname), closeSoftKeyboard());

        ViewInteraction editText3 = onView(allOf(withId(R.id.passwordRegister),isDisplayed()));
        editText3.perform(replaceText(uname), closeSoftKeyboard());

        ViewInteraction editText4 = onView(allOf(withId(R.id.password2Register),isDisplayed()));
        editText4.perform(replaceText(uname), closeSoftKeyboard());

        ViewInteraction editText5 = onView(allOf(withId(R.id.secQuestion1),isDisplayed()));
        editText5.perform(replaceText(uname), closeSoftKeyboard());

        ViewInteraction editText6 = onView(allOf(withId(R.id.secQuestion2),isDisplayed()));
        editText6.perform(replaceText(uname), closeSoftKeyboard());

        ViewInteraction editText8 = onView(allOf(withId(R.id.secQuestion3),isDisplayed()));
        editText8.perform(replaceText(uname), closeSoftKeyboard());

        ViewInteraction button = onView(allOf(withId(R.id.registerButton), withText("Register"),isDisplayed()));
        button.perform(click());

        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.usernameLogin),isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.usernameLogin),isDisplayed()));
        appCompatEditText2.perform(replaceText(uname), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(allOf(withId(R.id.passwordLogin),isDisplayed()));
        appCompatEditText3.perform(replaceText(uname), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(allOf(withId(R.id.loginButton), withText("Login"),isDisplayed()));
        appCompatButton2.perform(click());


        ViewInteraction invScreen = onView(allOf(withId(R.id.addItemButton),isDisplayed()));
    }

}
