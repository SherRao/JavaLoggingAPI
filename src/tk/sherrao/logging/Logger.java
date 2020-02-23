package tk.sherrao.logging;


import java.io.PrintStream;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;

public class Logger {

    public static final String DEFAULT_OUTPUT_FORMAT = getDefaultOutputFormat();
    public static final String DEFAULT_TIMESTAMP_FORMAT = getDefaultTimeFormat();
    
    private String name;
    private LogLevel level;
    private List<PrintStream> outputs;
    private String outputFormat;
    private String timeFormat;
    
    public Logger( String name ) {
        this( name, LogLevel.FINE );
        
    }
    
    public Logger( String name, LogLevel level ) {
        this( name, level, System.out );
        
    }
    
    public Logger( String name, LogLevel level, PrintStream initialOut ) {
        this( name, level, initialOut, DEFAULT_OUTPUT_FORMAT );
    
    }
    
    public Logger( String name, LogLevel level, PrintStream initialOut, String outputFormat ) {
        this( name, level, initialOut, outputFormat, DEFAULT_TIMESTAMP_FORMAT );
    
    }
    
    public Logger( String name, LogLevel level, PrintStream initialOut, String outputFormat, String timeFormat ) {
        this.name = name;
        this.level = level;
        this.outputs = Arrays.asList( initialOut );
        this.outputFormat = outputFormat;
        this.timeFormat = timeFormat;
        
    }
    
    public void addOutput( PrintStream output ) {
        outputs.add( output );
        
    }
    
    public void setOutputFormat( String format ) {
        this.outputFormat = format;
        
    }
    
    public void setTimestampFormat( String format ) {
        this.timeFormat = format;
        
    }
    
    public void trace( String log ) {
        doLog( log, LogLevel.TRACE );
        
    }
    
    public void fine( String log ) {
        doLog( log, LogLevel.FINE );

    }

    public void info( String log ) {
        doLog( log, LogLevel.INFO );

    }

    public void warning( String log ) {
        doLog( log, LogLevel.WARNING );

    }
    
    public void error( String log ) {
        doLog( log, LogLevel.ERROR );

    }
    
    public void error( String log, Throwable... error ) {
        doLog( log, LogLevel.ERROR, error );

    }

    public void severe( String log ) {
        doLog( log, LogLevel.SEVERE );

    }
    
    public void severe( String log, Throwable... error ) {
        doLog( log, level, error );
        
    }

    public void blankLine() {
        for( PrintStream out : outputs ) 
            out.println( );
            
    }
    
    private void doLog( String output, LogLevel level, Throwable... throwables ) {
        if( LogLevel.canLog( this.level, level ) ) {
            OffsetDateTime now = OffsetDateTime.now();
            String timestamp = timeFormat
                    .replace( LogTimeFormat.YEAR.toString(), Integer.toString( now.get( ChronoField.YEAR ) ) )
                    .replace( LogTimeFormat.MONTH.toString(), Integer.toString( now.get( ChronoField.MONTH_OF_YEAR ) ) )
                    .replace( LogTimeFormat.DAY.toString(), Integer.toString( now.get( ChronoField.DAY_OF_MONTH ) ) )
                    .replace( LogTimeFormat.HOUR.toString(), Integer.toString( now.get( ChronoField.HOUR_OF_DAY ) ) )
                    .replace( LogTimeFormat.MINUTE.toString(), Integer.toString( now.get( ChronoField.MINUTE_OF_HOUR ) ) )
                    .replace( LogTimeFormat.SECOND.toString(), Integer.toString( now.get( ChronoField.SECOND_OF_MINUTE ) ) )
                    .replace( LogTimeFormat.MILLIS.toString(), Integer.toString( now.get( ChronoField.MILLI_OF_SECOND ) ) )
                    .replace( LogTimeFormat.NANO.toString(), Integer.toString( now.get( ChronoField.NANO_OF_SECOND ) ) )
                    .replace( LogTimeFormat.TIME_ZONE.toString(), "UTC" + now.getOffset().toString().substring( 0, 3 ) );

            String finalOutput = outputFormat
                    .replace( LogFormat.LOGGER_NAME.toString(), getName() )
                    .replace( LogFormat.OUTPUT_TEXT.toString(), output )
                    .replace( LogFormat.THREAD.toString(), Thread.currentThread().getName() )
                    .replace( LogFormat.TIMESTAMP.toString(), timestamp );
            
            for( PrintStream out : outputs ) {
                out.println( finalOutput );
                for( Throwable t : throwables )
                    t.printStackTrace( out );
            
            }
            
        } else 
            return;         
    
    }
    
    public String getName() {
        return name;
        
    }
    
    public String getOutputFormat() {
        return outputFormat;
        
    }
    
    public String getTimeFormat() {
        return timeFormat;
        
    }
    
    private static final String getDefaultOutputFormat() {
        return new StringBuilder( "[" )
                .append( LogFormat.TIMESTAMP )
                .append( "] [" )
                .append( LogFormat.LOGGER_NAME )
                .append( "]: " )
                .append( LogFormat.OUTPUT_TEXT )
                .toString();
        
    }
    
    private static final String getDefaultTimeFormat() {
        return new StringBuilder()
                .append( LogTimeFormat.YEAR.toString() )
                .append( "-" )
                .append( LogTimeFormat.MONTH.toString() )
                .append( "-" )
                .append( LogTimeFormat.DAY.toString() )
                .append( " " )
                .append( LogTimeFormat.HOUR )
                .append( ":" )
                .append( LogTimeFormat.MINUTE )
                .append( ":" )
                .append( LogTimeFormat.SECOND )
                .append( " " )
                .append( LogTimeFormat.TIME_ZONE )
                .toString();
        
    }
    
}