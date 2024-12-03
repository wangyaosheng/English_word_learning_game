package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
  private static final String BASE_PATH = "/Users/wangyaosheng/Desktop/JAVA实验/code/dict_game/System_library/";

  public static void saveWord(String fileName, String word, String status) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(BASE_PATH + fileName, true))) {
      writer.write(word + " - " + status);
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}