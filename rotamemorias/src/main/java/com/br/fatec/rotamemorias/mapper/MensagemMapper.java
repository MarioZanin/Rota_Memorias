package com.br.fatec.rotamemorias.mapper;

import com.br.fatec.rotamemorias.dto.MensagemDTO;
import com.br.fatec.rotamemorias.model.Mensagem;

@Mapper(componentModel = "spring")
public interface MensagemMapper {
    @Mapping(source = "falecido.id", target = "falecidoId")
    MensagemDTO toDTO(Mensagem mensagem);

    @Mapping(source = "falecidoId", target = "falecido.id")
    Mensagem toEntity(MensagemDTO mensagemDTO);
}
