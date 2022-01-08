# Android-Study-Jams

**YourStore**   ,  *Vocal for Local*


![vistors](https://visitor-badge.glitch.me/badge?page_id=yourstore) 

**Problem Statement:**

Over past two years, local businesses have faced lot of troubles due to Covid-19 and lockdown imposed. This has resulted in low sales, payroll cuts , unemployment in stores. 
Many businesses have permanently closed,  across the world, affecting the world economics. 
Local consumers also face a lot of difficulties buying the neccessary, daily products. Limited, open hours, covid protocols made the situation worse.


Covid has impacted almost every business be it small or medium, and it's effect is not going anywhere with new variants coming each year.

**Proposed Solution :**

This can be solved if there's a way to connect local buyers to local consumers. Yes there is, Android smartphones. there are over 3 billion active android users around the globe. I have worked on a Local e-commerce android applciation which connects local e-commerce to to potential consumers. Increasing Seller's branding, reach and sales. 

businesses can signup and  update info like store name, location and contact details. Then List products and share offers. 
<br>
local users can browse through product list , sellers list.  
This will lead to sales growth and increased reach to consumers. 

![buyer_flow](https://user-images.githubusercontent.com/40158577/148128716-ddc59abf-2833-4bdd-9201-a2d78ebba5d5.png)
![seller_flow](https://user-images.githubusercontent.com/40158577/148128731-723b0755-0c34-402e-b702-a48987c8d675.png)



**Functionality & Concepts used :**

**Techstack**

I made **Yourstore** on Android ( Kotlin ) and Firebase for backend and authentication Firestore for database, storing sellers & consumers profile data, Products data in collection. Cloud Storage for storing products image. 

 - **Authentication** is implemented for Login, Signup with firebase function like SigninWithEmailAndPassword() and errors are catched and displayed with proper error.
 - **Navigation :** Users can navigate across activities and fragments.
 -  **Constraint Layout :**  activities in the app uses a flexible constraint layout, which is easy to handle for different screen sizes.
 - **RecyclerView :** List of products queried from firebase is presented in recyclerview efficiently, loading  just the items on focus. Learned using Adapter, view holder, layout manager.
 - **LiveData:** [Glide](https://github.com/bumptech/glide) dependency is used for loading images from cloud store to imageviews.
 -  **Kotlin** Class and Data model used for firestore queries.




**Application Link & Future Scope :**

The application is in active development, some more features are needed to be added.  
Thanx, Google Developer students club, BIT Mesra for the workshops. 

**APK :**   [Try app](https://github.com/mayukhpankaj/Android-Study-Jams/tree/main/APK)

**Demo:**



https://user-images.githubusercontent.com/40158577/148118428-4040a037-0371-4272-92f8-7f68c344e45a.mp4



Learning materials: 

[developer.android.com](https://developer.android.com/courses/android-basics-kotlin/course)

[udacity, Android basics](https://www.udacity.com/course/developing-android-apps-with-kotlin--ud9012)

