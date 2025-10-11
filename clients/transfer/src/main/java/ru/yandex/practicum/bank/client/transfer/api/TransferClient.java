package ru.yandex.practicum.bank.client.transfer.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.bank.client.transfer.model.TransferDto;
import ru.yandex.practicum.bank.common.resilience.ResilienceExecutor;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransferClient {

    private final TransferApi transferApi;

    private final ResilienceExecutor resilience;


    public void transfer(TransferDto transferDto) {
        resilience.execute(
                () -> {
                    transferApi.transfer(transferDto);
                    return null;
                },
                () -> {
                    log.warn("Fallback: transfer from {} to {} failed",
                            transferDto.getFromAccount(), transferDto.getToAccount());
                    return null;
                }
        );
    }
}
