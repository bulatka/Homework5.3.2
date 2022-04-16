import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(250, 5, 16, 12.22);
        GameProgress gameProgress2 = new GameProgress(320, 7, 23, 15.45);
        GameProgress gameProgress3 = new GameProgress(1000, 2, 12, 14.54);
        saveGame("D://Games/savegames/save1.dat", gameProgress1);
        saveGame("D://Games/savegames/save2.dat", gameProgress2);
        saveGame("D://Games/savegames/save3.dat", gameProgress3);

        zipFiles("D://Games/savegames/zipsaves.zip",
                "D://Games/savegames/save1.dat",
                "D://Games/savegames/save2.dat",
                "D://Games/savegames/save3.dat");
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(String zipPath, String... filePaths) {
        try (FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zout = new ZipOutputStream(fos)) {
            for (int i = 0; i < filePaths.length; i++) {
                File file = new File(filePaths[i]);
                FileInputStream fis = new FileInputStream(file);
                ZipEntry zip = new ZipEntry("save" + (i + 1) + ".dat");

                zout.putNextEntry(zip);
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                zout.write(bytes);
                zout.closeEntry();
                fis.close();
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}