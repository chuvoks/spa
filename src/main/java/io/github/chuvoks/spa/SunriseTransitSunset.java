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
 * Sunrise, sun transit and sunset times.
 * <p>
 * Use {@link #from(SunriseTransitSunsetParameters)} to obtain an instance.
 */
public class SunriseTransitSunset {

    private final long sunriseMillis;
    private final long sunTransitMillis;
    private final long sunsetMillis;

    public SunriseTransitSunset(long sunriseMillis,
                                long sunTransitMillis,
                                long sunsetMillis) {
        this.sunriseMillis = sunriseMillis;
        this.sunTransitMillis = sunTransitMillis;
        this.sunsetMillis = sunsetMillis;
    }

    public long getSunriseMillis() {
        return sunriseMillis;
    }

    public long getSunTransitMillis() {
        return sunTransitMillis;
    }

    public long getSunsetMillis() {
        return sunsetMillis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SunriseTransitSunset that = (SunriseTransitSunset) o;

        if (sunriseMillis != that.sunriseMillis) return false;
        if (sunTransitMillis != that.sunTransitMillis) return false;
        return sunsetMillis == that.sunsetMillis;
    }

    @Override
    public int hashCode() {
        int result = (int) (sunriseMillis ^ (sunriseMillis >>> 32));
        result = 31 * result + (int) (sunTransitMillis ^ (sunTransitMillis >>> 32));
        result = 31 * result + (int) (sunsetMillis ^ (sunsetMillis >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SunriseTransitSunset{");
        sb.append("sunriseMillis=").append(sunriseMillis);
        sb.append(", sunTransitMillis=").append(sunTransitMillis);
        sb.append(", sunsetMillis=").append(sunsetMillis);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Calculate sunrise, transit and sunset using given parameters.
     *
     * @return sunrise, transit and sunset times or null if sun is below or above the horizon (defined by
     * {@link SunriseTransitSunsetParameters#getSunElevation()}) whole day
     */
    public static SunriseTransitSunset from(SunriseTransitSunsetParameters p) {
        double[] sunrts = SP.sunRiseTransitSet(p);
        if (sunrts != null) {
            long sunriseMillis;
            long sunTransitMillis;
            long sunsetMillis;
            double sunriseFraction;
            double sunTransitFraction;
            double sunsetFraction;
            long ut0time = SP.toZeroUT(p.getTimeInMillis());
            sunriseFraction = sunrts[1];
            sunTransitFraction = sunrts[0];
            sunsetFraction = sunrts[2];
            sunriseMillis = ut0time + Math.round(sunriseFraction * SP.dayInMillis);
            if (sunriseFraction >= sunTransitFraction) {
                sunriseMillis -= SP.dayInMillis;
            }
            sunsetMillis = ut0time + Math.round(sunsetFraction * SP.dayInMillis);
            if (sunsetFraction <= sunTransitFraction) {
                sunsetMillis += SP.dayInMillis;
            }
            sunTransitMillis = ut0time + Math.round(sunTransitFraction * SP.dayInMillis);
            return new SunriseTransitSunset(
                    sunriseMillis,
                    sunTransitMillis,
                    sunsetMillis
            );
        } else {
            return null;
        }
    }
}
