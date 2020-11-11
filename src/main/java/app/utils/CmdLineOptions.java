package app.utils;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

/**
 * Created by jorgeimperial on 10/10/2020.
 */
public class CmdLineOptions extends OptionsBase {
    @Option(
            name = "help",
            abbrev = 'h',
            help = "Prints usage info.",
            defaultValue = "true"
    )
    public boolean help;

    @Option(
            name = "startThreads",
            abbrev = 't',
            help = "Starting number of threads for this process",
            category = "threads",
            defaultValue = "0"
    )
    public int startThreads;

    @Option(
            name = "maxThreads",
            abbrev = 'x',
            help = "Maximum number of threads to spin",
            category = "threads",
            defaultValue = "100"
    )
    public int maxThreads;

    @Option(
            name = "deltaThreads",
            abbrev = 'd',
            help = "How many threads to start in each iteration.",
            category = "threads",
            defaultValue = "2"
    )
    public int deltaThreads;

    @Option(
            name = "iterations",
            abbrev = 'i',
            help = "Name of directory to serve static files.",
            category = "threads",
            defaultValue = "1000"
    )
    public int iterations;
    @Option(
            name = "sleep",
            abbrev = 's',
            help = "Milliseconds to sleep between iterations.",
            category = "threads",
            defaultValue = "500"
    )
    public int sleepMs;
}

