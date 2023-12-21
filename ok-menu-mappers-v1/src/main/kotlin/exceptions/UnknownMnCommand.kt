package ru.otuskotlin.test.mappers.v1.exceptions

import ru.otus.otuskotlin.models.MnCommand

class UnknownMnCommand(command: MnCommand) : Throwable("Wrong command $command at mapping toTransport stage")
