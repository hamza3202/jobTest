import jobtest.PersonService

class UnlockAccountJob {

    PersonService personService

    def execute(context) {
        def userEmail = context.mergedJobDataMap.get('username')
        personService.unlockUserAccount(userEmail)
        log.info("User Account: ${userEmail} has been unlocked")
    }
}