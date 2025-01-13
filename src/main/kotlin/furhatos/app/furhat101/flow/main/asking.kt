package furhatos.app.furhat101.flow.main

import furhatos.app.furhat101.flow.Parent
import furhatos.app.furhat101.setting.likeRobots
import furhatos.flow.kotlin.*
import furhatos.nlu.common.DontKnow
import furhatos.nlu.common.Maybe
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes


val Asking: State = state(Parent) {
    onEntry {
        var isFirstUser = true
        for (user in furhat.users.list) {
            furhat.attend(user)
            call(furhat101(isFirstUser))
            isFirstUser = false
        }
        goto(Ending)
    }

}

fun furhat101(firstUser: Boolean): State = state(Parent) {
    onEntry {
        if (firstUser) {
            furhat.ask {
                random {
                    +"Do you like robots?"
                    +"Would you say you enjoy robots?"
                    +"Are you into robots?"
                }
            }
        } else {
            furhat.ask("And what about you? ")
        }
    }
    onReentry {
        when (reentryCount) {
            1 -> furhat.ask("I didn't quite hear you. Do you like robots?")
            2 -> furhat.ask("One more time. Do you like robots? ")
            else -> terminate()
        }
    }
    onResponse<Yes> {
        furhat.say {
            random {
                +"How nice! "
                +"That's nice! "
                +"Lovely! "
            }
        }
        users.current.likeRobots = true
        terminate()
    }
    onResponse<No> {
        furhat.say {
            random {
                +"That's a shame! "
                +"Too bad! "
                +"Bummer! "
            }
        }
        users.current.likeRobots = false
        terminate()
    }
    onResponse<DontKnow> {
        furhat.say("That's ok. ")
        terminate()
    }
    onResponse<Maybe> {
        furhat.say("I'm going to take that as a yes.  ")
        users.current.likeRobots = true
        terminate()
    }
    onNoResponse {
        reentry()
    }
    /** User said something else unexpected **/
    onResponse {
        reentry()
    }
}