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

import static io.github.chuvoks.spa.Math2.*;
import static java.lang.Math.*;

/**
 * Implements equations from <a href="https://midcdmz.nrel.gov/spa/">NREL's Solar Position Algorithm (SPA)</a> paper
 * (Reference: Reda, I.; Andreas, A., Solar Position Algorithm for Solar Radiation Applications, Solar Energy. Vol.
 * 76(5), 2004; pp. 577-589).
 * <p>
 * Date parsing equations are omitted and unix timestamps are used instead.
 * <p>
 * Numbers in parenthesis through out the javadoc and source code refer to equations in the paper.
 */
public final class SP {

    private SP() {
    }

    /**
     * Convert unix time (in milliseconds) to Julian day (JD)
     */
    public static double julianDay(long timeInMillis) {
        return (timeInMillis / 86400000.0) + 2440587.5;
    }

    /**
     * Calculate the Julian Ephemeris Day (JDE) from the Julian Day and delta-time (5)
     *
     * @param jd
     * @param Delta_t
     * @return jde
     */
    public static double julianEphemerisDay(double jd, double Delta_t) {
        return jd + Delta_t / 86400.0;
    }

    /**
     * Calculate the Julian century (JC) from Julian Day (6)
     *
     * @param jd
     * @return jc
     */
    public static double julianCentury(double jd) {
        return jcOrJce(jd);
    }

    /**
     * Calculate the Julian Ephemeris Century (JCE) from Julian Ephemeris Day (7)
     *
     * @param jde
     * @return jce
     */
    public static double julianEphemerisCentury(double jde) {
        return jcOrJce(jde);
    }

    private static double jcOrJce(double x) {
        return (x - 2451545.0) / 36525.0;
    }

    /**
     * Calculate the Julian Millennium from Julian Ephemeris Century (8)
     *
     * @param jc
     * @return jm
     */
    public static double julianMillennium(double jc) {
        return jc / 10.0;
    }

    // Earth Periodic Terms (Table A4.2)
    /**
     * Earth Heliocentric Longitude coefficients (L0, L1, L2, L3, L4, L5)
     */
    private static final double[][][] EHL = {
            {
                    // L0
                    {175347046, 0, 0},
                    {3341656, 4.6692568, 6283.07585},
                    {34894, 4.6261, 12566.1517},
                    {3497, 2.7441, 5753.3849},
                    {3418, 2.8289, 3.5231},
                    {3136, 3.6277, 77713.7715},
                    {2676, 4.4181, 7860.4194},
                    {2343, 6.1352, 3930.2097},
                    {1324, 0.7425, 11506.7698},
                    {1273, 2.0371, 529.691},
                    {1199, 1.1096, 1577.3435},
                    {990, 5.233, 5884.927},
                    {902, 2.045, 26.298},
                    {857, 3.508, 398.149},
                    {780, 1.179, 5223.694},
                    {753, 2.533, 5507.553},
                    {505, 4.583, 18849.228},
                    {492, 4.205, 775.523},
                    {357, 2.92, 0.067},
                    {317, 5.849, 11790.629},
                    {284, 1.899, 796.298},
                    {271, 0.315, 10977.079},
                    {243, 0.345, 5486.778},
                    {206, 4.806, 2544.314},
                    {205, 1.869, 5573.143},
                    {202, 2.458, 6069.777},
                    {156, 0.833, 213.299},
                    {132, 3.411, 2942.463},
                    {126, 1.083, 20.775},
                    {115, 0.645, 0.98},
                    {103, 0.636, 4694.003},
                    {102, 0.976, 15720.839},
                    {102, 4.267, 7.114},
                    {99, 6.21, 2146.17},
                    {98, 0.68, 155.42},
                    {86, 5.98, 161000.69},
                    {85, 1.3, 6275.96},
                    {85, 3.67, 71430.7},
                    {80, 1.81, 17260.15},
                    {79, 3.04, 12036.46},
                    {75, 1.76, 5088.63},
                    {74, 3.5, 3154.69},
                    {74, 4.68, 801.82},
                    {70, 0.83, 9437.76},
                    {62, 3.98, 8827.39},
                    {61, 1.82, 7084.9},
                    {57, 2.78, 6286.6},
                    {56, 4.39, 14143.5},
                    {56, 3.47, 6279.55},
                    {52, 0.19, 12139.55},
                    {52, 1.33, 1748.02},
                    {51, 0.28, 5856.48},
                    {49, 0.49, 1194.45},
                    {41, 5.37, 8429.24},
                    {41, 2.4, 19651.05},
                    {39, 6.17, 10447.39},
                    {37, 6.04, 10213.29},
                    {37, 2.57, 1059.38},
                    {36, 1.71, 2352.87},
                    {36, 1.78, 6812.77},
                    {33, 0.59, 17789.85},
                    {30, 0.44, 83996.85},
                    {30, 2.74, 1349.87},
                    {25, 3.16, 4690.48}
            },
            {
                    // L1
                    {628331966747.0, 0, 0},
                    {206059, 2.678235, 6283.07585},
                    {4303, 2.6351, 12566.1517},
                    {425, 1.59, 3.523},
                    {119, 5.796, 26.298},
                    {109, 2.966, 1577.344},
                    {93, 2.59, 18849.23},
                    {72, 1.14, 529.69},
                    {68, 1.87, 398.15},
                    {67, 4.41, 5507.55},
                    {59, 2.89, 5223.69},
                    {56, 2.17, 155.42},
                    {45, 0.4, 796.3},
                    {36, 0.47, 775.52},
                    {29, 2.65, 7.11},
                    {21, 5.34, 0.98},
                    {19, 1.85, 5486.78},
                    {19, 4.97, 213.3},
                    {17, 2.99, 6275.96},
                    {16, 0.03, 2544.31},
                    {16, 1.43, 2146.17},
                    {15, 1.21, 10977.08},
                    {12, 2.83, 1748.02},
                    {12, 3.26, 5088.63},
                    {12, 5.27, 1194.45},
                    {12, 2.08, 4694},
                    {11, 0.77, 553.57},
                    {10, 1.3, 6286.6},
                    {10, 4.24, 1349.87},
                    {9, 2.7, 242.73},
                    {9, 5.64, 951.72},
                    {8, 5.3, 2352.87},
                    {6, 2.65, 9437.76},
                    {6, 4.67, 4690.48}
            },
            {
                    // L2
                    {52919, 0, 0},
                    {8720, 1.0721, 6283.0758},
                    {309, 0.867, 12566.152},
                    {27, 0.05, 3.52},
                    {16, 5.19, 26.3},
                    {16, 3.68, 155.42},
                    {10, 0.76, 18849.23},
                    {9, 2.06, 77713.77},
                    {7, 0.83, 775.52},
                    {5, 4.66, 1577.34},
                    {4, 1.03, 7.11},
                    {4, 3.44, 5573.14},
                    {3, 5.14, 796.3},
                    {3, 6.05, 5507.55},
                    {3, 1.19, 242.73},
                    {3, 6.12, 529.69},
                    {3, 0.31, 398.15},
                    {3, 2.28, 553.57},
                    {2, 4.38, 5223.69},
                    {2, 3.75, 0.98}
            },
            {
                    // L3
                    {289, 5.844, 6283.076},
                    {35, 0, 0},
                    {17, 5.49, 12566.15},
                    {3, 5.2, 155.42},
                    {1, 4.72, 3.52},
                    {1, 5.3, 18849.23},
                    {1, 5.97, 242.73}
            },
            {
                    // L4
                    {114, 3.142, 0},
                    {8, 4.13, 6283.08},
                    {1, 3.84, 12566.15}
            },
            {
                    // L5
                    {1, 3.14, 0}
            }
    };


