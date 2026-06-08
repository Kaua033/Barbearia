package Minha.Barbearia.Agendamento;

import java.time.LocalDateTime;

public record AgedamentoDTO(
        Long clienteId,
        Long barbeiroId,
        Long servicoId,
        LocalDateTime dataHora,
        String status
){}
