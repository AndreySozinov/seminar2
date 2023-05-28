package ru.savrey.lesson2;

import java.util.Random;
import java.util.Scanner;

public class Program {

    private static int WIN_COUNT = 3;
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = 'O';
    private static final char DOT_EMPTY = '▪';

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final Random random = new Random();

    /**
    * Игровое поле.
     */
    private static char[][] field;

    /**
    * Размерность игрового поля.
     */
    private static int fieldSizeX;
    private static int fieldSizeY;

    public static void main(String[] args) {
        do {
            initialize();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (gameCheck(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn();
                printField();
                if (gameCheck(DOT_AI, "Вы проиграли."))
                    break;
            }
            System.out.println("Желаете сыграть еще раз? (Y - да)");
        } while (SCANNER.next().equalsIgnoreCase("Y"));
    }

    /**
     * Инициализация игрового поля.
     */
    private static void initialize(){
        //Ставим размерность поля.
        int x, y;
        do{
            System.out.print("Введите размеры игрового поля X и Y (не меньше 3) через пробел >>> ");
            x = SCANNER.nextInt();
            y = SCANNER.nextInt();
        } while (x <= 2 || y <= 2);
        fieldSizeX = x;
        fieldSizeY = y;

        if (x > 3 && y > 3){
            int l;
            do{
                System.out.print("Введите число, подряд расположенных, фишек для выигрыша (не меньше 3) >>> ");
                l = SCANNER.nextInt();
            } while (l <= 2);
            WIN_COUNT = l;
        }

        field = new char[fieldSizeX][fieldSizeY];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                field[j][i] = DOT_EMPTY;
            }
        }
    }

    /**
     * Отрисовка игрового поля.
     */
    private static void printField(){

        // Header
        System.out.print(" ┌");
        for (int i = 1; i < fieldSizeX * 2; i++){
            System.out.print((i % 2 == 0) ? "┬" : i / 2 +1);
        }
        System.out.println("┐");

        //body
        for (int i = 0; i < fieldSizeY; i++){
            System.out.print(i + 1 + "│");
            for (int j = 0; j < fieldSizeX; j++){
                System.out.print(field[j][i] + "│");
            }
            System.out.println();
        }

         // Footer
        System.out.print(" └");
        for (int i = 0; i < fieldSizeX * 2 - 1; i++){
            System.out.print((i % 2 == 0) ? "─" : "┴");
        }
        System.out.println("┘");
    }

    /**
     * Обработка хода игрока (человек).
     */
    private static void humanTurn(){
        int x, y;
        do {
            System.out.print("Введите координаты хода X и Y через пробел >>> ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }

    /**
     * Обработка хода компьютера
     */
    private static void aiTurn(){
        int x, y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
    }

    /**
     * Проверка, ячейка не занята.
     * @param x координата X
     * @param y координата Y
     * @return true or false
     */
    static boolean isCellEmpty(int x, int y){
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка корректности введенных координат
     * @param x координата X
     * @param y координата Y
     * @return true or false
     */
    static boolean isCellValid(int x, int y){
        return x >=0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Проверка победы
     * @param c проверяемая фишка игрока - компьютера или человека
     * @return true or false
     */
    static boolean checkWin(char c){
        for (int i = 0; i < fieldSizeX; i++){
            for (int j = 0; j < fieldSizeY; j++){
                if (countRowRight(i, j, c) >= WIN_COUNT || countRowUpRight(i, j, c) >= WIN_COUNT
                        || countRowDownRight(i, j, c) >= WIN_COUNT || countRowDown(i, j, c) >= WIN_COUNT){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Подсчет подряд расположенных фишек вверх и вправо.
     * @param x начальная координата Х
     * @param y начальная координата Y
     * @param c проверяемая фишка игрока или компьютера
     * @return число подряд расположенных фишек
     */
    static int countRowUpRight(int x, int y, char c){
        int count = 0;
        do {
            if (field[x][y] == c) {
                count++;
                x++;
                y--;
            }
            else return count;
        } while (x < fieldSizeX && y >= 0);
        return count;
    }

    /**
     * Подсчет подряд расположенных фишек вправо.
     * @param x начальная координата Х
     * @param y начальная координата Y
     * @param c проверяемая фишка игрока или компьютера
     * @return число подряд расположенных фишек
     */
    static int countRowRight(int x, int y, char c){
        int count = 0;
        do {
            if (field[x][y] == c) {
                count++;
                x++;
            }
            else return count;
        } while (x < fieldSizeX);
        return count;
    }

    /**
     * Подсчет подряд расположенных фишек вниз и вправо.
     * @param x начальная координата Х
     * @param y начальная координата Y
     * @param c проверяемая фишка игрока или компьютера
     * @return число подряд расположенных фишек
     */
    static int countRowDownRight(int x, int y, char c){
        int count = 0;
        do {
            if (field[x][y] == c) {
                count++;
                x++;
                y++;
            }
            else return count;
        } while (x < fieldSizeX && y < fieldSizeY);
        return count;
    }

    /**
     * Подсчет подряд расположенных фишек вниз.
     * @param x начальная координата Х
     * @param y начальная координата Y
     * @param c проверяемая фишка игрока или компьютера
     * @return число подряд расположенных фишек
     */
    static int countRowDown(int x, int y, char c){
        int count = 0;
        do {
            if (field[x][y] == c) {
                count++;
                y++;
            }
            else return count;
        } while (y < fieldSizeY);
        return count;
    }

    /**
     * Проверка ничьей
     * @return true or false
     */
    static boolean checkDraw(){
        for (int x= 0; x < fieldSizeX; x++){
            for (int y = 0; x < fieldSizeY; y++){
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * Метод проверки состояния игры
     * @param c проверяемая фишка игрока или компьютера
     * @param str выводимое сообщение
     * @return true or false
     */
    static boolean gameCheck(char c, String str){
        if (checkWin(c)){
            System.out.println(str);
            return true;
        }
        if (checkDraw()){
            System.out.println("Ничья!");
            return true;
        }
        return false; // Игра продолжается.
    }
}
