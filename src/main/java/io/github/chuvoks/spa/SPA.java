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
 * Solar Position Algorithm (SPA).
 * <p>
 * Use {@link #from(SPAParameters)} to obtain an instance.
 *
 * @see SunriseTransitSunset
 * @see EquationOfTime
 */

public class SPA {

    private final double julianDay;
    private final double julianMillennium;
    private final double earthHeliocentricLongitude;
    private final double earthHeliocentricLatitude;
    private final double earthRadiusVector;
    private final double geocentricLongitude;
    private final double geocentricLatitude;
    private final double nutationInLongitude;
    private final double nutationInObliquity;
    private final double trueObliquityOfTheEcliptic;
    private final double apparentSunLongitude;
    private final double geocentricSunRightAscension;
    private final double geocentricSunDeclination;
    private final double observerLocalHourAngle;
    private final double topocentricSunRightAscension;
    private final double topocentricSunDeclination;
    private final double topocentricLocalHourAngle;
    private final double topocentricElevationAngleWithoutAtmosphericRefractionCorrection;
    private final double topocentricElevationAngle;
    private final double topocentricZenithAngle;
    private final double topocentricAstronomersAzimuthAngle;
    private final double topocentricAzimuthAngle;
    private final double incidenceAngleForSurface;

    public SPA(double jd,
               double jme,
               double L,
               double B,
               double R,
               double Theta,
               double beta,
               double Delta_psi,
               double Delta_epsilon,
               double epsilon,
               double lamda,
               double alpha,
               double delta,
               double Eta,
               double alpha_prime,
               double delta_prime,
               double Eta_prime,
               double e0,
               double e,
               double theta,
               double Gamma,
               double Phi,
               double I) {
        this.julianDay = jd;
        this.julianMillennium = jme;
        this.earthHeliocentricLongitude = L;
        this.earthHeliocentricLatitude = B;
        this.earthRadiusVector = R;
        this.geocentricLongitude = Theta;
        this.geocentricLatitude = beta;
        this.nutationInLongitude = Delta_psi;
        this.nutationInObliquity = Delta_epsilon;
        this.trueObliquityOfTheEcliptic = epsilon;
        this.apparentSunLongitude = lamda;
        this.geocentricSunRightAscension = alpha;
        this.geocentricSunDeclination = delta;
        this.observerLocalHourAngle = Eta;
        this.topocentricSunRightAscension = alpha_prime;
        this.topocentricSunDeclination = delta_prime;
        this.topocentricLocalHourAngle = Eta_prime;
        this.topocentricElevationAngleWithoutAtmosphericRefractionCorrection = e0;
        this.topocentricElevationAngle = e;
        this.topocentricZenithAngle = theta;
        this.topocentricAstronomersAzimuthAngle = Gamma;
        this.topocentricAzimuthAngle = Phi;
        this.incidenceAngleForSurface = I;
    }

    /**
     * Julian Day
     */
    public double getJulianDay() {
        return julianDay;
    }

    /**
     * Julian century
     */
    public double getJulianMillennium() {
        return julianMillennium;
    }

    /**
     * Earth heliocentric longitude L (in degrees)
     */
    public double getEarthHeliocentricLongitude() {
        return earthHeliocentricLongitude;
    }

    /**
     * Earth heliocentric latitude B (in degrees)
     */
    public double getEarthHeliocentricLatitude() {
        return earthHeliocentricLatitude;
    }

    /**
     * Earth radius vector R (in Astronomical Units, AU)
     */
    public double getEarthRadiusVector() {
        return earthRadiusVector;
    }

    /**
     * Geocentric longitude Theta (in degrees)
     */
    public double getGeocentricLongitude() {
        return geocentricLongitude;
    }

    /**
     * Geocentric latitude B (in degrees)
     */
    public double getGeocentricLatitude() {
        return geocentricLatitude;
    }

    /**
     * Nutation in longitude, Delta psi (in degrees)
     */
    public double getNutationInLongitude() {
        return nutationInLongitude;
    }

    /**
     * Nutation in obliquity, Delta epsilon (in degrees)
     */
    public double getNutationInObliquity() {
        return nutationInObliquity;
    }

    /**
     * True obliquity of the ecliptic, epsilon (in degrees)
     */
    public double getTrueObliquityOfTheEcliptic() {
        return trueObliquityOfTheEcliptic;
    }

    /**
     * Apparent sun longitude, lamda (in degrees)
     */
    public double getApparentSunLongitude() {
        return apparentSunLongitude;
    }

    /**
     * Geocentric sun right ascension, alpha (in degrees)
     */
    public double getGeocentricSunRightAscension() {
        return geocentricSunRightAscension;
    }

    /**
     * Geocentric sun declination, delta (in degrees)
     */
    public double getGeocentricSunDeclination() {
        return geocentricSunDeclination;
    }

    /**
     * Observer local hour angle, Eta (in degrees)
     */
    public double getObserverLocalHourAngle() {
        return observerLocalHourAngle;
    }

    /**
     * Topocentric sun right ascension alpha_prime (in degrees)
     */
    public double getTopocentricSunRightAscension() {
        return topocentricSunRightAscension;
    }

    /**
     * Topocentric sun declination, delta_prime (in degrees)
     */
    public double getTopocentricSunDeclination() {
        return topocentricSunDeclination;
    }

    /**
     * Topocentric local hour angle, Eta_prime (in degrees)
     */
    public double getTopocentricLocalHourAngle() {
        return topocentricLocalHourAngle;
    }

    /**
     * Topocentric elevation angle without atmospheric refraction correction, e0 (in degrees)
     */
    public double getTopocentricElevationAngleWithoutAtmosphericRefractionCorrection() {
        return topocentricElevationAngleWithoutAtmosphericRefractionCorrection;
    }

    /**
     * Topocentric elevation angle, e (in degrees)
     */
    public double getTopocentricElevationAngle() {
        return topocentricElevationAngle;
    }

    /**
     * Topocentric zenith angle, theta (in degrees)
     */
    public double getTopocentricZenithAngle() {
        return topocentricZenithAngle;
    }

    /**
     * Topocentric astronomers azimuth angle, Gamma (in degrees, 0° to 360°, measured westward from south).
     */
    public double getTopocentricAstronomersAzimuthAngle() {
        return topocentricAstronomersAzimuthAngle;
    }

    /**
     * Topocentric azimuth angle, Phi (in degrees, 0° to 360°, measured eastward from north).
     */
    public double getTopocentricAzimuthAngle() {
        return topocentricAzimuthAngle;
    }

    /**
     * Incidence angle for a surface oriented in any direction, I (in degrees)
     */
    public double getIncidenceAngleForSurface() {
        return incidenceAngleForSurface;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SPA spa = (SPA) o;

        if (Double.compare(spa.julianDay, julianDay) != 0) return false;
        if (Double.compare(spa.julianMillennium, julianMillennium) != 0) return false;
        if (Double.compare(spa.earthHeliocentricLongitude, earthHeliocentricLongitude) != 0) return false;
        if (Double.compare(spa.earthHeliocentricLatitude, earthHeliocentricLatitude) != 0) return false;
        if (Double.compare(spa.earthRadiusVector, earthRadiusVector) != 0) return false;
        if (Double.compare(spa.geocentricLongitude, geocentricLongitude) != 0) return false;
        if (Double.compare(spa.geocentricLatitude, geocentricLatitude) != 0) return false;
        if (Double.compare(spa.nutationInLongitude, nutationInLongitude) != 0) return false;
        if (Double.compare(spa.nutationInObliquity, nutationInObliquity) != 0) return false;
        if (Double.compare(spa.trueObliquityOfTheEcliptic, trueObliquityOfTheEcliptic) != 0) return false;
        if (Double.compare(spa.apparentSunLongitude, apparentSunLongitude) != 0) return false;
        if (Double.compare(spa.geocentricSunRightAscension, geocentricSunRightAscension) != 0) return false;
        if (Double.compare(spa.geocentricSunDeclination, geocentricSunDeclination) != 0) return false;
        if (Double.compare(spa.observerLocalHourAngle, observerLocalHourAngle) != 0) return false;
        if (Double.compare(spa.topocentricSunRightAscension, topocentricSunRightAscension) != 0) return false;
        if (Double.compare(spa.topocentricSunDeclination, topocentricSunDeclination) != 0) return false;
        if (Double.compare(spa.topocentricLocalHourAngle, topocentricLocalHourAngle) != 0) return false;
        if (Double.compare(spa.topocentricElevationAngleWithoutAtmosphericRefractionCorrection, topocentricElevationAngleWithoutAtmosphericRefractionCorrection) != 0)
            return false;
        if (Double.compare(spa.topocentricElevationAngle, topocentricElevationAngle) != 0) return false;
        if (Double.compare(spa.topocentricZenithAngle, topocentricZenithAngle) != 0) return false;
        if (Double.compare(spa.topocentricAstronomersAzimuthAngle, topocentricAstronomersAzimuthAngle) != 0)
            return false;
        if (Double.compare(spa.topocentricAzimuthAngle, topocentricAzimuthAngle) != 0) return false;
        return Double.compare(spa.incidenceAngleForSurface, incidenceAngleForSurface) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(julianDay);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(julianMillennium);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(earthHeliocentricLongitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(earthHeliocentricLatitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(earthRadiusVector);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(geocentricLongitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(geocentricLatitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(nutationInLongitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(nutationInObliquity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(trueObliquityOfTheEcliptic);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(apparentSunLongitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(geocentricSunRightAscension);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(geocentricSunDeclination);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(observerLocalHourAngle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(topocentricSunRightAscension);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(topocentricSunDeclination);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(topocentricLocalHourAngle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(topocentricElevationAngleWithoutAtmosphericRefractionCorrection);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(topocentricElevationAngle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(topocentricZenithAngle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(topocentricAstronomersAzimuthAngle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(topocentricAzimuthAngle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(incidenceAngleForSurface);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SPA{");
        sb.append("julianDay=").append(julianDay);
        sb.append(", julianMillennium=").append(julianMillennium);
        sb.append(", earthHeliocentricLongitude=").append(earthHeliocentricLongitude);
        sb.append(", earthHeliocentricLatitude=").append(earthHeliocentricLatitude);
        sb.append(", earthRadiusVector=").append(earthRadiusVector);
        sb.append(", geocentricLongitude=").append(geocentricLongitude);
        sb.append(", geocentricLatitude=").append(geocentricLatitude);
        sb.append(", nutationInLongitude=").append(nutationInLongitude);
        sb.append(", nutationInObliquity=").append(nutationInObliquity);
        sb.append(", trueObliquityOfTheEcliptic=").append(trueObliquityOfTheEcliptic);
        sb.append(", apparentSunLongitude=").append(apparentSunLongitude);
        sb.append(", geocentricSunRightAscension=").append(geocentricSunRightAscension);
        sb.append(", geocentricSunDeclination=").append(geocentricSunDeclination);
        sb.append(", observerLocalHourAngle=").append(observerLocalHourAngle);
        sb.append(", topocentricSunRightAscension=").append(topocentricSunRightAscension);
        sb.append(", topocentricSunDeclination=").append(topocentricSunDeclination);
        sb.append(", topocentricLocalHourAngle=").append(topocentricLocalHourAngle);
        sb.append(", topocentricElevationAngleWithoutAtmosphericRefractionCorrection=").append(topocentricElevationAngleWithoutAtmosphericRefractionCorrection);
        sb.append(", topocentricElevationAngle=").append(topocentricElevationAngle);
        sb.append(", topocentricZenithAngle=").append(topocentricZenithAngle);
        sb.append(", topocentricAstronomersAzimuthAngle=").append(topocentricAstronomersAzimuthAngle);
        sb.append(", topocentricAzimuthAngle=").append(topocentricAzimuthAngle);
        sb.append(", incidenceAngleForSurface=").append(incidenceAngleForSurface);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Calculate SPA using given parameters.
     */
    public static SPA from(SPAParameters p) {
        double jd = SP.julianDay(p.getTimeInMillis());
        double jde = SP.julianEphemerisDay(jd, p.getDelta_T());
        double jce = SP.julianCentury(jde);
        double jme = SP.julianMillennium(jce);
        double L = SP.earthHeliocentricLongitude(jme);
        double B = SP.earthHeliocentricLatitude(jme);
        double R = SP.earthRadiusVector(jme);
        double Theta = SP.geocentricLongitude(L);
        double beta = SP.geocentricLatitude(B);
        double[] psiEpsilon = SP.nutationLongitudeObliquity(jce);
        double Delta_psi = psiEpsilon[0];
        double Delta_epsilon = psiEpsilon[1];
        double Delta_tau = SP.abberationCorrection(R);
        double epsilon = SP.trueObliquityOfTheEcliptic(jme, Delta_epsilon);
        double lamda = SP.apparentSunLongitude(Theta, Delta_psi, Delta_tau);
        double alpha = SP.geocentricSunRightAscension(lamda, epsilon, beta);
        double delta = SP.geocentricSunDeclination(beta, epsilon, lamda);
        double jc = SP.julianCentury(jd);
        double nu = SP.apparentGreenwichSiderealTime(jd, jc, Delta_psi, epsilon);
        double Eta = SP.observerLocalHourAngle(nu, p.getLongitude(), alpha);
        double xi = SP.equatorialHorizontalParallaxOfTheSun(R);
        double u = SP.termU(p.getLatitude());
        double x = SP.termX(u, p.getElevation(), p.getLatitude());
        double y = SP.termY(u, p.getElevation(), p.getLatitude());
        double Delta_alpha = SP.parallaxInTheSunRightAscension(x, xi, Eta, delta);
        double alpha_prime = SP.topocentricSunRightAscension(alpha, Delta_alpha);
        double delta_prime = SP.topocentricSunDeclination(delta, y, x, xi, Delta_alpha, Eta);
        double Eta_prime = SP.topocentricLocalHourAngle(Eta, Delta_alpha);
        double e0 = SP.topocentricElevationAngleWithoutAtmosphericRefractionCorrection(p.getLatitude(), delta_prime, Eta_prime);
        double delta_e = SP.atmosphericRefractionCorrection(p.getPressure(), p.getTemperature(), e0, p.getH0_prime());
        double e = SP.topocentricElevationAngle(e0, delta_e);
        double theta = SP.topocentricZenithAngle(e);
        double Gamma = SP.topocentricAstronomersAzimuthAngle(Eta_prime, p.getLatitude(), delta_prime);
        double Phi = SP.topocentricAzimuthAngle(Gamma);
        double I = SP.incidenceAngleForSurface(theta, p.getSurfaceSlope(), Gamma, p.getSurfaceAzimuthRotation());
        return new SPA(
                jd,
                jme,
                L,
                B,
                R,
                Theta,
                beta,
                Delta_psi,
                Delta_epsilon,
                epsilon,
                lamda,
                alpha,
                delta,
                Eta,
                alpha_prime,
                delta_prime,
                Eta_prime,
                e0,
                e,
                theta,
                Gamma,
                Phi,
                I);
    }

}
