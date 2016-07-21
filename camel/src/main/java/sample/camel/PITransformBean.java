package sample.camel;

import com.pi4j.io.gpio.RaspiPin;
import de.pi3g.pi.rcswitch.RCSwitch;
import org.apache.camel.Exchange;

import java.util.BitSet;

public class PITransformBean {


    private static int ON_CODE_A = 1361; //Change this
    private static int OFF_CODE_A = 1364; //Change this
    private static int ON_CODE_B = 4433; //Change this
    private static int OFF_CODE_B = 4436; //Change this
    private static String ADDRESS_A = "11111"; // Change this
    private String message;
    private RCSwitch rcSwitch;


    public PITransformBean() {
    }

    ;

    //Here we transform the message to the appropriate signal ON/OFF
    //If build failed = ON signal
    //If build cleared = OFF signal
    public void sendSignal(Exchange ex) throws InterruptedException {

        String message = (String) ex.getIn().getBody();
        System.out.println("SEND MESSAGE: " + message);
        if (message.contains("BuildFailed")) {
            BitSet address = RCSwitch.getSwitchGroupAddress(ADDRESS_A);
            this.rcSwitch = new RCSwitch(RaspiPin.GPIO_00);
            rcSwitch.switchOn(address, ON_CODE_A);
            Thread.sleep(20000); // let it run for 20 seconds
            rcSwitch.switchOff(address, OFF_CODE_A);

        }

    }
}
