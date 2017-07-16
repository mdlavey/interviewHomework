package com.interview.homework.server;

import java.lang.InterruptedException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.math.BigInteger;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.DestinationVariable;

@Controller
public class FibonacciController {

    //prime ArrayList with first 5 values of fibonacci sequence
    private List<BigInteger> preCalculatedFib = new ArrayList<>(Arrays.asList(BigInteger.valueOf(0L),BigInteger.valueOf(1L),BigInteger.valueOf(1L),BigInteger.valueOf(2L),BigInteger.valueOf(3L)));


    @MessageMapping("/findFib")
    @SendTo("/returnFib")
    public BigInteger findNextFib(int n) throws InterruptedException {
        if(n < preCalculatedFib.size()) {
            return preCalculatedFib.get(n);
        }
        for(int i = preCalculatedFib.size() - 1; i <= n; i++) {

            preCalculatedFib.add(preCalculatedFib.get(i - 1).add(preCalculatedFib.get(i)));
        }
        return preCalculatedFib.get(n);
    }

}