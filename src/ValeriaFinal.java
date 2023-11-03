import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
            for (int j = 0; j < 100; j++) {           
                String[] split = Linea.split(";");
                resultados[j]=split;
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
    
    public static void main(String[] args) {

        String[] dias = {"lunes","martes","miercoles","jueves","viernes","sabado","domingo"};
        String[][] lunesData = fillWeekDays("lunes.txt");
        String[][] martesData = fillWeekDays("martes.txt");
        String[][] miercolesData = fillWeekDays("miercoles.txt");
        String[][] juevesData = fillWeekDays("jueves.txt");
        String[][] viernesData = fillWeekDays("viernes.txt");
        String[][] sabadoData = fillWeekDays("sabado.txt");
        String[][] domingoData = fillWeekDays("domingo.txt");

        
}
}