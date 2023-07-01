import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Task_HW_3{

private static String fileName = null;

    public static void main(String[] args) {
        dataFile(checkUserInput(getUserInput()));  
    }

    public static String[] getUserInput(){
        System.out.println("Введите Ваши данные в следующих форматах:");
        System.out.println("1. 'Фамилия, Имя, Отчество'");
        System.out.println("2.  Номер телефона:'111111111111'");
        System.out.println("Используйте пробел для разделения:");
        String [] userInputArray = null;
        boolean status=true;
        while(status){
          try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            String userInput = bufferedReader.readLine();
            if(userInput.isBlank()){
                throw new IllegalArgumentException("Неверный формат ввода: Вы ввели пустую строку. Попробйте снова.");
            }
            userInputArray = userInput.split(" ");
            } catch (IOException ex) {
             System.out.println("Наблюдаются проблемы со вводом/выводом.");
             ex.printStackTrace();
          }
        if (userInputArray.length!=4){
            throw new IllegalArgumentException("Неверный формат ввода: Вы ввели неверное количество параметров - введите снова");
        }else {
            status=false;
        }
        
    }
    return userInputArray;
}

public static HashMap<String,String> checkUserInput(String [] arrayStr){
    HashMap<String, String> resultData = new HashMap<String,String>();
    for(int i=0; i<arrayStr.length;i++){

       if (arrayStr[i].contains(",")){
            if (checkFullName(arrayStr[i])){
                resultData.put("Full name", arrayStr[i]);
            }
        } else if(!arrayStr[i].contains(".") && !arrayStr[i].contains(",") ){
            if (checkPhone(arrayStr[i])){
                resultData.put("Phone number", arrayStr[i]);
            }   
        } else{
            throw new IllegalArgumentException("Неверный формат ввода - попробуйте проверить его");
        }
    }
    return resultData;
}
    
public static boolean checkFullName(String nameStr) {
    boolean result=false;
    String pattern="\\D+";
    String [] tempArray=nameStr.split(",");
    fileName=tempArray[0]+".txt";
    if (tempArray.length!=3){
        throw new IllegalArgumentException("Неверный формат ПОЛНОГО ИМЕНИ. Используйте формат 'ФИО'. Проверьте "+nameStr);
    }else{
        for(int i=0; i<tempArray.length;i++){//проверяем, что в ФИО нет цифр.
            if(!Pattern.matches(pattern, tempArray[i])){//если строка содержит число,то ошибка
                throw new IllegalArgumentException ("Неверный формат '" + tempArray[i] + "'. Полное имя не должно содержать цифровых обозначений.");
            }
        }
        result=true;
    }
    return result;
}

public static boolean checkPhone(String phoneStr) {
    boolean result=false;
    try {
        Double.parseDouble(phoneStr);
        result=true;
    }
    catch (NumberFormatException exception) {
        System.out.println("Неверный формат номера телефона. Задан только номер. Проверьте "+phoneStr);
    }
    return result;
}

public static void dataFile(HashMap<String,String> data) {
    String currPathName = System.getProperty("user.dir");
    File currUserFile = new File(currPathName, fileName);
    try (FileWriter userData = new FileWriter(currUserFile,true);){
        for (HashMap.Entry<String, String> item : data.entrySet()) {
                userData.write(item.getKey()+" - ");
                userData.write(item.getValue()+";"+"\n");
        }
    System.out.println("Файл '"+fileName+"' был успешно создан "+" в папке '"+currPathName+"'.");
    } catch (IOException e) {
        System.out.println("Невозможно создать файл "+ fileName +".");
        e.printStackTrace();
    }
    
}

}

