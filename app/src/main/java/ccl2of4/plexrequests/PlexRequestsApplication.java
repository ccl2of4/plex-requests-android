package ccl2of4.plexrequests;

import android.app.Application;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;

import ccl2of4.plexrequests.model.request.RequestRepository;

@EApplication
public class PlexRequestsApplication extends Application {

    @Bean
    RequestRepository requestRepository;

}
