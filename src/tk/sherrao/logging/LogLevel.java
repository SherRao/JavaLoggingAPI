package tk.sherrao.logging;

public enum LogLevel {
    
    TRACE( "Trace" ), FINE( "Fine" ), INFO( "Info" ), WARNING( "Warning" ), ERROR( "ERROR" ), SEVERE( "SEVERE" );  
    
    private final String toString;
    
    private LogLevel( String toString ) {
        this.toString = toString;
        
    }
    
    @Override
    public String toString() {
        return toString;
        
        
    }
    
    public static boolean canLog( LogLevel logLevel, LogLevel compareTo ) {
        return compareTo.ordinal() >= logLevel.ordinal();
        
    }
    
}