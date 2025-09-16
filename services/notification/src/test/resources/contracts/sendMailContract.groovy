package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Send email when valid MailDto is POSTed with Authorization header")

    request {
        method 'POST'
        urlPath('/mail')

        headers {
            contentType(applicationJson())
            header('Authorization', value(consumer(regex('Bearer .+')), producer('Bearer test-token')))
        }

        body(
                email: $(consumer(regex('.+@.+\\..+')), producer('user@example.com')),
                subject: $(consumer(anyNonBlankString()), producer('Уведомление о платеже')),
                text: $(consumer(anyNonBlankString()), producer('Ваш перевод успешно выполнен.'))
        )
    }

    response {
        status 200
    }
}
