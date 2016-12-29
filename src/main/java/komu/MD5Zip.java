package komu;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class MD5Zip {

    private static String checksum(File file) throws IOException, NoSuchAlgorithmException {
        try (InputStream in = new FileInputStream(file)) {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            ZipInputStream zip = new ZipInputStream(in);
            byte[] buffer = new byte[2048];
            ZipEntry entry;

            while ((entry = zip.getNextEntry()) != null) {
                digest.update(entry.getName().getBytes(UTF_8));

                int n;
                while ((n = zip.read(buffer)) != -1)
                    digest.update(buffer, 0, n);
            }

            return DatatypeConverter.printHexBinary(digest.digest()).toLowerCase();
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("usage: java -jar md5zip.jar ZIP-FILE ...");
            System.exit(1);
        }

        try {
            for (String file : args)
                System.out.println(file + " = " + checksum(new File(file)));

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
