/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
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
 * Avión de pasajeros.
 */
public class Avion
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Número de sillas ejecutivas.
     */
    public final static int SILLAS_EJECUTIVAS = 8;

    /**
     * Número de sillas económicas.
     */
    public final static int SILLAS_ECONOMICAS = 42;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Sillas de la clase ejecutiva del avión.
     */

    private Silla[] sillasEjecutivas;

    /**
     * Sillas de la clase económica del avión.
     */
    private Silla[] sillasEconomicas;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el avión. <br>
     * <b>post: </b> Se inicializan las listas de sillas ejecutivas y económicas.
     */
    public Avion( )
    {
        Ubicacion ubicacion;

        // Crea las sillas ejecutivas
        sillasEjecutivas = new Silla[SILLAS_EJECUTIVAS];

        // Crea las sillas económicas
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
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Asigna la silla al pasajero en la clase y ubicación especificados. <br>
     * <b>post: </b> Si existe una silla con la clase y la ubicación dada, el pasajero queda asignado en la primera de ellas según el orden numérico.
     * @param pClase Clase elegida por el pasajero. Clase pertenece {Clase.EJECUTIVA, Clase.ECONOMICA}.
     * @param pUbicacion Ubicación elegida por el pasajero. Si clase = Clase.ECONOMICA entonces ubicación pertenece {VENTANA, CENTRAL, PASILLO}, <br>
     *        o si clase = Clase.EJECUTIVA entonces ubicación pertenece {VENTANA, PASILLO}.
     * @param pPasajero Pasajero a asignar. pPasajero != null y no tiene silla en el avión.
     * @return Silla asignada al pasajero o null si no se pudo asignar una silla al pasajero en la ubicación y clase especificados.
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
     * Busca la siguiente silla ejecutiva que este libre y tenga la ubicación indicada. <br>
     * <b>pre: </b> La lista de sillas ejecutivas está inicializada.
     * @param pUbicacion Ubicación en donde buscar la silla. pUbicación pertenece {VENTANA, PASILLO}.
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
     * Busca la siguiente silla económica que este libre y tenga la ubicación indicada. <br>
     * <b>pre: </b> La lista de sillas económicas está inicializada.
     * @param pUbicacion Ubicación en donde buscar la silla. pUbicación pertenece {VENTANA, CENTRAL, PASILLO}.
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
     * Busca un pasajero en el avión.
     * @param pPasajero Pasajero a buscar. pPasajero != null.
     * @return Silla en la que se encontró el pasajero. Si no lo encuentra retorna null.
     */
    public Silla buscarPasajero( Pasajero pPasajero )
    {
        // Busca el pasajero en ejecutiva
        Silla silla = buscarPasajeroEjecutivo( pPasajero );
        // Si no estaba en ejecutiva
        if( null == silla )
        {
            // Busca en económica
            silla = buscarPasajeroEconomico( pPasajero );
        }

        return silla;

    }

    /**
     * Busca un pasajero en las sillas ejecutivas. <br>
     * <b>pre: </b> La lista de sillas ejecutivas está inicializada.
     * @param pPasajero Pasajero a buscar. pPasajero != null.
     * @return Silla en la que se encontró el pasajero. Si no lo encuentra retorna null.
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
     * Busca un pasajero en las sillas económicas. <br>
     * <b>pre: </b> La lista de sillas económicas está inicializada.
     * @param pPasajero Pasajero a buscar. pPasajero != null.
     * @return Silla en la que se encontró el pasajero. Si no lo encuentra retorna null.
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
     * @return Retorna true si encontró el pasajero y des asignó la silla, false en caso contrario.
     */
    public boolean desasignarSilla( Pasajero pPasajero )
    {
        // Busca el pasajero en el avión
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
     * Retorna el número de sillas ejecutivas ocupadas. <br>
     * <b>pre: </b> La lista de sillas ejecutivas está inicializada.
     * @return Número de silla ejecutivas ocupadas.
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
     * Retorna el número de sillas económicas ocupadas. <br>
     * <b>pre: </b> La lista de sillas económicas está inicializada.
     * @return Número de sillas económicas ocupadas.
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
     * Calcula el porcentaje de ocupación del avión.
     * @return Porcentaje total de ocupación.
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
     * Retorna las sillas ejecutivas del avión.
     * @return Sillas ejecutivas del avión.
     */
    public Silla[] obtenerSillasEjecutivas( )
    {
        return sillasEjecutivas;
    }

    /**
     * Retorna las sillas económicas del avión.
     * @return Sillas económicas del avión.
     */
    public Silla[] obtenerSillasEconomicas( )
    {
        return sillasEconomicas;
    }

    /**
     * Método para la extensión 1.
     * @return Respuesta 1.
     */
    public String metodo1( )
    {
        return "Respuesta 1";
    }

    /**
     * Método para la extensión 2.
     * @return Respuesta 2.
     */
    public String metodo2( )
    {
        return "Respuesta 2";
    }

}