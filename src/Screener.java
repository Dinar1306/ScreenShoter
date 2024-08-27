import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Screener {
    static final String dir = "screens";
    SimpleDateFormat formatter = new SimpleDateFormat("YYYY.MM.dd HH-mm-ss");

    public void robo() throws Exception
    {
        //снимок всех экранов
		//System.out.println("Метод robo() запущен...");
		//System.out.println("Запуск метода getLocalGraphicsEnvironment() ...");
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		//System.out.println("Запуск метода getScreenDevices() ...");
        GraphicsDevice[] screens = ge.getScreenDevices();
		//System.out.println("Создание объекта Rectangle [ команда new Rectangle() ]...");
        Rectangle allScreenBounds = new Rectangle();
		//System.out.println("Объект Rectangle создан...");
		//System.out.println("Запуск цикла получения суммарной ширины экрана...");
        for (GraphicsDevice screen : screens) {
            Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();
            allScreenBounds.width += screenBounds.width;
            allScreenBounds.height = Math.max(allScreenBounds.height, screenBounds.height);
        }
		//System.out.println("Суммарная ширина экрана получена: "+ allScreenBounds.width + " px");
		//System.out.println("Получение календаря...");
        Calendar now = Calendar.getInstance();
		//System.out.println("Установка полного пути сохранения и названия файла снимка экрана...");
        String out = dir+File.separator+formatter.format(now.getTime())+".jpg";
		//System.out.println("Создание объекта Robot [ команда new Robot() ]...");
        Robot robot = new Robot();
        //BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		System.out.println("Получение снимка экрана...");
        BufferedImage screenShot = robot.createScreenCapture(allScreenBounds);
		System.out.println("Сохранение снимка экрана...");
        ImageIO.write(screenShot, "JPG", new File(out));
               // System.out.println(formatter.format(now.getTime()));
               // System.out.println(out);
    }

    public static void main(String[] args) throws Exception
    {
        int pause = 300000; //по умолчанию интервал между снимками 5 мин.
        //проверка аргументов
        //если args не пуст  //перезапись значения по-умолчанию
		System.out.println("Program started...");
        if (args.length!=0){
            try {
                pause = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Проверьте задаваемые параметры!");
                e.printStackTrace();
            }
        }
        //печать длительности паузы
        System.out.println("Пауза составляет: " + pause + " мс.");

        //Создаем папку для скринов если ее нет
        File screensFolder = new File(dir);
        if (!screensFolder.exists()) {  //если папки не существует, то создаем
            screensFolder.mkdirs();
			System.out.println("Создана папка для скринов...");
        }
        System.out.println("Папка для скринов уже есть...");
		System.out.println("Создание объекта Screener [ команда new Screener() ]...");
        Screener s2i = new Screener();
		System.out.println("Объект Screener создан...");
        while(1==1)
        {
			System.out.println("Начало цикла...");
            try {
				System.out.println("Запуск метода robo() ...");
                s2i.robo();
				System.out.println("Метод robo() завершен...");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
			    System.out.println("Пауза...");
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
