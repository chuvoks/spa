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
 * Standard sun declinations used to calculate various twilight times.
 */
public final class SunDeclination {

    private SunDeclination() {
    }

    /**
     * day 0°
     */
    public static final double day = 0.0;
    /**
     * civil twilight -6°
     */
    public static final double civil = -6.0;
    /**
     * twilight -8.333°, sun's actual angle below horizon at sunrise / sunset.
     * <a href="https://en.wikipedia.org/w/index.php?title=Sunrise&oldid=878327869#Angle">Sunrise@Wikipedia</a>
     */
    public static final double twilight = SP.h0_prime;
    /**
     * nautical twilight -12°
     */
    public static final double nautical = -12.0;
    /**
     * astronomical twilight -18°
     */
    public static final double astronomical = -18.0;

}
