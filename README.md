# BankWallet
Android application to store friend's IBAN bank accounts to local storage. Realm package is used in storing. Application is made with Android Studio. This was my hobby project - my first especially for Android made application.

It's not perfect: i.e IBAN-validation in *Validator* class doesn't recognize countries particularly. Like Malta uses 31 characters long IBANs when Norway uses 15 characters long IBANs. Now it just checks if it is in range 15-34.

## SDK versions
**Min Android API level:** 14 

**Target Android API level:** 23

## Installation
Open Project-file and compile out *.apk*-package. Open *.apk* package in your phone. Enabling 'unknown sources' might be needed in security settings.

