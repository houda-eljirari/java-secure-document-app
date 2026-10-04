package ma.enset.security;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class DocumentHMacVerifier {

    public static void main(String[] args) throws Exception {

        // Même clé secrète que l'émetteur
        String secret = "SDIA_CCN";

        // Chemin du document
        Path documentPath =
                Path.of("documents/document.txt");

        // Chemin du HMAC reçu
        Path hmacPath =
                Path.of("documents/document.hmac");

        // Lire le document
        String data =
                Files.readString(
                        documentPath,
                        StandardCharsets.UTF_8
                );

        // Lire le HMAC reçu
        String receivedSign =
                Files.readString(
                        hmacPath,
                        StandardCharsets.UTF_8
                ).trim();

        // Créer la clé secrète
        SecretKey secretKey =
                new SecretKeySpec(
                        secret.getBytes(StandardCharsets.UTF_8),
                        "HMACSHA256"
                );

        // Créer HMAC
        Mac mac =
                Mac.getInstance("HMACSHA256");

        // Initialiser
        mac.init(secretKey);

        // Recalculer le HMAC
        byte[] hmacSign =
                mac.doFinal(
                        data.getBytes(StandardCharsets.UTF_8)
                );

        // Convertir en Base64 URL
        String calculatedSign =
                Base64.getUrlEncoder()
                        .encodeToString(hmacSign);

        // Afficher
        System.out.println(
                "HMAC reçu      : " + receivedSign
        );

        System.out.println(
                "HMAC recalculé : " + calculatedSign
        );

        // Vérification
        if (calculatedSign.equals(receivedSign)) {

            System.out.println(
                    "\nDOCUMENTVALIDE"
            );

        } else {

            System.out.println(
                    "\nDOCUMENTMODIFIE"
            );
        }
    }
}