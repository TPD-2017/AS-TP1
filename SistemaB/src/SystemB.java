/******************************************************************************************************************
 * File:SystemB.java
 * Course: 17655
 * Project: Assignment 1
 * Copyright: Copyright (c) 2003 Carnegie Mellon University
 * Versions:
 *	1.0 November 2008 - Sample Pipe and Filter code (ajl).
 *
 * Description:
 *
 * This class serves as an example to illstrate how to use the PlumberTemplate to create a main thread that
 * instantiates and connects a set of filters. This example consists of three filters: a source, a middle filter
 * that acts as a pass-through filter (it does nothing to the data), and a sink filter which illustrates all kinds
 * of useful things that you can do with the input stream of data.
 *
 * Parameters: 		None
 *
 * Internal Methods:	None
 *
 ******************************************************************************************************************/
public class SystemB
{
    public static void main( String argv[])
    {
        /****************************************************************************
         * Here we instantiate three filters.
         ****************************************************************************/

        /*
        SourceFilter Filter1 = new SourceFilter();
        Splitter Filter2 = new Splitter();
        SinkFilter Filter3 = new SinkFilter();
        */

        TemperatureFilter temperatureFilter = new TemperatureFilter();
        AltitudeFilter altitudeFilter = new AltitudeFilter();
        SinkFileFilter sinkFilter = new SinkFileFilter("OutputB.dat");
        SinkFileFilter sinkFilter2 = new SinkFileFilter("WildPoints.dat");
        SourceFilter sourceFilter = new SourceFilter("FlightData.dat");
        CleanFilter cleanVelocity = new CleanFilter(1);
        CleanFilter cleanPitch = new CleanFilter(5);
        CleanFilter cleanTemperature = new CleanFilter(4);
        CleanFilter cleanAltitude = new CleanFilter(2);
        Splitter splitter = new Splitter();
        ReceiveSplitter receiver1 = new ReceiveSplitter();
        ReceiveSplitter receiver2 = new ReceiveSplitter();
        WildPointsFilter wildpointsfilter = new WildPointsFilter();
        WildPointsConverter wildpointsconverter = new WildPointsConverter();

        /****************************************************************************
         * Here we connect the filters starting with the sink filter (Filter 1) which
         * we connect to Filter2 the middle filter. Then we connect Filter2 to the
         * source filter (Filter3).
         ****************************************************************************/

        /*
        Filter3.Connect(Filter2); // This esstially says, "connect Filter3 input port to Filter2 output port
        Filter2.Connect(Filter1); // This esstially says, "connect Filter2 intput port to Filter1 output port
        */

        cleanVelocity.Connect(sourceFilter);
        cleanPitch.Connect(cleanVelocity);
        temperatureFilter.Connect(cleanPitch);
        altitudeFilter.Connect(temperatureFilter);

        splitter.Connect(altitudeFilter);

        receiver1.Connect(splitter);
        receiver2.Connect(splitter);

        wildpointsconverter.Connect(receiver1);

        cleanTemperature.Connect(receiver2);
        cleanAltitude.Connect(cleanTemperature);

        wildpointsfilter.Connect(cleanAltitude);

        sinkFilter.Connect(wildpointsconverter);
        sinkFilter2.Connect(wildpointsfilter);

        /****************************************************************************
         * Here we start the filters up. All-in-all,... its really kind of boring.
         ****************************************************************************/

        /*
        Filter1.start();
        Filter2.start();
        Filter3.start();
        */

        sourceFilter.start();
        cleanVelocity.start();
        cleanPitch.start();
        temperatureFilter.start();
        altitudeFilter.start();
        splitter.start();
        receiver1.start();
        receiver2.start();
        cleanTemperature.start();
        cleanAltitude.start();
        wildpointsfilter.start();
        wildpointsconverter.start();
        sinkFilter.start();
        sinkFilter2.start();

    } // main

} // SystemB