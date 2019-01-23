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
 * Math utilities
 */
public final class Math2 {

    private Math2() {
    }

    private static double DEG2RAD = Math.PI / 180.0;

    private static double RAD2DEG = 180.0 / Math.PI;

    /**
     * convert degrees to radians
     */
    public static double deg2rad(double deg) {
        return deg * DEG2RAD;
    }

    /**
     * convert radians to degrees
     */
    public static double rad2deg(double rad) {
        return rad * RAD2DEG;
    }

    /**
     * Evaluate a polynomial at points x
     */
    public static double polyval(double[] polynomial, double x) {
        //  Horner's method
        double result = polynomial[0];
        int n = polynomial.length;
        for (int i = 0; i < n; i++) {
            result = result * x + polynomial[i];
        }
        return result;
    }

    /**
     * calculate dot product of vectors a and b
     */
    public static double dot(double[] a, double[] b) {
        if (a.length != b.length)
            throw new IllegalArgumentException("Arrays are different sizes");
        double sum = 0.0;
        int n = a.length;
        for (int i = 0; i < n; i++)
            sum += a[i] * b[i];
        return sum;
    }

}
