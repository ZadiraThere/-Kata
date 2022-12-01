import java.util.*;

class Main {

    public static String[] calc(String input) {
        String[] enter = input.split("");
        if (enter.length != 3) {
            Scanner in = new Scanner(System.in);
            System.out.println("Формат математической операции не удовлетворяет заданию - 2 операции и 1 оператор");
            input = in.nextLine();
            return calc(input);
        } else {
            return enter;
        }
    }
    private static boolean its_an_arabic_numbers = true;
    public static void main(String[] args) {
        Scanner exp = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String correct = exp.nextLine();
        while (!correct.isEmpty()) {
            String[] calc_inputString;
            calc_inputString = calc(correct);
            String operation = calc_inputString[1];
            Number values;
            int num1 = 0;
            int num2 = 0;
            try {
                num1 = Integer.parseInt(calc_inputString[0]);
                num2 = Integer.parseInt(calc_inputString[2]);
            } catch (NumberFormatException e) {
                its_an_arabic_numbers = false;
            }
            if (its_an_arabic_numbers) {
                values = new Arabic(num1, num2);
            } else {
                values = new Roman(calc_inputString[0], calc_inputString[2]);
            }
            if (operation.equals("+")) {
                values.sum();
            } else if (operation.equals("-")) {
                values.sub();
            } else if (operation.equals("/")) {
                values.div();
            } else if (operation.equals("*")) {
                values.mul();
            }
            if (its_an_arabic_numbers) {
                System.out.println("Ответ: " + values.getResult());
            } else {
                System.out.println("Ответ: " + values.getStringResult());
            }
            System.out.println();
            System.out.print("Введите выражение: ");
            correct = exp.nextLine();
        }
    }
}
abstract class Number {
    public abstract void sum();
    public abstract void sub();
    public abstract void div();
    public abstract void mul();
    public abstract int getResult();
    public abstract String getStringResult();
}
class Roman extends Number {
    private String roman_value1;
    private String roman_value2;
    private int roman_value1_int;
    private int roman_value2_int;
    private int result_int;
    private String sign = "";
    private String result_string;
    private final String[] roman_letters_9 = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    Roman(String value1, String value2) {
        this.roman_value1 = value1;
        this.roman_value2 = value2;
        this.roman_value1_int = convert_to_int(roman_value1);
        this.roman_value2_int = convert_to_int(roman_value2);
    }
    private String convert_result_to_Roman(int n, int ostatok) {
        ostatok = n % 10;
        if (ostatok != 0) {
            try {
                return convert_result_to_Roman(n - ostatok, 0) + roman_letters_9[ostatok - 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                sign = "-";
                return convert_result_to_Roman(n - ostatok, 0) + roman_letters_9[(ostatok + 1) * -1];
            }
        }
        if (n > 0) {
            n = n - 10;
            return convert_result_to_Roman(n,0) + "X";
        } else if (n < 0) {
            n = n + 10;
            return convert_result_to_Roman(n,0) + "X";
        }   else {
            return sign;
        }
    }
    @Override
    public void sum() {
        result_int = roman_value1_int + roman_value2_int;
        result_string = convert_result_to_Roman(result_int, result_int);
    }
    @Override
    public void sub() {
        result_int = roman_value1_int - roman_value2_int;
        result_string = convert_result_to_Roman(result_int, result_int);
    }
    @Override
    public void div() {
        try {
            result_int = roman_value1_int / roman_value2_int;
            result_string = convert_result_to_Roman(result_int, result_int);
        } catch (ArithmeticException e) {
            System.out.print("Используются одновременно разные системы счисления");
            return;
        }
    }
    @Override
    public void mul() {
        result_int = roman_value1_int * roman_value2_int;
        result_string = convert_result_to_Roman(result_int, result_int);
    }
    @Override
    public int getResult() {
        return result_int;
    }
    public String getStringResult() {
        return result_string;
    }
    private int convert_to_int(String roman_value){
        char[] value_char = roman_value.toCharArray();
        int[] values_int = new int[value_char.length];
        for (int i = 0; i < value_char.length; i++) {
            switch (value_char[i]) {
                case 'I':
                    values_int[i] = 1;
                    break;
                case 'V':
                    values_int[i] = 5;
                    break;
                case 'X':
                    values_int[i] = 10;
                    break;
                default:
                    System.out.println("Неверный символ. Введите заново:" + "\n" +
                            "I = 1" + "\n" +
                            "V = 5" + "\n" +
                            "X = 10");
                    break;
            }
        }
        int result = values_int[0];
        for (int i = 0; i < values_int.length && values_int.length > i + 1; i++) {
            if (values_int[i] >= values_int[i+1]) {
                result += values_int[i+1];
            } else if (values_int[i] < values_int[i+1]) {
                result = result + values_int[i+1] - 2;
            }
        }
        return result;
    }
    public String getRoman_value1() {
        return roman_value1;
    }
    public String getRoman_value2() {
        return roman_value2;
    }
    public void setRoman_value1(String roman_value1) {
        this.roman_value1 = roman_value1;
    }
    public void setRoman_value2(String roman_value2) {
        this.roman_value2 = roman_value2;
    }
    public int getRoman_value1_int() {
        return roman_value1_int;
    }
    public int getRoman_value2_int() {
        return roman_value2_int;
    }
    public void setRoman_value1_int(int roman_value1_int) {
        this.roman_value1_int = roman_value1_int;
    }
    public void setRoman_value2_int(int roman_value2_int) {
        this.roman_value2_int = roman_value2_int;
    }
}
class Arabic extends Number {
    private int value1;
    private int value2;
    private int result;
    Arabic(int value1, int value2) {
        this.value1 = value1;
        this.value2 = value2;
    }
    public void sum() {
        this.result = value1 + value2;
    }
    public void sub() {
        this.result = value1 - value2;
    }
    public void div() {
        try{
            this.result = value1 / value2;
        } catch (ArithmeticException e) {
            return;
        }
    }
    public void mul() {
        this.result = value1 * value2;
    }
    @Override
    public int getResult() {
        return result;
    }
    @Override
    public String getStringResult() {
        return "" + result;
    }
    public int getValue1() {
        return value1;
    }
    public int getValue2() {
        return value2;
    }
    public void setResult(int result) {
        this.result = result;
    }
    public void setValue1(int value1) {
        this.value1 = value1;
    }
    public void setValue2(int value2) {
        this.value2 = value2;
    }
}