package jobtest

import grails.transaction.Transactional
import groovy.time.TimeCategory

@Transactional
class PersonService {

    def lockUserAccountForOneMin(String email) {
        Person person = Person.findByEmailLike(email)
        person.accountLocked = true
        person.save(flush: true, failOnError: true)
        Date scheduledDate = null
        use( TimeCategory ) {
            scheduledDate = new Date() + 5.seconds
        }

        UnlockAccountJob.schedule(scheduledDate,[username:person.email])
    }

    def unlockUserAccount(String email) {
        Person person = Person.findByEmailLike(email)
        person.accountLocked = false
        person.save(flush: true, failOnError: true)
    }
}
