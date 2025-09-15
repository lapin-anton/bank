package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Проверка операции перевода")

    request {
        method 'POST'
        urlPath('/transfer')

        headers {
            contentType(applicationJson())
            header('Authorization', value(consumer(regex('Bearer .+')), producer('Bearer test-token')))
        }

        body(
                fromAccount: $(consumer(regex('\\d{20}')), producer('40817810099910001111')),
                toAccount: $(consumer(regex('\\d{20}')), producer('40817810099910002222')),
                amount: $(consumer(regex('\\d+\\.\\d{2}')), producer('1500.00'))
        )
    }

    response {
        status 200
        headers {
            contentType(applicationJson())
        }

        body(
                result: true
        )
    }
}
