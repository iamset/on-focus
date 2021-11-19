package com.focusg.focusgroup.auth

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.focusg.focusgroup.R
import com.focusg.focusgroup.launchFragmentInHiltContainer
import com.focusg.focusgroup.presentation.auth.SignUpFragment
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SignUpFragmentTest {
    lateinit var context: Context
    @Before
    fun setUp(){
        launchFragmentInHiltContainer<SignUpFragment>()
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun signUpButton_whenClickedWithCorrectControlsValues_hideErrors(){

        onView(withId(R.id.username_signup)).perform(typeText("ncmaduji"), closeSoftKeyboard())
        onView(withId(R.id.email_signup)).perform(typeText("ncm@ncm.com"), closeSoftKeyboard())
        onView(withId(R.id.password_signup)).perform(typeText("nnamdimadujiN1*"), closeSoftKeyboard())
        onView(withId(R.id.confirm_password_signup)).perform(typeText("nnamdimadujiN1*"), closeSoftKeyboard())

        onView(withId(R.id.signup)).perform(scrollTo()).perform(click())


        onView(withId(R.id.username_signup_error)).check(matches(not(isDisplayed())))
        onView(withId(R.id.password_signup_error)).check(matches(not(isDisplayed())))
        onView(withId(R.id.confirm_password_signup_error)).check(matches(not(isDisplayed())))
        onView(withId(R.id.email_signup_error)).check(matches(not(isDisplayed())))
    }

    @Test
    fun signUpButton_whenClickedWithEmptyControlsValues_displayErrors(){
        val error = context.resources.getString(R.string.required)
        onView(withId(R.id.signup)).perform(scrollTo()).perform(click())


        onView(withId(R.id.username_signup_error)).check(matches(withText(error)))
        onView(withId(R.id.password_signup_error)).check(matches(withText(error)))
        onView(withId(R.id.confirm_password_signup_error)).check(matches(withText(error)))
        onView(withId(R.id.email_signup_error)).check(matches(withText(error)))
    }

    @Test
    fun signUpButton_whenClickedWithInvalidControlsValues_displayError(){

        onView(withId(R.id.username_signup)).perform(typeText("aco"), closeSoftKeyboard())
        onView(withId(R.id.email_signup)).perform(typeText("ncm@"), closeSoftKeyboard())
        onView(withId(R.id.password_signup)).perform(typeText("comecome1h"), closeSoftKeyboard())
        onView(withId(R.id.confirm_password_signup)).perform(typeText("comeahefe"), closeSoftKeyboard())

        onView(withId(R.id.signup)).perform(scrollTo()).perform(click())

        onView(withId(R.id.username_signup_error)).check(matches(withText(context.resources.getString(R.string.username_should_be_within_5_20_characters_and_should_not_consists_of_special_characters_at_both_ends))))
        onView(withId(R.id.password_signup_error)).check(matches(withText(context.resources.getString(R.string.password_should_be_between_12_to_50_characters_and_contain_at_least_1_number_1_special_and_1_uppercase_character))))
        onView(withId(R.id.confirm_password_signup_error)).check(matches(withText(context.resources.getString(R.string.passwords_do_not_match))))
        onView(withId(R.id.email_signup_error)).check(matches(withText(context.resources.getString(R.string.enter_a_valid_email))))
    }
}