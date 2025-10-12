package ru.yandex.practicum.bank.client.blocker.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.bank.client.blocker.model.CashCheckDto;
import ru.yandex.practicum.bank.client.blocker.model.ResultCheckDto;
import ru.yandex.practicum.bank.client.blocker.model.TransferCheckDto;
import ru.yandex.practicum.bank.common.resilience.ResilienceExecutor;

@Component
@RequiredArgsConstructor
public class BlockerClient {

    private final BlockerApi blockerApi;

    private final ResilienceExecutor resilience;

    public ResultCheckDto checkCash(CashCheckDto dto) {
        return resilience.execute(
                () -> blockerApi.checkCash(dto),
                this::defaultResult
        );
    }

    public ResultCheckDto checkTransfer(TransferCheckDto dto) {
        return resilience.execute(
                () -> blockerApi.checkTransfer(dto),
                this::defaultResult
        );
    }

    private ResultCheckDto defaultResult() {
        ResultCheckDto resultCheckDto = new ResultCheckDto();

        resultCheckDto.setResult(false);

        return resultCheckDto;
    }
}
