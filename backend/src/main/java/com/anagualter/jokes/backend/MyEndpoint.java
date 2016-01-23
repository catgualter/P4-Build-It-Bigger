/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.anagualter.jokes.backend;

import com.anagualter.JokesLibrary;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.jokes.anagualter.com", ownerName = "backend.jokes.anagualter.com", packagePath = ""))
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "randomJoke")
    public RandomJoke randomJoke() {
        RandomJoke response = new RandomJoke();
        JokesLibrary jokesLibrary = new JokesLibrary();
        response.setData(jokesLibrary.getJoke());

        return response;
    }

}
