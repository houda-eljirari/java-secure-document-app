package ma.enset.security;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Base64;

public class CertificateReader {

    public static void main(String[] args) throws Exception {

        // Chemin du certificat
        String certificatePath = "certificate.cer";

        // Créer une CertificateFactory X.509
        CertificateFactory factory =
                CertificateFactory.getInstance("X.509");

        // Lire le certificat
        try (InputStream inputStream =
                     new FileInputStream(certificatePath)) {

            Certificate certificate =
                    factory.generateCertificate(inputStream);

            // Afficher les informations du certificat
            System.out.println("===== CERTIFICAT X.509 =====");
            System.out.println(certificate);

            // Récupérer la clé publique
            PublicKey publicKey =
                    certificate.getPublicKey();

            System.out.println("\n===== CLÉ PUBLIQUE =====");

            // Algorithme
            System.out.println(
                    "Algorithme : "
                            + publicKey.getAlgorithm()
            );

            // Format
            System.out.println(
                    "Format : "
                            + publicKey.getFormat()
            );

            // Base64
            String publicKeyBase64 =
                    Base64.getEncoder()
                            .encodeToString(
                                    publicKey.getEncoded()
                            );

            System.out.println(
                    "Clé publique Base64 :"
            );

            System.out.println(publicKeyBase64);
        }
    }
}