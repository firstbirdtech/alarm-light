/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class WebsocketListener extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("ahc-ws://localhost:8080/") // change this to be a client
                .log(LoggingLevel.INFO, "Server says: ${body}")
                .bean(new PITransformBean(),"sendSignal");


        from("websocket://localhost:8080/")
                .log(">>> Message received from WebSocket Client : ${body}")
                .transform().simple("${body}")
                // send back to the client, by sending the message to the same endpoint
                // this is needed as by default messages is InOnly
                // and we will by default send back to the current client using the provided connection key
                .to("websocket://localhost:8080/");

        from("timer:hello?period={{timer.period}}")
                .transform(method("myBean", "saySomething"))
                .log(LoggingLevel.INFO, "Body: ${body}")
                .to("websocket://localhost:8080/?sendToAll=true");
    }
}