    /**
     * Earth Heliocentric Longitude coefficients (B0, B1)
     */
    private static final double[][][] EHB = {
            {
                    // B0
                    {280, 3.199, 84334.662},
                    {102, 5.422, 5507.553},
                    {80, 3.88, 5223.69},
                    {44, 3.7, 2352.87},
                    {32, 4, 1577.34}
            },
            {
                    // B1
                    {9, 3.9, 5507.55},
                    {6, 1.73, 5223.69}
            }
    };

    /**
     * Earth Heliocentric Radius coefficients (R0, R1, R2, R3, R4)
     */
    private static final double[][][] EHR = {
            {
                    // R0
                    {100013989, 0, 0},
                    {1670700, 3.0984635, 6283.07585},
                    {13956, 3.05525, 12566.1517},
                    {3084, 5.1985, 77713.7715},
                    {1628, 1.1739, 5753.3849},
                    {1576, 2.8469, 7860.4194},
                    {925, 5.453, 11506.77},
                    {542, 4.564, 3930.21},
                    {472, 3.661, 5884.927},
                    {346, 0.964, 5507.553},
                    {329, 5.9, 5223.694},
                    {307, 0.299, 5573.143},
                    {243, 4.273, 11790.629},
                    {212, 5.847, 1577.344},
                    {186, 5.022, 10977.079},
                    {175, 3.012, 18849.228},
                    {110, 5.055, 5486.778},
                    {98, 0.89, 6069.78},
                    {86, 5.69, 15720.84},
                    {86, 1.27, 161000.69},
                    {65, 0.27, 17260.15},
                    {63, 0.92, 529.69},
                    {57, 2.01, 83996.85},
                    {56, 5.24, 71430.7},
                    {49, 3.25, 2544.31},
                    {47, 2.58, 775.52},
                    {45, 5.54, 9437.76},
                    {43, 6.01, 6275.96},
                    {39, 5.36, 4694},
                    {38, 2.39, 8827.39},
                    {37, 0.83, 19651.05},
                    {37, 4.9, 12139.55},
                    {36, 1.67, 12036.46},
                    {35, 1.84, 2942.46},
                    {33, 0.24, 7084.9},
                    {32, 0.18, 5088.63},
                    {32, 1.78, 398.15},
                    {28, 1.21, 6286.6},
                    {28, 1.9, 6279.55},
                    {26, 4.59, 10447.39}
            },
            {
                    // R1
                    {103019, 1.10749, 6283.07585},
                    {1721, 1.0644, 12566.1517},
                    {702, 3.142, 0},
                    {32, 1.02, 18849.23},
                    {31, 2.84, 5507.55},
                    {25, 1.32, 5223.69},
                    {18, 1.42, 1577.34},
                    {10, 5.91, 10977.08},
                    {9, 1.42, 6275.96},
                    {9, 0.27, 5486.78}
            },
            {
                    // R2
                    {4359, 5.7846, 6283.0758},
                    {124, 5.579, 12566.152},
                    {12, 3.14, 0},
                    {9, 3.63, 77713.77},
                    {6, 1.87, 5573.14},
                    {3, 5.47, 18849.23}
            },
            {
                    // R3
                    {145, 4.273, 6283.076},
                    {7, 3.92, 12566.15}
            },
            {
                    // R4
                    {4, 2.56, 6283.08}
            }
    };

    /**
     * Limit degrees to the range from 0° to 360°
     */
    private static double limitDegrees(double degrees) {
        degrees %= 360.0;
        if (degrees < 0.0) degrees += 360;
        return degrees;
    }

    /**
     * Limit degrees to the range from 0° to 180°
     */
    private static double limitDegrees180(double degrees) {
        degrees %= 180.0;
        if (degrees < 0.0) degrees += 180;
        return degrees;
    }

