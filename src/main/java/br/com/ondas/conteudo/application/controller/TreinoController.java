package br.com.ondas.conteudo.application.controller;

import br.com.ondas.conteudo.domain.entity.Treino;
import br.com.ondas.conteudo.domain.port.GeradorDeUrlUpload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/treinos")
public class TreinoController {

    private final GeradorDeUrlUpload geradorDeUrlUpload;

    public TreinoController(GeradorDeUrlUpload geradorDeUrlUpload) {
        this.geradorDeUrlUpload = geradorDeUrlUpload;
    }

    @PostMapping("/upload-url")
    public ResponseEntity<UrlUploadResponse> solicitarUrlUpload(@RequestParam String nomeArquivo) {
        String urlPresignada = geradorDeUrlUpload.gerarPresignedUrl(nomeArquivo);
        return ResponseEntity.ok(new UrlUploadResponse(urlPresignada));
    }

    @PostMapping
    public ResponseEntity<Treino> publicarTreinoMetadata(@RequestBody PublicarTreinoRequest request) {
        Treino novoTreino = Treino.criarNovoUpload(
                request.professorId(),
                request.titulo(),
                request.descricao(),
                request.categoria(),
                request.urlS3Cru()
        );

        // repository.salvar(novoTreino);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoTreino.getId())
                .toUri();

        return ResponseEntity.created(uri).body(novoTreino);
    }
}
