# Solar Position Algorithm

Java implementation of the NREL's Solar Position Algorithm (SPA).

> This algorithm calculates the solar zenith and azimuth angles in the period from the year -2000 to 6000, with 
> uncertainties of +/- 0.0003 degrees based on the date, time, and location on Earth. (Reference: Reda, I.; Andreas, A., 
> Solar Position Algorithm for Solar Radiation Applications, Solar Energy. Vol. 76(5), 2004; pp. 577-589).

For details, see https://midcdmz.nrel.gov/spa/

## Features

* Solar position (e.g. topocentric zenith and azimuth angles)
* Sunrise, sun transit and sunset times
* Equation of time
* Zero dependencies

## Usage

There is a trivial example application: [src/test/java/io/github/chuvoks/spademo/SPADemo.java](src/test/java/io/github/chuvoks/spademo/SPADemo.java)
