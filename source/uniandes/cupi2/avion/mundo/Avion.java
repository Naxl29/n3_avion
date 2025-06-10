/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n3_avion
 * Autor: Equipo Cupi2 2017
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package uniandes.cupi2.avion.mundo;

import uniandes.cupi2.avion.mundo.Silla.Clase;
import uniandes.cupi2.avion.mundo.Silla.Ubicacion;

/**
 * Avi�n de pasajeros.
 */
public class Avion
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * N�mero de sillas ejecutivas.
     */
    public final static int SILLAS_EJECUTIVAS = 8;

    /**
     * N�mero de sillas econ�micas.
     */
    public final static int SILLAS_ECONOMICAS = 42;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Sillas de la clase ejecutiva del avi�n.
     */

    private Silla[] sillasEjecutivas;

    /**
     * Sillas de la clase econ�mica del avi�n.
     */
    private Silla[] sillasEconomicas;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el avi�n. <br>
     * <b>post: </b> Se inicializan las listas de sillas ejecutivas y econ�micas.
     */
    public Avion( )
    {
        Ubicacion ubicacion;

        // Crea las sillas ejecutivas
        sillasEjecutivas = new Silla[SILLAS_EJECUTIVAS];

        // Crea las sillas econ�micas
        sillasEconomicas = new Silla[SILLAS_ECONOMICAS];

        sillasEjecutivas[ 0 ] = new Silla( 1, Clase.EJECUTIVA, Ubicacion.VENTANA );
        sillasEjecutivas[ 1 ] = new Silla( 2, Clase.EJECUTIVA, Ubicacion.PASILLO );
        sillasEjecutivas[ 2 ] = new Silla( 3, Clase.EJECUTIVA, Ubicacion.PASILLO );
        sillasEjecutivas[ 3 ] = new Silla( 4, Clase.EJECUTIVA, Ubicacion.VENTANA );
        sillasEjecutivas[ 4 ] = new Silla( 5, Clase.EJECUTIVA, Ubicacion.VENTANA );
        sillasEjecutivas[ 5 ] = new Silla( 6, Clase.EJECUTIVA, Ubicacion.PASILLO );
        sillasEjecutivas[ 6 ] = new Silla( 7, Clase.EJECUTIVA, Ubicacion.PASILLO );
        sillasEjecutivas[ 7 ] = new Silla( 8, Clase.EJECUTIVA, Ubicacion.VENTANA );

        for( int numSilla = 1 + SILLAS_EJECUTIVAS, j = 1; j <= SILLAS_ECONOMICAS; numSilla++, j++ )
        {
            // Sillas ventana
            if( j % 6 == 1 || j % 6 == 0 )
                ubicacion = Ubicacion.VENTANA;
            // Sillas centrales
            else if( j % 6 == 2 || j % 6 == 5 )
                ubicacion = Ubicacion.CENTRAL;
            // Sillas pasillo
            else
                ubicacion = Ubicacion.PASILLO;

            sillasEconomicas[ j - 1 ] = new Silla( numSilla, Clase.ECONOMICA, ubicacion );
        }
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Asigna la silla al pasajero en la clase y ubicaci�n especificados. <br>
     * <b>post: </b> Si existe una silla con la clase y la ubicaci�n dada, el pasajero queda asignado en la primera de ellas seg�n el orden num�rico.
     * @param pClase Clase elegida por el pasajero. Clase pertenece {Clase.EJECUTIVA, Clase.ECONOMICA}.
     * @param pUbicacion Ubicaci�n elegida por el pasajero. Si clase = Clase.ECONOMICA entonces ubicaci�n pertenece {VENTANA, CENTRAL, PASILLO}, <br>
     *        o si clase = Clase.EJECUTIVA entonces ubicaci�n pertenece {VENTANA, PASILLO}.
     * @param pPasajero Pasajero a asignar. pPasajero != null y no tiene silla en el avi�n.
     * @return Silla asignada al pasajero o null si no se pudo asignar una silla al pasajero en la ubicaci�n y clase especificados.
     */
    public Silla asignarSilla( Clase pClase, Ubicacion pUbicacion, Pasajero pPasajero )
    {
        // busca una silla libre
        Silla silla = null;
        if( pClase == Clase.EJECUTIVA )
        {
            silla = buscarSillaEjecutivaLibre( pUbicacion );
        }
        else if( pClase == Clase.ECONOMICA )
        {
            silla = buscarSillaEconomicaLibre( pUbicacion );
        }
        if( silla != null )
        {
            silla.asignarAPasajero( pPasajero );
        }
        return silla;
    }

    /**
     * Busca la siguiente silla ejecutiva que este libre y tenga la ubicaci�n indicada. <br>
     * <b>pre: </b> La lista de sillas ejecutivas est� inicializada.
     * @param pUbicacion Ubicaci�n en donde buscar la silla. pUbicaci�n pertenece {VENTANA, PASILLO}.
     * @return La silla libre encontrada. Si no encuentra una silla retorna null.
     */
    public Silla buscarSillaEjecutivaLibre( Ubicacion pUbicacion )
    {
        boolean encontrado = false;
        Silla silla = null;
        for( int i = 0; i < SILLAS_EJECUTIVAS && !encontrado; i++ )
        {
            silla = sillasEjecutivas[ i ];
            if( ! ( silla.sillaAsignada( ) ) && silla.darUbicacion( ) == pUbicacion )
            {
                encontrado = true;
            }
        }
        if( !encontrado )
        {
            silla = null;
        }
        return silla;
    }

    /**
     * Busca la siguiente silla econ�mica que este libre y tenga la ubicaci�n indicada. <br>
     * <b>pre: </b> La lista de sillas econ�micas est� inicializada.
     * @param pUbicacion Ubicaci�n en donde buscar la silla. pUbicaci�n pertenece {VENTANA, CENTRAL, PASILLO}.
     * @return Silla libre encontrada. Si no encuentra una silla retorna null.
     */
    public Silla buscarSillaEconomicaLibre( Ubicacion pUbicacion )
    {
        boolean encontrado = false;
        Silla silla = null;
        for( int i = 0; i < SILLAS_ECONOMICAS && !encontrado; i++ )
        {
            silla = sillasEconomicas[ i ];
            if( ! ( silla.sillaAsignada( ) ) && silla.darUbicacion( ) == pUbicacion )
            {
                encontrado = true;
            }
        }
        if( !encontrado )
        {
            silla = null;
        }
        return silla;
    }

    /**
     * Busca un pasajero en el avi�n.
     * @param pPasajero Pasajero a buscar. pPasajero != null.
     * @return Silla en la que se encontr� el pasajero. Si no lo encuentra retorna null.
     */
    public Silla buscarPasajero( Pasajero pPasajero )
    {
        // Busca el pasajero en ejecutiva
        Silla silla = buscarPasajeroEjecutivo( pPasajero );
        // Si no estaba en ejecutiva
        if( null == silla )
        {
            // Busca en econ�mica
            silla = buscarPasajeroEconomico( pPasajero );
        }

        return silla;

    }

    /**
     * Busca un pasajero en las sillas ejecutivas. <br>
     * <b>pre: </b> La lista de sillas ejecutivas est� inicializada.
     * @param pPasajero Pasajero a buscar. pPasajero != null.
     * @return Silla en la que se encontr� el pasajero. Si no lo encuentra retorna null.
     */
    public Silla buscarPasajeroEjecutivo( Pasajero pPasajero )
    {
        boolean encontrado = false;
        Silla silla = null;
        for( int i = 0; i < SILLAS_EJECUTIVAS && !encontrado; i++ )
        {
            silla = sillasEjecutivas[ i ];
            if( silla.sillaAsignada( ) && silla.darPasajero( ).igualA( pPasajero ) )
            {
                encontrado = true;
            }
        }
        if( !encontrado )
        {
            silla = null;
        }
        return silla;
    }

    /**
     * Busca un pasajero en las sillas econ�micas. <br>
     * <b>pre: </b> La lista de sillas econ�micas est� inicializada.
     * @param pPasajero Pasajero a buscar. pPasajero != null.
     * @return Silla en la que se encontr� el pasajero. Si no lo encuentra retorna null.
     */
    public Silla buscarPasajeroEconomico( Pasajero pPasajero )
    {
        boolean encontrado = false;
        Silla silla = null;
        for( int i = 0; i < SILLAS_ECONOMICAS && !encontrado; i++ )
        {
            silla = sillasEconomicas[ i ];
            if( silla.sillaAsignada( ) && silla.darPasajero( ).igualA( pPasajero ) )
            {
                encontrado = true;
            }
        }
        if( !encontrado )
        {
            silla = null;
        }
        return silla;
    }

    /**
     * Desasigna la silla de un pasajero. <br>
     * <b>post: </b> Si se encuentra una silla con el pasajero, la silla quedara con su pasajero igual a null.
     * @param pPasajero Pasajero a retirar. pPasajero != null.
     * @return Retorna true si encontr� el pasajero y des asign� la silla, false en caso contrario.
     */
    public boolean desasignarSilla( Pasajero pPasajero )
    {
        // Busca el pasajero en el avi�n
        Silla silla = buscarPasajero( pPasajero );
        boolean resultado = false;
        // Si lo encuentra desasigna
        if( silla != null )
        {
            silla.desasignarSilla( );
            resultado = true;
        }
        return resultado;
    }

    /**
     * Retorna el n�mero de sillas ejecutivas ocupadas. <br>
     * <b>pre: </b> La lista de sillas ejecutivas est� inicializada.
     * @return N�mero de silla ejecutivas ocupadas.
     */
    public int contarSillasEjecutivasOcupadas( )
    {
        int contador = 0;
        for( Silla sillaEjecutiva : sillasEjecutivas )
        {
            if( sillaEjecutiva.sillaAsignada( ) )
            {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Retorna el n�mero de sillas econ�micas ocupadas. <br>
     * <b>pre: </b> La lista de sillas econ�micas est� inicializada.
     * @return N�mero de sillas econ�micas ocupadas.
     */
    public int contarSillasEconomicasOcupadas( )
    {
        int contador = 0;
        for( Silla sillaEconomica : sillasEconomicas )
        {
            if( sillaEconomica.sillaAsignada( ) )
            {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Calcula el porcentaje de ocupaci�n del avi�n.
     * @return Porcentaje total de ocupaci�n.
     */
    public double calcularPorcentajeOcupacion( )
    {
        double porcentaje;
        int totalSillas = SILLAS_ECONOMICAS + SILLAS_EJECUTIVAS;
        int sillasOcupadas = contarSillasEconomicasOcupadas( ) + contarSillasEjecutivasOcupadas( );
        porcentaje = ( double )sillasOcupadas / totalSillas * 100;
        return porcentaje;
    }

    /**
     * @return La clase con más sillas ocupadas.
     */
    public Clase darClaseConMasSillasEnVentanaOcupadas(){
        int ejecutivasVentanaOcupadas = 0;
        int economicasVentanaOcupadas = 0;

        for (Silla silla : sillasEjecutivas) {
            if (silla.sillaAsignada() && silla.darUbicacion() == Ubicacion.VENTANA) {
                ejecutivasVentanaOcupadas++;
            }
        }

        for (Silla silla : sillasEconomicas) {
            if (silla.sillaAsignada() && silla.darUbicacion() == Ubicacion.VENTANA) {
                economicasVentanaOcupadas++;
            }
        }

        if (ejecutivasVentanaOcupadas > economicasVentanaOcupadas) {
            return Clase.EJECUTIVA;
        } else if (economicasVentanaOcupadas > ejecutivasVentanaOcupadas) {
            return Clase.ECONOMICA;
        } else {
            return null; 
        }
    }


    /**
     * 
     * @return Primera silla economica libre en ventana
     */
    public Silla darSillaEconomicaLibreEnVentana(){
        for (Silla silla : sillasEconomicas) {
            if (!silla.sillaAsignada() && silla.darUbicacion() == Ubicacion.VENTANA) {
                return silla;
            }
        }
        return null;
    }

    /**
     * Retorna las sillas ejecutivas del avi�n.
     * @return Sillas ejecutivas del avi�n.
     */
    public Silla[] obtenerSillasEjecutivas( )
    {
        return sillasEjecutivas;
    }

    /**
     * Retorna las sillas econ�micas del avi�n.
     * @return Sillas econ�micas del avi�n.
     */
    public Silla[] obtenerSillasEconomicas( )
    {
        return sillasEconomicas;
    }

    /**
     * M�todo para la extensi�n 1.
     * @return Respuesta 1.
     */
    public String metodo1( )
    {
        Clase claseConMas = darClaseConMasSillasEnVentanaOcupadas();
        if(claseConMas == Clase.EJECUTIVA) {
            return "Hay más sillas ocupadas ubicadas en la ventana en la clase ejecutiva.";
        }
        else if(claseConMas == Clase.ECONOMICA) {
            return "Hay más sillas ocupadas ubicadas en la ventana en la clase económica.";
        }
        else {
            return "Hay un número igual de sillas ocupadas en la ventana.";
        }
    }

    /**
     * M�todo para la extensi�n 2.
     * @return Respuesta 2.
     */
    public String metodo2( )
    {
        Silla sillaLibre = darSillaEconomicaLibreEnVentana();
        
        if(sillaLibre != null) {
            return "Hay una silla económica gratuita en la ventana. El número de la silla es " + sillaLibre.darNumero() + ".";
        }
        else {
            return "No hay silla económica libre en la ventana.";
        }
    }

}