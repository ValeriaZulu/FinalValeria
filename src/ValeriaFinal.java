import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class ValeriaFinal {

    public static String[][] fillWeekDays(String fileName){

        String[][] resultados =new String[100][5];

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;      
            

        try {
            archivo=new File(fileName);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            
            String Linea;
            while ((Linea=br.readLine())!=null) {
                for (int j = 0; j < 100; j++) {           
                String[] split = Linea.split(";");
                resultados[j]=split;
                Linea=br.readLine();
             }
            }

            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error al leer el archivo");
        }finally{
            try {
                if(null != fr){
                    fr.close();
                }
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return resultados;
    }

    public static double dineroDia(String[][]resultados){
      
        double transaccion;
        double totalDia=0;

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

}
}