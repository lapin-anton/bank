package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Проверка операции пополнения")

    request {
        method 'POST'
        urlPath('/cash')

        headers {
            contentType(applicationJson())
            header('Authorization', value(consumer(regex('Bearer .+')), producer('Bearer test-token')))
        }

        body(
                accountNumber: $(consumer(regex('\\d{20}')), producer('40817810099910001111')),
                amount: $(consumer(regex('\\d+\\.\\d{2}')), producer('1000.00'))
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
