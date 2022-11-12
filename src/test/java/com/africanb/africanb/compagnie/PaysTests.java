package com.africanb.africanb.compagnie;

import com.africanb.africanb.dao.entity.compagnie.Pays;
import com.africanb.africanb.dao.repository.compagnieRepository.PaysRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class PaysTests {

    @Autowired
    private PaysRepository paysRepository;

    @Test
    public void createPays(){
        Pays pays = new Pays();
        pays.setDesignation("Nigeria1000");
        pays.setDescription("Pays de l'afrique de l'ouest");

        Pays rtn=paysRepository.save(pays);

        assertNotNull(rtn);
        assertEquals(pays.getDesignation(),rtn.getDesignation());
    }
}
