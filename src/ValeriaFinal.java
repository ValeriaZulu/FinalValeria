import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

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

        for (int i = 0; i < 100; i++) {
            transaccion = Double.parseDouble(resultados[i][1]);

            if (Integer.parseInt(resultados[i][2]) == 1) {
                transaccion = transaccion * 2;
            }
            if (Integer.parseInt(resultados[i][2]) == 2) {
                transaccion = transaccion * 2.8;
            }

            totalDia += transaccion;
        }
        return totalDia;
    }

    public static int hora(String[][] resultados) {

        int[] repeticiones = new int[resultados.length];

        for (int i = 0; i < resultados.length; i++) {
            int numero = Integer.parseInt(resultados[i][4]);
            repeticiones[numero]++;
        }

        int horaMasRepetida = 0;
        int maxRepeticion = 0;

        for (int i = 0; i < repeticiones.length; i++) {
            if (repeticiones[i] > maxRepeticion) {
                horaMasRepetida = i;
                maxRepeticion = repeticiones[i];
            }
        }

        return horaMasRepetida;
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

        int valorMasRepetido = 0;
        int maxRepeticion = 0;

        for (HashMap.Entry<Integer, Integer> entry : repeticiones.entrySet()) {
            if (entry.getValue() > maxRepeticion) {
                valorMasRepetido = entry.getKey();
                maxRepeticion = entry.getValue();
            }
        }

        System.out.print("La hora en la que más dinero se mueve es la hora " + valorMasRepetido + " con mayoría en ");

        String separador = "";
        for (Map.Entry<String, Integer> entry : horas.entrySet()) {
            if (entry.getValue() == valorMasRepetido) {
                System.out.print(separador + entry.getKey());
                separador = " y ";
            }
        }
    }
}