    /**
     * Limit value to the range from 0 to 1
     */
    public static double limitZeroToOne(double value) {
        value %= 1.0;
        if (value < 0.0) value += 1;
        return value;
    }

    /**
     * Limit degrees to the range from -180° to 180°
     */
    public static double limitDegreesPm180(double degrees) {
        degrees %= 360.0;
        if (degrees < -180.0) degrees += 360.0;
        if (degrees > 180.0) degrees -= 360.0;
        return degrees;
    }

    // Calculate terms L1, L2, L3, L4 and L5 from the Julian Ephemeris Millennium
    private static double[] heliocentricTerms(double[][][] coefficients, double jme) {
        double Li[] = new double[coefficients.length];
        int lastIdx = coefficients.length - 1;
        for (int i = lastIdx; i >= 0; i--) {
            double sum = 0.0;
            double[][] abcs = coefficients[i];
            for (int j = 0; j < abcs.length; j++) {
                double a = abcs[j][0];
                double b = abcs[j][1];
                double c = abcs[j][2];
                sum += a * cos(b + c * jme); // (9)
            }
            Li[lastIdx - i] = sum;
        }
        return Li;
    }

    /**
     * Calculate the Earth Heliocentric Longitude (L) in degrees from JME
     *
     * @param jme
     * @return L
     */
    public static double earthHeliocentricLongitude(double jme) {
        double Li[] = heliocentricTerms(EHL, jme);
        double L = polyval(Li, jme) / 1e8; // (11)
        L = limitDegrees(rad2deg(L));
        return L;
    }

    /**
     * Calculate the Earth Heliocentric Latitude (B) in degrees from JME
     *
     * @param jme
     * @return B
     */
    public static double earthHeliocentricLatitude(double jme) {
        double[] Bi = heliocentricTerms(EHB, jme);
        double B = polyval(Bi, jme) / 1e8; // (11)
        B = rad2deg(B);
        return B;
    }

    /**
     * Calculate the Earth radius vector, R (in Astronomical Units, AU) from JME
     *
     * @param jme
     * @return R
     */
    public static double earthRadiusVector(double jme) {
        double[] Ri = heliocentricTerms(EHR, jme);
        double R = polyval(Ri, jme) / 1e8; // (11)
        return R;
    }

    /**
     * Calculate the geocentric longitude, Theta (in degrees) (13)
     *
     * @param L
     * @return Theta
     */
    public static double geocentricLongitude(double L) {
        double Theta = limitDegrees(L + 180.0);
        return Theta;
    }

    /**
     * Calculate the geocentric latitude, beta (in degrees) (14)
     *
     * @param B return beta
     * @return beta
     */
    public static double geocentricLatitude(double B) {
        double beta = -B;
        return beta;
    }

