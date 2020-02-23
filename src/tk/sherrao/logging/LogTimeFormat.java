package tk.sherrao.logging;


public enum LogTimeFormat {

    TIME_ZONE( "<z>" ), YEAR( "<y>" ), MONTH( "<mo>" ), DAY( "<d>" ), HOUR( "h" ), MINUTE( "m" ), SECOND( "s" ), MILLIS( "ms" ), NANO( "n" );

    private final String toString;

    private LogTimeFormat( String toString ) {
        this.toString = toString;
    
    }

    @Override
    public String toString() {
        return toString;
    
    }
    
}