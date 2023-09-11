package com.miiky.houser.biometric

import androidx.biometric.BiometricManager
import androidx.fragment.app.FragmentActivity

fun isBiometric(context: FragmentActivity): Boolean{
    return BiometricManager.from(context).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
}