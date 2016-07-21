package sample.camel;

import com.pi4j.io.gpio.RaspiPin;
import de.pi3g.pi.rcswitch.RCSwitch;
import org.springframework.stereotype.Component;

import java.util.BitSet;

@Component("PiBean")
public class PITransformBean {

    private String message;

    private RCSwitch rcSwitch;
    private static int ON_CODE_A = 1111; //Change this
    private static int OFF_CODE_A = 1111; //Change this
    private static String ADDRESS_A = "11111"; // Change this

    public PITransformBean(String message) {
        this.message = message;
    }

    //Here we transform the message to the appropriate signal ON/OFF
    //If build failed = ON signal
    //If build cleared = OFF signal
    public void sendSignal() throws InterruptedException {
        if (message.contains("BuildFailed")) {
            BitSet address = RCSwitch.getSwitchGroupAddress(ADDRESS_A);
            this.rcSwitch = new RCSwitch(RaspiPin.GPIO_00);
            rcSwitch.switchOn(address, ON_CODE_A);
            Thread.sleep(20000); // let it run for 20 seconds
            rcSwitch.switchOff(address, OFF_CODE_A);

        }

    }
}
