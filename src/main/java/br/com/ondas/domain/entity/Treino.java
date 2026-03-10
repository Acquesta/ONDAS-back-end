package br.com.ondas.domain.entity;

import br.com.ondas.domain.enums.VideoStatus;

import java.util.UUID;

public class Treino {
    private UUID id;
    private UUID professorId;
    private String titulo;
    private String descricao;
    private String categoria;
    private Integer duracaoSegundos;
    private VideoStatus statusVideo;

    // URLs da AWS S3
    private String urlS3Cru;
    private String urlStreamingHls;
    private String urlDownloadMp4;

    private Treino(UUID professorId, String titulo, String descricao, String categoria, String urlS3Cru) {
        this.id = UUID.randomUUID();
        this.professorId = professorId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.urlS3Cru = urlS3Cru;
        this.statusVideo = VideoStatus.PROCESSANDO; // Nasce sempre processando
    }

    public static Treino criarNovoUpload(UUID professorId, String titulo, String descricao, String categoria, String urlS3Cru) {
        return new Treino(professorId, titulo, descricao, categoria, urlS3Cru);
    }

    // --- REGRAS DE NEGÓCIO (Sincronização de Estado) ---

    public void publicarVideoProcessado(String urlHls, String urlMp4, Integer duracao) {
        if (this.statusVideo != VideoStatus.PROCESSANDO) {
            throw new IllegalStateException("Apenas vídeos em processamento podem ser publicados.");
        }
        this.urlStreamingHls = urlHls;
        this.urlDownloadMp4 = urlMp4;
        this.duracaoSegundos = duracao;
        this.statusVideo = VideoStatus.PUBLICADO;
    }

    public void registrarErroDeProcessamento() {
        this.statusVideo = VideoStatus.ERRO;
    }

    public boolean isProntoParaConsumo() {
        return this.statusVideo == VideoStatus.PUBLICADO;
    }

    // Getters omitidos por brevidade
}
