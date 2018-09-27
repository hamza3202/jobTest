import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import jobtest.Person
import jobtest.PersonService
import spock.lang.Specification

@Integration
@Rollback
class AccountLockSpec extends Specification {

    PersonService personService

    void 'Job Test'() {
        setup:
        Person person = new Person(email: "testEmail@gmail.com", accountLocked: false)
        person.save(flush: true, failOnError: true)

        when:
        personService.lockUserAccountForOneMin(person.email)
        person.refresh()

        then:
        person.accountLocked

        when: "1 minute has passed"
        Thread.sleep(8000)

        then:
        person.refresh()
        !person.accountLocked
    }
}
