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

import static java.lang.Math.abs;
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
        double sunElevation = SP.h0_prime; // degrees
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
                sunElevation);
        return parameters;
    }

    private SPAParameters p = getParameters();
    private SPA spa = SPA.from(p);
    private SunriseTransitSunset sunRts = SunriseTransitSunset.from(p);
    private EquationOfTime eot = EquationOfTime.from(spa);
    private static double ERR_DELTA = Double.MIN_VALUE;

    @Test
    public void julianDay() {
        double expected = 2452930.312847;
        double actual = spa.getJulianDay();
        double actualError = expected - actual;
        double expectedError = -2.2212043404579163E-7;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void earthHeliocentricLongitude() {
        double expected = 24.0182616917;
        double actual = spa.getEarthHeliocentricLongitude();
        double actualError = expected - actual;
        double expectedError = 2.070166260637052E-11;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void earthHeliocentricLatitude() {
        double expected = -0.0001011219;
        double actual = spa.getEarthHeliocentricLatitude();
        double actualError = expected - actual;
        double expectedError = -4.830035839602925E-13;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void earthRadiusVector() {
        double expected = 0.9965422974;
        double actual = spa.getEarthRadiusVector();
        double actualError = expected - actual;
        double expectedError = 4.6029180467144215E-11;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void geocentricLongitude() {
        double expected = 204.0182616917;
        double actual = spa.getGeocentricLongitude();
        double actualError = expected - actual;
        double expectedError = 2.0691004465334117E-11;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void geocentricLatitude() {
        double expected = 0.0001011219;
        double actual = spa.getGeocentricLatitude();
        double actualError = expected - actual;
        double expectedError = 4.830035839602925E-13;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void nutationLongitude() {
        double expected = -0.00399840;
        double actual = spa.getNutationInLongitude();
        double actualError = expected - actual;
        double expectedError = 4.303333011115851E-9;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void nutationObliquity() {
        double expected = 0.00166657;
        double actual = spa.getNutationInObliquity();
        double actualError = expected - actual;
        double expectedError = 1.8227504744276757E-9;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void trueObliquityOfTheEcliptic() {
        double expected = 23.440465;
        double actual = spa.getTrueObliquityOfTheEcliptic();
        double actualError = expected - actual;
        double expectedError = 4.803824751320462E-7;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void apparentSunLongitude() {
        double expected = 204.0085519281;
        double actual = spa.getApparentSunLongitude();
        double actualError = expected - actual;
        double expectedError = 1.7280399333685637E-11;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void geocentricSunRightAscension() {
        double expected = 202.22741;
        double actual = spa.getGeocentricSunRightAscension();
        double actualError = expected - actual;
        double expectedError = 2.172802339828195E-6;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void geocentricSunDeclination() {
        double expected = -9.31434;
        double actual = spa.getGeocentricSunDeclination();
        double actualError = expected - actual;
        double expectedError = 9.087256458428783E-8;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void observerLocalHourAngle() {
        double expected = 11.105900;
        double actual = spa.getObserverLocalHourAngle();
        double actualError = expected - actual;
        double expectedError = -2.0139612413316854E-6;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void topocentricLocalHourAngle() {
        double expected = 11.10629;
        double actual = spa.getTopocentricLocalHourAngle();
        double actualError = expected - actual;
        double expectedError = 1.945105458922569E-5;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void topocentricSunRightAscension() {
        double expected = 202.22704;
        double actual = spa.getTopocentricSunRightAscension();
        double actualError = expected - actual;
        double expectedError = 7.077865120663773E-7;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void topocentricSunDeclination() {
        double expected = -9.316179;
        double actual = spa.getTopocentricSunDeclination();
        double actualError = expected - actual;
        double expectedError = -3.0007232609818857E-7;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void topocentricZenithAngle() {
        double expected = 50.11162;
        double actual = spa.getTopocentricZenithAngle();
        double actualError = expected - actual;
        double expectedError = -2.0242474576548375E-6;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void topocentricAzimuthAngle() {
        double expected = 194.34024;
        double actual = spa.getTopocentricAzimuthAngle();
        double actualError = expected - actual;
        double expectedError = -5.101984470456955E-7;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void incidenceAngleForSurface() {
        double expected = 25.18700;
        double actual = spa.getIncidenceAngleForSurface();
        double actualError = expected - actual;
        double expectedError = -2.0054617877462988E-7;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void sunMeanLongitude() {
        double expected = 205.8971722516;
        double actual = eot.getSunMeanLongitude();
        double actualError = expected - actual;
        double expectedError = 3.609557097661309E-11;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
    }

    @Test
    public void equationOfTime() {
        double expected = 14.641503;
        double actual = eot.getEquationOfTime();
        double actualError = expected - actual;
        double expectedError = -7.770857864741743E-6;
        assertEquals(expected, actual, abs(expectedError));
        assertEquals(expectedError, actualError, ERR_DELTA);
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