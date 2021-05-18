package com.mysport.mysport_mobile.fragments.chat

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mysport.mysport_mobile.R
import com.mysport.mysport_mobile.fragments.ProfileFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileFragmentTest {
    @Test
    fun test_isProfileDataVisible() {
        val scenario = launchFragmentInContainer<ProfileFragment>(
                fragmentArgs = null,
                factory = ProfileFragmentFactory()
        )
        onView(withId(R.id.profile_full_name)).check(matches(withText("Michael Tomson")))
        onView(withId(R.id.textEditEmail)).check(matches(withText("ktest6070@gmail.com")))
    }
}
