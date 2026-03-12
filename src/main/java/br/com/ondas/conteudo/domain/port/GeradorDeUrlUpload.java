package br.com.ondas.conteudo.domain.port;

public interface GeradorDeUrlUpload {
    /**
     * Gera uma URL temporária para o upload de um vídeo.
     * @param nomeArquivo O nome único gerado para o arquivo (ex: UUID.mp4)
     * @return A URL completa e assinada para o upload.
     */
    String gerarPresignedUrl(String nomeArquivo);
}