    /**
     * Nutation longitude and obliquity coefficients (Y).
     * Coefficients for Sin terms.
     */
    private static final double[][] NLOY = {
            {0, 0, 0, 0, 1},
            {-2, 0, 0, 2, 2},
            {0, 0, 0, 2, 2},
            {0, 0, 0, 0, 2},
            {0, 1, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {-2, 1, 0, 2, 2},
            {0, 0, 0, 2, 1},
            {0, 0, 1, 2, 2},
            {-2, -1, 0, 2, 2},
            {-2, 0, 1, 0, 0},
            {-2, 0, 0, 2, 1},
            {0, 0, -1, 2, 2},
            {2, 0, 0, 0, 0},
            {0, 0, 1, 0, 1},
            {2, 0, -1, 2, 2},
            {0, 0, -1, 0, 1},
            {0, 0, 1, 2, 1},
            {-2, 0, 2, 0, 0},
            {0, 0, -2, 2, 1},
            {2, 0, 0, 2, 2},
            {0, 0, 2, 2, 2},
            {0, 0, 2, 0, 0},
            {-2, 0, 1, 2, 2},
            {0, 0, 0, 2, 0},
            {-2, 0, 0, 2, 0},
            {0, 0, -1, 2, 1},
            {0, 2, 0, 0, 0},
            {2, 0, -1, 0, 1},
            {-2, 2, 0, 2, 2},
            {0, 1, 0, 0, 1},
            {-2, 0, 1, 0, 1},
            {0, -1, 0, 0, 1},
            {0, 0, 2, -2, 0},
            {2, 0, -1, 2, 1},
            {2, 0, 1, 2, 2},
            {0, 1, 0, 2, 2},
            {-2, 1, 1, 0, 0},
            {0, -1, 0, 2, 2},
            {2, 0, 0, 2, 1},
            {2, 0, 1, 0, 0},
            {-2, 0, 2, 2, 2},
            {-2, 0, 1, 2, 1},
            {2, 0, -2, 0, 1},
            {2, 0, 0, 0, 1},
            {0, -1, 1, 0, 0},
            {-2, -1, 0, 2, 1},
            {-2, 0, 0, 0, 1},
            {0, 0, 2, 2, 1},
            {-2, 0, 2, 0, 1},
            {-2, 1, 0, 2, 1},
            {0, 0, 1, -2, 0},
            {-1, 0, 1, 0, 0},
            {-2, 1, 0, 0, 0},
            {1, 0, 0, 0, 0},
            {0, 0, 1, 2, 0},
            {0, 0, -2, 2, 2},
            {-1, -1, 1, 0, 0},
            {0, 1, 1, 0, 0},
            {0, -1, 1, 2, 2},
            {2, -1, -1, 2, 2},
            {0, 0, 3, 2, 2},
            {2, -1, 0, 2, 2}
    };

    /**
     * Nutation Longitude and Obliquity coefficients (a,b)
     */
    private static final double[][] NLOab = {
            {-171996, -174.2},
            {-13187, -1.6},
            {-2274, -0.2},
            {2062, 0.2},
            {1426, -3.4},
            {712, 0.1},
            {-517, 1.2},
            {-386, -0.4},
            {-301, 0},
            {217, -0.5},
            {-158, 0},
            {129, 0.1},
            {123, 0},
            {63, 0},
            {63, 0.1},
            {-59, 0},
            {-58, -0.1},
            {-51, 0},
            {48, 0},
            {46, 0},
            {-38, 0},
            {-31, 0},
            {29, 0},
            {29, 0},
            {26, 0},
            {-22, 0},
            {21, 0},
            {17, -0.1},
            {16, 0},
            {-16, 0.1},
            {-15, 0},
            {-13, 0},
            {-12, 0},
            {11, 0},
            {-10, 0},
            {-8, 0},
            {7, 0},
            {-7, 0},
            {-7, 0},
            {-7, 0},
            {6, 0},
            {6, 0},
            {6, 0},
            {-6, 0},
            {-6, 0},
            {5, 0},
            {-5, 0},
            {-5, 0},
            {-5, 0},
            {4, 0},
            {4, 0},
            {4, 0},
            {-4, 0},
            {-4, 0},
            {-4, 0},
            {3, 0},
            {-3, 0},
            {-3, 0},
            {-3, 0},
            {-3, 0},
            {-3, 0},
            {-3, 0},
            {-3, 0}
    };

    /**
     * Nutation longitude and obliquity coefficients (c,d)
     */
    private static final double[][] NLOcd = {
            {92025, 8.9},
            {5736, -3.1},
            {977, -0.5},
            {-895, 0.5},
            {54, -0.1},
            {-7, 0},
            {224, -0.6},
            {200, 0},
            {129, -0.1},
            {-95, 0.3},
            {0, 0},
            {-70, 0},
            {-53, 0},
            {0, 0},
            {-33, 0},
            {26, 0},
            {32, 0},
            {27, 0},
            {0, 0},
            {-24, 0},
            {16, 0},
            {13, 0},
            {0, 0},
            {-12, 0},
            {0, 0},
            {0, 0},
            {-10, 0},
            {0, 0},
            {-8, 0},
            {7, 0},
            {9, 0},
            {7, 0},
            {6, 0},
            {0, 0},
            {5, 0},
            {3, 0},
            {-3, 0},
            {0, 0},
            {3, 0},
            {3, 0},
            {0, 0},
            {-3, 0},
            {-3, 0},
            {3, 0},
            {3, 0},
            {0, 0},
            {3, 0},
            {3, 0},
            {3, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0}
    };

    // mean elongation of the moon from the sun, polynomial constants.
    private static final double[] MEM = {1.0 / 189474, -0.0019142, 445267.111480, 297.85036};

    // mean anomaly of the sun, polynomial constants.
    private static final double[] MAS = {-1.0 / 3e5, -0.0001603, 35999.050340, 357.52772};

    // mean anomaly of the moon, polynomial constants.
    private static final double[] MAM = {1.0 / 56250, 0.0086972, 477198.867398, 134.96298};

    // moon’s argument of latitude, polynomial constants.
    private static final double[] MAL = {1.0 / 327270, -0.0036825, 483202.017538, 93.27191};

    // longitude of the ascending node of the moon’s mean orbit on the
    // ecliptic, measured from the mean equinox of the date, polynomial constants.
    private static final double[] LAN = {1.0 / 45e4, 0.0020708, -1934.136261, 125.04452};

    /**
     * Calculate the nutation in longitude, Delta_psi (in degrees), and nutation in obliquity, Delta_epsilon (in
     * degrees). (15, 16, 17, 18, 19, 20. 21, 22, 23)
     *
     * @param jce
     * @return Delta_psi, Delta_epsilon
     */
    public static double[] nutationLongitudeObliquity(double jce) {

        double[] xDotY = nutationLongitudeObliquityXdotY(jce);
        double Delta_psi = nutationLongitude(jce, xDotY);
        double Delta_epsilon = nutationObliquity(jce, xDotY);

        return new double[]{Delta_psi, Delta_epsilon};
    }

    /**
     * Nutation in longitude and obliquity (15, 16, 17, 18, 19, and X dot Y part for 20 and 21)
     * <p>
     * Calculate X dot Y, in radians
     *
     * @param jce
     * @return X dot Y
     */
    public static double[] nutationLongitudeObliquityXdotY(double jce) {

        // Calculate the mean elongation of the moon from the sun, X0 (15)
        double x0 = polyval(MEM, jce);
        // Calculate the mean anomaly of the sun (Earth), X1 (16)
        double x1 = polyval(MAS, jce);
        // Calculate the mean anomaly of the moon, X2 (in degrees), X2 (17)
        double x2 = polyval(MAM, jce);
        // Calculate the moon’s argument of latitude, X3 (18)
        double x3 = polyval(MAL, jce);
        // Calculate the longitude of the ascending node of the moon’s mean orbit on the
        // ecliptic, measured from the mean equinox of the date, X4 (19)
        double x4 = polyval(LAN, jce);

        double[] x = new double[]{x0, x1, x2, x3, x4};
        double[] dotXy = new double[NLOY.length];
        for (int i = 0; i < NLOY.length; i++) {
            double[] y = NLOY[i];
            dotXy[i] = deg2rad(dot(x, y));
        }

        return dotXy;
    }

    /**
     * Calculate the nutation in longitude, Delta_psi (in degrees) (20, 22)
     *
     * @param jce
     * @param xDotY calculated in {@link #nutationLongitudeObliquityXdotY(double)}
     * @return Delta_psi
     */
    public static double nutationLongitude(double jce, double[] xDotY) {
        double Delta_psi = 0.0;
        for (int i = 0; i < NLOY.length; i++) {
            double a = NLOab[i][0];
            double b = NLOab[i][1];
            Delta_psi += (a + b * jce) * sin(xDotY[i]); // (20)
        }

        // Calculate the nutation in longitude
        Delta_psi = Delta_psi / 36e6; // (22)

        return Delta_psi;
    }

    /**
     * Calculate the nutation in obliquity, Delta_epsilon (in degrees) (21, 23)
     *
     * @param jce
     * @param xDotY calculated in {@link #nutationLongitudeObliquityXdotY(double)}
     * @return Delta_epsilon
     */
    public static double nutationObliquity(double jce, double[] xDotY) {
        double Delta_epsilon = 0.0;
        for (int i = 0; i < NLOY.length; i++) {
            double c = NLOcd[i][0];
            double d = NLOcd[i][1];
            Delta_epsilon += (c + d * jce) * cos(xDotY[i]); // (21)
        }
        // Calculate the nutation in obliquity
        Delta_epsilon = Delta_epsilon / 36e6; // (23)

        return Delta_epsilon;
    }

    /**
     * Mean obliquity of the ecliptic coefficients
     */
    private static final double[] MEO = {2.45, 5.79, 27.87, 7.12, -39.05, -249.67, -51.38, 1999.25, -1.55, -4680.93, 84381.448};

    /**
     * Calculate the true obliquity of the ecliptic, epsilon (in degrees), given the Julian Ephemeris Millennium and the obliquity (24, 25)
     *
     * @param jme
     * @param Delta_epsilon
     * @return epsilon
     */
    public static double trueObliquityOfTheEcliptic(double jme, double Delta_epsilon) {
        double u = jme / 10;
        double e0 = polyval(MEO, u);
        double epsilon = e0 / 3600 + Delta_epsilon;
        return epsilon;
    }

    /**
     * Calculate the abberation correction (Delta_tau, in degrees) given the Earth Heliocentric Radius (in AU) (26)
     *
     * @param R
     * @return Delta_tau
     */
    public static double abberationCorrection(double R) {
        double Delta_tau = -20.4898 / (3600 * R);
        return Delta_tau;
    }

    /**
     * Calculate the apparent sun longitude (lamda, in degrees) (27)
     *
     * @param Theta
     * @param delta_psi
     * @param Delta_tau
     * @return lamda
     */
    public static double apparentSunLongitude(double Theta, double delta_psi, double Delta_tau) {
        double labda = Theta + delta_psi + Delta_tau;
        return labda;
    }

    /**
     * Calculate the apparent Greenwich sidereal time (nu, in degrees) given the Julian Day (28, 29)
     *
     * @param jd
     * @param jc
     * @param delta_psi
     * @param epsilon
     * @return nu
     */
    public static double apparentGreenwichSiderealTime(double jd, double jc, double delta_psi, double epsilon) {
        double nu0 = limitDegrees(280.46061837 + 360.98564736629 * (jd - 2451545) + 0.000387933 * (jc * jc) - (jc * jc * jc) / 38710000.0);
        double nu = nu0 + delta_psi * cos(deg2rad(epsilon));
        return nu;
    }

    /**
     * Calculate the geocentric sun right ascension, alpha (in degrees) (30)
     *
     * @param lamda
     * @param epsilon
     * @param beta
     * @return alpha
     */
    public static double geocentricSunRightAscension(double lamda, double epsilon, double beta) {
        double l = deg2rad(lamda);
        double e = deg2rad(epsilon);
        double alpha = limitDegrees(rad2deg(atan2(sin(l) * cos(e) - tan(deg2rad(beta)) * sin(e), cos(l))));
        return alpha;
    }

    /**
     * Calculate the geocentric sun declination, delta (in degrees) (31)
     *
     * @param beta
     * @param epsilon
     * @param lamda
     * @return delta
     */
    public static double geocentricSunDeclination(double beta, double epsilon, double lamda) {
        double b = deg2rad(beta);
        double e = deg2rad(epsilon);
        double l = deg2rad(lamda);
        double delta = rad2deg(asin(sin(b) * cos(e) + cos(b) * sin(e) * sin(l)));
        return delta;
    }

    /**
     * Calculate the observer local hour angle, Eta (in degrees) (32)
     *
     * @param nu
     * @param longitude
     * @param alpha
     * @return Eta
     */
    public static double observerLocalHourAngle(double nu, double longitude, double alpha) {
        double Eta = limitDegrees(nu + longitude - alpha);
        return Eta;
    }

    /**
     * Calculate the equatorial horizontal parallax of the sun (in degrees) (33)
     *
     * @param R
     * @return xi
     */
    public static double equatorialHorizontalParallaxOfTheSun(double R) {
        double xi = 8.794 / (3600 * R);
        return xi;
    }

    /**
     * Calculate the term u (in radians) (34)
     *
     * @param latitude
     * @return u
     */
    public static double termU(double latitude) {
        double phi = latitude;
        double u = atan(0.99664719 * tan(deg2rad(phi)));
        ;
        return u;
    }

    /**
     * Calculate the term x (35)
     *
     * @param u
     * @param elevation
     * @param latitude
     * @return x
     */
    public static double termX(double u, double elevation, double latitude) {
        double E = elevation;
        double phi = latitude;
        double x = cos(u) + E * cos(deg2rad(phi)) / 6378140.0;
        return x;
    }

    /**
     * Calculate the term y (36)
     *
     * @param u
     * @param elevation
     * @param latitude
     * @return y
     */
    public static double termY(double u, double elevation, double latitude) {
        double E = elevation;
        double phi = latitude;
        double y = 0.99664719 * sin(u) + E * sin(deg2rad(phi)) / 6378140.0;
        return y;
    }

    /**
     * Calculate the parallax in the sun right ascension, Delta_alpha (in degrees) (37)
     *
     * @param x
     * @param xi
     * @param Eta
     * @param delta
     * @return Delta_alpha
     */
    public static double parallaxInTheSunRightAscension(double x, double xi, double Eta, double delta) {
        double Delta_alpha = rad2deg(atan2(-x * sin(deg2rad(xi)) * sin(deg2rad(Eta)), cos(deg2rad(delta)) - x * sin(deg2rad(xi)) * cos(deg2rad(Eta))));
        return Delta_alpha;
    }

    /**
     * Calculate the topocentric sun right ascension alpha_prime (in degrees) (38)
     *
     * @param alpha
     * @param Delta_alpha
     * @return alpha_prime
     */
    public static double topocentricSunRightAscension(double alpha, double Delta_alpha) {
        double alpha_prime = alpha + Delta_alpha;
        return alpha_prime;
    }

    /**
     * Calculate the topocentric sun declination, delta_prime (in degrees) (39)
     *
     * @param delta
     * @param y
     * @param x
     * @param xi
     * @param Delta_alpha
     * @param Eta
     * @return delta_prime
     */
    public static double topocentricSunDeclination(double delta, double y, double x, double xi, double Delta_alpha, double Eta) {
        double delta_prime = rad2deg(atan2(
                sin(deg2rad(delta)) - y * sin(deg2rad(xi)) * cos(deg2rad(Delta_alpha)),
                cos(deg2rad(delta)) - x * sin(deg2rad(xi)) * cos(deg2rad(Eta))));
        return delta_prime;
    }


    /**
     * Calculate the topocentric local hour angle, Eta_prime (in degrees) (40)
     *
     * @param Eta
     * @param Delta_alpha
     * @return Eta_prime
     */
    public static double topocentricLocalHourAngle(double Eta, double Delta_alpha) {
        double Eta_prime = Eta - Delta_alpha;
        return Eta_prime;
    }

    /**
     * Calculate the topocentric elevation angle without atmospheric refraction correction, e0 (in degrees) (41)
     *
     * @param latitude
     * @param Delta_prime
     * @param Eta_prime
     * @return e0
     */
    public static double topocentricElevationAngleWithoutAtmosphericRefractionCorrection(double latitude, double Delta_prime, double Eta_prime) {
        double e0 = rad2deg(asin(sin(deg2rad(latitude)) * sin(deg2rad(Delta_prime)) +
                cos(deg2rad(latitude)) * cos(deg2rad(Delta_prime)) * cos(deg2rad(Eta_prime))));
        return e0;
    }

    /**
     * Calculate the atmospheric refraction correction, Delta_e (in degrees) (42)
     *
     * @param pressure
     * @param temperature
     * @param e0
     * @param h0_prime
     * @return Delta_e
     */
    public static double atmosphericRefractionCorrection(double pressure, double temperature, double e0, double h0_prime) {
        double P = pressure;
        double T = temperature;
        double Delta_e = 0.0;
        if (e0 >= h0_prime) {
            double e0_prime = deg2rad(e0 + 10.3 / (e0 + 5.11));
            Delta_e = (P / 1010.0) * (283.0 / (273 + T)) * (1.02 / (60 * tan(e0_prime)));

        }
        return Delta_e;
    }

    /**
     * Calculate the topocentric elevation angle, e (in degrees) (43)
     *
     * @param e0
     * @param Delta_e
     * @return e
     */
    public static double topocentricElevationAngle(double e0, double Delta_e) {
        double e = e0 + Delta_e;
        return e;
    }

    /**
     * Calculate the topocentric zenith angle, theta (in degrees) (44)
     *
     * @param e
     * @return theta
     */
    public static double topocentricZenithAngle(double e) {
        double theta = 90.0 - e;
        return theta;
    }


    /**
     * Calculate the topocentric astronomers azimuth angle, Gamma (in degrees) (45)
     *
     * @param Eta_prime
     * @param latitude
     * @param delta_prime
     * @return Gamma
     */
    public static double topocentricAstronomersAzimuthAngle(double Eta_prime, double latitude, double delta_prime) {
        double phi = deg2rad(latitude);
        double Gamma = limitDegrees(rad2deg(atan2(sin(deg2rad(Eta_prime)), cos(deg2rad(Eta_prime)) * sin(phi) - tan(deg2rad(delta_prime)) * cos(phi))));
        return Gamma;
    }

    /**
     * Calculate the topocentric azimuth angle (in degrees) (46)
     *
     * @param Gamma
     * @return Phi
     */
    public static double topocentricAzimuthAngle(double Gamma) {
        double Phi = limitDegrees(Gamma + 180);
        return Phi;
    }

    /**
     * Calculate the incidence angle for a surface oriented in any direction, I (in degrees) (47)
     *
     * @param theta
     * @param surfaceSlope
     * @param Gamma
     * @param surfaceAzimuthRotation
     * @return I
     */
    public static double incidenceAngleForSurface(double theta, double surfaceSlope, double Gamma, double surfaceAzimuthRotation) {
        double omega = surfaceSlope;
        double gamma = surfaceAzimuthRotation;
        double I = rad2deg(acos(cos(deg2rad(theta)) * cos(deg2rad(omega)) + sin(deg2rad(omega)) * sin(deg2rad(theta)) * cos(deg2rad(Gamma - gamma))));
        return I;
    }

    // sun’s mean longitude, polynomial constants.
    private static double[] SML = {-1.0 / 2000000, -1.0 / 15300, 1.0 / 49931, 0.03032028, 360007.6982779, 280.4664567};

    /**
     * Calculate sun’s mean longitude (in degrees) (A2)
     *
     * @param jme
     * @return M
     */
    public static double sunMeanLongitude(double jme) {
        double M = limitDegrees(polyval(SML, jme));
        return M;
    }

    /**
     * The Equation of Time, E, is the difference between solar apparent and mean time. Calculate E (in minutes of time). (A1)
     *
     * @param M
     * @param alpha
     * @param Delta_psi
     * @param epsilon
     * @return E
     */
    public static double equationOfTime(double M, double alpha, double Delta_psi, double epsilon) {
        double E = 4 * (M - 0.0057183 - alpha + Delta_psi * cos(deg2rad(epsilon)));
        if (E > 20) {
            E -= 1440;
        } else if (E < -20) {
            E += 1440;
        }
        return E;
    }

    /**
     * Calculate the approximate sun transit time, m0, in fraction of day. (A3)
     *
     * @param alpha0
     * @param longitude
     * @param nu
     * @return m0
     */
    public static double approximateSunTransitTime(double alpha0, double longitude, double nu) {
        double sigma = longitude;
        double m0 = (alpha0 - longitude - nu) / 360.0;
        return m0;
    }

    /**
     * The value of 0.5667° is typically adopted for the atmospheric refraction at sunrise and sunset times.
     */
    public static double atmosphericRefractionAtSunriseAndSunsetTimes = 0.5667;

    /**
     * The sun radius of 0.26667°
     */
    public static double sunRadius = 0.26667;

    /**
     * The value -0.8333° of sun elevation is chosen to calculate the times of sunrise and sunset.
     */
    public static double h0_prime = -sunRadius - atmosphericRefractionAtSunriseAndSunsetTimes;

    /**
     * Calculate the local hour angle corresponding to the sun elevation equals -0.8333, H0 (in degrees). (A4)
     *
     * @param latitude
     * @param delta0
     * @param h0_prime
     * @return H0 or -1 if sun is always above or below the horizon for that day.
     */
    public static double localHourAngleAtSunriseSunset(double latitude, double delta0, double h0_prime) {
        double H0 = -1;
        double aux = (sin(deg2rad(h0_prime)) - sin(deg2rad(latitude)) * sin(deg2rad(delta0))) / (cos(deg2rad(latitude)) * cos(deg2rad(delta0)));
        if (aux >= -1 && aux <= 1) {
            H0 = limitDegrees180(rad2deg(acos(aux)));
        }
        return H0;
    }

    /**
     * Calculate the sidereal time at Greenwich, in degrees (A7)
     *
     * @param nu
     * @param m_i
     * @return nu_i
     */
    public static double siderealTimeAtGreenwich(double nu, double m_i) {
        double nu_i = nu + 360.985647 * m_i;
        return nu_i;
    }

    /**
     * Calculate the sun altitude, h (in degrees) (A12)
     *
     * @param latitude
     * @param delta_prime
     * @param H_prime
     * @return h
     */
    public static double sunAltitude(double latitude, double delta_prime, double H_prime) {
        double phi = latitude;
        double h = rad2deg(asin(sin(deg2rad(phi)) * sin(deg2rad(delta_prime)) + cos(deg2rad(phi)) * cos(deg2rad(delta_prime)) * cos(deg2rad(H_prime))));
        return h;
    }

    /**
     * Calculate the sunrise R or sunset S, (in fraction of day)
     */
    private static double sunriseSunset(double mx, double hx, double delta_prime_x, double latitude, double H_prime_x, double h0_prime) {
        double phi = latitude;
        double X = mx + (hx - h0_prime) / (360 * cos(deg2rad(delta_prime_x)) * cos(deg2rad(phi)) * sin(deg2rad(H_prime_x)));
        return X;
    }

    /**
     * Calculate the sunrise R, (in fraction of day)
     *
     * @param m1
     * @param h1
     * @param delta_prime_1
     * @param latitude
     * @param H_prime_1
     * @return R
     */
    public static double sunrise(double m1, double h1, double delta_prime_1, double latitude, double H_prime_1, double h0_prime) {
        return sunriseSunset(m1, h1, delta_prime_1, latitude, H_prime_1, h0_prime);
    }

    /**
     * Calculate the sunset R, (in fraction of day)
     *
     * @param m2
     * @param h2
     * @param delta_prime_2
     * @param latitude
     * @param H_prime_2
     * @return R
     */
    public static double sunset(double m2, double h2, double delta_prime_2, double latitude, double H_prime_2, double h0_prime) {
        return sunriseSunset(m2, h2, delta_prime_2, latitude, H_prime_2, h0_prime);
    }

    public static long dayInMillis = 24 * 60 * 60 * 1000;

    /**
     * Return time at 0 UT (midnight)
     *
     * @param timeInMillis
     * @return
     */
    public static long toZeroUT(long timeInMillis) {
        long mod = timeInMillis % dayInMillis;
        if (mod != 0) {
            return timeInMillis - mod;
        }
        return timeInMillis;
    }

    /**
     * Calculate the apparent sidereal time at Greenwich at 0 UT, nu (in degrees)
     *
     * @return nu
     */
    public static double apparentSiderealTimeAtGreenwich(long timeInMillis, double Delta_t) {
        double jd = julianDay(timeInMillis);
        double jde = julianEphemerisDay(jd, Delta_t);
        double jce = julianCentury(jde);
        double jme = julianMillennium(jce);
        double[] psiEpsilon = nutationLongitudeObliquity(jce);
        double Delta_psi = psiEpsilon[0];
        double Delta_epsilon = psiEpsilon[1];
        double epsilon = trueObliquityOfTheEcliptic(jme, Delta_epsilon);
        double jc = julianCentury(jd);
        double nu = apparentGreenwichSiderealTime(jd, jc, Delta_psi, epsilon);
        return nu;
    }

    /**
     * Calculate the geocentric right ascension and declination
     *
     * @return alpha, delta
     */
    public static double[] geocentricRightAscensionAndDeclination(long timeInMillis, double Delta_t) {
        double jd = julianDay(timeInMillis);
        double jde = julianEphemerisDay(jd, Delta_t);
        double jce = julianCentury(jde);
        double jme = julianMillennium(jce);
        double L = earthHeliocentricLongitude(jme);
        double B = earthHeliocentricLatitude(jme);
        double R = earthRadiusVector(jme);
        double Theta = geocentricLongitude(L);
        double beta = geocentricLatitude(B);
        double[] psiEpsilon = nutationLongitudeObliquity(jce);
        double Delta_psi = psiEpsilon[0];
        double Delta_epsilon = psiEpsilon[1];
        double Delta_tau = abberationCorrection(R);
        double epsilon = trueObliquityOfTheEcliptic(jme, Delta_epsilon);
        double lamda = apparentSunLongitude(Theta, Delta_psi, Delta_tau);
        double alpha = geocentricSunRightAscension(lamda, epsilon, beta);
        double delta = geocentricSunDeclination(beta, epsilon, lamda);
        return new double[]{alpha, delta};
    }

    /**
     * Calculate sunrise, sun transit, and sunset times (in fraction of day)
     *
     * @param in
     * @return new double[]{suntransit, sunrise, sunset} or null if sun is below or above the horizon whole day
     */
    public static double[] sunRiseTransitSet(SunriseTransitSunsetParameters in) {
        long ut0time = toZeroUT(in.getTimeInMillis());
        double Delta_t = in.getDelta_T();
        // Calculate the apparent sidereal time at Greenwich at 0 UT, nu (in degrees), using equation 29.
        double nu = apparentSiderealTimeAtGreenwich(ut0time, Delta_t);
        Delta_t = 0;

        // Calculate the geocentric right ascension and declination at 0 TT
        // for the day before the day of interest (D-1),
        // the day of interest (D0)
        // then the day after (D +1), in degrees.
        int D_minus = 0;
        int D_zero = 1;
        int D_plus = 2;
        long[] D = new long[]{-dayInMillis, 0, dayInMillis};
        double[] alpha = new double[3];
        double[] delta = new double[3];
        for (int i = 0; i < 3; i++) {
            double[] alphadelta = geocentricRightAscensionAndDeclination(ut0time + D[i], Delta_t);
            alpha[i] = alphadelta[0];
            delta[i] = alphadelta[1];
        }

        double[] m_i = new double[3];
        // Calculate the approximate sun transit time
        m_i[0] = approximateSunTransitTime(alpha[D_zero], in.getLongitude(), nu);
        // Calculate the local hour angle
        double H0 = localHourAngleAtSunriseSunset(in.getLatitude(), delta[D_zero], in.getH0_prime());
        if (H0 >= 0) {
            // Calculate the approximate sunrise
            m_i[1] = m_i[0] - H0 / 360; // (A5)
            // Calculate the approximate sunset time
            m_i[2] = m_i[0] + H0 / 360; // (A6)

            // Limit the values of m0, m1, and m2 to a value between 0 and 1
            for (int i = 0; i < 3; i++) {
                m_i[i] = limitZeroToOne(m_i[i]);
            }

            // Calculate the sidereal time at Greenwich, in degrees, for the sun transit, sunrise and sunset
            double[] nu_i = new double[3];
            for (int i = 0; i < 3; i++) {
                nu_i[i] = siderealTimeAtGreenwich(nu, m_i[i]);
            }

            // Calculate the terms n_i
            double[] n_i = new double[3];
            for (int i = 0; i < 3; i++) {
                n_i[i] = m_i[i] + in.getDelta_T() / 86400; // (A8)
            }

            double a = alpha[D_zero] - alpha[D_minus];
            double a_prime = delta[D_zero] - delta[D_minus];
            double b = alpha[D_plus] - alpha[D_zero];
            double b_prime = delta[D_plus] - delta[D_zero];

            // If the absolute value of a, a’, b, or b’ is greater than 2, then limit its value between 0 and 1
            if (abs(a) > 2)
                a = limitZeroToOne(a);
            if (abs(a_prime) > 2)
                a_prime = limitZeroToOne(a_prime);
            if (abs(b) > 2)
                b = limitZeroToOne(b);
            if (abs(b_prime) > 2)
                b_prime = limitZeroToOne(b_prime);
            double c = b - a;
            double c_prime = b_prime - a_prime;

            // Calculate the values alpha_prime and delta_prime, in degrees
            double[] alpha_prime_i = new double[3];
            double[] delta_prime_i = new double[3];
            for (int i = 0; i < 3; i++) {
                alpha_prime_i[i] = alpha[D_zero] + (n_i[i] * (a + b + c * n_i[i])) / 2; // (A9)
                delta_prime_i[i] = delta[D_zero] + (n_i[i] * (a_prime + b_prime + c_prime * n_i[i])) / 2; // (A10)
            }

            // Calculate the local hour angle for the sun transit, sunrise, and sunset, H_prime_i (in degrees)
            double[] H_prime_i = new double[3];
            for (int i = 0; i < 3; i++) {
                H_prime_i[i] = limitDegreesPm180(nu_i[i] + in.getLongitude() - alpha_prime_i[i]); // (A11)
            }

            // Calculate the sun altitude for the sun transit, sunrise, and sunset, h_i (in degrees)
            double[] h_i = new double[3];
            for (int i = 0; i < 3; i++) {
                h_i[i] = sunAltitude(in.getLatitude(), delta_prime_i[i], H_prime_i[i]);
            }

            // Calculate the sun transit, T (in fraction of day)
            double T = m_i[0] - H_prime_i[0] / 360;

            // Calculate the sunrise, R (in fraction of day)
            double R = sunrise(m_i[1], h_i[1], delta_prime_i[1], in.getLatitude(), H_prime_i[1], in.getH0_prime());

            // Calculate the sunset, S (in fraction of day)
            double S = sunset(m_i[2], h_i[2], delta_prime_i[2], in.getLatitude(), H_prime_i[2], in.getH0_prime());

            return new double[]{T, R, S};
        } else {
            return null;
        }
    }

}
