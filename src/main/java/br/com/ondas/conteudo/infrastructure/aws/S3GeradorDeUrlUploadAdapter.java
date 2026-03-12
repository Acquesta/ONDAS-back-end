package br.com.ondas.conteudo.infrastructure.aws;

import br.com.ondas.conteudo.domain.port.GeradorDeUrlUpload;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.time.Duration;
import org.springframework.stereotype.Component;

@Component
public class S3GeradorDeUrlUploadAdapter  implements GeradorDeUrlUpload {
    private final S3Presigner s3Presigner;
    private final String bucketEntrada;

    // O Spring injeta o nome do bucket direto do seu arquivo application.properties
    public S3GeradorDeUrlUploadAdapter(
            S3Presigner s3Presigner,
            @Value("${aws.s3.bucket.entrada}") String bucketEntrada) {
        this.s3Presigner = s3Presigner;
        this.bucketEntrada = bucketEntrada;
    }

    @Override
    public String gerarPresignedUrl(String nomeArquivo) {
        // 1. Prepara as instruções de como o arquivo deve ser salvo no S3
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketEntrada)
                .key("treinos/raw/" + nomeArquivo) // Organizando dentro de uma pasta
                .contentType("video/mp4")          // Forçando o tipo para evitar uploads maliciosos
                .build();

        // 2. Assina o pedido garantindo que ele expire (ex: em 30 minutos)
        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(requisicao -> requisicao
                .signatureDuration(Duration.ofMinutes(30))
                .putObjectRequest(objectRequest)
        );

        // 3. Retorna a URL pronta para o Frontend (React) usar no formulário
        return presignedRequest.url().toString();
    }
}
