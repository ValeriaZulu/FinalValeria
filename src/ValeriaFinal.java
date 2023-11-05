import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class ValeriaFinal {

    public static String[][] fillWeekDays(String fileName) {

        String[][] resultados = new String[100][5];

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File(fileName);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String Linea;
            while ((Linea = br.readLine()) != null) {
                for (int j = 0; j < 100; j++) {
                    String[] split = Linea.split(";");
                    resultados[j] = split;
                    Linea = br.readLine();
                }
            }

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
        return resultados;
    }

    public static double dineroDia(String[][] resultados) {

        double transaccion;
        double totalDia = 0;

        for (int x = 0; x < 100; x++) {
            transaccion = Double.parseDouble(resultados[x][1]);

            if (Integer.parseInt(resultados[x][2]) == 1) {
                transaccion = transaccion * 2;
            }
            if (Integer.parseInt(resultados[x][2]) == 2) {
                transaccion = transaccion * 2.8;
            }

            totalDia += transaccion;
        }
        return totalDia;
    }

    public static int hora(String[][] resultados) {

        int[] repeticiones = new int[resultados.length];

        for (int x = 0; x < resultados.length; x++) {
            int numero = Integer.parseInt(resultados[x][4]);
            repeticiones[numero]++;
        }

        int horaMasRepetida = 0;
        int maxRepeticion = 0;

        for (int x = 0; x < repeticiones.length; x++) {
            if (repeticiones[x] > maxRepeticion) {
                horaMasRepetida = x;
                maxRepeticion = repeticiones[x];
            }
        }

        return horaMasRepetida;
    }

    public static String searchId(String[][] resultados, String id) {

        String response = "";

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

    public static void main(String[] args) {

        String[][] lunesData = fillWeekDays("src\\lunes.txt");
        String[][] martesData = fillWeekDays("src\\martes.txt");
        String[][] miercolesData = fillWeekDays("src\\miercoles.txt");
        String[][] juevesData = fillWeekDays("src\\jueves.txt");
        String[][] viernesData = fillWeekDays("src\\viernes.txt");
        String[][] sabadoData = fillWeekDays("src\\sabado.txt");
        String[][] domingoData = fillWeekDays("src\\domingo.txt");

        HashMap<String, Double> ventas = new HashMap<String, Double>();

        ventas.put("Lunes", dineroDia(lunesData));
        ventas.put("Martes", dineroDia(martesData));
        ventas.put("Miercoles", dineroDia(miercolesData));
        ventas.put("Jueves", dineroDia(juevesData));
        ventas.put("Viernes", dineroDia(viernesData));
        ventas.put("Sabado", dineroDia(sabadoData));
        ventas.put("Domingo", dineroDia(domingoData));

        String maxVentas = null;
        double maxValue = Double.MIN_VALUE;

        for (String dia : ventas.keySet()) {
            double value = ventas.get(dia);

            if (value > maxValue) {
                maxValue = value;
                maxVentas = dia;
            }
        }
        System.out.println("El día de la semana que más dinero movió fue el " + maxVentas);

        HashMap<String, Integer> horas = new HashMap<String, Integer>();

        horas.put("Lunes", hora(lunesData));
        horas.put("Martes", hora(martesData));
        horas.put("Miercoles", hora(miercolesData));
        horas.put("Jueves", hora(juevesData));
        horas.put("Viernes", hora(viernesData));
        horas.put("Sabado", hora(sabadoData));
        horas.put("Domingo", hora(domingoData));

        HashMap<Integer, Integer> repeticiones = new HashMap<>();

        for (int valor : horas.values()) {
            repeticiones.put(valor, repeticiones.getOrDefault(valor, 0) + 1);
        }

        int horaMasRepetida = 0;
        int maxRepeticion = 0;

        for (HashMap.Entry<Integer, Integer> entry : repeticiones.entrySet()) {
            if (entry.getValue() > maxRepeticion) {
                horaMasRepetida = entry.getKey();
                maxRepeticion = entry.getValue();
            }
        }

        System.out.print("La hora en la que más dinero se mueve es la hora " + horaMasRepetida + " con mayoría en ");

        String separador = "";
        for (HashMap.Entry<String, Integer> entry : horas.entrySet()) {
            if (entry.getValue() == horaMasRepetida) {
                System.out.print(separador + entry.getKey());
                separador = " y ";
            }
        }

        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID a buscar: ");
        String id = scanner.nextLine();

        String result;
        do {
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

            System.out.println("ID no encontrado.");

        } while (result.isEmpty());

        String[] split = new String[5];
        for (int j = 0; j < split.length; j++) {
            split = result.split(" ");
        }
        System.out.println("ID: " + split[0]);
        System.out.println("Envió: $" + split[1]);
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

        scanner.close();

    }
}