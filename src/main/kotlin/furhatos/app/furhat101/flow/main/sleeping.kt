package furhatos.app.furhat101.flow.main

import furhat.libraries.standard.GesturesLib
import furhatos.flow.kotlin.*

val Sleeping: State = state {
    onEntry{
        furhat.attendNobody()
        furhat.gesture(GesturesLib.PerformFallAsleepPersist)
    }

    onUserEnter {
        furhat.gesture(GesturesLib.PerformWakeUpWithHeadShake)
        furhat.attend(it) // it = user that entered
        goto(Greeting)
    }
}