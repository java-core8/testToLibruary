package ru.tcreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {
  final static String PATH = "src/main/resources/Latin-Lipsum.txt";

  public static void main(String[] args) throws IOException {
    System.out.println(getSortArrayList(
            parseByteToStringList(
                    getByteBufferByPath(PATH)
            )
    ));
  }

  /**
   * Перегоняет содержимое файла в массив байт
   * @param path
   * @return byte[]
   * @throws IOException
   */
  static public byte[] getByteBufferByPath(String path) throws IOException {
    File file = new File(path);
    FileInputStream inputStream = new FileInputStream(file);
    byte[] byteBuffer = new byte[inputStream.available()];
    inputStream.read(byteBuffer, 0, byteBuffer.length);
    inputStream.close();
    return byteBuffer;
  }

  /**
   * Парсит массив байт в список слов
   * пробелы, переносы строк, запятые и точки пропускает
   * @param byteArray
   * @return {@link ArrayList<String>}
   */
  static public ArrayList<String> parseByteToStringList(byte[] byteArray) {
    ArrayList<String> stringArray = new ArrayList<>();
    StringBuilder tmpStr = new StringBuilder();
    for (int i = 0; i < byteArray.length; i++) {
      switch (byteArray[i]) {
        case 32: {
          stringArray.add(tmpStr.toString());
          tmpStr = new StringBuilder();
        }
        break;
        case 13:
        case 10:
        case 46: // схитрил немного и сразу снёс запятые с точками
        case 44:
          break;
        default:
          tmpStr.append((char) byteArray[i]);
      }
    }
    return stringArray;
  }

  /**
   * Сортирует список строк по алфавиту и возвращает отсортированный список
   * @param list {@link ArrayList<String>} список
   * @return {@link ArrayList<String>} отсортированный список
   */
  static public ArrayList<String> getSortArrayList(ArrayList<String> list) {
    return list.stream()
            .sorted()
            .collect(
                    Collectors.toCollection(ArrayList::new));
  }
}
