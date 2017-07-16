package com.interview.homework.server;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.DestinationVariable;

@Controller
public class FibonacciController {

    //prime ArrayList with first 5 values of fibonacci sequence
	private List<Integer> preCalculatedFib = new ArrayList<>(Arrays.asList(0,1,1,2,3));


    @MessageMapping("/findFib/{n}")
    @SendTo("/returnFib")
    public int findNextFib(@DestinationVariable("n") int n) {
        if(n < preCalculatedFib.size()) {
            return preCalculatedFib.get(n);
        }
        for(int i = preCalculatedFib.size() - 1; i <= n; i++) {
            preCalculatedFib.add(preCalculatedFib.get(i - 1) + preCalculatedFib.get(i));
        }
        return preCalculatedFib.get(n);
    }

}