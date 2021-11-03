package com.laxmena

import com.laxmena.HelperUtil.{CreateLogger, ValidationUtil, ObtainConfigReference}
import com.twitter.finagle.{Http, Service, http}
import com.twitter.util.{Await, Future}

/**
 * FinagleClient
 *
 * Accepts the input from the user and invokes the AWS Lambda Function.
 * LogQueryREST Lambda function is invoked.
 *
 * List of arguments:
 * <li>
 * <ul> args(0) => date: String (YYYY-MM-DD) </ul>
 * <ul> args(1) => time: String (HH:MM:SS) </ul>
 * <ul> args(2) => window: Int (in minutes) </ul>
 * </li>
 *
 * Functionality:
 * - Validates the input arguments
 * - Invokes the LogQueryREST Lambda function
 * - Lambda checks the logs for the given date, time, window and search pattern.
 * - Prints the response
 *
 * How to run:
 * sbt "run-main com.laxmena.FinagleClient [date] [time] [window]"
 *
 * @author Laxmena
 */
class FinagleClient
object FinagleClient {
    // Initialize the logger and obtain the configuration reference
    val logger = CreateLogger(classOf[FinagleClient])
    val config = ObtainConfigReference(Constants.FINAGLE_CONFIG) match {
        case Some(c) => c.getConfig(Constants.FINAGLE_CONFIG)
        case None => throw new Exception(Constants.CONFIG_NOT_FOUND)
    }


    def main(args: Array[String]) {

        // If the number of arguments are not 3, Exit the program
        if(args.length != 3) {
            println(Constants.INVALID_ARGUMENT_MESSAGE)
            System.exit(1)
        }

        // Get the input arguments
        val date = args(0)
        val timeStamp = args(1)
        val window = args(2)
        val pattern = config.getString(Constants.PATTERN)
        logger.info(s"date: $date, time: $timeStamp, window: $window, pattern: $pattern")

        // Load configuration from the config file
        val host = config.getString(Constants.HOST) // "jtf8r2pte5.execute-api.us-east-1.amazonaws.com"
        val port = config.getString(Constants.PORT) // "443"
        val route = config.getString(Constants.ROUTE) // "/default/logQueryREST"
        logger.info(s"host: $host, port: $port")

        // Create HTTP Finagle Client
        val client: Service[http.Request, http.Response] = Http.client
          .withTransport.tls(host)
          .newService(s"$host:$port")

        // Validate Argument Inputs
        if(ValidationUtil.validateDate(date) && ValidationUtil.validateTime(timeStamp) && ValidationUtil.validateWindow(window)) {
            // Construct the request
            // Parameters required for Lambda Function: date, timeStamp, window, pattern
            val param = s"date=$date&timeStamp=$timeStamp&window=$window&pattern=$pattern"
            logger.info(s"Query Params: $param")
            val request: http.Request = http.Request(http.Method.Get, s"$route?$param")

            // Set the headers
            request.host = host
            request.headerMap.set(Constants.CONTENT_TYPE, config.getString(Constants.CONTENT_TYPE))
            request.headerMap.set(Constants.ACCEPT, config.getString(Constants.ACCEPT))

            // Invoke the Lambda Function
            logger.info(s"Invoking Lambda Function: $host$route?$param")
            val response: Future[http.Response] = client(request)

            // Await the response and print the response
            val responseBody = Await.result(response).contentString
            if(responseBody.contains(pattern)) {
                logger.warn(Constants.NO_LOG_MESSAGE)
            } else {
                logger.info(Constants.LOG_FOUND_MESSAGE)
                logger.info(responseBody)
            }
        } else {
            logger.error(Constants.INVALID_ARGUMENT_MESSAGE)
        }
    }

}