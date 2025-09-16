package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Конвертация валюты")

    request {
        method 'POST'
        urlPath('/convert')

        headers {
            contentType(applicationJson())
            header('Authorization', value(consumer(regex('Bearer .+')), producer('Bearer test-token')))
        }

        body(
                fromCurrency: $(consumer(regex('RUB|USD|EUR')), producer('USD')),
                toCurrency: $(consumer(regex('RUB|USD|EUR')), producer('RUB')),
                amount: $(consumer(regex('\\d+\\.\\d{2}')), producer('5000.00'))
        )
    }

    response {
        status 200
        headers {
            contentType(applicationJson())
        }

        body(
                result: "462500.00"
        )
    }
}
