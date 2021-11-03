    import com.twitter.finagle.{Http, Service}
    import com.twitter.finagle.http
    import com.twitter.util.{Await, Future}
import com.twitter.io.Buf

class FinagleClient
object FinagleClient {

    def main(args: Array[String]) {

        // Make request to external REST API
        val host = "jtf8r2pte5.execute-api.us-east-1.amazonaws.com"
        val client: Service[http.Request, http.Response] = Http.client
          .withTransport.tls(host)
          .newService(s"$host:443")

        val date = args(0)
        val timeStamp = args(1)
        val window = args(2)
        val pattern = "ERROR"

        val request: http.Request = http.Request(http.Method.Get,
            s"/default/logQueryREST?date=$date&timeStamp=$timeStamp&window=$window&pattern=$pattern")

        request.host = host
        request.headerMap.set("Content-Type", "application/json")
        request.headerMap.set("Accept", "application/json")
        val response: Future[http.Response] = client(request)
        Await.result(response.onSuccess { rep: http.Response => println("GET success: " + rep + " " + rep.contentString) })
    }
}