import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ValeriaFinal {

    // Función para llenar las matrices de los días con los archivos
    public static String[][] fillWeekDays(String fileName) {

        // Inicializacion de las variables
        String[][] resultados = new String[100][5];
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Asignacion de valor a cada variable segun el archivo
            archivo = new File(fileName);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String Linea;
            // Ciclo para leer cada linea y pasar de linea
            while ((Linea = br.readLine()) != null) {
                for (int j = 0; j < 100; j++) {
                    String[] split = Linea.split(";");
                    resultados[j] = split;
                    Linea = br.readLine();
                }
            }
            // catch para capturar cualquier error
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error al leer el archivo");
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        // Retorna una matriz llena con base al archivo
        return resultados;
    }

    // Funcion para calcular las ventas totales en un dia
    public static double dineroDia(String[][] resultados) {

        double transaccion;
        double totalDia = 0;

        // Ciclo para recorrer las filas de las matrices de los archivos
        for (int x = 0; x < 100; x++) {
            transaccion = Double.parseDouble(resultados[x][1]);

            // If para saber a cuanto equivale la transaccion
            if (Integer.parseInt(resultados[x][2]) == 1) {
                transaccion = transaccion * 2;
            }
            if (Integer.parseInt(resultados[x][2]) == 2) {
                transaccion = transaccion * 2.8;
            }

            // Sumatoria de cada linea en la posicion requerida
            totalDia += transaccion;
        }
        return totalDia;
    }

    // Función para saber cual es la hora que más se repite en un archivo
    public static int hora(String[][] resultados) {

        // Array para guardar cada número
        int[] repeticiones = new int[resultados.length];

        // Llenado del array, se usa [4] ya que en esa columna están las horas
        for (int x = 0; x < resultados.length; x++) {
            int numero = Integer.parseInt(resultados[x][4]);
            repeticiones[numero]++;
        }

        int horaMasRepetida = 0;
        int maxRepeticion = 0;

        // Recorre el array para saber la hora más repetida
        for (int x = 0; x < repeticiones.length; x++) {
            if (repeticiones[x] > maxRepeticion) {
                horaMasRepetida = x;
                maxRepeticion = repeticiones[x];
            }
        }

        return horaMasRepetida;
    }

    // Función para hallar la información de un ID que ingrese el usuario
    public static String searchId(String[][] resultados, String id) {

        String response = "";
        // Ciclo para buscar el valor en las columnas 0, si se encuentra concatena todos
        // los valores de esa fila en una cadena
        for (int x = 0; x < resultados.length; x++) {
            if (resultados[x][0].equals(id)) {
                for (int j = 0; j < resultados[x].length; j++) {
                    response += resultados[x][j] + " ";
                }
                break;
            }
        }

        return response;
    }

    // Función para imprimir n datos de x día
    public static void getInfoDeseada(String[][] resultados, String x, int n) {

        System.out.println("A continuación " + n + " transacciones del " + x);
        System.out.println();
        System.out.println("--------------*--------------");
        for (int i = 0; i < n; i++) {
            System.out.println("ID: " + resultados[i][0]);
            System.out.println("Envió: $" + resultados[i][1]);
            switch (resultados[i][2]) {
                case "0":
                    System.out.println("Remitente: natural");
                    break;
                case "1":
                    System.out.println("Remitente: jurídica");
                    break;
                case "2":
                    System.out.println("Remitente: ONG");
                    break;
            }
            switch (resultados[i][3]) {
                case "0":
                    System.out.println("Receptor: natural");
                    break;
                case "1":
                    System.out.println("Receptor: jurídica");
                    break;
                case "2":
                    System.out.println("Receptor: ONG");
                    break;
            }
            System.out.println("Hora del día: " + resultados[i][4]);
            System.out.println("--------------*--------------");
            System.out.println();
            System.out.println("--------------*--------------");

        }

    }

    public static void main(String[] args) {

        // Se llama a la función 1 para cada array con base a un archivo especifico
        String[][] lunesData = fillWeekDays("src\\lunes.txt");
        String[][] martesData = fillWeekDays("src\\martes.txt");
        String[][] miercolesData = fillWeekDays("src\\miercoles.txt");
        String[][] juevesData = fillWeekDays("src\\jueves.txt");
        String[][] viernesData = fillWeekDays("src\\viernes.txt");
        String[][] sabadoData = fillWeekDays("src\\sabado.txt");
        String[][] domingoData = fillWeekDays("src\\domingo.txt");

        // Hashmap para guardar el día y la suma de ventas de ese día
        HashMap<String, Double> ventas = new HashMap<String, Double>();

        ventas.put("Lunes", dineroDia(lunesData));
        ventas.put("Martes", dineroDia(martesData));
        ventas.put("Miercoles", dineroDia(miercolesData));
        ventas.put("Jueves", dineroDia(juevesData));
        ventas.put("Viernes", dineroDia(viernesData));
        ventas.put("Sabado", dineroDia(sabadoData));
        ventas.put("Domingo", dineroDia(domingoData));

        // Buscar el dia con mayor numero de venta
        String maxVentas = null;
        double maxValue = Double.MIN_VALUE;

        // Ciclo para recorrer cada key del hashmap
        for (String dia : ventas.keySet()) {
            double value = ventas.get(dia);

            if (value > maxValue) {
                maxValue = value;
                maxVentas = dia;
            }
        }

        // Hallar a que hora se movió más dinero
        // Hashmap de las horas que mas se repiten cada dia
        HashMap<String, Integer> horas = new HashMap<String, Integer>();

        horas.put("Lunes", hora(lunesData));
        horas.put("Martes", hora(martesData));
        horas.put("Miercoles", hora(miercolesData));
        horas.put("Jueves", hora(juevesData));
        horas.put("Viernes", hora(viernesData));
        horas.put("Sabado", hora(sabadoData));
        horas.put("Domingo", hora(domingoData));

        // Hashmap para saber cuantas veces se repite un valor en el hashmap anterior
        HashMap<Integer, Integer> repeticiones = new HashMap<>();

        // Ciclo que recorre cada valor del hashmap "horas"
        for (int valor : horas.values()) {
            repeticiones.put(valor, repeticiones.getOrDefault(valor, 0) + 1);
        }

        int horaMasRepetida = 0;
        int maxRepeticion = 0;

        // Ciclo para saber cuál hora se repite más
        for (HashMap.Entry<Integer, Integer> entry : repeticiones.entrySet()) {
            if (entry.getValue() > maxRepeticion) {
                horaMasRepetida = entry.getKey();
                maxRepeticion = entry.getValue();
            }
        }

        // Menú de selección
        System.out.println();
        Scanner scanner = new Scanner(System.in);

        System.out.println("----------------------------------------------");
        System.out.println("| ** Bienvenido a la Billetera Digital NV ** |");
        System.out.println("----------------------------------------------");

        int respuesta = 0;
        while (respuesta == 0) {

            System.out.println("Pulse el numero del proceso que desea realizar");
            System.out.println("");
            System.out.println("1. Consultar el dia en el que mas dinero se \n   movio");
            System.out.println("");
            System.out.println("2. Consultar la hora en que mas dinero se mueve \n   en promedio");
            System.out.println("");
            System.out.println("3. Encontrar informacion con tu numero de ID");
            System.out.println("");
            System.out.println("4. Visualizar datos de cierto dia");
            System.out.println("");
            System.out.println("0. Para finalizar el programa");
            try {
                respuesta = scanner.nextInt();

                switch (respuesta) {
                    case 1:
                        // Se imprime el día que más dinero movió
                        System.out.println("-------------------");
                        System.out.println("\nCalculando...");
                        System.out.println("El día de la semana que más dinero movió fue el " + maxVentas);
                        break;
                    case 2:
                        // Se imprime la hora en que más dinero se mueve
                        System.out.println("-------------------");
                        System.out.println("\nCalculando...");
                        System.out.print("La hora en la que más dinero se mueve es la hora " + horaMasRepetida
                                + " con mayoría en ");

                        // Para imprimir los días que contienen esa hora
                        String separador = "";
                        for (HashMap.Entry<String, Integer> entry : horas.entrySet()) {
                            if (entry.getValue() == horaMasRepetida) {
                                System.out.print(separador + entry.getKey());
                                separador = " y ";
                            }
                        }
                        break;
                    case 3:
                        Scanner scanner2 = new Scanner(System.in);
                        String result;

                        // Ciclo para buscar el ID que el usuario ingrese en cada día
                        do {
                            System.out.print("Ingrese el ID a buscar: ");
                            String id = scanner2.nextLine();

                            result = searchId(lunesData, id);
                            if (!result.isEmpty()) {
                                break;
                            }
                            result = searchId(martesData, id);
                            if (!result.isEmpty()) {
                                break;
                            }
                            result = searchId(miercolesData, id);
                            if (!result.isEmpty()) {
                                break;
                            }
                            result = searchId(juevesData, id);
                            if (!result.isEmpty()) {
                                System.out.println(result);
                                break;
                            }
                            result = searchId(viernesData, id);
                            if (!result.isEmpty()) {
                                break;
                            }
                            result = searchId(sabadoData, id);
                            if (!result.isEmpty()) {
                                break;
                            }
                            result = searchId(domingoData, id);
                            if (!result.isEmpty()) {
                                break;
                            }

                            System.out.println("\n*****************");
                            System.out.println("ID no encontrado.");
                            System.out.println("*****************");
                            System.out.println();

                        } while (result.isEmpty());

                        // Se hace un split para poder imprimir la info del ID buscado
                        String[] split = new String[5];
                        for (int j = 0; j < split.length; j++) {
                            split = result.split(" ");
                        }
                        // Se imprimen dicha info
                        System.out.println("ID: " + split[0]);
                        System.out.println("Envió: $" + split[1]);
                        // Se hacen los swithcs para indicar qué tipo de remitente y receptor es
                        switch (split[2]) {
                            case "0":
                                System.out.println("Remitente: natural");
                                break;
                            case "1":
                                System.out.println("Remitente: jurídica");
                                break;
                            case "2":
                                System.out.println("Remitente: ONG");
                                break;
                        }
                        switch (split[3]) {
                            case "0":
                                System.out.println("Receptor: natural");
                                break;
                            case "1":
                                System.out.println("Receptor: jurídica");
                                break;
                            case "2":
                                System.out.println("Receptor: ONG");
                                break;
                        }
                        System.out.println("Hora del día: " + split[4]);

                        break;

                    case 4:
                        // imprimir n datos de x día
                        int contador = 0;
                        while (contador == 0) {

                            try {
                                // Se solicita al usuario ingresar x y n
                                System.out.print("Ingresa el día: ");
                                String x = scanner.next();
                                System.out.print("Ingresa cuántas transacciones del " + x + " quieres ver: ");
                                int n = scanner.nextInt();

                                // Se verifica qué día desea ver el usuario y se llama la función para imprimir
                                // la información la cantidad de veces que el usuario solicitó
                                if (x.equals("lunes")) {
                                    getInfoDeseada(lunesData, x, n);
                                    contador++;
                                } else if (x.equals("martes")) {
                                    getInfoDeseada(martesData, x, n);
                                    contador++;
                                } else if (x.equals("miercoles")) {
                                    getInfoDeseada(miercolesData, x, n);
                                    contador++;
                                } else if (x.equals("jueves")) {
                                    getInfoDeseada(juevesData, x, n);
                                    contador++;
                                } else if (x.equals("viernes")) {
                                    getInfoDeseada(viernesData, x, n);
                                    contador++;
                                } else if (x.equals("sabado")) {
                                    getInfoDeseada(sabadoData, x, n);
                                    contador++;
                                } else if (x.equals("domingo")) {
                                    getInfoDeseada(domingoData, x, n);
                                    contador++;
                                } else {
                                    System.out.println();
                                    System.out.println("Ingrese un día válido");
                                    System.out.println();
                                }
                            } catch (InputMismatchException e) {
                                System.out.println();
                                System.out.println("Ingresa un número válido");
                                System.out.println();
                                scanner.next();

                            }
                        }
                        break;

                    case 0:
                        // Se termina el programa
                        System.out.println("--------------------");
                        System.out.println("Programa terminado");
                        System.out.println("--------------------");
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        // Por si el usuario ingresa un número que no estaba entre las opciones
                        System.out.println("-------------------------");
                        System.out.println("Ingrese un número válido");
                        System.out.println("-------------------------");
                        break;
                }
                // If para saber si el usuario quiere seguir consultando o desea finalizar el
                // programa
                if (respuesta != 0) {
                    int respuesta2 = 0;
                    while (respuesta2 == 0) {
                        System.out.println();
                        System.out.println("------------------------------");
                        System.out.println("Desea realizar otra pregunta?.\n1. si\n2. No, finalizar programa");
                        try {
                            respuesta2 = scanner.nextInt();
                            if (respuesta2 == 1) {
                                respuesta = 0;
                            } else if (respuesta2 == 2) {
                                System.out.println("\n--------------------");
                                System.out.println("Programa finalizado");
                                System.out.println("--------------------");
                                scanner.close();
                                System.exit(0);
                            } else {
                                System.out.println("-------------------------");
                                System.out.println("Ingrese un número válido");
                                System.out.println("-------------------------");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("\n-------------------------");
                            System.out.println("Ingrese un valor numerico");
                            System.out.println("-------------------------");

                            scanner.next();
                        }
                    }
                }
                // Se captura la excepción en caso de que el usuario ingrese otro tipo de dato
                // que no sea int
            } catch (InputMismatchException e) {
                System.out.println("\n-------------------------");
                System.out.println("Ingrese un valor numérico");
                System.out.println("-------------------------");
                System.out.println();
                scanner.next();

            }
        }
        scanner.close();
    }
}