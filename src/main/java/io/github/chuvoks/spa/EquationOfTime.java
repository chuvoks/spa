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
 * The Equation of Time, E, is the difference between solar apparent and mean time (in minutes of time).
 * <p>
 * Use {@link #from(SPA)} to obtain an instance.
 */
public class EquationOfTime {

    private final double sunMeanLongitude;
    private final double equationOfTime;

    public EquationOfTime(double M, double E) {
        this.sunMeanLongitude = M;
        this.equationOfTime = E;
    }

    /**
     * Sun’s mean longitude, M, (in degrees)
     */
    public double getSunMeanLongitude() {
        return sunMeanLongitude;
    }

    /**
     * The Equation of Time, E, is the difference between solar apparent and mean time (in minutes of time)
     */
    public double getEquationOfTime() {
        return equationOfTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EquationOfTime that = (EquationOfTime) o;

        if (Double.compare(that.sunMeanLongitude, sunMeanLongitude) != 0) return false;
        return Double.compare(that.equationOfTime, equationOfTime) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(sunMeanLongitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(equationOfTime);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EquationOfTime{");
        sb.append("sunMeanLongitude=").append(sunMeanLongitude);
        sb.append(", equationOfTime=").append(equationOfTime);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Calculate EOT using given SPA.
     */
    public static EquationOfTime from(SPA spa) {
        double M = SP.sunMeanLongitude(spa.getJulianMillennium());
        double E = SP.equationOfTime(M, spa.getGeocentricSunRightAscension(), spa.getNutationInLongitude(), spa.getTrueObliquityOfTheEcliptic());
        return new EquationOfTime(M, E);
    }

}
