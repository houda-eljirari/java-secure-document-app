package ma.enset.security;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class DocumentHMacGenerator {

    public static void main(String[] args) throws Exception {

        // Clé secrète donnée dans l'exemple du professeur
        String secret = "SDIA_CCN";

        // Fichier contenant le document
        Path documentPath =
                Path.of("documents/document.txt");

        // Fichier où enregistrer le HMAC
        Path hmacPath =
                Path.of("documents/document.hmac");

        // Lire le contenu du document
        String data =
                Files.readString(
                        documentPath,
                        StandardCharsets.UTF_8
                );

        // Créer la clé secrète
        SecretKey secretKey =
                new SecretKeySpec(
                        secret.getBytes(StandardCharsets.UTF_8),
                        "HMACSHA256"
                );

        // Créer le HMAC
        Mac mac =
                Mac.getInstance("HMACSHA256");

        // Initialiser avec la clé
        mac.init(secretKey);

        // Calculer le HMAC
        byte[] hmacSign =
                mac.doFinal(
                        data.getBytes(StandardCharsets.UTF_8)
                );

        // Convertir en Base64 comme dans l'exemple du professeur
        String signature =
                Base64.getUrlEncoder()
                        .encodeToString(hmacSign);

        // Afficher
        System.out.println("===== DOCUMENT =====");
        System.out.println(data);

        System.out.println("\n===== HMAC =====");
        System.out.println(signature);

        // Enregistrer dans document.hmac
        Files.writeString(
                hmacPath,
                signature,
                StandardCharsets.UTF_8
        );

        System.out.println(
                "\nHMAC enregistré dans : "
                        + hmacPath
        );
    }
}