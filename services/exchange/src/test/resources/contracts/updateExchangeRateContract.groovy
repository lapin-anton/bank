package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Обновление курса валюты")

    request {
        method 'POST'
        urlPath('/accept')

        headers {
            contentType(applicationJson())
            header('Authorization', value(consumer(regex('Bearer .+')), producer('Bearer test-token')))
        }

        body(
                currency: $(consumer(regex('RUB|USD|EUR')), producer('USD')),
                value: $(consumer(regex('\\d+\\.\\d{2}')), producer('92.50'))
        )
    }

    response {
        status 200
    }
}
