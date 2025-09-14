package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Изменить баланс счёта по ID")

    request {
        method 'PATCH'
        urlPath($(consumer(regex('/[0-9]+/balance')), producer('/1234/balance')))

        headers {
            contentType(applicationJson())
        }

        body(
                amount: $(consumer(regex('\\d+\\.\\d{2}')), producer('1500.00')),
                version: $(consumer(regex('\\d+')), producer('2'))
        )
    }

    response {
        status 200
    }
}
