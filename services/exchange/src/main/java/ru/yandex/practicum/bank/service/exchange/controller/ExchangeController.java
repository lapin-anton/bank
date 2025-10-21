package ru.yandex.practicum.bank.service.exchange.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bank.service.exchange.dto.RateDto;
import ru.yandex.practicum.bank.service.exchange.dto.ConvertRequestDto;
import ru.yandex.practicum.bank.service.exchange.dto.ConvertResponseDto;
import ru.yandex.practicum.bank.service.exchange.service.ExchangeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping("/convert")
    public ResponseEntity<ConvertResponseDto> convert(@Valid @RequestBody ConvertRequestDto convertRequestDto) {
        return ResponseEntity.ok(exchangeService.convert(convertRequestDto));
    }

    @PostMapping("/accept")
    public ResponseEntity<Void> acceptRate(@Valid @RequestBody RateDto rateDto) {
        exchangeService.acceptRate(rateDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/rates")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity<List<RateDto>> rates() {
        return ResponseEntity.ok(exchangeService.rates());
    }
}
