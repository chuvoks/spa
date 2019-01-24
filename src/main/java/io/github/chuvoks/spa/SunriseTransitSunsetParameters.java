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

/**
 * Parameters to calculate sunrise, sun transit and sunset times.
 * See {@link SunriseTransitSunset#from(SunriseTransitSunsetParameters)}.
 */
public class SunriseTransitSunsetParameters {

    private final long timeInMillis;
    private final double longitude;
    private final double latitude;
    private final double Delta_T;
    private final double h0_prime;

    /**
     * @param timeInMillis time in milliseconds since unix epoc. Notice that the actual calculation is done at 0 UT.
     *                     That is, the values are truncated to UTC midnight. e.g from 2003-10-17T19:30:30.000Z to
     *                     2003-10-17T00:00:00.000Z.
     * @param longitude    longitude in decimal degrees. -180.0 to +180.0.
     *                     Negative for East, Positive for West. e.g. -105.1786 == 105°10′42.96" W.
     * @param latitude     latitude in decimal degrees. -90.0 to +90.0.
     *                     Negative for South, Positive for North. e.g. 39.742476 == 39°44′32.914" N.
     * @param Delta_T      the difference between the Earth rotation time and the Terrestrial Time, in
     *                     seconds. e.g. 64.0 (rough estimate for year 2002).
     * @param h0_prime     sun elevation angle (in degrees) to calculate the times of sunriseMillis and sunsetMillis. E.g.
     *                     {@link SunDeclination#twilight}.
     */
    public SunriseTransitSunsetParameters(long timeInMillis,
                                          double longitude,
                                          double latitude,
                                          double Delta_T,
                                          double h0_prime) {
        this.timeInMillis = timeInMillis;
        if (longitude > 180.0)
            throw new IllegalArgumentException("longitude=" + longitude + " must be less than or equal to 180 degrees");
        if (longitude < -180.0)
            throw new IllegalArgumentException("longitude=" + longitude + " must be greater than or equal to -180 degrees");
        this.longitude = longitude;
        if (latitude > 90.0)
            throw new IllegalArgumentException("latitude=" + latitude + " must be less than or equal to 90 degrees");
        if (latitude < -90.0)
            throw new IllegalArgumentException("latitude=" + latitude + " must be greater than or equal to -90 degrees");
        this.latitude = latitude;
        this.Delta_T = Delta_T;
        this.h0_prime = h0_prime;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getDelta_T() {
        return Delta_T;
    }

    public double getH0_prime() {
        return h0_prime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SunriseTransitSunsetParameters that = (SunriseTransitSunsetParameters) o;

        if (timeInMillis != that.timeInMillis) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.Delta_T, Delta_T) != 0) return false;
        return Double.compare(that.h0_prime, h0_prime) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (timeInMillis ^ (timeInMillis >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(Delta_T);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(h0_prime);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SunriseTransitSunsetParameters{");
        sb.append("timeInMillis=").append(timeInMillis);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", Delta_T=").append(Delta_T);
        sb.append(", h0_prime=").append(h0_prime);
        sb.append('}');
        return sb.toString();
    }

}
