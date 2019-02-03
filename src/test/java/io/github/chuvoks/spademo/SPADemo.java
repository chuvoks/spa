/*
 * Copyright (C) 2019 Juha Heljoranta
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.chuvoks.spademo;

import io.github.chuvoks.spa.*;

/**
 * Trivial SPA usage demo
 */
public class SPADemo {

    /**
     * Program prints:
     *
     * zenith:  50.11162202424746 degrees
     * azimuth: 194.34024051019844 degrees
     * surise:  1066396363440 ms
     * transit: 1066416364962 ms
     * sunset:  1066436419199 ms
     * EOT:     14.641510770857865 minutes
     */
    public static void main(String[] args) {
        long timeInMillis = 1066419030000L; // 2003-10-17T19:30:30.000Z
        double longitude = -105.1786; // 105°10′42.96" W
        double latitude = 39.742476; // 39°44′32.914" N
        double pressure = 820; // millibars
        double elevation = 1830.14; // meters
        double temperature = 11; // celsius
        double surfaceSlope = 30; // degrees
        double surfaceAzimuthRotation = -10; // degrees
        double delta_t = 67; // seconds
        double h0_prime = SunDeclination.twilight; // -8.333 degrees
        SPAParameters parameters = new SPAParameters(
                timeInMillis,
                longitude,
                latitude,
                pressure,
                elevation,
                temperature,
                surfaceSlope,
                surfaceAzimuthRotation,
                delta_t,
                h0_prime);
        SPA spa = SPA.from(parameters);
        SunriseTransitSunset sunRts = SunriseTransitSunset.from(parameters);
        EquationOfTime eot = EquationOfTime.from(spa);
        System.out.println("zenith:  " + spa.getTopocentricZenithAngle() + " degrees"); // 50.11162202424746
        System.out.println("azimuth: " + spa.getTopocentricAzimuthAngle() + " degrees"); // 194.34024051019844 (0° to 360°, measured eastward from north)
        System.out.println("sunrise: " + sunRts.getSunriseMillis() + " ms"); // 1066396363440 <==> 2003-10-17T13:12:43.440Z
        System.out.println("transit: " + sunRts.getSunTransitMillis() + " ms"); // 1066416364962 <==> 2003-10-17T18:46:04.962Z
        System.out.println("sunset:  " + sunRts.getSunsetMillis() + " ms"); // 1066436419199 <==> 2003-10-18T00:20:19.199Z
        System.out.println("EOT:     " + eot.getEquationOfTime() + " minutes"); // 14.641510770857865
    }
}
