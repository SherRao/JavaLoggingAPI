package tk.sherrao.logging;


public enum LogFormat {

    OUTPUT_TEXT( "<output>" ), TIMESTAMP( "<time>" ), THREAD( "<thread>" ), LOGGER_NAME( "<name>" );
    
    private final String toString;
    
    private LogFormat( String toString ) {
        this.toString = toString;
        
    }
    
    @Override
    public String toString() {
        return toString;
        
    }
    
}
