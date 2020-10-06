package com.sachin.montyhall.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sachin.montyhall.domain.interfaces.IMontyHallDoorsDomain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MontyHallDomainTest {
    
    @Autowired
    IMontyHallDoorsDomain montyHallDoorsDomain;

    @Test
    public void testChooseAnotherDoor(){
        assertEquals(0, montyHallDoorsDomain.chooseAnotherDoor(1, 2));
    }
    @Test
    public void testopenDoorWithGoat() {
        assertEquals("Goat",
                montyHallDoorsDomain.getRandomlyAssignedDoorList().get(montyHallDoorsDomain.openDoorWithGoat(1)));
    }
}
