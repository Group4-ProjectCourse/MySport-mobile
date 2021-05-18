package com.mysport.mysport_mobile.fragments.chat

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.mysport.mysport_mobile.fragments.ProfileFragment

class ProfileFragmentFactory : FragmentFactory() {
    private val TAG: String = "AppDebug"

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return ProfileFragment();
    }
}