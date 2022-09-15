package utilidad;

/**
 * Esta clase se encarga de consultar al sistema tanto que SO utiliza, arquitectura y version de la misma
 */
public class SystemQuery{

    //OS obtiene el nombre del sistema operativo en el que se esta ejecutando el programa
    private static final String OS = System.getProperty("os.name").toLowerCase();

    //OSArch obtiene la arquitectura del sistema operativo en el que se esta ejecutando el programa
    private static final String OSArch = System.getProperty("os.arch").toLowerCase();

    //OSVersion obtiene la version del sistema operativo en el que se esta ejecutando el programa
    private static final String OSVersion = System.getProperty("os.version").toLowerCase();

    /**
     * El metodo busca un subString dentro de la variable para determinar si el programa esta corriendo
     * en un sistema windows
     * 
     * @return                true en caso de que el sistema operativo en que se esta ejecutando el 
     *                        programa es windows, y false en caso de que fuere otro sistema
     */
    public static boolean isWindows(){
        return (OS.contains("win"));
    }

    /**
     * El metodo busca un substring dentro de la variable para determinar si el programa esta corriendo
     * en un sistema Unix/Linux
     * 
     * @return               true en caso de que el sistema operativo en que se esta ejecutando el
     *                       programa es Unix/Linux, y false en caso de que fuere otro sistema
     */
    public static boolean isUnix(){
        return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0 );
    }

    /**
     * El metodo busca un substring dentro de la variable para determinar si el programa esta corriendo
     * en un sistema Mac OS
     * 
     * @return               true en caso de que el sistema operativo en que se esta ejecutando el
     *                       programa es Mac OS, y false en caso de que fuere otro sistema
     */
    public static boolean isMacOS(){
        return (OS.contains("mac"));
    }

    /**
     * El metodo busca un substring dentro de la variable para determinar si el programa esta corriendo
     * en un sistema Solaris
     * 
     * @return               true en caso de que el sistema operativo en que se esta ejecutando el
     *                       programa es un Solaris, y false en caso d e que fuera otro sistema
     */
    public static boolean isSolaris(){
        return (OS.contains("solus"));
    }
    
    /**
     * Retorna el nombre del sistema operativo en el que el programa se esta ejecutando
     * 
     * @return               devuelve el nombre del sistema operativo
     */
    public static String getOS(){
        return OS;
    }

    /**
     * Retorna la arquitectura del sistema operativo en el que el programa se esta ejecutando
     * 
     * @return               devuelve la arquitectura del sistema operativo
     */
    public static String getOSArch(){
        return OSArch;
    }

    /**
     * Retorna la version del sistema operativo en el que el programa se esta ejecutando
     * 
     * @return               devuelve la version del sistema operativo 
     */
    public static String getOSVersion(){
        return OSVersion;
    }
    
    /**
     * Retorna el nombre de la carpeta personal del usuario en caso de que el programa se
     * ejecute en un sistema Unix, caso contrario se le informara del error y no se realizara
     * ninguna operacion.
     * 
     * @return               devuelve el nombre de la carpera user
     */
    public static boolean getLinuxUser(){
        if (isUnix) {
            return System.getProperty("user.home");
        } else {
            System.out.println("El sistema no es Unix... No se puede realizar esta operacion");
        }
    }

}