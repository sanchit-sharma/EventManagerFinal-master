package com.example.android.qwerty;

import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeConfiguration;

/**
 * Created by Rohan on 4/14/2018.
 */

public class MyWelcomeActivity extends com.stephentuso.welcome.WelcomeActivity {
    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this).defaultBackgroundColor(R.color.colorPrimary)
                .page(new TitlePage(R.drawable.akg,"Welcome to the Event Manager"))
                .page(new BasicPage(R.drawable.event,"Stay Updated","With the details of Ongoing and Upcoming events in our college").background(R.color.welcome))
                .page(new BasicPage(R.drawable.upload,"Post an event","Extend your promotional reach to a larger scale ").background(R.color.colorAccent))
                .swipeToDismiss(true)
                .canSkip(false)
                .backButtonSkips(false)
                .build();
    }
}
