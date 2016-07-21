/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.camel;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.websocket.WebsocketComponent;
import org.apache.camel.component.websocket.WebsocketConsumer;
import org.apache.camel.component.websocket.WebsocketEndpoint;
import org.apache.camel.main.Main;
import org.apache.camel.main.MainListenerSupport;
import org.apache.camel.main.MainSupport;

import java.util.Date;

//CHECKSTYLE:OFF

/**
 * A sample Spring Boot application that starts the Camel routes.
 */
public class MainExample {

    private Main main;

    public static void main(String[] args) throws Exception {
        MainExample example = new MainExample();
        example.boot();
    }

    public void boot() throws Exception {

        WebsocketComponent wsocket = new WebsocketComponent();
        wsocket.setThreadPool(1);
        wsocket.setHost("localhost");
        wsocket.setPort(8080);
        // create a Main instance
        main = new Main();
        // bind MyBean into the registry
        main.bind("foo", new MyBean());
        main.bind("wsocket", new WebsocketEndpoint(wsocket, "", "bla", null));
        // add routes
        main.addRouteBuilder(new MyRouteBuilder());

        // add event listener
        main.addMainListener(new Events());

        // run until you terminate the JVM
        System.out.println("Starting Camel. Use ctrl + c to terminate the JVM.\n");
        main.run();
    }

    private static class MyRouteBuilder extends RouteBuilder {
        @Override
        public void configure() throws Exception {
            from("ahc-ws://localhost:8080/") // change this to be a client
                    .log(LoggingLevel.INFO, "Server says: ${body}")
                    .bean(new PITransformBean(),"sendSignal");


            from("websocket://localhost:8080/?threadPool=5")
                    .log(">>> Message received from WebSocket Client : ${body}")
                    .transform().simple("${body}")
                    .to("websocket://localhost:8080/");

            from("timer:hello?period=2000")
                    .transform().constant("Good Bye!")
                    .log(LoggingLevel.INFO, "Body: ${body}")
                    .to("websocket://localhost:8080/?sendToAll=true");
        }
    }

    public static class MyBean {
        public Exchange callMe(Exchange in) {

            in.getIn().setBody("Hello!");
            return in;
        }
    }

    public static class Events extends MainListenerSupport {

        @Override
        public void afterStart(MainSupport main) {
            System.out.println("MainExample with Camel is now started!");
        }

        @Override
        public void beforeStop(MainSupport main) {
            System.out.println("MainExample with Camel is now being stopped!");
        }
    }
}

//CHECKSTYLE:ON
