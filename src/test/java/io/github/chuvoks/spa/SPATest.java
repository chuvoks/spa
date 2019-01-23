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


package io.github.chuvoks.spa;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SPATest {

    private SPAParameters getParameters() {
        long timeInMillis = new DateTime("2003-10-17T12:30:30-07:00").getMillis();
        // Coordinates to NREL's Solar Radiation Research Laboratory (SRRL) at Denver, Colorado
        double longitude = -105.1786; // 105°10′42.96" W
        double latitude = 39.742476; // 39°44′32.914" N
        double pressure = 820; // millibars
        double elevation = 1830.14; // meters
        double temperature = 11; // in celsius
        double surfaceSlope = 30; // degrees
        double surfaceAzimuthRotation = -10; // degrees
        double delta_t = 67; // seconds
        double h0_prime = SunDeclination.twilight; // degrees
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
        return parameters;
    }

    private SPAParameters p = getParameters();
    private SPA spa = SPA.from(p);
    private SunriseTransitSunset sunRts = SunriseTransitSunset.from(p);
    private EquationOfTime eot = EquationOfTime.from(spa);

    @Test
    public void julianDay() {
        assertEquals(2452930.312847, spa.getJulianDay(), 2.2212043404579163E-7);
    }

    @Test
    public void earthHeliocentricLongitude() {
        assertEquals(24.0182616917, spa.getEarthHeliocentricLongitude(), 2.070166260637052E-11);
    }

    @Test
    public void earthHeliocentricLatitude() {
        assertEquals(-0.0001011219, spa.getEarthHeliocentricLatitude(), 4.830035839602925E-13);
    }

    @Test
    public void earthRadiusVector() {
        assertEquals(0.9965422974, spa.getEarthRadiusVector(), 4.6029180467144215E-11);
    }

    @Test
    public void geocentricLongitude() {
        assertEquals(204.0182616917, spa.getGeocentricLongitude(), 2.0691004465334117E-11);
    }

    @Test
    public void geocentricLatitude() {
        assertEquals(0.0001011219, spa.getGeocentricLatitude(), 4.830035839602925E-13);
    }

    @Test
    public void nutationLongitude() {
        assertEquals(-0.00399840, spa.getNutationInLongitude(), 4.303333011115851E-9);
    }

    @Test
    public void nutationObliquity() {
        assertEquals(0.00166657, spa.getNutationInObliquity(), 1.8227504744276757E-9);
    }

    @Test
    public void trueObliquityOfTheEcliptic() {
        assertEquals(23.440465, spa.getTrueObliquityOfTheEcliptic(), 4.803824751320462E-7);
    }

    @Test
    public void apparentSunLongitude() {
        assertEquals(204.0085519281, spa.getApparentSunLongitude(), 1.7280399333685637E-11);
    }

    @Test
    public void geocentricSunRightAscension() {
        assertEquals(202.22741, spa.getGeocentricSunRightAscension(), 2.172802339828195E-6);
    }

    @Test
    public void geocentricSunDeclination() {
        assertEquals(-9.31434, spa.getGeocentricSunDeclination(), 9.087256458428783E-8);
    }

    @Test
    public void observerLocalHourAngle() {
        assertEquals(11.105900, spa.getObserverLocalHourAngle(), 2.0139612413316854E-6);
    }

    @Test
    public void topocentricLocalHourAngle() {
        assertEquals(11.10629, spa.getTopocentricLocalHourAngle(), 1.945105458922569E-5);
    }

    @Test
    public void topocentricSunRightAscension() {
        assertEquals(202.22704, spa.getTopocentricSunRightAscension(), 7.077865120663773E-7);
    }

    @Test
    public void topocentricSunDeclination() {
        assertEquals(-9.316179, spa.getTopocentricSunDeclination(), 3.0007232609818857E-7);
    }

    @Test
    public void topocentricZenithAngle() {
        assertEquals(50.11162, spa.getTopocentricZenithAngle(), 2.0242474576548375E-6);
    }

    @Test
    public void topocentricAzimuthAngle() {
        assertEquals(194.34024, spa.getTopocentricAzimuthAngle(), 5.101984470456955E-7);
    }

    @Test
    public void incidenceAngleForSurface() {
        assertEquals(25.18700, spa.getIncidenceAngleForSurface(), 2.0054617877462988E-7);
    }

    @Test
    public void sunMeanLongitude() {
        assertEquals(205.8971722516, eot.getSunMeanLongitude(), 3.609557097661309E-11);
    }

    @Test
    public void equationOfTime() {
        assertEquals(14.641503, eot.getEquationOfTime(), 7.770857864741743E-6);
    }

    @Test
    public void sunTransitTime() {
        assertEquals((double) Instant.parse("2003-10-17T18:46:04.97Z").getMillis(), (double) sunRts.getSunTransitMillis(), 8.0);
    }

    @Test
    public void sunrise() {
        assertEquals((double) Instant.parse("2003-10-17T13:12:43.46Z").getMillis(), (double) sunRts.getSunriseMillis(), 20.0);
    }

    @Test
    public void sunset() {
        assertEquals((double) Instant.parse("2003-10-18T00:20:19.19Z").getMillis(), (double) sunRts.getSunsetMillis(), 9.0);
    }

}