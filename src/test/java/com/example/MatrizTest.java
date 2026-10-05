package com.example;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;


/**
 * Pruebas de los constructores de Matriz.
 */
public class MatrizTest {

    /*
     * Comprobamos el caso más sencillo:
     * el constructor recibe una matriz rectangular.
     */
    @Test
    public void constructorMatrizRectangular() {

        double[][] datos = {
            { 1.0, 2.0, 3.0 },
            { 4.0, 5.0, 6.0 }
        };

        Matriz m = new Matriz(datos);

        assertArrayEquals(
                new int[] { 2, 3 },
                m.getDimension());

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(datos[i][j], m.getElemento(i, j), 1e-9);
            }
        }        
    }

    /*
     * Si las filas no tienen la misma longitud,
     * la matriz toma como número de columnas la longitud
     * de la fila más larga y completa con ceros.
     */
    @Test
    public void constructorRellenaConCeros() {

        double[][] datos = {
            { 1.0, 2.0, 3.0 },
            { 4.0, 5.0 },
            { 6.0 }
        };

        Matriz m = new Matriz(datos);

        assertArrayEquals(
                new int[] { 3, 3 },
                m.getDimension());

        assertEquals(4.0, m.getElemento(1, 0), 1e-9);
        assertEquals(5.0, m.getElemento(1, 1), 1e-9);
        assertEquals(0.0, m.getElemento(1, 2), 1e-9);

        assertEquals(6.0, m.getElemento(2, 0), 1e-9);
        assertEquals(0.0, m.getElemento(2, 1), 1e-9);
        assertEquals(0.0, m.getElemento(2, 2), 1e-9);
    }

    /*
     * El constructor debe copiar los datos recibidos.
     * Modificar posteriormente el array original no debe
     * modificar la matriz.
     */
    @Test
    public void constructorHaceUnaCopiaDeLosDatos() {

        double[][] datos = {
            { 1.0, 2.0 },
            { 3.0, 4.0 }
        };

        Matriz m = new Matriz(datos);

        datos[0][0] = 100.0;

        assertEquals(1.0, m.getElemento(0, 0), 1e-9);
    }

    /*
     * Comprobamos que el constructor lanza una excepción si recibe
     * un array null o vacío.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorMatrizNull() {
        new Matriz(null);
    }  
    
    /*
     * Comprobamos que el constructor lanza una excepción si recibe
     * un array vacío.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorMatrizVacia() {
        new Matriz(new double[0][0]);
    }

    /*
     * Comprobamos que el constructor lanza una excepción si recibe
     * todos los arrays con filas vacías.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorMatrizConFilasVacias() {
        double[][] datos = {
            { },
            { }
        };

        new Matriz(datos);
    }

    /*
     * Comprobamos que el constructor lanza una excepción si recibe
     * un array con filas null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorMatrizConFilasNull() {
        double[][] datos = {
            { 1.0, 2.0 },
            null
        };

        new Matriz(datos);
    }
    @Test
    public void sumarInstanciaMatricesMismasDimensiones() {

        double[][] datos1 = {
            { 1.0, 2.0 },
            { 3.0, 4.0 }
        };

        double[][] datos2 = {
            { 5.0, 6.0 },
            { 7.0, 8.0 }
        };

        Matriz a = new Matriz(datos1);
        Matriz b = new Matriz(datos2);

        Matriz s = a.sumar(b);

        assertNotNull(s);
        assertArrayEquals(new int[] { 2, 2 }, s.getDimension());

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(datos1[i][j] + datos2[i][j], s.getElemento(i, j), 1e-9);
            }
        }
    }

    @Test
    public void sumarInstanciaDevuelveNullSiArgumentoNull() {

        double[][] datos = {
            { 1.0, 2.0 }
        };

        Matriz m = new Matriz(datos);

        assertNull(m.sumar(null));
    }

    @Test
    public void sumarInstanciaDevuelveNullSiDimensionesNoCoinciden() {

        double[][] datos1 = {
            { 1.0, 2.0 }
        };

        double[][] datos2 = {
            { 1.0 },
            { 2.0 }
        };

        Matriz a = new Matriz(datos1); // 1x2
        Matriz b = new Matriz(datos2); // 2x1

        assertNull(a.sumar(b));
    }

    @Test
    public void sumarEstaticoMatricesMismasDimensiones() {

        double[][] datos1 = {
            { 1.0, 0.5, 2.0 }
        };

        double[][] datos2 = {
            { 0.5, 1.5, -2.0 }
        };

        Matriz a = new Matriz(datos1);
        Matriz b = new Matriz(datos2);

        Matriz s = Matriz.sumar(a, b);

        assertNotNull(s);
        assertArrayEquals(new int[] { 1, 3 }, s.getDimension());

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(datos1[i][j] + datos2[i][j], s.getElemento(i, j), 1e-9);
            }
        }
    }

    @Test
    public void sumarEstaticoDevuelveNullSiAlgunoNull() {

        double[][] datos = {
            { 1.0 }
        };

        Matriz m = new Matriz(datos);

        assertNull(Matriz.sumar(null, m));
        assertNull(Matriz.sumar(m, null));
    }

    @Test
    public void sumarEstaticoDevuelveNullSiDimensionesNoCoinciden() {

        double[][] datos1 = {
            { 1.0, 2.0 }
        };

        double[][] datos2 = {
            { 1.0 }
        };

        Matriz a = new Matriz(datos1);
        Matriz b = new Matriz(datos2);

        assertNull(Matriz.sumar(a, b));
    }

    @Test
    public void traspuestaInstanciaMatrizRectangular() {

        double[][] datos = {
            { 1.0, 2.0, 3.0 },
            { 4.0, 5.0, 6.0 }
        };

        Matriz m = new Matriz(datos);
        Matriz t = m.traspuesta();

        assertNotNull(t);
        assertArrayEquals(new int[] { 3, 2 }, t.getDimension());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(datos[j][i], t.getElemento(i, j), 1e-9);
            }
        }
    }

    @Test
    public void traspuestaEstaticaMatrizRectangular() {

        double[][] datos = {
            { 1.0, 2.0, 3.0 },
            { 4.0, 5.0, 6.0 }
        };

        Matriz m = new Matriz(datos);
        Matriz t = Matriz.traspuesta(m);

        assertNotNull(t);
        assertArrayEquals(new int[] { 3, 2 }, t.getDimension());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(datos[j][i], t.getElemento(i, j), 1e-9);
            }
        }
    }

    @Test
    public void traspuestaEstaticaDevuelveNullSiArgumentoNull() {
        assertNull(Matriz.traspuesta(null));
    }

}
