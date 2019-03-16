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
 * Parameters used to calculate {@link SPA}. See {@link SPA#from(SPAParameters)}.
 */
public class SPAParameters extends SunriseTransitSunsetParameters {

    private final double pressure;
    private final double elevation;
    private final double temperature;
    private final double surfaceSlope;
    private final double surfaceAzimuthRotation;

    /**
     * @param timeInMillis           time in milliseconds since unix epoc
     * @param longitude              longitude in decimal degrees. -180.0 to +180.0.
     *                               Negative for East, Positive for West. e.g. -105.1786 == 105°10′42.96" W.
     * @param latitude               latitude in decimal degrees. -90.0 to +90.0.
     *                               Negative for South, Positive for North. e.g. 39.742476 == 39°44′32.914" N.
     * @param pressure               pressure in millibars. e.g. 1013.25 (average sea-level pressure)
     * @param elevation              elevation in meters. e.g. 0.0 (sea-level)
     * @param temperature            temperature in celsius degrees.
     *                               e.g. 14.0 (global average surface temperature estimate)
     * @param surfaceSlope           the slope of the surface measured from the horizontal plane.
     *                               in decimal degrees. -90.0 to +90.0.
     * @param surfaceAzimuthRotation the surface azimuth rotation angle, measured from south to the projection of the
     *                               surface normal on the horizontal plane, positive or negative if oriented west or
     *                               east from south, respectively. -180.0 to +180.0.
     * @param Delta_T                the difference between the Earth rotation time and the Terrestrial Time, in
     *                               seconds. e.g. 64.0 (rough estimate for year 2002).
     * @param sunElevation           Sun elevation angle (in degrees) to calculate the times of sunrise and sunset or
     *                               if atmospheric refraction correction is needed. E.g. {@link SP#h0_prime}.
     */
    public SPAParameters(long timeInMillis,
                         double longitude,
                         double latitude,
                         double pressure,
                         double elevation,
                         double temperature,
                         double surfaceSlope,
                         double surfaceAzimuthRotation,
                         double Delta_T,
                         double sunElevation) {
        super(timeInMillis, longitude, latitude, Delta_T, sunElevation);
        this.pressure = pressure;
        this.elevation = elevation;
        this.temperature = temperature;
        if (surfaceSlope > 90.0)
            throw new IllegalArgumentException("surfaceSlope=" + surfaceSlope + " must be less than or equal to 90 degrees");
        if (surfaceSlope < -90.0)
            throw new IllegalArgumentException("surfaceSlope=" + surfaceSlope + " must be greater than or equal to -90 degrees");
        this.surfaceSlope = surfaceSlope;
        if (surfaceAzimuthRotation > 180.0)
            throw new IllegalArgumentException("surfaceAzimuthRotation=" + surfaceAzimuthRotation + " must be less than or equal to 180 degrees");
        if (surfaceAzimuthRotation < -180.0)
            throw new IllegalArgumentException("surfaceAzimuthRotation=" + surfaceAzimuthRotation + " must be greater than or equal to -180 degrees");
        this.surfaceAzimuthRotation = surfaceAzimuthRotation;
    }

    public double getPressure() {
        return pressure;
    }

    public double getElevation() {
        return elevation;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getSurfaceSlope() {
        return surfaceSlope;
    }

    public double getSurfaceAzimuthRotation() {
        return surfaceAzimuthRotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SPAParameters that = (SPAParameters) o;

        if (Double.compare(that.pressure, pressure) != 0) return false;
        if (Double.compare(that.elevation, elevation) != 0) return false;
        if (Double.compare(that.temperature, temperature) != 0) return false;
        if (Double.compare(that.surfaceSlope, surfaceSlope) != 0) return false;
        return Double.compare(that.surfaceAzimuthRotation, surfaceAzimuthRotation) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(pressure);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(elevation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(temperature);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(surfaceSlope);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(surfaceAzimuthRotation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SPAParameters{");
        sb.append("pressure=").append(pressure);
        sb.append(", elevation=").append(elevation);
        sb.append(", temperature=").append(temperature);
        sb.append(", surfaceSlope=").append(surfaceSlope);
        sb.append(", surfaceAzimuthRotation=").append(surfaceAzimuthRotation);
        sb.append(", timeInMillis=").append(getTimeInMillis());
        sb.append(", longitude=").append(getLongitude());
        sb.append(", latitude=").append(getLatitude());
        sb.append(", delta_T=").append(getDelta_T());
        sb.append(", h0_prime=").append(getSunElevation());
        sb.append('}');
        return sb.toString();
    }
}
