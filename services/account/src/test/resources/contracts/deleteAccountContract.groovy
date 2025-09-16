package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Удалить счёт по ID")

    request {
        method 'DELETE'
        urlPath($(consumer(regex('/user/[0-9]+')), producer('/user/123')))

        headers {
            header('Authorization', value(consumer(regex('Bearer .+')), producer('Bearer test-token')))
        }
    }

    response {
        status 200
    }
}
