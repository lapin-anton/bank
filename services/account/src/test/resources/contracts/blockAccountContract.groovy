package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Заблокировать счёт по ID")

    request {
        method 'PATCH'
        urlPath($(consumer(regex('/[0-9]+/block')), producer('/1234/block')))
    }

    response {
        status 200
    }
}
