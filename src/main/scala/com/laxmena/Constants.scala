package com.laxmena

object Constants {
  val APP_NAME = "finagle-client"
  val APP_VERSION = "0.1"
  val APP_AUTHOR = "Lakshmanan Meiyappan"
  val APP_AUTHOR_EMAIL = "lmeiya2@uic.edu"
  val APP_DESCRIPTION = "Finagle HTTP Client to invoke AWS Lambda"

  val FINAGLE_CONFIG = "finagle_config"
  val CONFIG_NOT_FOUND = "Config not found"
  val HOST = "host"
  val PORT = "port"
  val ROUTE = "route"

  val PATTERN = "pattern"

  val CONTENT_TYPE = "Content-type"
  val ACCEPT = "Accept"

  // Alert Messages
  val INVALID_ARGUMENT_MESSAGE = "Invalid arguments. Please provide the following arguments: date(yyyy-MM-dd), time(HH:mm:ss), and window(int)"
  val INVALID_DATE_FORMAT_MESSAGE = "Invalid date format"
  val INVALID_TIME_FORMAT_MESSAGE = "Invalid time format"
  val INVALID_WINDOW_MESSAGE = "Window should be between 1 and 60"
  val NO_LOG_MESSAGE = "No logs found found for given parameters"
  val LOG_FOUND_MESSAGE = "Logs found for given parameters"
}
