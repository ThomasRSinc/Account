# Account
An online account manager app for Android 8+ (API 26+)
**(24 May 2023)**

3rd year university project (CMP309) of an Android app combining several features and views. Written in Java. Tested up to Android 13 (API 33).

- Functionality to add a service and associate accounts with it, and record account details
- Multiple screen layouts (views), activities, interactions, flexible and responsive UI
- Two linked SQL databases with built-in error checking and input validation
- Recommendation alerts and timed reminder notifications
- Clearbit free API for grabbing service logos based on domain
- Auth types: password, phone, one-time code, security key, security question, backup codes, app pin code, app biometrics, app push notification, other (custom)

...
  
The name comes from 1) the idea of an online account, and 2) the idea of "accounting" for things — in this case, the potential vulnerabilities present [as a result of the modern internet].

In a nutshell, the original idea of this app was to function as a hub for online accounts. Users could input the services and accounts they used, and the system would identify dependencies, intelligently spotting weaknesses and suggesting improvements. With the complicated web of services and accounts used and different methods of authentication, it can get difficult to keep track of it all. Crucially, the security can be overlooked, and thus it becomes an attack vector.

No credentials (password etc.) are input, only the details of the service and details of the authentication services in use. This would also have the ability for Single Sign-On (SSO) "linking". Alerts for security breaches (HaveIBeenPwned etc.) would also be a useful feature.

Think, for example of an account used frequently for SSO itself having no (or weak) multi-factor authentication. Perhaps a central email account doesn't have authentication failsafes (multiple types available in case one MFA method cannot be accessed). Maybe a linked account could be problematic for OSINT, or potentially an unintended access/jumping point in the event of a breach.

...

This is quite rudimentary and messy, but I'm putting it here as a sort of proof of concept and because I think there should be a professional version of something like this.

My original idea was admittedly ambitious. Due to time constraints and other factors I couldn't realise as much of it as I would have liked, but the basic idea is there at least.

Some things are included (either purely or incompletely) because that's what the module leader wanted. Security could also be greatly improved. The files included are just taken from the Android Studio IDE and should be enough for a successful import to view the code.
