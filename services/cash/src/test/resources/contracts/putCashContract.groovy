package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Пополнение счёта")

    request {
        method 'PUT'
        urlPath('/put')

        headers {
            contentType(applicationJson())
            header('Authorization', value(consumer(regex('Bearer .+')), producer('Bearer test-token')))
        }

        body(
                accountNumber: $(consumer(regex('\\d{20}')), producer('40817810099910004321')),
                amount: $(consumer(regex('\\d+\\.\\d{2}')), producer('2500.00'))
        )
    }

    response {
        status 200
    }
}