package furhatos.app.furhat101

import furhatos.app.furhat101.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class Furhat101Skill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}