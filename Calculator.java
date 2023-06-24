// ****
// calc
// ****

import java.util.Scanner;                               // Подключаем класс Scanner

public class Calculator {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("""
                Введите запрос на арифметическую операцию строго в формате
                                       a х b
                где a - первое число, пробел, b - последнее число, пробел, а х - арифметическая операция,
                присутствующая в этом списке +, -, *, /""");                   // Запрос ввода
        String expression = input.nextLine();           // Ввод выражения
        input.close();
        String answer = Main.calc(expression.trim());        // Метод calc для объекта result
        System.out.println("Результат:\n" + answer);        // Вывожу результат
    }
}

class Main {
    public static String calc(String input){
        boolean romanOrArabic = false;   // Для понимания какое число на выходе - римское или арабское)
        String exception = "Некорректный ввод арифметического выражения\n" +
                "работа программы завершена";            // Текст ввода исключения
        Main romanExam = new Main();               // Вводим для проверки и перевода из рим в араб
        Main arabicToRoman = new Main();           // Для перевода ответа в римские
        int result;                                 // Считает выражение
        String[] inputConv = input.split(" ");
        if (inputConv .length != 3){
            return exception;                             // Неверный ввод данных
        }
        Integer alphaNum;
        Integer omegaNum;
        try {
            alphaNum = Integer.valueOf(inputConv[0]);
            omegaNum = Integer.valueOf(inputConv[2]);
        } catch (NumberFormatException e) {                          // Не арабское число
            try {
                alphaNum = romanExam.romanToArabic(inputConv[0]);
                omegaNum = romanExam.romanToArabic(inputConv[2]);
                romanOrArabic = true;
            } catch (NumberFormatException ex) {                     // Не римское число
                return exception;
            }
        }

        if ((alphaNum < 1) || (alphaNum > 10) || (omegaNum < 1) || (omegaNum > 10)){
            return exception;                                       // Указываем диапазон значений
        }

        String sign = inputConv[1];
        switch (sign) {
            case "+" -> result = alphaNum + omegaNum;
            case "-" -> result = alphaNum - omegaNum;
            case "*" -> result = alphaNum * omegaNum;
            case "/" -> result = alphaNum / omegaNum;
            default -> {
                return exception;                                    // Не арифметическая операция
            }
        }

        String output;                                               // Возврат метода

        if (romanOrArabic){
            if(result < 1){
                return "        меньше единицы\nработа программы завершена";
            } else {
                output = arabicToRoman.arabicToRome(result);
            }
        } else {
            output = Integer.toString(result);
        }

        return output;
    }

    String arabicToRome(int arInput){                                  // Перевод арабских цифер в римские
        String result = "";
        int value;
        int[] arabic = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        for (int i = 0; i < arabic.length; i++){
            value = arInput / arabic[i];
            for (int j = 0; j < value; j++){
                result = result.concat(roman[i]);
            }
            arInput = arInput % arabic[i];
        }
        return result;
    }
    Integer romanToArabic(String romInput){               // Переводим римскую цифирь в арабскую
        int result = 0;
        int[] arabic = {10, 9, 5, 4, 1};
        String[] roman = {"X", "IX", "V", "IV", "I"};
        for (int i = 0; i < arabic.length; i++ ) {
            while (romInput.indexOf(roman[i]) == 0) {
                result += arabic[i];
                romInput = romInput.substring(roman[i].length());  // Исключаем посчитанные числа
            }
        }
        return result;
    }
}