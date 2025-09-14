package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Получить счёт по номеру")

    request {
        method 'GET'
        urlPath($(consumer(regex('/\\d{20}')), producer('/40817810099910004321')))
    }

    response {
        status 200
        headers {
            contentType(applicationJson())
        }

        body(
                id      : anyNumber(),
                number  : anyNonBlankString(),
                userId  : anyNonBlankString(),
                balance : anyNumber(),
                status  : regex('ACTIVE|BLOCKED'),
                currency: regex('RUB|USD|EUR'),
                version : anyNumber()
        )
    }
}
