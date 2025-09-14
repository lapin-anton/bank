package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Получить счета по ID пользователя")

    request {
        method 'GET'
        urlPath($(consumer(regex('/user/.+')), producer('/user/user-456')))

        headers {
            header('Authorization', value(consumer(regex('Bearer .+')), producer('Bearer test-token')))
        }
    }

    response {
        status 200
        headers {
            contentType(applicationJson())
        }

        body([
                $(consumer(
                        [
                                id      : anyNumber(),
                                number  : anyNonBlankString(),
                                userId  : anyNonBlankString(),
                                balance : anyNumber(),
                                status  : regex('ACTIVE|BLOCKED'),
                                currency: regex('RUB|USD|EUR'),
                                version : anyNumber()
                        ]
                ), producer(
                        [
                                id      : 1001,
                                number  : "40817810000000000001",
                                userId  : "user-456",
                                balance : 5000.00,
                                status  : "ACTIVE",
                                currency: "RUB",
                                version : 1
                        ]
                ))
        ])
    }
}
